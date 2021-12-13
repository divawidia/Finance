package com.kelompok8.finance.ui.home;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Group;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kelompok8.finance.R;
import com.kelompok8.finance.adapter.PengeluaranAdapter;
import com.kelompok8.finance.adapter.TransaksiAdapter;
import com.kelompok8.finance.database.DBHelper;
import com.kelompok8.finance.model.Pengeluaran;
import com.kelompok8.finance.ui.profile.EditPasswordActivity;
import com.kelompok8.finance.ui.profile.EditProfileActivity;
import com.kelompok8.finance.ui.profile.ProfileActivity;
import com.kelompok8.finance.ui.stats.StatisticActivity;
import com.kelompok8.finance.ui.wallets.AddDompetActivity;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView recyclerViewPengeluaran, recyclerViewTransaksi;
    private SQLiteDatabase sqLiteDatabase;
    private ArrayList<Pengeluaran> pengeluaranHolder = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().hide();

        TextView pengeluaranShow = findViewById(R.id.pengeluaranShowAll);
        TextView transaksiShow = findViewById(R.id.transaksiShowAll);
        TextView emptyPengeluaran = findViewById(R.id.pengeluaranNull);
        TextView emptyTransaksi = findViewById(R.id.transaksiNull);
        ImageView addDompet = findViewById(R.id.dompetkuAdd);
        Group profile = findViewById(R.id.groupProfile);

        pengeluaranShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, StatisticActivity.class));
            }
        });
        transaksiShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, StatisticActivity.class));
            }
        });
        addDompet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, AddDompetActivity.class));
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, ProfileActivity.class));
            }
        });

        DBHelper db = new DBHelper(this);

        recyclerViewPengeluaran = (RecyclerView) findViewById(R.id.listPengeluaran);
        recyclerViewPengeluaran.setLayoutManager(new LinearLayoutManager(this));


        recyclerViewTransaksi = (RecyclerView) findViewById(R.id.listTransaksi);
        recyclerViewTransaksi.setLayoutManager(new LinearLayoutManager(this));

        Cursor cursor = new DBHelper(this).readPengeluaran();

        if (cursor.getCount()  == 0) {
            emptyPengeluaran.setVisibility(View.VISIBLE);
            emptyTransaksi.setVisibility(View.VISIBLE);
        }

//        if (cursor.getCount() > 0) {
//            emptyView.setVisibility(View.GONE);
//        }
//        else {
//            emptyView.setVisibility(View.VISIBLE);
//        }

        while(cursor.moveToNext()){
            Pengeluaran pengeluaran = new Pengeluaran(cursor.getInt(0),
                    cursor.getInt(1),
                    cursor.getInt(2),
                    cursor.getInt(3),
                    cursor.getString(4),
                    cursor.getString(5));
            pengeluaranHolder.add(pengeluaran);
        }

        PengeluaranAdapter pengeluaranAdapter = new PengeluaranAdapter(pengeluaranHolder, HomeActivity.this, sqLiteDatabase);
        recyclerViewPengeluaran.setAdapter((RecyclerView.Adapter) pengeluaranAdapter);


        TransaksiAdapter transaksiAdapter = new TransaksiAdapter(pengeluaranHolder, HomeActivity.this, sqLiteDatabase);
        recyclerViewTransaksi.setAdapter((RecyclerView.Adapter) transaksiAdapter);
    }
}