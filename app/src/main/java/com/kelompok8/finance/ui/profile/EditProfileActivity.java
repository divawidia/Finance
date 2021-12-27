package com.kelompok8.finance.ui.profile;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kelompok8.finance.LoginManager;
import com.kelompok8.finance.R;
import com.kelompok8.finance.database.DBHelper;
import com.kelompok8.finance.model.User;
import com.kelompok8.finance.ui.home.HomeActivity;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfileActivity extends AppCompatActivity {
    private User user;
    private DBHelper dbHelper;
    int idUser;

    int mYear, mMonth, mDay, statusDate;
    long starttime, endtime;
    String dateStr;

    //konstanta permission
    private static final int CAMERA_REQUEST_CODE = 100;
    private static final int STORAGE_REQUEST_CODE = 101;
    //konstanta ambil gambar
    private static final int IMAGE_PICK_CAMERA_CODE = 102;
    private static final int IMAGE_PICK_GALERY_CODE = 103;
    //array permission
    private String[] cameraPermissions;
    private String[] storagePermissions;

    private Uri imageUri;

    CircleImageView fotoProfile;

    String username, email, tglLahir, telepon;

    LoginManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        getSupportActionBar().hide();

        EditText editTglLahir = findViewById(R.id.editTextTglLahir);
        EditText editEmail = findViewById(R.id.editTextEmail);
        EditText editUsername = findViewById(R.id.editTextUsername);
        EditText editTelepon = findViewById(R.id.editTextTelepon);
        Button simpan = findViewById(R.id.btnSimpanProfile);
        Button addDate = findViewById(R.id.addClockActivity);
        fotoProfile = findViewById(R.id.profile_image);
        ImageView btnBack = findViewById(R.id.ic_backProfile);

        idUser = this.getSharedPreferences("login_session", 0).getInt("key_id", 0);
        dbHelper = new DBHelper(this);

        cameraPermissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};


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

        editTglLahir.setText(user.getTanggal_lahir());
        addDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(EditProfileActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        dateStr = String.valueOf(dayOfMonth) + "/" + String.valueOf(month) + "/" + String.valueOf(year);
                        editTglLahir.setText(dateStr);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });


        editEmail.setText(user.getEmail());
        editTelepon.setText(user.getTelepon());
        editUsername.setText(user.getUsername());

        String foto = user.getFoto();

        if (foto == null){
            fotoProfile.setImageResource(R.drawable.blank_user);
        }
        else {
            imageUri = Uri.parse(foto);
            fotoProfile.setImageURI(imageUri);
        }

        fotoProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imagePickDialog();
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent home = new Intent(EditProfileActivity.this, ProfileActivity.class);
                startActivity(home);
            }
        });

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

//                    User currentUser = dbHelper.findUser(user.getIdUser());
//                    User check = dbHelper.checkLogin(username, user.getPassword());
//
//                    if (check == null){
                        ContentValues values =new ContentValues();
                        values.put("username", username);
                        values.put("email", email);
                        values.put("tanggal_lahir", tglLahir);
                        values.put("telepon", telepon);
                        values.put("foto", String.valueOf(imageUri));
                        dbHelper.updateUser(values, idUser);

                        Toast.makeText(EditProfileActivity.this, "Profile berhasil diubah", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(EditProfileActivity.this, ProfileActivity.class);
                        startActivity(intent);
//                    }
//                    else {
//                        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
//                        builder.setTitle("Error");
//                        builder.setMessage("Change profile error");
//                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                dialogInterface.cancel();
//                            }
//                        });
//                        builder.show();
//                    }
                }
            }
        });

    }

    private void imagePickDialog() {
        String[] options = {"Camera", "Galery"};
        AlertDialog.Builder  builder = new AlertDialog.Builder(this);
        builder.setTitle("Ambil gambar dari");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //handle click
                if (which==0){
                    //camera clicked
                    if (!checkCameraPermissions()){
                        requestCameraPermissions();
                    }else {
                        pickFromCamera();
                    }
                }
                else if (which==1){
                    if (!checkStoragePermissions()){
                        requestStoragePermissions();
                    }else {
                        pickFromGalery();
                    }
                }
            }
        });
        builder.create().show();
    }

    private void pickFromGalery() {
        //intent untuk ngambil gambar dari galery, gambar bakal direturn di method onActivityResult
        Intent galeryIntent = new Intent(Intent.ACTION_PICK);
        galeryIntent.setType("image/*"); //hanya boleh dalam format image
        startActivityForResult(galeryIntent, IMAGE_PICK_GALERY_CODE);
    }

    private void pickFromCamera() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.Images.Media.TITLE, "Image title");
        contentValues.put(MediaStore.Images.Media.DESCRIPTION, "Image description");

        imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);

        Intent cameraintent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraintent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(cameraintent, IMAGE_PICK_CAMERA_CODE);
    }

    private boolean checkStoragePermissions(){
        // mengecek jika penyimpanan di enable atau tidak
        boolean result = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);

        return result;
    }

    private void requestStoragePermissions(){
        // request izin penyimpanan
        ActivityCompat.requestPermissions(this, storagePermissions, STORAGE_REQUEST_CODE);
    }

    private boolean checkCameraPermissions(){
        // mengecek jika izin kamera sudah diaktifkan atau tidak
        boolean result = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA ) == (PackageManager.PERMISSION_GRANTED);
        boolean result1 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE ) == (PackageManager.PERMISSION_GRANTED);

        return result && result1;
    }

    private void requestCameraPermissions(){
        // request izin penyimpanan
        ActivityCompat.requestPermissions(this, cameraPermissions, CAMERA_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //hasil dari izin diterima/tidak
        switch (requestCode){
            case CAMERA_REQUEST_CODE:{
                if (grantResults.length>0){
                    //if allowed return true otherwise false
                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean storageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (cameraAccepted && storageAccepted){
                        pickFromCamera();
                    }
                    else {
                        Toast.makeText(this, "Izin dari kamera & penyimpanan diperlukan", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            break;
            case STORAGE_REQUEST_CODE:{
                if (grantResults.length>0){
                    //if allowed return true otherwise false
                    boolean storageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (storageAccepted){
                        pickFromGalery();
                    }
                    else {
                        Toast.makeText(this, "Izin dari penyimpanan diperlukan", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //gambar yang diambil dari kamera/galery bakal diterima disini
        if (resultCode == RESULT_OK){
            //image is picked
            if (requestCode == IMAGE_PICK_GALERY_CODE){
                //picked from galery

                //crop image
                CropImage.activity(data.getData())
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1,1)
                        .start(this);
            }
            else if (requestCode == IMAGE_PICK_CAMERA_CODE){
                //picked from camera

                //crop image
                CropImage.activity(imageUri)
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1,1)
                        .start(this);
            }
            else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
                //cropped image recieved
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                if (resultCode == RESULT_OK){
                    Uri resultUri = result.getUri();
                    imageUri = resultUri;
                    fotoProfile.setImageURI(resultUri);
                }
                else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                    //error
                    Exception error = result.getError();
                    Toast.makeText(this, ""+error,Toast.LENGTH_SHORT).show();
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}