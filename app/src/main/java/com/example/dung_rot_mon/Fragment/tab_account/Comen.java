package com.example.dung_rot_mon.Fragment.tab_account;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.dung_rot_mon.Sql.DatabaseHelper;

public class Comen {
    private String id_acc,Name;
    private String id_car;
    private String text;
    private double so_sao;
    byte[]anh;

public Comen(){

}
    // Constructor
    public Comen(String id_acc, String id_car, String text, double so_sao, Context cc) {
        this.id_acc = id_acc;
        DatabaseHelper db=new DatabaseHelper(cc);
        SQLiteDatabase dba=db.openDatabase();
        Cursor cursor = dba.rawQuery("SELECT * FROM account WHERE id = ?", new String[]{id_acc});
        if(cursor.moveToFirst()){
            int emailColumnIndex1 = cursor.getColumnIndex("image");
            int emailColumnIndex = cursor.getColumnIndex("name");
            Name=cursor.getString(emailColumnIndex);
            anh=cursor.getBlob(emailColumnIndex1);
        }
        this.id_car = id_car;
        this.text = text;
        this.so_sao = so_sao;
    }

    // Getter cho các thuộc tính
    public String getIdAcc() {
        return id_acc;
    }
    public String getname() {
        return Name;
    }
    public byte[] get_anh() {

        return anh;
    }

    public String getIdCar() {
        return id_car;
    }

    public String getText() {
        return text;
    }

    public double getSoSao() {
        return so_sao;
    }
}
