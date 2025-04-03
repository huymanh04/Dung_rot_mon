package com.example.dung_rot_mon.Fragment.tab_account;

public class lic_su {

        private String ma_thue;
        private String status;
        private String ngay_thue;
        private String ngay_tra;
        private String  car_name;
        private String  car_type;

        private int _tong_tien;
        private int carid;

public lic_su(){

}
    public lic_su(String ma_thue, String status,String car_type, String ngay_thue, String ngay_tra, String car_name, int _tong_tien,int car_id) {
        this.ma_thue = ma_thue;
        this.status = status;
        this.car_type = car_type;
        this.carid = car_id;
        this.ngay_thue = ngay_thue;
        this.ngay_tra = ngay_tra;
        this.car_name = car_name;
        this._tong_tien = _tong_tien;
    }
    public String getMa_thue() {
        return ma_thue;
    }

    public String getStatus() {
        return status;
    }  public String gettype() {
        return car_type;
    }

    public String getNgay_thue() {
        return ngay_thue;
    }

    public String getNgay_tra() {
        return ngay_tra; // Corrected typo here
    }

    public String getCar_name() {
        return car_name;
    }

    public int get_tong_tien() {
        return _tong_tien;
    } public int getidcarr() {
        return carid;
    }
}
