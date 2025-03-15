package com.example.dung_rot_mon.admin;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dung_rot_mon.R;
import com.example.dung_rot_mon.Sql.DatabaseManager;

import java.util.ArrayList;
import java.util.List;


public class Frg_baner extends Fragment {

    public Frg_baner() {
        // Required empty public constructor
    }

    private RecyclerView bannerRecyclerView;
    private BannerAdapter bannerAdapter;
    public List<Banner> bannerList;
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_frg_baner, container, false);

        // Set RecyclerView and LinearLayoutManager
        bannerRecyclerView = view.findViewById(R.id.bannerRecyclerView);
        bannerRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        dbManager = new DatabaseManager(getContext());
        bannerList =new ArrayList<>();
        bannerAdapter = new BannerAdapter(bannerList);
        bannerRecyclerView.setAdapter(bannerAdapter);

        Button addBannerButton = view.findViewById(R.id.addBannerButton);
        addBannerButton.setOnClickListener(v -> {
            // Add a new banner


            // Get sample banners and update adapter



        });

        fetchData();
        return view;
    }

    private DataAdapter adapter;
    private DatabaseManager dbManager;
    private void fetchData() {
        Cursor cursor = dbManager.getAllData(); // Lấy dữ liệu từ cơ sở dữ liệu
        int nameColumnIndex = cursor.getColumnIndex("name");
        int imgColumnIndex = cursor.getColumnIndex("img");

        // Kiểm tra nếu các chỉ số cột hợp lệ (lớn hơn hoặc bằng 0)
        if (nameColumnIndex == -1 || imgColumnIndex == -1) {
            Log.e("Database", "Column not found!");
            return;
        }

        // Duyệt qua tất cả các bản ghi
        while (cursor.moveToNext()) {
            String name = cursor.getString(nameColumnIndex);
            byte[] img = cursor.getBlob(imgColumnIndex);

            // Thêm vào danh sách itemList
            bannerList.add(new Banner(name, img,"Admin"));
        }

        cursor.close(); // Đóng con trỏ sau khi sử dụng
        bannerAdapter.notifyDataSetChanged(); // Cập nhật RecyclerView
    }

    // Add a new banner (this would normally launch a new activity or dialog)


    // BannerAdapter class (simplified)
    class BannerAdapter extends RecyclerView.Adapter<BannerAdapter.BannerViewHolder> {

        private List<Banner> bannerList;

        BannerAdapter(List<Banner> bannerList) {
            this.bannerList = bannerList;
        }

        @Override
        public BannerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.banner_item, parent, false);
            return new BannerViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(BannerViewHolder holder, int position) {
            Banner banner = bannerList.get(position);
            holder.bannerTitle.setText(banner.getTitle());
            holder.bannerDescription.setText(banner.getDescription());
            var ffff=banner.getImageUrl();
            var dfdf =banner.getImageUrl().length;
            // Chuyển đổi mảng byte (BLOB) thành hình ảnh Bitmap

            Bitmap bitmap = BitmapFactory.decodeByteArray(banner.getImageUrl(), 0, banner.getImageUrl().length);
            holder.bannerImage.setImageBitmap(bitmap);

            // Handle Edit and Delete clicks
            holder.editBannerButton.setOnClickListener(v -> editBanner(position));
            holder.deleteBannerButton.setOnClickListener(v -> deleteBanner(position));
        }

        @Override
        public int getItemCount() {
            return bannerList.size();
        }

        // Edit banner logic
        private void editBanner(int position) {
            // Edit banner logic
            Toast.makeText(getContext(), "Edit Banner: " + position, Toast.LENGTH_SHORT).show();
        }

        // Delete banner logic
        private void deleteBanner(int position) {
            bannerList.remove(position);
            notifyItemRemoved(position);
            Toast.makeText(getContext(), "Deleted Banner: " + position, Toast.LENGTH_SHORT).show();
        }

        class BannerViewHolder extends RecyclerView.ViewHolder {

            TextView bannerTitle, bannerDescription;
            Button editBannerButton, deleteBannerButton;
ImageView bannerImage;
            BannerViewHolder(View itemView) {
                super(itemView);
                bannerTitle = itemView.findViewById(R.id.bannerTitle);
                bannerImage = itemView.findViewById(R.id.bannerImage);
                bannerDescription = itemView.findViewById(R.id.bannerDescription);
                editBannerButton = itemView.findViewById(R.id.editBannerButton);
                deleteBannerButton = itemView.findViewById(R.id.deleteBannerButton);
            }
        }
    }

    // Banner model class
    class Banner {
        private String title;
        private byte[] imageUrl;
        private String description;

        Banner(String title, byte[] imageUrl, String description) {
            this.title = title;
            this.imageUrl = imageUrl;
            this.description = description;
        }

        String getTitle() {
            return title;
        }

        String getDescription() {
            return description;
        }

        byte[]  getImageUrl() {
            return imageUrl;
        }
    }
}