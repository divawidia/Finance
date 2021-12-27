package com.kelompok8.finance.database;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.kelompok8.finance.model.Category;
import com.kelompok8.finance.model.Dompet;
import com.kelompok8.finance.model.User;
import com.kelompok8.finance.ui.home.HomeActivity;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

public class DBHelper extends SQLiteOpenHelper {
    private final Context context;

    public DBHelper(Context context) {
        super(context, "finance.db", null, 2);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE session(id INTEGER PRIMARY KEY AUTOINCREMENT,username TEXT, password TEXT, login TEXT)");
        db.execSQL("INSERT INTO session(id, login) VALUES(1, 'kosong')");
        db.execSQL("CREATE TABLE user(id_user INTEGER PRIMARY KEY AUTOINCREMENT, email TEXT, username TEXT, password TEXT, tanggal_lahir TEXT, telepon TEXT, foto TEXT)");
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

    public Cursor login(String username_login, String password_login){
        SQLiteDatabase db=this.getReadableDatabase();
        User user;
        Cursor cursor = db.rawQuery("SELECT * FROM user WHERE username = '"+ username_login + "' AND password = '"+ password_login +"'", null);

        return cursor;
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
        String query = ("SELECT id_pengeluaran,  kategori.nama_kategori, pengeluaran.id_user, jumlah_pengeluaran, catatan, tanggal, kategori.id_kategori \n" +
                "FROM pengeluaran \n" +
                "INNER JOIN kategori ON kategori.id_kategori = pengeluaran.id_kategori\n" +
                "ORDER BY id_pengeluaran DESC");
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }

    public Cursor readPengeluaranGByCategory() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = ("SELECT id_pengeluaran,  kategori.nama_kategori, pengeluaran.id_user, SUM(jumlah_pengeluaran), catatan, tanggal, kategori.id_kategori \n" +
                "FROM pengeluaran \n" +
                "INNER JOIN kategori ON kategori.id_kategori = pengeluaran.id_kategori\n" +
                "GROUP BY pengeluaran.id_kategori\n" +
                "ORDER BY id_pengeluaran DESC");
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }

    public void deletePengeluaran(String id) {
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

    public void insertKategori(Integer id_user, String nama_kategori, Integer icon, String warna) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id_user", id_user);
        contentValues.put("nama_kategori", nama_kategori);
        contentValues.put("icon", icon);
        contentValues.put("warna", warna);
        db.insert("kategori", null, contentValues);
    }

