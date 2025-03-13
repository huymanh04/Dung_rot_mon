package com.example.dung_rot_mon.admin;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dung_rot_mon.R;
import com.example.dung_rot_mon.Sql.DatabaseManager;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class show extends AppCompatActivity {
    private RecyclerView recyclerView;

    private FirebaseFirestore firestore;
    private Button button;

    private DataAdapter adapter;
    private List<Item> itemList;
    private DatabaseManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_show);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        firestore = FirebaseFirestore.getInstance();
        recyclerView = findViewById(R.id.recyclerView);
        itemList = new ArrayList<>();
        dbManager = new DatabaseManager(this);

        // Cấu hình RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new DataAdapter(this, itemList);
        recyclerView.setAdapter(adapter);

        // Truy vấn dữ liệu từ cơ sở dữ liệu và hiển thị
        fetchData();
    }

    private void fetchData() {
        Cursor cursor = dbManager.getAllData(); // Lấy dữ liệu từ cơ sở dữ liệu
        int nameColumnIndex = cursor.getColumnIndex("name");
        int imgColumnIndex = cursor.getColumnIndex("img");

        // Kiểm tra nếu các chỉ số cột hợp lệ (lớn hơn hoặc bằng 0)
        if (nameColumnIndex == -1 || imgColumnIndex == -1) {
            Log.e("Database", "Column not found!");
            return;
        }

        // Duyệt qua tất cả các bản ghi
        while (cursor.moveToNext()) {
            String name = cursor.getString(nameColumnIndex);
            byte[] img = cursor.getBlob(imgColumnIndex);

            // Thêm vào danh sách itemList
            itemList.add(new Item(name, img));
        }

        cursor.close(); // Đóng con trỏ sau khi sử dụng
        adapter.notifyDataSetChanged(); // Cập nhật RecyclerView
    }

}