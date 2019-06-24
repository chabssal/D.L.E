package org.techtown.dle;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends  RecyclerView.Adapter<MyAdapter.ViewHolder> {
    ArrayList<Datas> items;

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textview;

        public ViewHolder(View itemView) {
            super(itemView);
            textview = itemView.findViewById(R.id.word);
        }
    }

    public MyAdapter(ArrayList<Datas> items) {
        this.items= items;
    }


    @NonNull
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_recycler, viewGroup, false);
        MyAdapter.ViewHolder vh = new MyAdapter.ViewHolder(v);

        return vh;
    }
    @Override
    public void onBindViewHolder(final @NonNull MyAdapter.ViewHolder vh, int i) {
        vh.textview.setText("#"+items.get(i).word);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
