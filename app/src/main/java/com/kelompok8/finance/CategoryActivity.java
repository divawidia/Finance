package com.kelompok8.finance;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kelompok8.finance.adapter.CategoryAdapter;
import com.kelompok8.finance.adapter.TransaksiAdapter;
import com.kelompok8.finance.database.DBHelper;
import com.kelompok8.finance.model.Category;
import com.kelompok8.finance.model.Pengeluaran;
import com.kelompok8.finance.ui.home.HomeActivity;
import com.kelompok8.finance.ui.stats.StatisticActivity;

import java.util.ArrayList;

public class CategoryActivity extends AppCompatActivity {
    private RecyclerView recyclerViewKategori;
    private ArrayList<Category> kategoriHolder;
    private SQLiteDatabase sqLiteDatabase;
    private int idUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_category);
        idUser = this.getSharedPreferences("login_session", 0).getInt("key_id", 0);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00903D")));
        getSupportActionBar().setTitle("Daftar Kategori");

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CategoryActivity.this, AddCategoryActivity.class);
                intent.putExtra("action", "add");
                startActivity(intent);
            }
        });
    }

    private void init(){
        DBHelper db = new DBHelper(this);

        recyclerViewKategori = (RecyclerView) findViewById(R.id.listPengeluaran);
        recyclerViewKategori.setLayoutManager(new GridLayoutManager(this, 4));

        Cursor cursor = new DBHelper(this).readKategori(idUser);

        ArrayList<Category> kategoriHolder = new ArrayList<>();

        while(cursor.moveToNext()){
            Category category = new Category(cursor.getInt(0),
                    cursor.getInt(1),
                    cursor.getString(2),
                    cursor.getInt(3),
                    cursor.getString(4));
            kategoriHolder.add(category);
        }

        CategoryAdapter categoryAdapter = new CategoryAdapter(kategoriHolder, CategoryActivity.this, sqLiteDatabase);
        recyclerViewKategori.setAdapter((RecyclerView.Adapter) categoryAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        init();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, AddPengeluaranActivity.class);
        intent.putExtra("action", "add");
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return false;
    }

}