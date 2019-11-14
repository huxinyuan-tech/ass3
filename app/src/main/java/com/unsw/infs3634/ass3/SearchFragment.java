package com.unsw.infs3634.ass3;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SearchFragment extends Fragment {

    private RecyclerView recyclerView;
    private RequestQueue requestQueue;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.search_fragment, container, false);

        requestQueue = Volley.newRequestQueue(view.getContext());

        EditText searchText = view.findViewById(R.id.catBreedSearchText);
        addSearchTextListener(searchText);

        recyclerView = view.findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(
                new DividerItemDecoration(view.getContext(), LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(new CatBreedRowAdapter(DataStore.catBreeds));

        return view;
    }

    private void addSearchTextListener(EditText searchText) {
        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                StringRequest request = new StringRequest(Request.Method.GET,
                        "https://api.thecatapi.com/v1/breeds/search?q=" + editable.toString(),
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                List<CatBreed> catBreeds = convertJsonToCatBreedList(response);
                                if (catBreeds != null) {
                                    DataStore.catBreeds.clear();
                                    DataStore.catBreeds.addAll(catBreeds);

                                    if (recyclerView.getAdapter() != null) {
                                        recyclerView.getAdapter().notifyDataSetChanged();
                                    }
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        });

                requestQueue.add(request);
            }
        });
    }

    private List<CatBreed> convertJsonToCatBreedList(String json) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            CatBreed[] catBreedArray = objectMapper.readValue(json, CatBreed[].class);
            return Arrays.asList(catBreedArray);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

}
