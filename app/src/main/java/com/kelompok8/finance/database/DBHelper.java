package com.kelompok8.finance.database;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.kelompok8.finance.ui.home.HomeActivity;

import es.dmoral.toasty.Toasty;

public class DBHelper extends SQLiteOpenHelper {
    private final Context context;

    public DBHelper(Context context) {
        super(context, "finance.db", null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE session(id integer PRIMARY KEY, login text)");
        db.execSQL("CREATE TABLE user(id_user INTEGER PRIMARY KEY AUTOINCREMENT, email TEXT, username TEXT, password TEXT, tanggal_lahir TEXT, telepon TEXT)");
        db.execSQL("CREATE TABLE kategori(id_kategori INTEGER PRIMARY KEY AUTOINCREMENT, id_user INTEGER, nama_kategori TEXT, icon TEXT, warna TEXT)");
        db.execSQL("CREATE TABLE dompet(id_dompet INTEGER PRIMARY KEY AUTOINCREMENT, id_user INTEGER, nama_dompet TEXT, saldo_awal INTEGER)");
        db.execSQL("CREATE TABLE pengeluaran(id_pengeluaran INTEGER PRIMARY KEY AUTOINCREMENT, id_kategori INTEGER, id_user INTEGER, jumlah_pengeluaran INTEGER, catatan TEXT, tanggal TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS finance.db");
        onCreate(db);
    }

    public void insert() {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
    }

//    public Cursor read() {
//        SQLiteDatabase db = this.getReadableDatabase();
//        String query = ("SELECT*FROM");
//        Cursor cursor = db.rawQuery(query, null);
//        return cursor;
//    }

    public void delete(String id_pengeluaran) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM pengeluaran WHERE id_pengeluaran='" + id_pengeluaran + "'");
    }

    public void update(String id_user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
    }

    public void insertPengeluaran(Integer id_kategori, Integer id_user, Integer jumlah_pengeluaran, String catatan, String tanggal) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id_kategori", id_kategori);
        contentValues.put("id_user", id_user);
        contentValues.put("jumlah_pengeluaran", jumlah_pengeluaran);
        contentValues.put("catatan", catatan);
        contentValues.put("tanggal", tanggal);
        db.insert("pengeluaran", null, contentValues);
    }

    public Cursor readPengeluaran() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = ("SELECT id_pengeluaran,  kategori.nama_kategori, pengeluaran.id_user, jumlah_pengeluaran, catatan, tanggal \n" +
                "FROM pengeluaran \n" +
                "INNER JOIN kategori ON kategori.id_kategori = pengeluaran.id_kategori\n" +
                "ORDER BY id_pengeluaran DESC");
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }

    public Cursor readPengeluaranGByCategory() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = ("SELECT id_pengeluaran,  kategori.nama_kategori, pengeluaran.id_user, SUM(jumlah_pengeluaran), catatan, tanggal \n" +
                "FROM pengeluaran \n" +
                "INNER JOIN kategori ON kategori.id_kategori = pengeluaran.id_kategori\n" +
                "GROUP BY pengeluaran.id_kategori\n" +
                "ORDER BY id_pengeluaran DESC");
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }

    public void deletePengeluaran(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        if(db.delete("pengeluaran", "id_pengeluaran" + "=" + id, null) > 0){
            Toasty.success(context, "Delete Success", Toast.LENGTH_SHORT, true).show();
        }else {
            Toasty.error(context, "Delete Failed", Toast.LENGTH_SHORT, true).show();
        }
    }

    public void updatePengeluaran (Integer id, Integer id_kategori, Integer id_user, Integer jumlah_pengeluaran, String tanggal){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id_kategori", id_kategori);
        contentValues.put("id_user", id_user);
        contentValues.put("jumlah_pengeluaran", jumlah_pengeluaran);
        contentValues.put("tanggal", tanggal);
        if(db.update("pengeluaran", contentValues, "id_pengeluaran" + "=" + id, null) > 0){
            Toasty.success(context, "Update Success", Toast.LENGTH_SHORT, true).show();
            Intent intent = new Intent(context, HomeActivity.class);
            context.startActivity(intent);
        }else {
            Toasty.error(context, "Update Failed", Toast.LENGTH_SHORT, true).show();
        }
    }

    public void insertKategori(Integer id_user, String nama_kategori, String icon, String warna) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id_user", id_user);
        contentValues.put("nama_kategori", nama_kategori);
        contentValues.put("icon", icon);
        contentValues.put("warna", warna);
        db.insert("kategori", null, contentValues);
    }

    public Cursor readKategori() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = ("SELECT*FROM kategori ORDER BY id_kategori DESC");
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }

    public void deleteKategori(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        if(db.delete("kategori", "id_kategori" + "=" + id, null) > 0){
            Toasty.success(context, "Delete Success", Toast.LENGTH_SHORT, true).show();
        }else {
            Toasty.error(context, "Delete Failed", Toast.LENGTH_SHORT, true).show();
        }
    }

    public void updateKategori (Integer id, Integer id_user, String nama_kategori, String icon, String warna){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id_user", id_user);
        contentValues.put("nama_kategori", nama_kategori);
        contentValues.put("icon", icon);
        contentValues.put("warna", warna);
        if(db.update("kategori", contentValues, "id_kategori" + "=" + id, null) > 0){
            Toasty.success(context, "Update Success", Toast.LENGTH_SHORT, true).show();
            Intent intent = new Intent(context, HomeActivity.class);
            context.startActivity(intent);
        }else {
            Toasty.error(context, "Update Failed", Toast.LENGTH_SHORT, true).show();
        }
    }

    public Boolean checkSession(String sessionValues) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =db.rawQuery("SELECT * FROM sesion WHERE login = ?", new String[]{sessionValues});
        if (cursor.getCount() > 0){
            return true;
        }
        else{
            return false;
        }
    }

    public Boolean upgradeSession(String sessionValues, int id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("com/kelompok8/finance/ui/login", sessionValues);
        long update = db.update("session", contentValues, "id="+id, null);
        if(update == 1){
            return false;
        }
        else{
            return true;
        }
    }

    public Boolean insertUser(String strEmail, String username, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);
        long insert = db.insert("user", null, contentValues);
        if (insert == 1){
            return false;
        }
        else{
            return true;
        }
    }

    public Boolean checkLogin(String username, String password){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM user WHERE username = ? AND password = ?", new String[]{username, password});
        if(cursor.getCount() > 0){
            return true;
        }
        else{
            return false;
        }
    }
}