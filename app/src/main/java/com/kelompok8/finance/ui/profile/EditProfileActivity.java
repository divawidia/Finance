package com.kelompok8.finance.ui.profile;

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
import android.widget.TextView;
import android.widget.Toast;

import com.kelompok8.finance.LoginManager;
import com.kelompok8.finance.R;
import com.kelompok8.finance.database.DBHelper;
import com.kelompok8.finance.model.User;

public class EditProfileActivity extends AppCompatActivity {
    private User user;
    private DBHelper dbHelper;
    int idUser;

    String username, email, tglLahir, telepon;

    LoginManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        EditText editTglLahir = findViewById(R.id.editTextTglLahir);
        EditText editEmail = findViewById(R.id.editTextEmail);
        EditText editUsername = findViewById(R.id.editTextUsername);
        EditText editTelepon = findViewById(R.id.editTextTelepon);
        Button simpan = findViewById(R.id.btnSimpanProfile);

        idUser = this.getSharedPreferences("login_session", 0).getInt("key_id", 0);
        dbHelper = new DBHelper(this);

        try {
            Cursor cursor = (Cursor) dbHelper.getUserLogin(idUser);
            cursor.moveToLast();
            user =new User(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5)
            );
        }catch (Exception e){
            Log.e("error user", "Error:" + e.getMessage());
            return;
        }

        editTglLahir.setText(user.getTanggal_lahir());
        editEmail.setText(user.getEmail());
        editTelepon.setText(user.getTelepon());
        editUsername.setText(user.getUsername());

        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = editUsername.getText().toString();
                email = editEmail.getText().toString();
                tglLahir = editTglLahir.getText().toString();
                telepon = editTelepon.getText().toString();

                if (TextUtils.isEmpty(username)){
                    editUsername.requestFocus();
                    editUsername.setError("Username harus diisi!");
                }else if (TextUtils.isEmpty(email)){
                    editEmail.requestFocus();
                    editEmail.setError("Email harus diisi!");
                }else if (TextUtils.isEmpty(tglLahir)){
                    editTglLahir.requestFocus();
                    editTglLahir.setError("Tanggal lahir harus diisi!");
                }else if (TextUtils.isEmpty(tglLahir)){
                    editTglLahir.requestFocus();
                    editTelepon.setError("Email harus diisi!");
                }else {

                    User currentUser = dbHelper.findUser(user.getIdUser());
                    User check = dbHelper.checkUsername(editUsername.getText().toString());

                    if (check == null){
                        ContentValues values =new ContentValues();
                        values.put("username", username);
                        values.put("email", email);
                        values.put("tanggal_lahir", tglLahir);
                        values.put("telepon", telepon);
                        dbHelper.updateUser(values, idUser);

                        Toast.makeText(EditProfileActivity.this, "Profile berhasil diubah", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(EditProfileActivity.this, ProfileActivity.class);
                        startActivity(intent);
                    }
                    else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                        builder.setTitle("Error");
                        builder.setMessage("Change profile error");
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

    }
}