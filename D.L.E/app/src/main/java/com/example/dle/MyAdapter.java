package com.example.dle;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends  RecyclerView.Adapter<MyAdapter.ViewHolder> {
    ArrayList<String> items;

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textview;

        public ViewHolder(View itemView) {
            super(itemView);
            textview = itemView.findViewById(R.id.word);
        }
    }

    public MyAdapter(ArrayList<String> items) {
        this.items= items;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_recycler, viewGroup, false);
        ViewHolder vh = new ViewHolder(v);

        return vh;
    }
    @Override
    public void onBindViewHolder(final @NonNull ViewHolder vh, int i) {
        vh.textview.setText(items.get(i));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
