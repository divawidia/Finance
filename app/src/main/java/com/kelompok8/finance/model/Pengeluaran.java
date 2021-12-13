package com.kelompok8.finance.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Pengeluaran implements Parcelable {
    private Integer id, id_kategori, id_user, jumlah_pengeluaran;
    private String tanggal, catatan;

    public Pengeluaran (Integer id, Integer id_kategori, Integer id_user, Integer jumlah_pengeluaran, String catatan, String tanggal){
        this.id = id;
        this.id_kategori = id_kategori;
        this.id_user = id_user;
        this.jumlah_pengeluaran = jumlah_pengeluaran;
        this.catatan = catatan;
        this.tanggal = tanggal;
    }

    public Integer getId() { return id; }
    public Integer getIdKategori() { return id_kategori; }
    public Integer getIdUser() { return id_user; }
    public Integer getJumlahPengeluaran() { return jumlah_pengeluaran; }
    public String getTanggal() { return tanggal; }
    public String getCatatan() { return catatan; }

    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.id_kategori);
        dest.writeInt(this.id_user);
        dest.writeInt(this.jumlah_pengeluaran);
        dest.writeString(this.catatan);
        dest.writeString(this.tanggal);
    }

    protected Pengeluaran(Parcel in) {
        id = in.readInt();
        id_kategori = in.readInt();
        id_user = in.readInt();
        jumlah_pengeluaran = in.readInt();
        catatan = in.readString();
        tanggal = in.readString();
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
