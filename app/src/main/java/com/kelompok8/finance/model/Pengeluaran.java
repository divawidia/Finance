package com.kelompok8.finance.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.kelompok8.finance.database.DBHelper;

public class Pengeluaran implements Parcelable {
    private Integer id, id_user, jumlah_pengeluaran, id_category;
    private String tanggal, catatan, kategori;

    public Pengeluaran (Integer id, String kategori, Integer id_user, Integer jumlah_pengeluaran, String catatan, String tanggal, Integer id_category){
        this.id = id;
        this.kategori = kategori;
        this.id_user = id_user;
        this.jumlah_pengeluaran = jumlah_pengeluaran;
        this.catatan = catatan;
        this.tanggal = tanggal;
        this.id_category = id_category;
    }

    public Integer getId() { return id; }
    public String getKategori() { return kategori; }
    public Integer getIdUser() { return id_user; }
    public Integer getJumlahPengeluaran() { return jumlah_pengeluaran; }
    public String getTanggal() { return tanggal; }
    public String getCatatan() { return catatan; }
    public Integer getIdCategory() { return id_category; }

    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.kategori);
        dest.writeInt(this.id_user);
        dest.writeInt(this.jumlah_pengeluaran);
        dest.writeString(this.catatan);
        dest.writeString(this.tanggal);
        dest.writeInt(this.id_category);
    }

    protected Pengeluaran(Parcel in) {
        id = in.readInt();
        kategori = in.readString();
        id_user = in.readInt();
        jumlah_pengeluaran = in.readInt();
        catatan = in.readString();
        tanggal = in.readString();
        id_category = in.readInt();
    }

    public static final Creator<Pengeluaran> CREATOR = new Creator<Pengeluaran>() {
        @Override
        public Pengeluaran createFromParcel(Parcel in) {
            return new Pengeluaran(in);
        }

        @Override
        public Pengeluaran[] newArray(int size) {
            return new Pengeluaran[size];
        }
    };
}
