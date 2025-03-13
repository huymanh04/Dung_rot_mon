package com.example.dung_rot_mon.admin;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.dung_rot_mon.R;

import java.util.List;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {

    private Context context;
    private List<Item> itemList;

    public DataAdapter(Context context, List<Item> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_banner, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Item item = itemList.get(position);
        holder.nameTextView.setText(item.getName());
        var ffff=item.getImg();
        var dfdf =item.getImg().length;
        // Chuyển đổi mảng byte (BLOB) thành hình ảnh Bitmap

            Bitmap bitmap = BitmapFactory.decodeByteArray(item.getImg(), 0, item.getImg().length);
            if (bitmap != null) {
               holder.imageView.setImageBitmap(bitmap); // Hiển thị hình ảnh lên ImageView
            } else {
                Log.e("Bitmap Error", "Failed to decode byte array into Bitmap.");
            }


    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);
            nameTextView = itemView.findViewById(R.id.textViewName);
        }
    }
}