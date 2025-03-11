package com.example.dung_rot_mon.Login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.dung_rot_mon.MainActivity;
import com.example.dung_rot_mon.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class recover_pass extends AppCompatActivity {
    EditText editTextEmail; FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_recover_pass);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
      TextView textViewBackToLogin=findViewById(R.id.textViewBackToLogin);
      Button resetPasswordButton=findViewById(R.id.resetPasswordButton);
        editTextEmail=findViewById(R.id.editTextEmail);
      textViewBackToLogin.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              finish();
          }
      });

        mAuth=FirebaseAuth.getInstance();
        resetPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                recover();
            }
        });
    }
    private void recover() {

        String editTextEmaila = editTextEmail.getText().toString().trim();


        if (TextUtils.isEmpty(editTextEmaila) ) {
            Toast.makeText(this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }
        mAuth.sendPasswordResetEmail(editTextEmaila).addOnCompleteListener(this, task -> {
            if (task.isSuccessful()) {
                FirebaseUser user = mAuth.getCurrentUser();
                if (user != null) {
                    // Nếu đăng nhập thành công, chuyển hướng tới màn hình chính
                    Toast.makeText(recover_pass.this, "Kiểm tra email của bạn", Toast.LENGTH_SHORT).show();

                    finish();  // Đóng LoginActivity sau khi đăng nhập thành công
                }
            } else {
                Toast.makeText(recover_pass.this, "Thất bại: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}