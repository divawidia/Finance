package com.kelompok8.finance.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.kelompok8.finance.AddCategoryActivity;
import com.kelompok8.finance.CategoryActivity;
import com.kelompok8.finance.R;
import com.kelompok8.finance.database.DBHelper;
import com.kelompok8.finance.model.Category;
import com.kelompok8.finance.model.Pengeluaran;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder>{

    private ArrayList<Category> categoryHolder = new ArrayList<>();
    private Context context;
    private RecyclerView recyclerView;
    DBHelper databaseHelper;

    public CategoryAdapter(ArrayList<Category> categoryHolder, Context context, SQLiteDatabase sqLiteDatabase) {
        this.categoryHolder = categoryHolder;
        this.context = context;
        databaseHelper = new DBHelper(context);
    }

    @NonNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        return new CategoryAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.pengeluaran.setText(categoryHolder.get(position).getNamaKategori());
        int colorSelected = Color.parseColor(categoryHolder.get(position).getWarna());
        holder.card.setCardBackgroundColor(colorSelected);
        holder.icon.setImageResource(categoryHolder.get(position).getIcon());

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMoreDialog(
                        ""+categoryHolder.get(position).getId(),
                        ""+categoryHolder.get(position).getIdUser(),
                        ""+categoryHolder.get(position).getNamaKategori(),
                        ""+categoryHolder.get(position).getIcon(),
                        ""+categoryHolder.get(position).getWarna());
            }
        });
    }

    @Override
    public int getItemCount() {
        return (categoryHolder != null) ? categoryHolder.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView pengeluaran;
        CardView card;
        ImageView icon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            pengeluaran = (TextView) itemView.findViewById(R.id.pengeluaran);
            card = (CardView) itemView.findViewById(R.id.cardpiw);
            icon = (ImageView) itemView.findViewById(R.id.roundedImageView);
        }
    }
    public interface TombolAdapterDitekan {
        void OperasiAdapter();
    }

    PengeluaranAdapter.TombolAdapterDitekan tombolAdapterDitekan;

    public void setClickEvent(PengeluaranAdapter.TombolAdapterDitekan event) {
        this.tombolAdapterDitekan = event;
    }

    public void showMoreDialog(String id, String id_user, String nama_kategori, String icon, String warna){
        String[] options = {"Edit", "Delete"};

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //tombol edit diklik
                if (which==0){
                    //tombol edit diklik
                    Intent intent = new Intent(context, AddCategoryActivity.class);
                    intent.putExtra("id", id);
                    intent.putExtra("id_user", id_user);
                    intent.putExtra("nama_kategori", nama_kategori);
                    intent.putExtra("icon", icon);
                    intent.putExtra("warna", warna);
                    intent.putExtra("action", "edit");
                    context.startActivity(intent);
                }
                //tombol delete diklik
                else if (which==1){
                    databaseHelper.deleteKategori(id);
                    ((CategoryActivity)context).onResume();
                }
            }
        });
        builder.create().show();
    }
}

