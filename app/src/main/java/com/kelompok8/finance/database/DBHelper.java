package com.kelompok8.finance;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class DBHelper extends SQLiteOpenHelper {
    private final Context context;

    public DBHelper(Context context) {
        super(context, "finance.db", null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE user(id_user INTEGER PRIMARY KEY AUTO INCREMENT, email TEXT, username TEXT, password TEXT, tanggal_lahir TEXT, telepon TEXT)");
        db.execSQL("CREATE TABLE kategori(id_kategori INTEGER PRIMARY KEY AUTO INCREMENT, id_user INTEGER, nama_kategori TEXT, icon TEXT, warna TEXT)");
        db.execSQL("CREATE TABLE dompet(id_dompet INTEGER PRIMARY KEY AUTO INCREMENT, id_user INTEGER, nama_dompet TEXT, saldo_awal INTEGER");
        db.execSQL("CREATE TABLE id_pengeluaran(id_pengeluaran INTEGER PRIMARY KEY AUTO INCREMENT, id_kategori INTEGER, id_user INTEGER, jumlah_pengeluaran INTEGER, tanggal TEXT");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS finance.db");
        onCreate(db);
    }

    public Boolean insert() {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
    }

    public Cursor read() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = ("SELECT*FROM");
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }

    public void delete(String id_pengeluaran) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM pengeluaran WHERE id_pengeluaran='" + id_pengeluaran + "'");
    }

    void update(String id_user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        }
    }
}