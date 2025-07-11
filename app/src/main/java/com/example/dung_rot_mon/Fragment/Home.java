package com.example.dung_rot_mon.Fragment;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dung_rot_mon.Adapter.BannerAdapter;
import com.example.dung_rot_mon.Adapter.Viewtab;
import com.example.dung_rot_mon.Adapter.ItemModel;
import com.example.dung_rot_mon.Adapter.diadiemnoibat;
import com.example.dung_rot_mon.Adapter.sanbayadapter;
import com.example.dung_rot_mon.R;
import com.example.dung_rot_mon.Sql.DatabaseHelper;
import com.example.dung_rot_mon.Sql.DatabaseManager;
import com.example.dung_rot_mon.ViewPagerAdapterhome;
import com.example.dung_rot_mon.admin.Frg_baner;
import com.example.dung_rot_mon.dialog.BaoHiemDialogFragment;
import com.example.dung_rot_mon.dialog.GioiThieuDialogFragment;
import com.example.dung_rot_mon.dialog.UuDiemDialogFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;

public class Home extends Fragment {

   public Home(String emnail){
        mail=emnail;
    }
    String mail;
    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private ViewGroup.LayoutParams layoutParams;
    private Viewtab adapter;
    private ViewPager2 viewPager1;
    private ImageView imageView;
    private static ViewPagerAdapterhome adapter1;
    private static DatabaseManager dbManager;
    public static List<Frg_baner.Banner> bannerList;
    private Calendar selectedDateTime;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
      imageView=  view.findViewById(R.id.imageViewProfile);
        db= new DatabaseHelper(getActivity());
        if (mail!=""&&mail!=null){
            readData(mail);
            if (imga != null) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(imga, 0, imga.length);
                if (bitmap != null) {
                    imageView.setImageBitmap(bitmap);
                }
            }
            TextView na = view.findViewById(R.id.textViewName);
            na.setText(Name);
        }else { TextView na = view.findViewById(R.id.textViewName);
            na.setText("Vui lòng đăng nập để sử dụng dịch vụ");}

/// tìm chuyến
        {
            tabLayout = view.findViewById(R.id.tab_layout);
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
            View firstTab = ((ViewGroup) tabLayout.getChildAt(0)).getChildAt(0);
            if (firstTab != null) {
                firstTab.setBackgroundColor(getResources().getColor(R.color.teal_200));
            }
            tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

                @Override
                public void onTabSelected(TabLayout.Tab tab) {



                    tab.view.setBackgroundColor(getResources().getColor(R.color.teal_200)); // Màu khi chọn
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

        // Click vào CardView
//        CardView cardView = view.findViewById(R.id.imageView1);
//        cardView.setOnClickListener(v -> {
//            Log.d("ClickTest", "CardView được click!");
//            Toast.makeText(getContext(), "CardView Clicked!", Toast.LENGTH_SHORT).show();
//        });



        ImageView bannerImage = view.findViewById(R.id.imgClickable);
        bannerImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("DEBUG", "ImageView được click!");

                BaoHiemDialogFragment dialogFragment = new BaoHiemDialogFragment();
                dialogFragment.show(requireActivity().getSupportFragmentManager(), "BaoHiemDialog");
            }
        });
        CardView cardViewUuDiem = view.findViewById(R.id.cardViewUuDiem);

        // Gán sự kiện click
        cardViewUuDiem.setOnClickListener(v -> {
            // Hiển thị DialogFragment
            UuDiemDialogFragment dialog = new UuDiemDialogFragment();
            dialog.show(getParentFragmentManager(), "UuDiemDialog");
        });
        CardView cardViewGioiThieu = view.findViewById(R.id.cardViewGioiThieu);

        // Bắt sự kiện click vào CardView
        cardViewGioiThieu.setOnClickListener(v -> {
            // Hiển thị GioiThieuDialogFragment
            GioiThieuDialogFragment gioiThieuDialogFragment = new GioiThieuDialogFragment();
            gioiThieuDialogFragment.show(getParentFragmentManager(), "GioiThieuDialogFragment");
        });



