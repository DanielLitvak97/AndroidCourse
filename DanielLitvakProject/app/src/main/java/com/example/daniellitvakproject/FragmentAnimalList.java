package com.example.daniellitvakproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FragmentAnimalList extends Fragment {
    private static final String BASE = "https://api.inaturalist.org/";
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private List<FirebaseAnimal> hebrewAnimalList = new ArrayList<>();
    private RecyclerView.Adapter adapter;
    private List<Animal> animalList = new ArrayList<>();
    private String language;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        if(getArguments()!=null){
            language = getArguments().getString("language");
        }
    }

    public FragmentAnimalList() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_animal_list, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        if("Hebrew".equals(language)){
            adapter = new HebrewAnimalAdapter(hebrewAnimalList);
        }
        else{
            adapter = new AnimalAdapter(animalList);
        }

        recyclerView.setAdapter(adapter);
        if("Hebrew".equals(language)){
            fetchHebrewAnimalsFromDB();
        }
        else{
            fetchAnimalsUsingAPI();
        }

        return view;
    }

    private void fetchAnimalsUsingAPI(){
        Retrofit retrofit =
                new Retrofit.Builder()
                        .baseUrl(BASE)
                        .addConverterFactory(
                                GsonConverterFactory.create()
                        )
                        .build();

        AnimalApiService service = retrofit.create(AnimalApiService.class);
        Call<AnimalResponse> call = service.getAnimals("species", "Mammalia", 50);
        call.enqueue(
                new Callback<AnimalResponse>() {
                    @Override
                    public void onResponse(
                            Call<AnimalResponse> call,
                            Response<AnimalResponse> response) {
                        if(response.isSuccessful() && response.body()!=null){
                            animalList.clear();
                            animalList.addAll(response.body().getResults());
                            adapter.notifyDataSetChanged();
                        }
                    }
                    @Override
                    public void onFailure(
                            Call<AnimalResponse> call,
                            Throwable t) {
                    }
                });
    }

    private void fetchHebrewAnimalsFromDB(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("animals");
        myRef.addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot){
                        hebrewAnimalList.clear();
                        for(DataSnapshot data : snapshot.getChildren()){
                            FirebaseAnimal animal = data.getValue(FirebaseAnimal.class);
                            animal.setId(Integer.parseInt(data.getKey()));
                            hebrewAnimalList.add(animal);
                        }

                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(DatabaseError error){
                    }
                });
    }
}