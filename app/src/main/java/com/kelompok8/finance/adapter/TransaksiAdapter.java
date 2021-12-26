package com.kelompok8.finance.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.kelompok8.finance.R;
import com.kelompok8.finance.database.DBHelper;
import com.kelompok8.finance.model.Category;
import com.kelompok8.finance.model.Pengeluaran;
import com.kelompok8.finance.ui.home.HomeActivity;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class TransaksiAdapter extends RecyclerView.Adapter<TransaksiAdapter.ViewHolder>{

    private ArrayList<Pengeluaran> pengeluaranHolder = new ArrayList<>();
    private Context context;
    private RecyclerView recyclerView;

    public TransaksiAdapter(ArrayList<Pengeluaran> pengeluaranHolder, Context context, SQLiteDatabase sqLiteDatabase) {
        this.pengeluaranHolder = pengeluaranHolder;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_transaksi, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Cursor cursor = new DBHelper(this.context).getKategoriItem(pengeluaranHolder.get(position).getIdCategory());
        Category kategori = null;

        while(cursor.moveToNext()){
            Category category = new Category(cursor.getInt(0),
                    cursor.getInt(1),
                    cursor.getString(2),
                    cursor.getInt(3),
                    cursor.getString(4));
            kategori = category;
        }

        Integer iconSelected = kategori.getIcon();
        int colorSelected = Color.parseColor(kategori.getWarna());
        holder.card.setCardBackgroundColor(colorSelected);

        holder.icon.setImageResource(iconSelected);
        holder.title.setText(pengeluaranHolder.get(position).getCatatan());
        holder.category.setText(pengeluaranHolder.get(position).getKategori().toString());
        holder.date.setText(pengeluaranHolder.get(position).getTanggal());
        holder.amount.setText("Rp " + NumberFormat.getNumberInstance(Locale.US).format(pengeluaranHolder.get(position).getJumlahPengeluaran()));
    }

    @Override
    public int getItemCount() {
        return (pengeluaranHolder != null) ? pengeluaranHolder.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, amount, category, date;
        ImageView icon;
        CardView card;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            amount = (TextView) itemView.findViewById(R.id.amount);
            icon = (ImageView) itemView.findViewById(R.id.roundedImageView);
            date = (TextView) itemView.findViewById(R.id.date);
            category = (TextView) itemView.findViewById(R.id.category);
            card = (CardView) itemView.findViewById(R.id.cardpiw);
        }
    }
    public interface TombolAdapterDitekan {
        void OperasiAdapter();
    }

    TombolAdapterDitekan tombolAdapterDitekan;

    public void setClickEvent(TombolAdapterDitekan event) {
        this.tombolAdapterDitekan = event;
    }
}

