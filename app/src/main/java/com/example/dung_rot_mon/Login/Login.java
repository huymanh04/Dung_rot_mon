package com.example.dung_rot_mon.Login;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.dung_rot_mon.MainActivity;
import com.example.dung_rot_mon.R;
import com.example.dung_rot_mon.Sql.DatabaseHelper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;

public class Login extends AppCompatActivity {

    TextView username;TextView pass;TextView dangkyngay;
    Button btnlogin;
    public  String email;
     FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });



        mAuth=FirebaseAuth.getInstance();
        TextView textView2=findViewById(R.id.textView2);
        btnlogin = findViewById(R.id.loginButton);
        username = findViewById(R.id.username);
        pass = findViewById(R.id.password);
        dangkyngay=findViewById(R.id.dangkyngay);
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loginUser();
            }
        });
        dangkyngay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent manh = new Intent(Login.this, register.class);
                startActivity(manh); overridePendingTransition(R.drawable.zoom_in, R.drawable.zoom_out);

            }
        });
        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent manh = new Intent(Login.this, recover_pass.class);
                startActivity(manh); overridePendingTransition(R.drawable.zoom_in, R.drawable.zoom_out);

            }
        });
    }
    private void loginUser() {
        String phoneNumber = username.getText().toString().trim();
        String password = pass.getText().toString().trim();

        if (TextUtils.isEmpty(phoneNumber) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(phoneNumber  , password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            email=phoneNumber;    MainActivity.ktralogin=true;
                            // Nếu đăng nhập thành công, chuyển hướng tới màn hình chính
                            Toast.makeText(Login.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Login.this, MainActivity.class);
                            intent.putExtra("email", email);
                            startActivity(intent);
                            finish();  // Đóng LoginActivity sau khi đăng nhập thành công
                        }
                    } else {
                        Toast.makeText(Login.this, "Đăng nhập thất bại: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}