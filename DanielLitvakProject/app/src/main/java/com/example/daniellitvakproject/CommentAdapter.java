package com.example.daniellitvakproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.MyViewHolder>{
    private List<Comment> dataset;

    public CommentAdapter(List<Comment> dataset){
        this.dataset = dataset;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView email;
        TextView text;

        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            email = itemView.findViewById(R.id.textViewEmail);
            text = itemView.findViewById(R.id.textViewComment);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_card, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position){
        Comment comment = dataset.get(position);
        holder.email.setText(comment.getEmail());
        holder.text.setText(comment.getText());
    }

    @Override
    public int getItemCount(){
        return dataset.size();
    }
}
