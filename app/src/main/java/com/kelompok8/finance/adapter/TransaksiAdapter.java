package com.kelompok8.finance.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kelompok8.finance.R;
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
        holder.title.setText(pengeluaranHolder.get(position).getCatatan());
        holder.category.setText(pengeluaranHolder.get(position).getIdKategori().toString());
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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            amount = (TextView) itemView.findViewById(R.id.amount);
            icon = (ImageView) itemView.findViewById(R.id.icon);
            date = (TextView) itemView.findViewById(R.id.date);
            category = (TextView) itemView.findViewById(R.id.category);
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

