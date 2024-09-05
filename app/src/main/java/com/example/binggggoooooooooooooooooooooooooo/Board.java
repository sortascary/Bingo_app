package com.example.binggggoooooooooooooooooooooooooo;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Board extends Fragment {
    private String[] prediction = new String[25];
    Recyclerviewadapter adapter;
    ArrayList<Items> itemArraylist;

    public boolean[] Real = {false, false, false, false, false,
            false, false, false, false, false,
            false, false, false, false, false,
            false, false, false, false, false,
            false, false, false, false, false};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frame_board, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("BingoApp", Context.MODE_PRIVATE);

        for (int i = 0; i < 25; i++) {
            String savedPrediction = sharedPreferences.getString("prediction_" + i, null);
            boolean savedbool = sharedPreferences.getBoolean("Real_" + i, false);
            if (savedPrediction != null) {
                prediction[i] = savedPrediction;
            }
            Real[i] = savedbool;
        }

        dataInitialized();

        adapter = new Recyclerviewadapter(getContext(), itemArraylist, Real);
        RecyclerView recyclerView = view.findViewById(R.id.binggo_board);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 5);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        // Handle item click
        adapter.setClickListener((v, position) -> {
            // Toggle the boolean value
            Real[position] = !Real[position];

            // Save the updated value in SharedPreferences
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("Real_" + position, Real[position]);
            editor.apply();

            // Notify adapter to update the UI
            adapter.notifyItemChanged(position);
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        dataInitialized();
        adapter.notifyDataSetChanged();
    }

    private void dataInitialized() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            String[] updatedPrediction = bundle.getStringArray("updated_predictions");
            if (updatedPrediction != null) {
                prediction = updatedPrediction;
            }
        }

        itemArraylist = new ArrayList<>();
        for (int i = 0; i < 25; i++) {
            itemArraylist.add(new Items(prediction[i], i));
        }
    }
}