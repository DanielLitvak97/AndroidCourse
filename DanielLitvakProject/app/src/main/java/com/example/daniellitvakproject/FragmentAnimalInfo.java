package com.example.daniellitvakproject;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ImageView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import java.util.ArrayList;
import java.util.List;

public class FragmentAnimalInfo extends Fragment {
    private String animalName;
    private List<Comment> commentList = new ArrayList<>();
    private TextView description;
    private CommentAdapter adapter;
    private int animalId;
    private RecyclerView recyclerView;
    private boolean isHebrew;
    private String language;
    private static final String BASE = "https://api.inaturalist.org/";
    public FragmentAnimalInfo(){
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.fragment_animal_info, container, false);
        TextView name = view.findViewById(R.id.textViewAnimalName);
        ImageView image = view.findViewById(R.id.imageViewAnimal);
        description = view.findViewById(R.id.textViewDescription);
        EditText comment = view.findViewById(R.id.editTextComment);
        recyclerView = view.findViewById(R.id.commentsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new CommentAdapter(commentList);
        recyclerView.setAdapter(adapter);
        Button send = view.findViewById(R.id.buttonSendComment);
        Bundle bundle = getArguments();
        isHebrew = bundle.getBoolean("Hebrew", false);
        if(bundle != null){
            language = bundle.getString("language");
            animalName = bundle.getString("name");
            name.setText(animalName);
            animalId = bundle.getInt("id");
            if(isHebrew){
                loadHebrewAnimal(bundle);
            }
            else{
                loadAnimalDescription(animalId);
            }

            loadComments(String.valueOf(animalId));
            Glide.with(requireContext())
                    .load(bundle.getString("image"))
                    .into(image);
        }

        send.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String commentText = comment.getText().toString();

                        if(commentText.isEmpty()){
                            Toast.makeText(requireContext(), "Write a comment first", Toast.LENGTH_SHORT).show();

                            return;
                        }

                        MainActivity activity = (MainActivity)getActivity();
                        activity.sendComment(String.valueOf(animalId), commentText);
        comment.setText("");
    }
});

        return view;
    }

    private void loadComments(String animalId){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("comments").child(animalId);
        ref.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snapshot) {
                commentList.clear();
                for(DataSnapshot data : snapshot.getChildren()){
                    Comment comment = data.getValue(Comment.class);
                    commentList.add(comment);
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error){
            }
        });
    }

    private void loadAnimalDescription(int id){
        if("Hebrew".equals(language)){
            Bundle bundle = getArguments();
            if(bundle != null){
                String text =
                        "ממלכה: "
                                + bundle.getString("kingdom")
                                + "\n\n"
                                + "מערכה: "
                                + bundle.getString("phylum")
                                + "\n\n"
                                + "תת-מערכה: "
                                + bundle.getString("subphylum")
                                + "\n\n"
                                + "מחלקה: "
                                + bundle.getString("className")
                                + "\n\n"
                                + "סדרה: "
                                + bundle.getString("order")
                                + "\n\n"
                                + "משפחה: "
                                + bundle.getString("family");
                description.setText(text);
            }
        }
        else{
            Retrofit retrofit =
                    new Retrofit.Builder()
                            .baseUrl(BASE)
                            .addConverterFactory(
                                    GsonConverterFactory.create()
                            )
                            .build();
            AnimalApiService service = retrofit.create(AnimalApiService.class);
            Call<AnimalResponse> call = service.getAnimalDetails(id);
            call.enqueue(new Callback<AnimalResponse>() {
                @Override
                public void onResponse(
                        Call<AnimalResponse> call,
                        Response<AnimalResponse> response) {
                    if(response.isSuccessful() && response.body()!=null){
                        if(response.body().getResults().isEmpty()){
                            return;
                        }

                        Animal animal = response.body().getResults().get(0);
                        description.setText(animal.getDescription());
                    }
                }

                @Override
                public void onFailure(Call<AnimalResponse> call, Throwable t) {
                    Toast.makeText(getContext(), "Failed to load description", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void loadHebrewAnimal(Bundle bundle){
        String text =
                "ממלכה: " + bundle.getString("kingdom")
                        + "\n\nמערכה: " + bundle.getString("phylum")
                        + "\n\nתת-מערכה: " + bundle.getString("subphylum")
                        + "\n\nמחלקה: " + bundle.getString("className")
                        + "\n\nסדרה: " + bundle.getString("order")
                        + "\n\nמשפחה: " + bundle.getString("family");
        description.setText(text);
    }
}