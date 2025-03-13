package com.example.dung_rot_mon.admin;

public class Item {

    private String name;
    private byte[] img;

    public Item(String name, byte[] img) {
        this.name = name;
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public byte[] getImg() {
        return img;
    }
}