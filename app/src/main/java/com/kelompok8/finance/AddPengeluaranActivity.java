package com.kelompok8.finance;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kelompok8.finance.adapter.CategoryAdapter;
import com.kelompok8.finance.database.DBHelper;
import com.kelompok8.finance.model.Category;
import com.kelompok8.finance.model.Pengeluaran;
import com.kelompok8.finance.ui.stats.StatisticActivity;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

public class AddPengeluaranActivity extends AppCompatActivity {
    EditText jumlahUang, catatan;
    private DBHelper db;
    private Pengeluaran pengeluaran;
    private Button btnSubmit;
    private ImageView showCategories;
    private RecyclerView recyclerViewKategori;
    private ArrayList<Category> kategoriHolder = new ArrayList<>();
    private SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pengeluaran);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00903D")));
        getSupportActionBar().setTitle("Tambah Pengeluaran");

        jumlahUang = findViewById(R.id.amountInput);
        catatan = findViewById(R.id.noteInput);
        showCategories = findViewById(R.id.showCategory);
        showCategories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddPengeluaranActivity.this, CategoryActivity.class);
                startActivity(intent);
            }
        });

        DBHelper db = new DBHelper(this);

        recyclerViewKategori = (RecyclerView) findViewById(R.id.listCategory);
        recyclerViewKategori.setLayoutManager(new GridLayoutManager(this, 4));

        Cursor cursor = new DBHelper(this).readKategori();

        while(cursor.moveToNext()){
            Category category = new Category(cursor.getInt(0),
                    cursor.getInt(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4));
            kategoriHolder.add(category);
        }

        CategoryAdapter categoryAdapter = new CategoryAdapter(kategoriHolder, AddPengeluaranActivity.this, sqLiteDatabase);
        recyclerViewKategori.setAdapter((RecyclerView.Adapter) categoryAdapter);

        btnSubmit = (Button) findViewById(R.id.button_update);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pengeluaran = new Pengeluaran(0, "CatGory", 1,
                        Integer.parseInt(jumlahUang.getText().toString()),
                        catatan.getText().toString(),
                        "14 Desember 2021");

                saveDataToDB();

                Intent intent = new Intent(AddPengeluaranActivity.this, StatisticActivity.class);
                startActivity(intent);
            }
        });
    }

    private void saveDataToDB(){
        db = new DBHelper(this);
        db.insertPengeluaran(1,
                pengeluaran.getIdUser(),
                pengeluaran.getJumlahPengeluaran(),
                pengeluaran.getCatatan(),
                pengeluaran.getTanggal());
    }
}
