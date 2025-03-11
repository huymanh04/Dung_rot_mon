package com.example.dung_rot_mon.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dung_rot_mon.R;

import java.util.List;

public class diadiemnoibat extends RecyclerView.Adapter<diadiemnoibat.ViewHolder> {

    private List<ItemModel> itemList;

    public diadiemnoibat(List<ItemModel> itemList) {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recycler_dia_diem_noi_bat, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ItemModel item = itemList.get(position);
        holder.imageView.setImageResource(item.getImageResId());
        holder.textViewTitle.setText(item.getTitle());
        holder.textViewCount.setText(item.getCount());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textViewTitle, textViewCount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewCount = itemView.findViewById(R.id.textViewCount);
        }
    }
}
