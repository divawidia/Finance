package com.kelompok8.finance.model;

import java.io.Serializable;

public class User implements Serializable{
    int id_user;
    String email;
    String username;
    String password;
    String tanggal_lahir;
    String telepon;

    public User(int id_user, String email, String username, String password, String tanggal_lahir, String telepon) {
        this.id_user = id_user;
        this.email = email;
        this.username = username;
        this.password = password;
        this.tanggal_lahir = tanggal_lahir;
        this.telepon = telepon;
    }

    public User() {

    }

    public int getIdUser() {
        return id_user;
    }

    public void setIdUser(int id_user) {
        this.id_user = id_user;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTanggal_lahir() {
        return tanggal_lahir;
    }

    public void setTanggal_lahir(String tanggal_lahir) {
        this.tanggal_lahir = tanggal_lahir;
    }

    public String getTelepon() {
        return telepon;
    }

    public void setTelepon(String telepon) {
        this.telepon = telepon;
    }
}