    public Cursor readKategori(int idUser) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = ("SELECT*FROM kategori WHERE id_user = "+idUser);
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }

    public ArrayList<Category> getKategori(int idUser) {
        ArrayList<Category> arrayList = new ArrayList<>();
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery("SELECT*FROM kategori WHERE id_user = "+idUser, null);

        //looping semua baris dan menambahkan ke list
        if (cursor.moveToFirst()){
            do {
                Category category = new Category();
                category.setId(cursor.getInt(0));
                category.setIdUser(cursor.getInt(1));
                category.setNamaKategori(cursor.getString(2));
                category.setIcon(cursor.getInt(3));
                category.setWarna(cursor.getString(4));
                arrayList.add(category);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return arrayList;
    }

    public void deleteKategori(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        if(db.delete("kategori", "id_kategori" + "=" + id, null) > 0){
            Toasty.success(context, "Delete Success", Toast.LENGTH_SHORT, true).show();
        }else {
            Toasty.error(context, "Delete Failed", Toast.LENGTH_SHORT, true).show();
        }
    }

    public void updateKategori (Integer id, Integer id_user, String nama_kategori, Integer icon, String warna){
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
        Cursor cursor =db.rawQuery("SELECT * FROM session WHERE login = ?", new String[]{sessionValues});
        if (cursor.getCount() > 0){
            return true;
        }
        else{
            return false;
        }
    }

    public Boolean upgradeSession(String sessionValues, int id, String username, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("login", sessionValues);
        contentValues.put("username", username);
        contentValues.put("password", password);
        long update = db.update("session", contentValues, "id="+id, null);
        if(update == 1){
            return false;
        }
        else{
            return true;
        }
    }

    public Cursor getUserLogin(int idUser){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = ("SELECT*FROM user WHERE id_user = "+idUser);
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }

    public Boolean insertUser(String email, String username, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", email);
        contentValues.put("username", username);
        contentValues.put("password", password);
        long insert = db.insert("user", null, contentValues);
        db.execSQL("INSERT INTO dompet (id_user, nama_dompet, saldo_awal) VALUES (1, 'cash', 0)");
        if (insert == 1){
            return false;
        }
        else{
            return true;
        }
    }

//    public Boolean checkLogin(String username, String password){
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery("SELECT * FROM user WHERE username = ? AND password = ?", new String[]{username, password});
//        if(cursor.getCount() > 0){
//            return true;
//        }
//        else{
//            return false;
//        }
//    }
    public User checkLogin(String username, String password){
        User user = null;
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM user WHERE username = ? AND password = ?", new String[]{username, password});
            if(cursor.moveToFirst()){
                user = new User();
                user.setIdUser(cursor.getInt(0));
                user.setEmail(cursor.getString(1));
                user.setUsername(cursor.getString(2));
                user.setPassword(cursor.getString(3));
                user.setTanggal_lahir(cursor.getString(4));
                user.setTelepon(cursor.getString(5));
            }
        }catch (Exception e){
            user = null;
        }
        return user;
    }

    public User checkUsername(String username){
        User user = null;
        try {
            SQLiteDatabase sqLiteDatabase = getReadableDatabase();
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM user WHERE username = ?", new String[]{username});
            if (cursor.moveToFirst()){
                user = new User();
                user.setIdUser(cursor.getInt(0));
                user.setEmail(cursor.getString(1));
                user.setUsername(cursor.getString(2));
                user.setPassword(cursor.getString(3));
                user.setTanggal_lahir(cursor.getString(4));
                user.setTelepon(cursor.getString(5));
                user.setFoto(cursor.getString(6));
            }
        }catch (Exception e){
            user = null;
        }
        return user;
    }

//    public boolean checkUsername(String username){
//        SQLiteDatabase database = this.getReadableDatabase();
//        long jumlahRows = DatabaseUtils.queryNumEntries(database, "user", "id = ?", new String[]{username});
//        if (jumlahRows <= 1){
//            return true;
//        }else {
//            return false;
//        }
//    }

    public User checkPasswordLama(int id, String password){
        User user = null;
        try {
            SQLiteDatabase sqLiteDatabase = getReadableDatabase();
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM user WHERE id_user = " +id+ " AND password = ?", new String[]{password});
            if (cursor.moveToFirst()){
                user = new User();
                user.setIdUser(cursor.getInt(0));
                user.setEmail(cursor.getString(1));
                user.setUsername(cursor.getString(2));
                user.setPassword(cursor.getString(3));
                user.setTanggal_lahir(cursor.getString(4));
                user.setTelepon(cursor.getString(5));
            }
        }catch (Exception e){
            user = null;
        }
        return user;

    }

    public Dompet checkNamaDompet(int id, String nama){
        Dompet dompet = null;
        try {
            SQLiteDatabase sqLiteDatabase = getReadableDatabase();
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM dompet WHERE id_user = " +id+ " AND nama_dompet = ?", new String[]{nama});
            if (cursor.moveToFirst()){
                dompet = new Dompet();
                dompet.setId_dompet(cursor.getInt(0));
                dompet.setId_user(cursor.getInt(1));
                dompet.setNama_dompet(cursor.getString(2));
                dompet.setSaldo_awal(cursor.getInt(3));
            }
        }catch (Exception e){
            dompet = null;
        }
        return dompet;

    }

    public User findUser(int id){
        User user = null;
        try {
            SQLiteDatabase sqLiteDatabase = getReadableDatabase();
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM user WHERE id = ?", new String[]{String.valueOf(id)});
            if (cursor.moveToFirst()){
                user = new User();
                user.setIdUser(cursor.getInt(0));
                user.setEmail(cursor.getString(1));
                user.setUsername(cursor.getString(2));
                user.setPassword(cursor.getString(3));
                user.setTanggal_lahir(cursor.getString(4));
                user.setTelepon(cursor.getString(5));
            }
        }catch (Exception e){
            user = null;
        }
        return user;

    }

    public void updateUser(ContentValues contentValues, Integer id){
        SQLiteDatabase db =getWritableDatabase();
        db.update("user", contentValues, "id_user = " +id, null);
    }

    public void addDompet(ContentValues contentValues){
        SQLiteDatabase db =getWritableDatabase();
        db.insert("dompet", null, contentValues);
    }

    public ArrayList<Dompet> getDompet(int id) {
        ArrayList<Dompet> arrayList = new ArrayList<>();
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM dompet WHERE id_user = "+id, null);

        //looping semua baris dan menambahkan ke list
        if (cursor.moveToFirst()){
            do {
                Dompet dompet = new Dompet();
                dompet.setId_dompet(cursor.getInt(0));
                dompet.setId_user(cursor.getInt(1));
                dompet.setNama_dompet(cursor.getString(2));
                dompet.setSaldo_awal(cursor.getInt(3));
                arrayList.add(dompet);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return arrayList;
    }

    public Cursor getOneDompet(int idDompet){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = ("SELECT*FROM dompet WHERE id_dompet = "+idDompet);
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }

    public void updateDompet(ContentValues contentValues, Integer id){
        SQLiteDatabase db =getWritableDatabase();
        db.update("dompet", contentValues, "id_dompet = " +id, null);
    }

    public void deleteOneDompet(int idDompet){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("dompet", "id_dompet = ?", new String[]{String.valueOf(idDompet)});
        db.close();
    }

    public Boolean Logout (){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("login","");
        long update = db.update("session", contentValues, "id="+1, null);
        if(update == 1){
            return false;
        }
        else{
            return true;
        }
    }

    public Cursor getKategoriItem(Integer id){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = ("SELECT * FROM kategori WHERE id_kategori = " + id);
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }
}