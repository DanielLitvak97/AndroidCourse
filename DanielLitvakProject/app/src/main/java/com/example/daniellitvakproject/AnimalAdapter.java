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

public class AnimalAdapter extends RecyclerView.Adapter<AnimalAdapter.MyViewHolder> {
    private List<Animal> dataset;

    public AnimalAdapter(List<Animal> dataset) {
        this.dataset = dataset;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView image;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textViewName);
            image = itemView.findViewById(R.id.imageView);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardlayout, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull MyViewHolder holder,
            int position) {


        Animal animal = dataset.get(position);

        if(animal.getPreferredCommonName()!=null){
            holder.name.setText(animal.getPreferredCommonName());
        }
        else{
            holder.name.setText(animal.getName());
        }
        // animal image
        Glide.with(holder.itemView.getContext())
                .load(animal.getImageUrl())
                .into(holder.image);

        // click on animal row
        holder.itemView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int currentPosition = holder.getAdapterPosition();

                        if(currentPosition != RecyclerView.NO_POSITION){
                            Animal selectedAnimal = dataset.get(currentPosition);
                            Bundle bundle = new Bundle();
                            String animalName;

                            if(selectedAnimal.getPreferredCommonName()!=null){
                                animalName = selectedAnimal.getPreferredCommonName();
                            }
                            else{
                                animalName = selectedAnimal.getName();
                            }

                            bundle.putString("name", animalName);
                            bundle.putString("description", selectedAnimal.getDescription());
                            bundle.putString("image", selectedAnimal.getImageUrl());
                            bundle.putInt("id", selectedAnimal.getId());
                            NavController navController = Navigation.findNavController(v);
                            navController.navigate(R.id.action_fragmentAnimalList_to_fragmentAnimalInfo, bundle);
                        }
                    }
                });
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }
}