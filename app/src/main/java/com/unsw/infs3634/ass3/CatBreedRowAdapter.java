package com.unsw.infs3634.ass3;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CatBreedRowAdapter extends RecyclerView.Adapter {

    private List<CatBreed> catBreeds;

    public CatBreedRowAdapter(List<CatBreed> catBreeds) {
        this.catBreeds = catBreeds;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.cat_breed_row, viewGroup, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, int i) {
        final CatBreed cb = catBreeds.get(i);
        MyViewHolder myViewHolder = (MyViewHolder) viewHolder;
        myViewHolder.name.setText(cb.getName());
        myViewHolder.name.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), CatBreedDetailsActivity.class);
                i.putExtra("catBreed", cb);
                view.getContext().startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return catBreeds.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView name;

        MyViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.catBreedName);
        }
    }
}
