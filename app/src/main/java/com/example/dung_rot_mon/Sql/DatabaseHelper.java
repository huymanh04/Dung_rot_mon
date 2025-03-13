package com.example.dung_rot_mon.Sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "my_database.db";
    private static final int DATABASE_VERSION = 1;

    // Tạo bảng với cột 'name' và 'img'
    private static final String CREATE_TABLE = "CREATE TABLE baner ("
            + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "name TEXT NOT NULL, "
            + "img BLOB NOT NULL);";

    public DatabaseHelper(Context context) {
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