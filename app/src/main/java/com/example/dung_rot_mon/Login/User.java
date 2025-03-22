package com.example.dung_rot_mon.Login;
public class User {
    private String fullName;
    private String phoneNumber;
    private String email;
    int taixe;

    // Constructor
    public User(String fullName, String phoneNumber, String email,int taixe) {
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.taixe=taixe;
    }

    // Getter và Setter
    public String getFullName() {
        return fullName;
    }
    public int gettaixe() {
        return taixe;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
