package com.example.dung_rot_mon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.dung_rot_mon.Login.Accountt;
import com.example.dung_rot_mon.Fragment.Car;
import com.example.dung_rot_mon.Fragment.Chat;
import com.example.dung_rot_mon.Fragment.Home;
import com.example.dung_rot_mon.Fragment.Support;
import com.example.dung_rot_mon.Login.Login;
import com.example.dung_rot_mon.Login.register;
import com.example.dung_rot_mon.Sql.DatabaseHelper;
import com.example.dung_rot_mon.admin.MainAdmin;
import com.example.dung_rot_mon.databinding.ActivityMainBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    Intent intent;
    public static boolean ktralogin=false;
    public static DatabaseHelper dbHelper; String email12="";
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
                    replaceFragment(new Chat());
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

//                replaceFragment(new Car());
                Intent m = new Intent(MainActivity.this, MainAdmin.class);
                startActivity(m);
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