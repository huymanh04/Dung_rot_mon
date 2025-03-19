package com.example.dung_rot_mon.admin;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.dung_rot_mon.R;
import com.example.dung_rot_mon.Sql.DatabaseManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class add extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;

    private Uri imageUri;
    private ImageView imageView;
    private DatabaseManager dbManager;

    private TextView tvBase64String;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        imageView = findViewById(R.id.imageView);


        // Xử lý chọn ảnh từ thiết bị
        Button btnChooseImage = findViewById(R.id.btnChooseImage);
        Button open = findViewById(R.id.openshow);
        FloatingActionButton btnChooseImage1 = findViewById(R.id.btnChooseImagde2);

        btnChooseImage.setOnClickListener(view -> {
            openImageChooser();
        });

        // Đảm bảo khởi tạo đúng đối tượng DatabaseManager
        dbManager = new DatabaseManager(this);

        btnChooseImage1.setOnClickListener(view -> {
            Frg_baner.bannerList.clear();
           Frg_baner.fetchData();
            finish();
                  });

        open.setOnClickListener(view -> {
            EditText v= findViewById(R.id.etName);

         var numbe=   dbManager.insertData(v.getText().toString(),imageView);
         if(numbe==-1){
             Toast.makeText(this, "Add erro" , Toast.LENGTH_SHORT).show();
         }else {
             Toast.makeText(this, "Add ok" , Toast.LENGTH_SHORT).show();

         }

            v.setText("");
           this.recreate();
        });
    }

    private void openImageChooser() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }





    // Xử lý kết quả khi người dùng chọn ảnh
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData(); // Lấy URI của ảnh đã chọn
            imageView.setImageURI(imageUri); // Hiển thị ảnh trong ImageView
        }
    }
}
