package com.example.binggggoooooooooooooooooooooooooo;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Recyclerviewadapter extends RecyclerView.Adapter<Recyclerviewadapter.MViewHolder>{
    Context context;
    List<Items> itemsArraylist;
    boolean[] realArray;
    static ItemClickListener mClickListener;

    public Recyclerviewadapter(Context context, List<Items> items, boolean[] realArray) {
        this.context = context;
        this.itemsArraylist = items;
        this.realArray = realArray;
    }


    @NonNull
    @Override
    public MViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.binggo_box, parent, false);
        return new MViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MViewHolder holder, int position) {
        Items items = itemsArraylist.get(position);
        holder.name.setText(items.name);

        // Update the background color based on the boolean value in the Real array
        if (realArray[position]) {
            holder.itemView.setBackgroundColor(Color.GREEN); // Example color for true
        } else {
            holder.itemView.setBackgroundColor(Color.RED); // Example color for false
        }

        // Set up the click listener
        holder.itemView.setOnClickListener(v -> {
            if (mClickListener != null) {
                mClickListener.onItemClick(v, holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemsArraylist.size();
    }

    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    public static class MViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name;

        public MViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

}
