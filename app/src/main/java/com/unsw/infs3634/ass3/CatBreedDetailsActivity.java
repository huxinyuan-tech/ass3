package com.unsw.infs3634.ass3;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class CatBreedDetailsActivity extends AppCompatActivity {

    private CatBreed cb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cat_breed_details);

        setAddToFavListener((Button) findViewById(R.id.addToFavButton));

        cb = (CatBreed) getIntent().getSerializableExtra("catBreed");
        getTextViewById(R.id.breedName).setText(cb.getName());
        getTextViewById(R.id.description).setText(cb.getDescription());
        getTextViewById(R.id.origin).setText(cb.getOrigin());
        getTextViewById(R.id.temperament).setText(cb.getTemperament());
        getTextViewById(R.id.lifeSpan).setText(cb.getLifeSpan());
        getTextViewById(R.id.friendlinessToDog).setText(cb.getDogFriendlinessLevel());
        getTextViewById(R.id.wikiLink).setText(cb.getWikiLink());
        getTextViewById(R.id.imperial).setText(cb.getWeight().getImperial());
        getTextViewById(R.id.metric).setText(cb.getWeight().getMetric());
    }

    private void setAddToFavListener(Button addToFavButton) {
        addToFavButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!DataStore.starredBreedNames.contains(cb.getName())) {
                    DataStore.starredBreedNames.add(cb.getName());
                    DataStore.starredCatBreeds.add(cb);
                }
            }
        });
    }

    private TextView getTextViewById(int id) {
        return (TextView) findViewById(id);
    }
}
