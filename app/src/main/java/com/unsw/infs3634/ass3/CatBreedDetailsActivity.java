package com.unsw.infs3634.ass3;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class CatBreedDetailsActivity extends AppCompatActivity {

    private CatBreed cb;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cat_breed_details);

        setAddToFavListener((Button) findViewById(R.id.addToFavButton));

        requestQueue = Volley.newRequestQueue(getApplicationContext());

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

        StringRequest request = new StringRequest(Request.Method.GET,
                "https://api.thecatapi.com/v1/images/search?breed_id=" + cb.getId(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(final String response) {
                        List<CatImage> catImages = convertJsonToCatImageList(response);
                        new DownloadImageTask((ImageView) findViewById(R.id.img))
                                .execute(catImages.get(0).getUrl());
//                        Thread t = new Thread(new Runnable() {
//                            @Override
//                            public void run() {
//                                List<CatImage> catImages = convertJsonToCatImageList(response);
//                                try {
//                                    InputStream in = new java.net.URL(catImages.get(0).getUrl()).openStream();
//                                    Bitmap bitmap = BitmapFactory.decodeStream(in);
//                                    ((ImageView) findViewById(R.id.img)).setImageBitmap(bitmap);
//                                } catch (Exception e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                        });
//
//                        t.start();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        requestQueue.add(request);
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

    private List<CatImage> convertJsonToCatImageList(String json) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            CatImage[] catImages = objectMapper.readValue(json, CatImage[].class);
            return Arrays.asList(catImages);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    private TextView getTextViewById(int id) {
        return (TextView) findViewById(id);
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
