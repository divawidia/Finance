package com.kelompok8.finance.ui.stats;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.kelompok8.finance.AddPengeluaranActivity;
import com.kelompok8.finance.R;
import com.kelompok8.finance.adapter.PengeluaranAdapter;
import com.kelompok8.finance.adapter.PengeluaranOneLineAdapter;
import com.kelompok8.finance.adapter.TransaksiAdapter;
import com.kelompok8.finance.database.DBHelper;
import com.kelompok8.finance.model.Pengeluaran;
import com.kelompok8.finance.ui.home.HomeActivity;

import java.util.ArrayList;

public class StatisticActivity extends AppCompatActivity {

    private RecyclerView recyclerViewTransaksi;
    private RecyclerView recyclerViewPengeluaran;
    private ArrayList<Pengeluaran> pengeluaranHolder = new ArrayList<>();
    private ArrayList<Pengeluaran> pengeluaranHolder2 = new ArrayList<>();
    private SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);
        getSupportActionBar().hide();

        Button addBtn = (Button) findViewById(R.id.createButton);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StatisticActivity.this, AddPengeluaranActivity.class));
            }
        });

        DBHelper db = new DBHelper(this);

        recyclerViewTransaksi = (RecyclerView) findViewById(R.id.listTransaksi);
        recyclerViewTransaksi.setLayoutManager(new LinearLayoutManager(this));

        Cursor cursor = new DBHelper(this).readPengeluaran();

        while(cursor.moveToNext()){
            Pengeluaran pengeluaran = new Pengeluaran(cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getInt(2),
                    cursor.getInt(3),
                    cursor.getString(4),
                    cursor.getString(5));
            pengeluaranHolder.add(pengeluaran);
        }

        TransaksiAdapter pengeluaranAdapter = new TransaksiAdapter(pengeluaranHolder, StatisticActivity.this, sqLiteDatabase);
        recyclerViewTransaksi.setAdapter((RecyclerView.Adapter) pengeluaranAdapter);


        //code spacer


        recyclerViewPengeluaran = (RecyclerView) findViewById(R.id.listPengeluaran);
        LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(StatisticActivity.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewPengeluaran.setLayoutManager(horizontalLayoutManagaer);

        Cursor cursor2 = new DBHelper(this).readPengeluaranGByCategory();

        while(cursor2.moveToNext()){
            Pengeluaran pengeluaran = new Pengeluaran(cursor2.getInt(0),
                    cursor2.getString(1),
                    cursor2.getInt(2),
                    cursor2.getInt(3),
                    cursor2.getString(4),
                    cursor2.getString(5));
            pengeluaranHolder2.add(pengeluaran);
        }

        PengeluaranOneLineAdapter pengeluaranOneLineAdapter = new PengeluaranOneLineAdapter(pengeluaranHolder2, StatisticActivity.this, sqLiteDatabase);
        recyclerViewPengeluaran.setAdapter((RecyclerView.Adapter) pengeluaranOneLineAdapter);
    }
}