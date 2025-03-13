package com.example.dung_rot_mon;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import com.example.dung_rot_mon.Fragment.Accountt;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class Utils {

    // Hàm chuyển đổi hình ảnh từ drawable thành Base64
    public static String convertImageToBase64(Accountt context, int resourceId) {
        try {
            // Đọc hình ảnh từ tài nguyên drawable
            InputStream inputStream = context.getResources().openRawResource(resourceId);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

            // Chuyển Bitmap thành byte array
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] imageBytes = byteArrayOutputStream.toByteArray();

            // Mã hóa byte array thành Base64
            return Base64.encodeToString(imageBytes, Base64.DEFAULT);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
