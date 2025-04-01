package com.example.dung_rot_mon.tab_car;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;

import com.example.dung_rot_mon.Sql.DatabaseManager;

import java.util.List;

public class Car {
    int idcar;
    private int idname;
    private String carName;
    private String details;
    private String location;
    private String priceOld;
    private String priceNew;
    private List<Bitmap> carImage;
    private Bitmap ownerImage;
private  int status;
    private  String nguyenlieu;
    private    int sochongoi;
    private    int Tong_chuyen=0;
    private String bio;
    private String vitri;

String typecar;

Context cc;
    public Car(Context conte, int idcar, int Idname, String carName, String type, String details, String location,
               String priceOld, String priceNew, List<Bitmap> carImage, Bitmap ownerImage, String nguyenlieu, int sochongoi, String bio, String vitri,int Tong_chuyen) {
        this.idname = Idname;
        cc=conte;
        this.Tong_chuyen=Tong_chuyen;
        this.carName = carName;
        this.details = details;
        this.location = location;
        this.priceOld = priceOld;
        this.priceNew = priceNew;
        this.carImage = carImage;
        this.ownerImage = ownerImage;
        this.nguyenlieu = nguyenlieu;
        this.sochongoi = sochongoi;
        this.bio = bio;
        this.vitri = vitri;
        this.status=0;
        this.typecar=type;
        this.idcar=idcar;

        try{     image1=carImage.get(0);}catch(Exception ee){  image2=null;}
        try{   image2=carImage.get(1);}catch(Exception ee){  image2=null;}
        try{    image3=carImage.get(2);}catch(Exception ee){image3=null;}
        try{     image4=carImage.get(3);}catch(Exception ee){image4=null;}




    }
    private Bitmap image1;
    private Bitmap image2;
    private Bitmap image3;
    private Bitmap image4;

    public Bitmap getImage1() { return image1; }
    public Bitmap getImage2() { return image2; }
    public Bitmap getImage3() { return image3; }
    public Bitmap  getImage4() { return image4; }
    // Getters
    public int getIDTAXE() { return idname; }
    public String getType() { return typecar; }
    public String getOwnerName() {
        String driverName = null;
        SQLiteDatabase db=new DatabaseManager(cc).getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT name FROM account WHERE id = ?", new String[]{String.valueOf(idname)});

        if (cursor.moveToFirst()) {
            driverName = cursor.getString(0); // Lấy cột đầu tiên (tên tài xế)
        }

        cursor.close();
        db.close();
        return driverName; }
    public int getTaixe() {
        int driverName = 0;
        SQLiteDatabase db=new DatabaseManager(cc).getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT taixe FROM account WHERE id = ?", new String[]{String.valueOf(3)});
        int idColumnIndex = cursor.getColumnIndex("taixe");
        if (cursor.moveToFirst()) {
            driverName = cursor.getInt(idColumnIndex); // Lấy cột đầu tiên (tên tài xế)
        }

        cursor.close();
        db.close();
        return driverName; }
    public String getCarName() { return carName; }
    public String getDetails() { return details; }
    public String getLocation() { return location; }
    public String getPriceOld() { return priceOld; }
    public String getPriceNew() { return priceNew; }
    public List<Bitmap> getCarImage() { return carImage; }
    public Bitmap getOwnerImage() { return ownerImage; }
    public String getNguyenlieu() { return nguyenlieu; }
    public int getSochongoi() { return sochongoi; }
    public int get_tong() { return Tong_chuyen; }
    public int getIDcarr() { return idcar; }
    public int getTrangthai() { return status; }
    public String getBio() { return bio; }
    public String getVitri() { return vitri; }





}