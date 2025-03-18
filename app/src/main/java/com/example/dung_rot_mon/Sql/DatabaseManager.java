package com.example.dung_rot_mon.Sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

public class DatabaseManager {

    private static DatabaseHelper dbHelper;
    private Context context;

    public DatabaseManager(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

//    public Cursor getAllData() {
//        SQLiteDatabase db = dbHelper.getReadableDatabase();
//        return db.rawQuery("SELECT * FROM baner", null); // Truy vấn tất cả dữ liệu từ bảng
//    }
//
//    public SQLiteDatabase getReadableDatabase() {
//        return dbHelper.getReadableDatabase();
//    }
//
//    public SQLiteDatabase getWritableDatabase() {
//        return dbHelper.getWritableDatabase();
//    }
public static SQLiteDatabase getWritableDatabase(){
    return dbHelper.openDatabase();
}
    public int insertData(String name, ImageView imagePath) {
        SQLiteDatabase db = null; int d=0;
      try{ try {
            db = dbHelper.openDatabase();
            ContentValues values = new ContentValues();

            byte[] imageBytes = imageToByteArray(imagePath);
            if (imageBytes == null) {
                Log.e("Database", "Image byte array is null");
                return -1; // Không thêm dữ liệu nếu mảng byte là null
            }

            values.put("name", name);
                values.put("img", imageBytes); // Lưu ảnh dưới dạng mảng byte vào cơ sở dữ liệu

            long result = db.insert("baner", null, values);

            if (result == -1) {
                Log.e("Database", "Error inserting data");
                d=-1;
            } else {
                Log.i("Database", "Thêm  liệu thành cong");
                d=0;
            }


        }

        finally {
            if (db != null && db.isOpen()) {
                db.close();
            }
        }
      }catch(Exception a) {d=-1;}
        return d;
    }

    // Phương thức chuyển đổi hình ảnh thành mảng byte
    private byte[] imageToByteArray(ImageView imagePath) {
        BitmapDrawable dra=(BitmapDrawable) imagePath.getDrawable();
        Bitmap bmp =dra.getBitmap();
        ByteArrayOutputStream strem=new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG,80,strem);
        byte[] anh=strem.toByteArray();
        return anh;
    }
}
