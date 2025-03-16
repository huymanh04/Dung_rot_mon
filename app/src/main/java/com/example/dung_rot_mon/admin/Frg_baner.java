package com.example.dung_rot_mon.admin;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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


import com.example.dung_rot_mon.Adapter.BannerAdapter;
import com.example.dung_rot_mon.R;
import com.example.dung_rot_mon.Sql.DatabaseManager;

import java.util.ArrayList;
import java.util.List;


public class Frg_baner extends Fragment {

    public Frg_baner() {
        // Required empty public constructor
    }
    private RecyclerView bannerRecyclerView;
    private static BannerAdapter bannerAdapter;
    public static List<Banner> bannerList;
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
        bannerAdapter = new BannerAdapter(bannerList,getActivity());
        bannerRecyclerView.setAdapter(bannerAdapter);

        Button addBannerButton = view.findViewById(R.id.addBannerButton);
        addBannerButton.setOnClickListener(v -> {
            // Add a new banner


            // Get sample banners and update adapter



        });
view.findViewById(R.id.addBannerButton).setOnClickListener(v->{
    Intent manh = new Intent(getActivity(), add.class);
    startActivity(manh);

});
        fetchData();
        return view;
    }



    private static DatabaseManager dbManager;

    public static void fetchData() {
        Cursor cursor = dbManager.getAllData(); // Lấy dữ liệu từ cơ sở dữ liệu

        int idcloumname = cursor.getColumnIndex("id");
        int nameColumnIndex = cursor.getColumnIndex("name");
        int imgColumnIndex = cursor.getColumnIndex("img");

        // Kiểm tra nếu các chỉ số cột hợp lệ (lớn hơn hoặc bằng 0)
        if (nameColumnIndex == -1 || imgColumnIndex == -1)
        {
            Log.e("Database", "Column not found!");
            return;
        }

        // Duyệt qua tất cả các bản ghi
        while (cursor.moveToNext()) {
            String name = cursor.getString(nameColumnIndex);
            byte[] img = cursor.getBlob(imgColumnIndex);
            int id= Integer.parseInt(cursor.getString(idcloumname));
            // Thêm vào danh sách itemList
            bannerList.add(new Banner(name, img,"Admin",id));
        }

        cursor.close(); // Đóng con trỏ sau khi sử dụng
        bannerAdapter.notifyDataSetChanged(); // Cập nhật RecyclerView
    }





    // BannerAdapter class (simplified)

    // Banner model class
  public static   class  Banner {
        private String title;
        private byte[] imageUrl;
        private String description;
        private int Id;

        public Banner(String title, byte[] imageUrl, String description, int id) {
            this.title = title;
            this.imageUrl = imageUrl;
            this.description = description;
            Id=id;
        }

        public String getTitle() {
            return title;
        }
        public int getID() {
            return Id;
        }

        String getDescription() {
            return description;
        }

        public byte[]  getImageUrl() {
            return imageUrl;
        }
    }
}