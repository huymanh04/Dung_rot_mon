package com.example.dung_rot_mon.Sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseAccount extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Thue_xe.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_USER = "account";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_IMAGE = "image";

    private static final String CREATE_TABLE_USER = "CREATE TABLE " + TABLE_USER + " ("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_EMAIL + " TEXT, "
            + COLUMN_NAME + " TEXT, "
            + COLUMN_IMAGE + " BLOB)";
    private static final String CREATE_TABLE = "CREATE TABLE baner ("
            + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "name TEXT NOT NULL, "
            + "img BLOB NOT NULL);";
    public DatabaseAccount(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS baner");

        onCreate(db);
    }

    // Phương thức chèn dữ liệu vào bảng User
    public void addUser(String email, String name, byte[] image) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_IMAGE, image);

        var t=db.insert(TABLE_USER, null, values);
        db.close();
    }
}
