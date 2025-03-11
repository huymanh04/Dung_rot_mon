package com.example.dung_rot_mon.Sql;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
public class Sqlheplper extends SQLiteOpenHelper {

    public static final String TABLE_PEOPLE = "people";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_PERSON = "person";

    private static final String DATABASE_NAME = "people.db";
    private static final int DATABASE_VERSION = 1;

    // Câu lệnh khởi tạo Database.
    private static final String DATABASE_CREATE = "create table "
            + TABLE_PEOPLE + "( " + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_PERSON
            + " text not null);";

    public Sqlheplper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(Sqlheplper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PEOPLE);
        onCreate(db);
    }
}
