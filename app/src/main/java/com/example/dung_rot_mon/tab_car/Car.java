package com.example.dung_rot_mon.tab_car;

public class Car {
    private String ownerName;
    private String carName;
    private String details;
    private String location;
    private String priceOld;
    private String priceNew;
    private int carImage;
    private int ownerImage;

    private  String nguyenlieu;
    private    int sochongoi;
    private String bio;
    private String vitri;




    public Car(String ownerName, String carName, String details, String location,
               String priceOld, String priceNew, int carImage, int ownerImage,  String nguyenlieu, int sochongoi, String bio, String vitri ) {
        this.ownerName = ownerName;
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




    }

    // Getters
    public String getOwnerName() { return ownerName; }
    public String getCarName() { return carName; }
    public String getDetails() { return details; }
    public String getLocation() { return location; }
    public String getPriceOld() { return priceOld; }
    public String getPriceNew() { return priceNew; }
    public int getCarImage() { return carImage; }
    public int getOwnerImage() { return ownerImage; }
    public String getNguyenlieu() { return nguyenlieu; }
    public int getSochongoi() { return sochongoi; }
    public String getBio() { return bio; }
    public String getVitri() { return vitri; }





}