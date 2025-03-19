package com.example.dung_rot_mon.Sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper1 extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Thue_xe.db";
    private static final int DATABASE_VERSION = 1;
    private static final int DATABASE_VERSIONa = 1;

    // Tạo bảng với cột 'name' và 'img'
    private static final String CREATE_TABLE = "CREATE TABLE baner ("
            + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "name TEXT NOT NULL, "
            + "img BLOB NOT NULL);";
    private static final String DB_NAME = "Thue_xe.db";  // Tên tệp cơ sở dữ liệu trong assets
    private static final String DB_PATH = "/data/data/com.example.yourapp/databases/";  // Đường dẫn đến thư mục databases

    public boolean checkDatabase() {
        SQLiteDatabase checkDB = null;
        try {
            String dbPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e) {
            // Nếu cơ sở dữ liệu không tồn tại, sẽ tạo mới
            return false;
        }
        if (checkDB != null) {
            checkDB.close();
        }
        return true;
    }
    public DatabaseHelper1(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tạo bảng khi cơ sở dữ liệu được tạo lần đầu
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Nếu cơ sở dữ liệu cần nâng cấp, sẽ xóa bảng cũ và tạo lại
        db.execSQL("DROP TABLE IF EXISTS baner");
        onCreate(db);
    }
}