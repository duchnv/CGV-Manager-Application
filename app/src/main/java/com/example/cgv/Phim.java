package com.example.cgv;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Phim {
    private int id;
    private String ten;
    private String mota;
    private int gia;
    private String gio;
    private String theloai;
    private String anh;

    public Phim() {
    }

    public Phim(int id, String ten, int gia, String gio, String theloai) {
        this.id = id;
        this.ten = ten;
        this.gia = gia;
        this.gio = gio;
        this.theloai = theloai;
    }

    public Phim(int id, String ten, String mota, int gia, String gio, String theloai) {
        this.id = id;
        this.ten = ten;
        this.mota = mota;
        this.gia = gia;
        this.gio = gio;
        this.theloai = theloai;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }
    public int getGia() {
        return gia;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }

    public String getGio() {
        return gio;
    }

    public void setGio(String gio) {
        this.gio = gio;
    }

    public String getTheloai() {
        return theloai;
    }

    public void setTheloai(String theloai) {
        this.theloai = theloai;
    }

    public String getAnh() {
        return anh;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("ten", ten);
        result.put("gio", gio);
        result.put("gia", gia);
        result.put("theloai", theloai);
        result.put("mota", mota);
        return result;
    }

}
