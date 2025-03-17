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
import com.example.dung_rot_mon.databinding.ActivityMainBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new Home());
        intent = getIntent();
/// botto,nagivation
        {
            binding.bottomNavigation.setBackground(null);
            binding.bottomNavigation.setOnNavigationItemSelectedListener(item -> {
                // Lấy title của mục đã chọn
                CharSequence title = item.getTitle().toString(); // Lấy title của mục được chọn
                Log.d("BottomNavigation", "Selected item title: " + title);

// So sánh bằng cách sử dụng equals()
                if (title.equals("Trang chủ")) {
                    replaceFragment(new Home());
                } else if (title.equals("Tin nhắn")) {
                    replaceFragment(new Chat());
                } else if (title.equals("Car")) {
                    replaceFragment(new Car());
                } else if (title.equals("Hỗ trợ")) {
                    replaceFragment(new Support());
                } else if (title.equals("Tài khoản")) {
                    String ss=intent.getStringExtra("email");
                    replaceFragment(new Accountt(ss));
                }

                return true;
            });
            FloatingActionButton btncarr;
            btncarr = findViewById(R.id.btncarr);
            btncarr.setOnClickListener(v -> {
                replaceFragment(new Car());
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