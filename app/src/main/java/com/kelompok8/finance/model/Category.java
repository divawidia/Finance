package com.kelompok8.finance.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Category implements Parcelable {
    private Integer id, id_user;
    private String nama_kategori, icon, warna;

    public Category (Integer id, Integer id_user, String nama_kategori, String icon, String warna){
        this.id = id;
        this.id_user = id_user;
        this.nama_kategori = nama_kategori;
        this.icon = icon;
        this.warna = warna;
    }

    public Integer getId() { return id; }
    public Integer getIdUser() { return id_user; }
    public String getNamaKategori() { return nama_kategori; }
    public String getIcon() { return icon; }
    public String getWarna() { return warna; }

    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.id_user);
        dest.writeString(this.nama_kategori);
        dest.writeString(this.icon);
        dest.writeString(this.warna);
    }

    protected Category(Parcel in) {
        id = in.readInt();
        id_user = in.readInt();
        nama_kategori = in.readString();
        icon = in.readString();
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
