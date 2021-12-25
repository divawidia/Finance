package com.kelompok8.finance.model;

public class Dompet {
    int id_dompet;
    int id_user;
    int saldo_awal;
    String nama_dompet;

    public Dompet(int id_dompet, int id_user, int saldo_awal, String nama_dompet) {
        this.id_dompet = id_dompet;
        this.id_user = id_user;
        this.saldo_awal = saldo_awal;
        this.nama_dompet = nama_dompet;
    }

    public Dompet() {

    }

    public int getId_dompet() {
        return id_dompet;
    }

    public void setId_dompet(int id_dompet) {
        this.id_dompet = id_dompet;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public int getSaldo_awal() {
        return saldo_awal;
    }

    public void setSaldo_awal(int saldo_awal) {
        this.saldo_awal = saldo_awal;
    }

    public String getNama_dompet() {
        return nama_dompet;
    }

    public void setNama_dompet(String nama_dompet) {
        this.nama_dompet = nama_dompet;
    }
}
