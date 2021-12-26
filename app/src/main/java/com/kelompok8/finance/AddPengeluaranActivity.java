package com.kelompok8.finance;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kelompok8.finance.adapter.CategoryAdapter;
import com.kelompok8.finance.adapter.CategorySelectAdapter;
import com.kelompok8.finance.adapter.DompetSelectAdapter;
import com.kelompok8.finance.adapter.IconAdapter;
import com.kelompok8.finance.database.DBHelper;
import com.kelompok8.finance.model.Category;
import com.kelompok8.finance.model.Dompet;
import com.kelompok8.finance.model.Pengeluaran;
import com.kelompok8.finance.ui.stats.StatisticActivity;

import java.util.ArrayList;
import java.util.Calendar;

import es.dmoral.toasty.Toasty;

public class AddPengeluaranActivity extends AppCompatActivity {
    EditText jumlahUang, catatan;
    TextView dateInput;
    DatePickerDialog.OnDateSetListener setListener;
    private DBHelper db;
    private Pengeluaran pengeluaran;
    private Button btnSubmit;
    private ImageView showCategories;
    private RecyclerView recyclerViewKategori;
    private ArrayList<Category> kategoriHolder = new ArrayList<>();
    private SQLiteDatabase sqLiteDatabase;
    private ArrayList<Dompet> mDompetList;
    private DompetSelectAdapter mDompetAdapter;
    private ArrayList<Category> mCategoryList;
    private CategorySelectAdapter mCategoryAdapter;
    private int idUser;
    private Integer dompetId, categoryId;
    String tanggal;

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



        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        dateInput =  (TextView) findViewById(R.id.dateInput);
        dateInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog;
                datePickerDialog = new DatePickerDialog(AddPengeluaranActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month = month + 1;
                        String date = day + "/" + month + "/" + year;
                        dateInput.setText(date);
                        tanggal = date;
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        DBHelper db = new DBHelper(this);
        idUser = this.getSharedPreferences("login_session", 0).getInt("key_id", 0);
        mDompetList = db.getDompet(idUser);

        Spinner spinnerDompets = findViewById(R.id.walletInput);
        mDompetAdapter = new DompetSelectAdapter(this, mDompetList);
        spinnerDompets.setAdapter(mDompetAdapter);

        spinnerDompets.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Dompet clickedItem = (Dompet) parent.getItemAtPosition(position);
                dompetId = clickedItem.getId_dompet();
//                Toast.makeText(AddCategoryActivity.this, String.valueOf(clickedItem) + " selected", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mCategoryList = db.getKategori(idUser);

        Spinner spinnerCategories = findViewById(R.id.categoryInput);
        mCategoryAdapter = new CategorySelectAdapter(this, mCategoryList);
        spinnerCategories.setAdapter(mCategoryAdapter);

        spinnerCategories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                Dompet clickedItem = (Dompet) parent.getItemAtPosition(position);
//                dompetId = clickedItem.getId_dompet();
//                Toast.makeText(AddCategoryActivity.this, String.valueOf(clickedItem) + " selected", Toast.LENGTH_SHORT).show();
                Category clickedItem = (Category) parent.getItemAtPosition(position);
                categoryId = clickedItem.getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnSubmit = (Button) findViewById(R.id.button_update);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pengeluaran = new Pengeluaran(0, "CatGory", idUser,
                        Integer.parseInt(jumlahUang.getText().toString()),
                        catatan.getText().toString(),
                        tanggal, categoryId);

                saveDataToDB();

                Intent intent = new Intent(AddPengeluaranActivity.this, StatisticActivity.class);
                startActivity(intent);
            }
        });
    }

    private void saveDataToDB(){
        db = new DBHelper(this);
        db.insertPengeluaran(pengeluaran.getIdCategory(),
                pengeluaran.getIdUser(),
                pengeluaran.getJumlahPengeluaran(),
                pengeluaran.getCatatan(),
                pengeluaran.getTanggal());
    }
}
