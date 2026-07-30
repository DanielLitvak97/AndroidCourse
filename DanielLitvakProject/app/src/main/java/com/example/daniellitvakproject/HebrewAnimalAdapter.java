package com.example.daniellitvakproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.List;

public class HebrewAnimalAdapter extends RecyclerView.Adapter<HebrewAnimalAdapter.MyViewHolder>{
    private List<FirebaseAnimal> dataset;

    public HebrewAnimalAdapter(List<FirebaseAnimal> dataset){

        this.dataset = dataset;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        ImageView image;

        public MyViewHolder(View itemView){
            super(itemView);
            name = itemView.findViewById(R.id.textViewName);
            image = itemView.findViewById(R.id.imageView);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType){
        View view = LayoutInflater.from(parent.getContext())
                        .inflate(
                                R.layout.cardlayout,
                                parent,
                                false
                        );

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull MyViewHolder holder,
            int position){
        FirebaseAnimal animal = dataset.get(position);
        holder.name.setText(animal.getName());
        Glide.with(holder.itemView.getContext())
                .load(animal.getImage())
                .into(holder.image);

        holder.itemView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int currentPosition = holder.getAdapterPosition();

                        if(currentPosition != RecyclerView.NO_POSITION){
                            FirebaseAnimal selectedAnimal = dataset.get(currentPosition);
                            Bundle bundle = new Bundle();
                            bundle.putInt("id", selectedAnimal.getId());
                            bundle.putString("name", selectedAnimal.getName());
                            bundle.putString("kingdom", selectedAnimal.getKingdom());
                            bundle.putString("phylum", selectedAnimal.getPhylum());
                            bundle.putString("subphylum", selectedAnimal.getSubphylum());
                            bundle.putString("className", selectedAnimal.getClassName());
                            bundle.putString("order", selectedAnimal.getOrder());
                            bundle.putString("family", selectedAnimal.getFamily());
                            bundle.putString("image", selectedAnimal.getImage());
                            NavController navController = Navigation.findNavController(v);
                            bundle.putBoolean("Hebrew", true);
                            navController.navigate(R.id.action_fragmentAnimalList_to_fragmentAnimalInfo, bundle);
                        }
                    }
                });
    }

    @Override
    public int getItemCount(){

        return dataset.size();
    }
}
