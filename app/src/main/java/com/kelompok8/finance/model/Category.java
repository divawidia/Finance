package com.kelompok8.finance.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Category implements Parcelable {
    private Integer id, id_user, icon;
    private String nama_kategori, warna;

    public Category (Integer id, Integer id_user, String nama_kategori, Integer icon, String warna){
        this.id = id;
        this.id_user = id_user;
        this.nama_kategori = nama_kategori;
        this.icon = icon;
        this.warna = warna;
    }

    public Category() {

    }

    public Integer getId() { return id; }
    public Integer getIdUser() { return id_user; }
    public String getNamaKategori() { return nama_kategori; }
    public Integer getIcon() { return icon; }
    public String getWarna() { return warna; }


    public void setId(int id) { this.id = id; }
    public void setIdUser(int id_user) { this.id_user = id_user; }
    public void setNamaKategori(String nama_kategori) { this.nama_kategori = nama_kategori; }
    public void setIcon(int icon) { this.icon = icon; }
    public void setWarna(String warna) { this.warna = warna; }

    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.id_user);
        dest.writeString(this.nama_kategori);
        dest.writeInt(this.icon);
        dest.writeString(this.warna);
    }

    protected Category(Parcel in) {
        id = in.readInt();
        id_user = in.readInt();
        nama_kategori = in.readString();
        icon = in.readInt();
        warna = in.readString();
    }

    public static final Parcelable.Creator<Category> CREATOR = new Parcelable.Creator<Category>() {
        @Override
        public Category createFromParcel(Parcel in) {
            return new Category(in);
        }

        @Override
        public Category[] newArray(int size) {
            return new Category[size];
        }
    };
}
