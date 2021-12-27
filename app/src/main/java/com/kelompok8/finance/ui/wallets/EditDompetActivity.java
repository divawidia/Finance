package com.kelompok8.finance.ui.wallets;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.kelompok8.finance.R;
import com.kelompok8.finance.database.DBHelper;
import com.kelompok8.finance.model.Dompet;
import com.kelompok8.finance.model.User;
import com.kelompok8.finance.ui.home.HomeActivity;
import com.kelompok8.finance.ui.profile.EditProfileActivity;
import com.kelompok8.finance.ui.profile.ProfileActivity;

public class EditDompetActivity extends AppCompatActivity {

    private int idDompet;
    private DBHelper dbHelper;
    private Dompet dompet;
    private int idUser;
    private User user;

    private String namaDompet, jumlahSaldo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_dompet);
        getSupportActionBar().hide();

        EditText editNamaDompet = findViewById(R.id.editNamaDompet);
        EditText editJumlahSaldo = findViewById(R.id.editJumlahDompet);

        Button buttonEdit = findViewById(R.id.buttonEditWallet);
        Button buttonDelete = findViewById(R.id.buttonDeleteWallet);

        ImageView btnBack = findViewById(R.id.ic_backHome);

        Intent intent = getIntent();
        idDompet = intent.getExtras().getInt("id_dompet");

        dbHelper = new DBHelper(this);

        try {
            Cursor cursor = (Cursor) dbHelper.getOneDompet(idDompet);
            cursor.moveToLast();
            dompet =new Dompet(
                    cursor.getInt(0),
                    cursor.getInt(1),
                    cursor.getInt(3),
                    cursor.getString(2)
            );
        }catch (Exception e){
            Log.e("error user", "Error:" + e.getMessage());
            return;
        }

        idUser = this.getSharedPreferences("login_session", 0).getInt("key_id", 0);
        try {
            Cursor cursor = (Cursor) dbHelper.getUserLogin(idUser);
            cursor.moveToLast();
            user =new User(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(6)
            );
        }catch (Exception e){
            Log.e("error user", "Error:" + e.getMessage());
            return;
        }

        editNamaDompet.setText(dompet.getNama_dompet());
        editJumlahSaldo.setText(String.valueOf(dompet.getSaldo_awal()));

        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                namaDompet = editNamaDompet.getText().toString();
                jumlahSaldo = editJumlahSaldo.getText().toString();

                if (TextUtils.isEmpty(namaDompet)) {
                    editNamaDompet.requestFocus();
                    editNamaDompet.setError("Nama dompet harus diisi!");
                } else if (TextUtils.isEmpty(jumlahSaldo)) {
                    editJumlahSaldo.requestFocus();
                    editJumlahSaldo.setError("Jumlah dompet  harus diisi!");
                } else {
                    Dompet check = dbHelper.checkNamaDompet(idUser, namaDompet);

                    if (check == null) {
                        ContentValues values = new ContentValues();
                        values.put("id_user", idUser);
                        values.put("nama_dompet", namaDompet);
                        values.put("saldo_awal", jumlahSaldo);
                        dbHelper.updateDompet(values, idDompet);

                        Toast sukses = Toast.makeText(getApplicationContext(), "Berhasil menambahkan dompet", Toast.LENGTH_SHORT);
                        sukses.show();

                        Intent intent = new Intent(EditDompetActivity.this, HomeActivity.class);
                        startActivity(intent);
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                        builder.setTitle("Error");
                        builder.setMessage("Nama dompet sudah ada");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                        builder.show();
                    }
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent home = new Intent(EditDompetActivity.this, HomeActivity.class);
                startActivity(home);
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Peringatan!");
                builder.setMessage("Apakah anda yakin ingi menghapus dompet ini?");
                builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dbHelper.deleteOneDompet(idDompet);

                        Toast sukses = Toast.makeText(getApplicationContext(), "Dompet berhasil dihapus", Toast.LENGTH_SHORT);
                        sukses.show();

                        Intent intent = new Intent(EditDompetActivity.this, HomeActivity.class);
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                builder.show();
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(EditDompetActivity.this, HomeActivity.class);
        startActivity(intent);
    }
}