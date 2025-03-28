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

import android.util.Base64;
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
import com.example.dung_rot_mon.dialog.bao_hiem_dialog;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

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
      imageView=  view.findViewById(R.id.imageView8);
        db= new DatabaseHelper(getActivity());
        if (mail!=""&&mail!=null){
            readData(mail);
            if (imga != null) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(imga, 0, imga.length);
                if (bitmap != null) {
                    imageView.setImageBitmap(bitmap);
                }
            }
            TextView na = view.findViewById(R.id.textView13);
            na.setText(Name);
        }else { TextView na = view.findViewById(R.id.textView13);
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

            tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {

                    Toast.makeText(getContext(), "Image clicked!", Toast.LENGTH_SHORT).show();

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

        // Click vào CardView
//        CardView cardView = view.findViewById(R.id.imageView1);
//        cardView.setOnClickListener(v -> {
//            Log.d("ClickTest", "CardView được click!");
//            Toast.makeText(getContext(), "CardView Clicked!", Toast.LENGTH_SHORT).show();
//        });


        TextView test = view.findViewById(R.id.test);
        test.setOnClickListener(v -> {
            bao_hiem_dialog dialog = new bao_hiem_dialog();
            dialog.show(getParentFragmentManager(), "bao_hiem_dialog");
            Log.d("ClickTest", "ImageView được click!");
            Toast.makeText(getContext(), "ImageView Clicked!", Toast.LENGTH_SHORT).show();
        });
        // Click vào ImageView
        ImageView imageView = view.findViewById(R.id.imgClickable);
        imageView.setOnClickListener(v -> {
            Log.d("ClickTest", "ImageView được click!");
            Toast.makeText(getContext(), "ImageView Clicked!", Toast.LENGTH_SHORT).show();
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
            viewPager1.setUserInputEnabled(false);
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
            itemList.add(new ItemModel(R.drawable.images, "Sài Gòn", "150+ xe"));
            itemList.add(new ItemModel(R.drawable.img_2, "Long An", "100+ xe"));
            itemList.add(new ItemModel(R.drawable.img_3, "Long An", "100+ xe"));
            itemList.add(new ItemModel(R.drawable.img_2, "Long An", "100+ xe"));
            adapter = new diadiemnoibat(itemList);
            recyclerView.setAdapter(adapter);
        }

        // Xử lý click cho ngày thuê và giờ thuê
        {
            // Khởi tạo selectedDateTime
            selectedDateTime = Calendar.getInstance();

            // Xử lý click cho ngày thuê
            View ngayThueLayout = view.findViewById(R.id.edt_rent_date);
            if (ngayThueLayout != null) {
                ngayThueLayout.setOnClickListener(v -> {
                    DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getContext(),
                        (view1, year, month, dayOfMonth) -> {
                            String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                            TextView txtNgayThue = view.findViewById(R.id.edt_rent_date);
                            if (txtNgayThue != null) {
                                txtNgayThue.setText(selectedDate);
                            }
                            selectedDateTime.set(Calendar.YEAR, year);
                            selectedDateTime.set(Calendar.MONTH, month);
                            selectedDateTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                            
                            Calendar currentTime = Calendar.getInstance();
                            if (selectedDate.equals(currentTime.get(Calendar.DATE))) {
                                if (year < currentTime.get(Calendar.YEAR) || 
                                    (year == currentTime.get(Calendar.YEAR) && (month < currentTime.get(Calendar.MONTH) || 
                                    (month == currentTime.get(Calendar.MONTH) && dayOfMonth < currentTime.get(Calendar.DAY_OF_MONTH))))) {
                                    Toast.makeText(getContext(), "Vui lòng chọn thời gian trong tương lai", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            }
                        },
                        Calendar.getInstance().get(Calendar.YEAR),
                        Calendar.getInstance().get(Calendar.MONTH),
                        Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
                    );
                    datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                    datePickerDialog.show();
                });
            }

            // Xử lý click giờ thuê
            View gioThueLayout = view.findViewById(R.id.edt_rent_time);
            if (gioThueLayout != null) {
                gioThueLayout.setOnClickListener(v -> {
                    TimePickerDialog timePickerDialog = new TimePickerDialog(
                        getContext(),
                        (view12, hourOfDay, minute) -> {
                            String selectedTime = String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute);
                            TextView txtGioThue = view.findViewById(R.id.edt_rent_time);
                            if (txtGioThue != null) {
                                txtGioThue.setText(selectedTime);
                            }
                            selectedDateTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                            selectedDateTime.set(Calendar.MINUTE, minute);
                        },
                        Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                        Calendar.getInstance().get(Calendar.MINUTE),
                        true
                    );
                    timePickerDialog.show();
                });
            }
        }
        return view;
    }
    private static BannerAdapter bannerAdapter;
    private void adjustTabLayoutHeight(int position) {
        if (position == 0) {
            layoutParams.height = dpToPx(280);
        } else {
            layoutParams.height = dpToPx(360);
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