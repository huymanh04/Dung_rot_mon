package com.example.dung_rot_mon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import com.example.dung_rot_mon.Fragment.Chat_.tab_chat;
import com.example.dung_rot_mon.Fragment.Support;
import com.example.dung_rot_mon.Fragment.tab_account.Xe_yeu_thich;
import com.example.dung_rot_mon.Login.Accountt;
import com.example.dung_rot_mon.Fragment.Car;
import com.example.dung_rot_mon.Fragment.Home;
import com.example.dung_rot_mon.Login.Login;
import com.example.dung_rot_mon.Sql.DatabaseHelper;
import com.example.dung_rot_mon.Sql.DatabaseManager;
import com.example.dung_rot_mon.databinding.ActivityMainBinding;
import com.example.dung_rot_mon.tab_car.Quan_ly_xe;
import com.example.dung_rot_mon.tab_car.tim_xe;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    Intent intent;
   public static int Id_account=-1;
    public static boolean ktralogin=false;
    public static DatabaseHelper dbHelper; String email12="huymanh@gmail.com";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        dbHelper = new DatabaseHelper(this);
        try {
            dbHelper.copyDatabase();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        setContentView(binding.getRoot());
        intent = getIntent();

        if(ktralogin)
        {
            email12=intent.getStringExtra("email");
        }else {email12="";}

        SQLiteDatabase dba=dbHelper.openDatabase();
        Cursor cursor = dba.rawQuery("SELECT id FROM account WHERE email = ?", new String[]{String.valueOf(email12)});
        if (cursor.moveToFirst()) {
            Id_account = cursor.getInt(0); // Lấy cột đầu tiên (tên tài xế)
        }
        replaceFragment(new Home(email12));


        /// botto,nagivation
        {
            binding.bottomNavigation.setBackground(null);
            binding.bottomNavigation.setOnNavigationItemSelectedListener(item -> {
                // Lấy title của mục đã chọn
                CharSequence title = item.getTitle().toString(); // Lấy title của mục được chọn
                Log.d("BottomNavigation", "Selected item title: " + title);

// So sánh bằng cách sử dụng equals()
                if (title.equals("Trang chủ")) {
                    replaceFragment(new Home(email12));
                } else if (title.equals("Tin nhắn")) {
                    replaceFragment(new tab_chat());
                } else if (title.equals("Car")) {
                    replaceFragment(new Car());
                } else if (title.equals("Hỗ trợ")) {
                   replaceFragment(new Support());

                } else if (title.equals("Tài khoản")) {
if(email12!=null&&email12!=""){
                    replaceFragment(new Accountt(email12));}else{
    Intent manh = new Intent(MainActivity.this, Login.class);
    startActivity(manh); overridePendingTransition(R.drawable.zoom_in, R.drawable.zoom_out);
    finish();
}
                }

                return true;
            });
            FloatingActionButton btncarr;
            btncarr = findViewById(R.id.btncarr);
            btncarr.setOnClickListener(v -> {

              replaceFragment(new Quan_ly_xe(email12));
               /* Intent m = new Intent(MainActivity.this, MainAdmin.class);
                startActivity(m);*/
            });
        }

    }



    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

}