package com.example.dung_rot_mon.Login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.dung_rot_mon.MainActivity;
import com.example.dung_rot_mon.R;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;


public class register extends AppCompatActivity {
    TextView txtlogin;
    EditText username2;
    EditText username3;
    EditText password2;
    EditText password3;
    FirebaseAuth mAuth;
    Button dangky;
     FirebaseFirestore firestore;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        password3=findViewById(R.id.password3);
        password2=findViewById(R.id.password2);
        username3=findViewById(R.id.username3);
        username2=findViewById(R.id.username2);

        mAuth=FirebaseAuth.getInstance();
dangky=findViewById(R.id.loginButton2);
        txtlogin=findViewById(R.id.txtlogin);
        txtlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        dangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
          registerUser();
            }
        });
    }
    private void registerUser() {
        RadioButton radioButton= findViewById(R.id.radioButton);
        String fullName = username3.getText().toString().trim();
        String phoneNumber = username2.getText().toString().trim();
        String password = password2.getText().toString().trim();
        String confirmPassword = password3.getText().toString().trim();
        // Hiển thị thông báo đăng ký thành công

        if (TextUtils.isEmpty(fullName) || TextUtils.isEmpty(phoneNumber) || TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPassword)) {
            Toast.makeText(this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }
        firestore = FirebaseFirestore.getInstance();
        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Mật khẩu xác nhận không đúng", Toast.LENGTH_SHORT).show();
            return;
        }if(!radioButton.isChecked()){
            Toast.makeText(this, "Bạn chưa chập nhận điều khoản", Toast.LENGTH_SHORT).show();
            return;
        }
        mAuth.createUserWithEmailAndPassword(phoneNumber , password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();

                        // Lưu thông tin người dùng vào Firestore
                        User newUser = new User(fullName, "",phoneNumber);
                        firestore.collection("users").document(user.getUid())
                                .set(newUser)
                                .addOnSuccessListener(documentReference  -> {
                                    Toast.makeText(register.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();


                                        finish(); // Quay lại màn hình đăng nhập

                                    // 2000 ms = 2 giây

                                })
                                .addOnFailureListener(e -> Toast.makeText(register.this, "Lỗi: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                    } else {
                        Toast.makeText(register.this, "Đăng ký thất bại: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

}