package com.example.dung_rot_mon.Sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.dung_rot_mon.Login.Login;

import java.util.Calendar;

public class DatabaseAccount  {

    private  DatabaseHelper dbHelper;   private Context context;
    public DatabaseAccount(Context context){
        dbHelper = new DatabaseHelper(context);
        this.context=context;
    }

    // Phương thức chèn dữ liệu vào bảng User
    public void addUser(String email, String phone, String name, byte[] image, byte[] img_blx) {
        dbHelper = new DatabaseHelper(context);
        SQLiteDatabase db = dbHelper.openDatabase();
        ContentValues values = new ContentValues();
        Calendar calendar = Calendar.getInstance();  // Lấy đối tượng Calendar với thời gian hiện tại
        int year = calendar.get(Calendar.YEAR);      // Lấy năm
        int month = calendar.get(Calendar.MONTH) + 1; // Lấy tháng (tháng bắt đầu từ 0, cộng thêm 1)
        int day = calendar.get(Calendar.DAY_OF_MONTH);  // Lấy ngày trong tháng

        values.put("email", email);
        values.put("phone", phone);
        values.put("name", name);
        values.put("image", image);
        values.put("ngaythamgia", day + "/" + month + "/" + year);
        values.put("sochuyen", 0);
        values.put("image_blx", img_blx);

        // Xây dựng câu lệnh SQL INSERT
        StringBuilder sqlQuery = new StringBuilder();
        sqlQuery.append("INSERT INTO account (email, phone, name, image, ngaythamgia, sochuyen, image_blx) ");
        sqlQuery.append("VALUES ('");
        sqlQuery.append(email).append("', '");
        sqlQuery.append(phone).append("', '");
        sqlQuery.append(name).append("', ");
        sqlQuery.append("X'").append(bytesToHex(image)).append("', '");
        sqlQuery.append(day + "/" + month + "/" + year).append("', ");
        sqlQuery.append("0, ");
        sqlQuery.append("X'").append(bytesToHex(img_blx)).append("');");
String manh=sqlQuery.toString();
        // In ra câu lệnh SQL
        Log.d("SQL Query", sqlQuery.toString());

        // Thực thi câu lệnh insert vào cơ sở dữ liệu
        long result = db.insert("account", null, values);
        if (result == -1) {
            Log.e("Database Error", "Failed to insert data");
        } else {
            Log.i("Database Success", "User inserted successfully");
        }
        db.close();
    }

    // Hàm chuyển đổi byte[] thành chuỗi hex để hiển thị ảnh dưới dạng dữ liệu nhị phân
    private String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xff & bytes[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

}
