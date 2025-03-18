package com.example.dung_rot_mon.Sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DatabaseHelper {

    private static final String DB_NAME = "Thue_xe.db";  // Tên cơ sở dữ liệu
    private static final String DB_PATH = "/data/data/com.example.dung_rot_mon/databases/";  // Đường dẫn thư mục cơ sở dữ liệu
    private Context context;

    public DatabaseHelper(Context context) {
        this.context = context;

    }

    // Kiểm tra cơ sở dữ liệu đã tồn tại chưa
    public boolean checkDatabase() {
        SQLiteDatabase checkDB = null;
        try {
            String dbPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e) {
            // Nếu cơ sở dữ liệu không tồn tại, trả về false
            return false;
        }
        if (checkDB != null) {
            checkDB.close();
        }
        return true;
    }

    // Sao chép cơ sở dữ liệu từ assets vào bộ nhớ trong của thiết bị
    public void copyDatabase() throws IOException {
        if (!checkDatabase()) {
            // Tạo thư mục databases nếu chưa có
            File dir = new File(DB_PATH);
            if (!dir.exists()) {
                dir.mkdir();
            }

            // Mở tệp cơ sở dữ liệu từ assets
            InputStream inputStream = context.getAssets().open(DB_NAME);
            String outFileName = DB_PATH + DB_NAME;
            OutputStream outputStream = new FileOutputStream(outFileName);

            // Sao chép byte từ inputStream vào outputStream
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }

            // Đóng các stream
            outputStream.flush();
            outputStream.close();
            inputStream.close();
        }
    }
    public void backupDatabase() {
        try {
            // Đường dẫn đến cơ sở dữ liệu gốc trong bộ nhớ trong
            File currentDB = new File(DB_PATH + DB_NAME);

            // Đường dẫn sao lưu đến bộ nhớ ngoài
            File backupDB = new File(Environment.getExternalStorageDirectory(), DB_NAME);

            // Kiểm tra quyền truy cập bộ nhớ ngoài
            if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                Log.e("Database Backup", "External storage is not mounted or accessible.");
                return;
            }

            // Mở các tệp tin cần sao chép
            FileInputStream fis = new FileInputStream(currentDB);
            FileOutputStream fos = new FileOutputStream(backupDB);

            // Sao chép byte từ cơ sở dữ liệu vào tệp sao lưu
            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer)) > 0) {
                fos.write(buffer, 0, length);
            }
            fos.flush();
            fos.close();
            fis.close();

            Log.i("Database Backup", "Backup successful to: " + backupDB.getAbsolutePath());
        } catch (IOException e) {
            Log.e("Database Backup", "Failed to backup database", e);
        }
    }
    // Mở cơ sở dữ liệu đã sao chép
    public SQLiteDatabase openDatabase() throws SQLiteException {
        String dbPath = DB_PATH + DB_NAME;
        return SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE);
    }
}
