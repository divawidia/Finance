package com.kelompok8.finance;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kelompok8.finance.adapter.CategoryAdapter;
import com.kelompok8.finance.adapter.IconAdapter;
import com.kelompok8.finance.database.DBHelper;
import com.kelompok8.finance.helper.IconPickerHelper;
import com.kelompok8.finance.model.Category;
import com.kelompok8.finance.ui.stats.StatisticActivity;
import com.maltaisn.icondialog.IconDialog;
import com.maltaisn.icondialog.IconDialogSettings;
import com.maltaisn.icondialog.pack.IconPack;
import com.thebluealliance.spectrum.SpectrumPalette;

import java.util.ArrayList;
import java.util.List;

public class AddCategoryActivity extends AppCompatActivity implements IconDialog.Callback {
    private Category category;
    private DBHelper db;
    private static final String ICON_DIALOG_TAG = "icon-dialog";
    private String colorString;
    private Integer iconPath, id;
    private ArrayList<Integer> mIconList;
    private IconAdapter mIconAdapter;
    private int idUser;
    String action, namaCategoryVal, iconVal, warnaVal;
    TextView colorTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00903D")));
        getSupportActionBar().setTitle("Tambah Kategori");

        IconDialog dialog = (IconDialog) getSupportFragmentManager().findFragmentByTag(ICON_DIALOG_TAG);
        IconDialog iconDialog = dialog != null ? dialog
                : IconDialog.newInstance(new IconDialogSettings.Builder().build());
        idUser = this.getSharedPreferences("login_session", 0).getInt("key_id", 0);

        SpectrumPalette spectrumPalette = (SpectrumPalette) findViewById(R.id.colorPicker);
        spectrumPalette.setOnColorSelectedListener(new SpectrumPalette.OnColorSelectedListener() {
            @Override
            public void onColorSelected(@ColorInt int color) {
                colorString = "#" + Integer.toHexString(color).toUpperCase();
            }
        });

        initIconList();
        Spinner spinnerIcons = findViewById(R.id.spinnerIcon);
        mIconAdapter = new IconAdapter(this, mIconList);
        spinnerIcons.setAdapter(mIconAdapter);

        spinnerIcons.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Integer clickedItem = (Integer) parent.getItemAtPosition(position);
                iconPath = clickedItem;
//                Toast.makeText(AddCategoryActivity.this, String.valueOf(clickedItem) + " selected", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        EditText namaCategory = findViewById(R.id.categoryInput);
        Button btnSubmit = (Button) findViewById(R.id.button_update);
        colorTv = findViewById(R.id.colorLabel);

        Intent intent = getIntent();

        action = intent.getStringExtra("action");

        Log.v("action", action);

        if(action.equals("edit")){
            btnSubmit.setText("Update");
            id = Integer.valueOf(intent.getStringExtra("id"));
            namaCategoryVal = intent.getStringExtra("nama_kategori");
            iconVal = intent.getStringExtra("icon");
            warnaVal = intent.getStringExtra("warna");

            namaCategory.setText(namaCategoryVal);
            selectSpinnerItemByValue(spinnerIcons, Integer.parseInt(iconVal));
            spectrumPalette.setVisibility(View.GONE);
            colorTv.setVisibility(View.GONE);

            btnSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    category = new Category(id, idUser,
                            namaCategory.getText().toString(),
                            iconPath,
                            warnaVal);

                    db = new DBHelper(AddCategoryActivity.this);
                    db.updateKategori(category.getId(),
                            category.getIdUser(),
                            category.getNamaKategori(),
                            category.getIcon(),
                            category.getWarna());

                    Intent intent = new Intent(AddCategoryActivity.this, CategoryActivity.class);
                    startActivity(intent);
                }
            });
        }else {
            btnSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    category = new Category(0, idUser,
                            namaCategory.getText().toString(),
                            iconPath,
                            colorString);

                    saveDataToDB();

                    Intent intent = new Intent(AddCategoryActivity.this, CategoryActivity.class);
                    startActivity(intent);
                }
            });
        }

    }

    private void saveDataToDB(){
        db = new DBHelper(this);
        db.insertKategori(category.getIdUser(),
                category.getNamaKategori(),
                category.getIcon(),
                category.getWarna());
    }

    @Nullable
    @Override
    public IconPack getIconDialogIconPack() {
        return ((IconPickerHelper) getApplication()).getIconPack();
    }

    @Override
    public void onIconDialogCancelled() {}

    @Override
    public void onIconDialogIconsSelected(@NonNull IconDialog iconDialog, @NonNull List<com.maltaisn.icondialog.data.Icon> list) {
        // Show a toast with the list of selected icon IDs.
        StringBuilder sb = new StringBuilder();
        for (com.maltaisn.icondialog.data.Icon icon : list) {
            sb.append(icon.getId());
            sb.append(", ");
        }
        sb.delete(sb.length() - 2, sb.length());
        Toast.makeText(this, "Icons selected: " + sb, Toast.LENGTH_SHORT).show();
    }

    private void initIconList() {
        mIconList = new ArrayList<>();
        mIconList.add(new Integer(R.drawable.focus));
        mIconList.add(new Integer(R.drawable.training));
        mIconList.add(new Integer(R.drawable.struggle));
        mIconList.add(new Integer(R.drawable.arm));
        mIconList.add(new Integer(R.drawable.color));
        mIconList.add(new Integer(R.drawable.flute));
        mIconList.add(new Integer(R.drawable.mortarboard));
        mIconList.add(new Integer(R.drawable.school));
        mIconList.add(new Integer(R.drawable.book));
        mIconList.add(new Integer(R.drawable.puzzle));
        mIconList.add(new Integer(R.drawable.strong));
    }

    public static void selectSpinnerItemByValue(Spinner spnr, int value) {
        IconAdapter adapter = (IconAdapter) spnr.getAdapter();
        for (int position = 0; position < adapter.getCount(); position++) {
            if(adapter.getItem(position) == value) {
                spnr.setSelection(position);
                return;
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, CategoryActivity.class);
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
