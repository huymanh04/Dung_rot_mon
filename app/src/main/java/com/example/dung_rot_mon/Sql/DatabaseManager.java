package com.example.dung_rot_mon.Sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class DatabaseManager {

    private DatabaseHelper dbHelper;
    private Context context;

    public DatabaseManager(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    public Cursor getAllData() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        return db.rawQuery("SELECT * FROM baner", null); // Truy vấn tất cả dữ liệu từ bảng
    }

    public SQLiteDatabase getReadableDatabase() {
        return dbHelper.getReadableDatabase();
    }

    public SQLiteDatabase getWritableDatabase() {
        return dbHelper.getWritableDatabase();
    }

    public void insertData(String name, String imagePath) {
        SQLiteDatabase db = null;
        try {
            db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();

            byte[] imageBytes = imageToByteArray(imagePath);
            if (imageBytes == null) {
                Log.e("Database", "Image byte array is null");
                return; // Không thêm dữ liệu nếu mảng byte là null
            }

            values.put("name", name);
                values.put("img", imageBytes); // Lưu ảnh dưới dạng mảng byte vào cơ sở dữ liệu

            long result = db.insert("baner", null, values);
            if (result == -1) {
                Log.e("Database", "Error inserting data");
            } else {
                Log.i("Database", "Data inserted successfully");
            }
        } finally {
            if (db != null && db.isOpen()) {
                db.close();
            }
        }
    }

    // Phương thức chuyển đổi hình ảnh thành mảng byte
    private byte[] imageToByteArray(String imagePath) {
        if (context == null) {
            Log.e("Database", "Context is null");
            return null;
        }

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        InputStream inputStream = null;

        try {
            inputStream = context.getContentResolver().openInputStream(android.net.Uri.parse(imagePath));
            if (inputStream == null) {
                Log.e("Database", "InputStream is null. Could not open image.");
                return null;
            }

            Log.i("Database", "Reading image from: " + imagePath);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, length);
            }

            byte[] byteArray = byteArrayOutputStream.toByteArray();
            Log.i("Database", "Image byte array length: " + byteArray.length); // Log chiều dài mảng byte

            return byteArray;

        } catch (FileNotFoundException e) {
            Log.e("Database", "File not found: " + e.getMessage());
        } catch (IOException e) {
            Log.e("Database", "IOException: " + e.getMessage());
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                Log.e("Database", "Error closing InputStream: " + e.getMessage());
            }
        }

        return byteArrayOutputStream.toByteArray();
    }
}
