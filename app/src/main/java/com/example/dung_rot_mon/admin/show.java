package com.example.dung_rot_mon.admin;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dung_rot_mon.Login.Login;
import com.example.dung_rot_mon.MainActivity;
import com.example.dung_rot_mon.R;
import com.example.dung_rot_mon.Sql.DatabaseManager;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class show extends AppCompatActivity {
    private RecyclerView bannerRecyclerView;
    private BannerAdaptera bannerAdapter;
    private List<Banner> bannerList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_show);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        bannerRecyclerView = findViewById(R.id.bannerRecyclerView);
        bannerRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        bannerList = getSampleBanners();
        // Initialize your adapter and set it to RecyclerView
        bannerAdapter = new BannerAdaptera(bannerList);
        bannerRecyclerView.setAdapter(bannerAdapter);

        // Add Banner Button
        findViewById(R.id.addBannerButton).setOnClickListener(v -> addNewBanner());
    }

    private List<Banner> getSampleBanners() {
        List<Banner> banners = new ArrayList<>();
        banners.add(new Banner("Promo 1", "https://example.com/banner1.jpg", "Discount on electronics"));
        banners.add(new Banner("Promo 2", "https://example.com/banner2.jpg", "Summer Sale"));
        return banners;
    }

    // Add a new banner (this would normally launch a new activity or dialog)
    private void addNewBanner() {
        bannerList.add(new Banner("New Promo", "https://example.com/new_banner.jpg", "New Description"));
        bannerAdapter.notifyDataSetChanged();
        // Logic to add a new banner
        Toast.makeText(this, "Add New Banner", Toast.LENGTH_SHORT).show();
    }

    // BannerAdapter class (simplified)
    class BannerAdaptera extends RecyclerView.Adapter<BannerAdaptera.BannerViewHolder> {

        private List<Banner> bannerList;

        BannerAdaptera(List<Banner> bannerList) {
            this.bannerList = bannerList;
        }

        @Override
        public BannerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.banner_item, parent, false);
            return new BannerViewHolder(itemView);
        }
        public boolean deleteRecordById(int id) {
            SQLiteDatabase db = MainActivity.dbHelper.openDatabase();
            // Xác định điều kiện xóa (trong trường hợp này là xóa theo ID)
            String whereClause = "id = ?";
            String[] whereArgs = new String[] { String.valueOf(id) };

            // Thực hiện xóa bản ghi trong bảng
            int rowsAffected = db.delete("baner", whereClause, whereArgs);
            db.close();

            // Trả về true nếu xóa thành công, false nếu không có bản ghi nào bị xóa
            return rowsAffected > 0;
        }
        @Override
        public void onBindViewHolder(BannerViewHolder holder, int position) {
            Banner banner = bannerList.get(position);
            holder.bannerTitle.setText(banner.getTitle());
            holder.bannerDescription.setText(banner.getDescription());

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
            Toast.makeText(show.this, "Edit Banner: " + position, Toast.LENGTH_SHORT).show();
        }

        // Delete banner logic
        private void deleteBanner(int position) {
            bannerList.remove(position);
            notifyItemRemoved(position);
            deleteRecordById(Integer.parseInt(bannerList.get(position).getDescription()));
            // Thông báo cho adapter rằng các item trong khoảng từ vị trí này đã thay đổi
            notifyItemRangeChanged(position, bannerList.size());
            Toast.makeText(show.this, "Deleted Banner: " + position, Toast.LENGTH_SHORT).show();
        }

        class BannerViewHolder extends RecyclerView.ViewHolder {

            TextView bannerTitle, bannerDescription;
            Button editBannerButton, deleteBannerButton;

            BannerViewHolder(View itemView) {
                super(itemView);
                bannerTitle = itemView.findViewById(R.id.bannerTitle);
                bannerDescription = itemView.findViewById(R.id.id);

                deleteBannerButton = itemView.findViewById(R.id.deleteBannerButton);
            }
        }
    }

    // Banner model class
    class Banner {
        private String title;
        private String imageUrl;
        private String description;

        Banner(String title, String imageUrl, String description) {
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

        String getImageUrl() {
            return imageUrl;
        }
    }
}