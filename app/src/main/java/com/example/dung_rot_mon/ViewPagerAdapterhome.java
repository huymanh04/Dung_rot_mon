package com.example.dung_rot_mon;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dung_rot_mon.R;
import com.example.dung_rot_mon.admin.Frg_baner;

import java.util.BitSet;
import java.util.List;

public class ViewPagerAdapterhome extends RecyclerView.Adapter<ViewPagerAdapterhome.ViewHolder> {

    private List<Frg_baner.Banner> bannerList;
    public ViewPagerAdapterhome(List<Frg_baner.Banner> bannerList) {
        this.bannerList = bannerList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_viewpager, parent, false);

        // Đảm bảo item có match_parent
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
        view.setLayoutParams(layoutParams);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Lấy Bitmap từ danh sách imageList và đặt nó vào ImageView
        Frg_baner.Banner banner = bannerList.get(position);
        Bitmap bitmap = BitmapFactory.decodeByteArray(banner.getImageUrl(), 0, banner.getImageUrl().length);
        holder.imageView.setImageBitmap(bitmap);
    }


    @Override
    public int getItemCount() {
        return bannerList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}