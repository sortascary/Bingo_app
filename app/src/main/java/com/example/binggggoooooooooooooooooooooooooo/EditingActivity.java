package com.example.binggggoooooooooooooooooooooooooo;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class EditingActivity extends Fragment {

    String text;
    int numId;
    Button change;
    EditText textedit;
    Editing editing;

    public EditingActivity(Editing editing, String name, int num) {
        this.editing = editing;
        this.text = name;
        this.numId = num;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.editing_activity, container, false);
    }

    private void setFragment(Fragment fragment) {
        FragmentManager manager = getParentFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frame, fragment);
        transaction.commit();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        change = view.findViewById(R.id.change);
        textedit = view.findViewById(R.id.editingtext);
        textedit.setText(text);

        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newText = textedit.getText().toString();

                // Store the updated value in SharedPreferences
                SharedPreferences sharedPreferences = getContext().getSharedPreferences("BingoApp", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("prediction_" + numId, newText);
                editor.apply();

                // Create a new instance of Editing fragment to navigate back
                Editing newEditingFragment = new Editing();
                setFragment(newEditingFragment);
            }
        });
    }
}