//        ImageView imageView = view.findViewById(R.id.imageView);
//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                bao_hiem_dialog dialog = new bao_hiem_dialog();
//                Log.d("ImageClick", "Hình ảnh đã được nhấn!");
//                dialog.show(getParentFragmentManager(), "bao_hiem_dialog");
//                Toast.makeText(getContext(), "Image clicked!", Toast.LENGTH_SHORT).show();
//            }
//        });
//        CardView cardView = view.findViewById(R.id.imageView1);
//        cardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getContext(), "CardView Clicked!", Toast.LENGTH_SHORT).show();
//                Log.d("ClickTest", "CardView được click!");
//            }
//        });

        // chương trình khuyến maix
        {

            viewPager1 = view.findViewById(R.id.viewppppp);
            dbManager = new DatabaseManager(getContext());
            bannerList = new ArrayList<>();
            adapter1 = new ViewPagerAdapterhome(bannerList);
            viewPager1.setAdapter(adapter1);
//            viewPager1.setUserInputEnabled(false);
            viewPager1.setUserInputEnabled(true);
            // Tạo khoảng cách giữa các item
            viewPager1.setOffscreenPageLimit(3);
            RecyclerView recyclerView = (RecyclerView) viewPager1.getChildAt(0);
            recyclerView.setClipToPadding(false);
            recyclerView.setClipChildren(false);
            recyclerView.setPadding(50, 0, 50, 0);
            fetchData();
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
            itemList.add(new ItemModel(R.drawable.tansonnhat, "Tân Sơn Nhất", "1000+ xe"));
            itemList.add(new ItemModel(R.drawable.noibai, "Nội Bài", "200+ xe"));
            itemList.add(new ItemModel(R.drawable.danangsb, "Đà Nẵng", "100+ xe"));
            itemList.add(new ItemModel(R.drawable.bmt, "Đắk Lắk", "50+ xe"));
            itemList.add(new ItemModel(R.drawable.phuyensb, "Phú Yên", "50+ xe"));
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
            itemList.add(new ItemModel(R.drawable.images, "Sài Gòn", "150+ xe"));
            itemList.add(new ItemModel(R.drawable.danang, "Đà Nẵng", "100+ xe"));
            itemList.add(new ItemModel(R.drawable.phuyen, "Phú Yên", "100+ xe"));
            itemList.add(new ItemModel(R.drawable.hanoi, "Hà Nội", "100+ xe"));
            itemList.add(new ItemModel(R.drawable.dongnai, "Đồng Nai", "100+ xe"));
            itemList.add(new ItemModel(R.drawable.hue, "Huế", "100+ xe"));
            itemList.add(new ItemModel(R.drawable.vungtau, "Vũng Tàu", "100+ xe"));
            adapter = new diadiemnoibat(itemList);
            recyclerView.setAdapter(adapter);
        }

        // Xử lý click cho ngày thuê và giờ thuê


        return view;
    }
    private static BannerAdapter bannerAdapter;
    private void adjustTabLayoutHeight(int position) {
        if (position == 0) {
            layoutParams.height = dpToPx(340);
        } else {
            layoutParams.height = dpToPx(580);
        }
        viewPager.setLayoutParams(layoutParams);
    }
    public void onCardClick(View view) {
        Toast.makeText(getContext(), "CardView Clicked!", Toast.LENGTH_SHORT).show();
    }

    private int dpToPx(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }    static String Name;
    static String Email;
    static byte[] imga;
    public static void readData(String email) {
        SQLiteDatabase dba = db.openDatabase();
        Cursor cursor = dba.rawQuery("SELECT * FROM account WHERE email = ?", new String[]{email});

        // Initialize default values
        Name = "";
        Email = "";
        imga = null;

        try {
            // Get column indices
            int idColumnIndex = cursor.getColumnIndex("id");
            int emailColumnIndex = cursor.getColumnIndex("email");
            int nameColumnIndex = cursor.getColumnIndex("name");
            int imgColumnIndex = cursor.getColumnIndex("image");
            int ngaythamgiaColumnIndex = cursor.getColumnIndex("ngaythamgia");

            // Check if columns exist
            if (idColumnIndex == -1 || emailColumnIndex == -1 || nameColumnIndex == -1 || imgColumnIndex == -1) {
                Log.e("Database", "Column not found!");
                return;
            }

            // Check if cursor has data
            if (cursor.moveToFirst()) {
                Email = cursor.getString(emailColumnIndex);
                Name = cursor.getString(nameColumnIndex);
                // Add null check for image blob
                byte[] imageData = cursor.getBlob(imgColumnIndex);
                if (imageData != null && imageData.length > 0) {
                    imga = imageData;
                }
            }
        } catch (Exception e) {
            Log.e("Database", "Error reading data: " + e.getMessage());
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
    }
    static DatabaseHelper db;
    public static void fetchData() {
     // Lấy dữ liệu từ cơ sở dữ liệu
        SQLiteDatabase dba = db.openDatabase();
        Cursor cursor = dba.rawQuery("SELECT * FROM baner", null);

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
            bannerList.add(new Frg_baner.Banner(name, img,"Admin",id));
        }

        cursor.close(); // Đóng con trỏ sau khi sử dụng
        adapter1.notifyDataSetChanged(); // Cập nhật RecyclerView
    }

    // Hàm chuyển Base64 thành Bitmap
}