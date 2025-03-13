package com.example.dung_rot_mon.Fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Base64;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dung_rot_mon.Adapter.Viewtab;
import com.example.dung_rot_mon.Adapter.ItemModel;
import com.example.dung_rot_mon.Adapter.diadiemnoibat;
import com.example.dung_rot_mon.Adapter.sanbayadapter;
import com.example.dung_rot_mon.R;
import com.example.dung_rot_mon.ViewPagerAdapterhome;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class Home extends Fragment {

    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private ViewGroup.LayoutParams layoutParams;
    private Viewtab adapter;
    private ViewPager2 viewPager1;
    private ViewPagerAdapterhome adapter1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
/// tìm chuyến
        {  tabLayout = view.findViewById(R.id.tab_layout);
        viewPager = view.findViewById(R.id.view_page);

        // Khởi tạo Adapter
        adapter = new Viewtab(this);

        viewPager.setAdapter(adapter);

        // Liên kết TabLayout với ViewPager2
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("Xe tự lái");

                    break;
                case 1:
                    tab.setText("Xe có tài xế");
                    break;
            }
        }).attach();
        layoutParams = viewPager.getLayoutParams();

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.view.setBackgroundColor(getResources().getColor(R.color.selected_tab)); // Màu khi chọn
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.view.setBackgroundColor(getResources().getColor(R.color.unselected_tab)); // Màu khi bỏ chọn
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // Không cần xử lý
            }
        });
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                adjustTabLayoutHeight(position);
            }
        });
        }
        // chương trình khuyến maix
        {

            viewPager1 = view.findViewById(R.id.viewppppp);
            firestore = FirebaseFirestore.getInstance();
            List<Bitmap> imageList = new ArrayList<>();
            getItemsFromFirestore(imageList);


            adapter1 = new ViewPagerAdapterhome(imageList);
            viewPager1.setAdapter(adapter1);

            // Tạo khoảng cách giữa các item
            viewPager1.setOffscreenPageLimit(3);
            RecyclerView recyclerView = (RecyclerView) viewPager1.getChildAt(0);
            recyclerView.setClipToPadding(false);
            recyclerView.setClipChildren(false);
            recyclerView.setPadding(50, 0, 50, 0);
        }
        // sân bay
        {
            RecyclerView recyclerView;
            sanbayadapter adapter;
            List<ItemModel> itemList;
            recyclerView = view.findViewById(R.id.recyclerView);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
            recyclerView.setLayoutManager(layoutManager);

            // Khởi tạo danh sách dữ liệu
            itemList = new ArrayList<>();
            itemList.add(new ItemModel(R.drawable.img_1, "Tân Sơn Nhất", "1000+ xe"));
            itemList.add(new ItemModel(R.drawable.img_2, "Nội Bài", "200+ xe"));
            itemList.add(new ItemModel(R.drawable.img_3, "Đà Nẵng", "100+ xe"));
            itemList.add(new ItemModel(R.drawable.img_4, "Cam Ranh", "50+ xe"));
            itemList.add(new ItemModel(R.drawable.img_4, "Cam Ranh", "50+ xe"));
            itemList.add(new ItemModel(R.drawable.img_4, "Cam Ranh", "50+ xe"));
            itemList.add(new ItemModel(R.drawable.img_4, "Cam Ranh", "50+ xe"));
            itemList.add(new ItemModel(R.drawable.img_4, "Cam Ranh", "50+ xe"));
            itemList.add(new ItemModel(R.drawable.img_4, "Cam Ranh", "50+ xe"));
            itemList.add(new ItemModel(R.drawable.img_4, "Cam Ranh", "50+ xe"));

            adapter = new sanbayadapter(itemList);
            recyclerView.setAdapter(adapter);
        }
        // địa đnooinonoi bat
        {
            RecyclerView recyclerView;
            diadiemnoibat adapter;
            List<ItemModel> itemList;
            recyclerView = view.findViewById(R.id.recyclerView1);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
            recyclerView.setLayoutManager(layoutManager);

            // Khởi tạo danh sách dữ liệu
            itemList = new ArrayList<>();
            itemList.add(new ItemModel(R.drawable.img_1, "Phú Quốc", "150+ xe"));
            itemList.add(new ItemModel(R.drawable.img_2, "Long An", "100+ xe"));
            itemList.add(new ItemModel(R.drawable.img_3, "Long An", "100+ xe"));
            itemList.add(new ItemModel(R.drawable.img_2, "Long An", "100+ xe"));
            adapter = new diadiemnoibat(itemList);
            recyclerView.setAdapter(adapter);
        }
        return  view;
    } private void adjustTabLayoutHeight(int position) {
        if (position == 0) {
            layoutParams.height = dpToPx(280);
        } else {
            layoutParams.height = dpToPx(360);
        }
        viewPager.setLayoutParams(layoutParams);
    }
    private int dpToPx(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    } private FirebaseFirestore firestore;
    private void getItemsFromFirestore( List<Bitmap> imageList) {
        firestore.collection("baner_khuyen_mai")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot querySnapshot = task.getResult();
                        if (querySnapshot != null && !querySnapshot.isEmpty()) {
                            for (QueryDocumentSnapshot document : querySnapshot) {
                                String name = document.getString("name");
                                String base64String = document.getString("path");

                                // Chuyển Base64 thành Bitmap
                                Bitmap bitmap = base64ToBitmap(base64String);
                                imageList.add(bitmap);
                                // Thêm dữ liệu vào itemList

                            }

                        }
                    } else {

                    }
                });
    }

    // Hàm chuyển Base64 thành Bitmap
    public static Bitmap base64ToBitmap(String base64String) {
        try {
            byte[] decodedString = Base64.decode(base64String, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}