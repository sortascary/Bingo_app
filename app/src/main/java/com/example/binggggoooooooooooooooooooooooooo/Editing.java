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

public class Editing extends Fragment {

    Recyclerviewadapter adapter;
    public String[] prediction = {"edot", "edut", "edit", "edit", "edit",
            "edit", "edit", "edit", "edit", "edit",
            "edit", "edit", "edit", "edit", "edit",
            "edit", "edit", "edit", "edzt", "edit",
            "edit", "edit", "edit", "edit", "edite"};

    public boolean[] Real = {false, false, false, false, false,
            false, false, false, false, false,
            false, false, false, false, false,
            false, false, false, false, false,
            false, false, false, false, false};

    ArrayList<Items> itemArraylist;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frame_edit, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Retrieve data from SharedPreferences
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("BingoApp", Context.MODE_PRIVATE);

        for (int i = 0; i < 25; i++) {
            String savedPrediction = sharedPreferences.getString("prediction_" + i, null);
            if (savedPrediction != null) {
                prediction[i] = savedPrediction;
            }
        }

        dataInitialized();

        adapter = new Recyclerviewadapter(getContext(), itemArraylist, Real);
        RecyclerView recyclerView = view.findViewById(R.id.binggo_board_edit);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 5);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        adapter.setClickListener(this::onItemClick);
    }


    @Override
    public void onResume() {
        super.onResume();
        // Update the data when the fragment becomes visible again
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

    private void setFragment(Fragment fragment) {
        FragmentManager manager = getParentFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frame, fragment);
        transaction.commit();
    }

    public void onItemClick(View view, int position) {
        Fragment editingFragment = new EditingActivity(this, prediction[position], position);
        setFragment(editingFragment);
    }

    public void notifyItemChanged(int position, String predict) {
        if (adapter != null && position >= 0 && position < itemArraylist.size()) {
            Log.d("Editing", "Updating position: " + position + " with new text: " + predict);
            prediction[position] = predict;
            itemArraylist.set(position, new Items(predict, position));
            adapter.notifyItemChanged(position);
        }
    }

}