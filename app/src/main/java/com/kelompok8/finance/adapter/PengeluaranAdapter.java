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

public class PengeluaranAdapter extends RecyclerView.Adapter<PengeluaranAdapter.ViewHolder>{

    private ArrayList<Pengeluaran> pengeluaranHolder = new ArrayList<>();
    private Context context;
    private RecyclerView recyclerView;

    public PengeluaranAdapter(ArrayList<Pengeluaran> pengeluaranHolder, Context context, SQLiteDatabase sqLiteDatabase) {
        this.pengeluaranHolder = pengeluaranHolder;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pengeluaran, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.pengeluaran.setText(pengeluaranHolder.get(position).getIdKategori().toString());
        holder.amount.setText("Rp " + NumberFormat.getNumberInstance(Locale.US).format(pengeluaranHolder.get(position).getJumlahPengeluaran()));
    }

    @Override
    public int getItemCount() {
        return (pengeluaranHolder != null) ? pengeluaranHolder.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView pengeluaran, amount;
        ImageView icon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            pengeluaran = (TextView) itemView.findViewById(R.id.pengeluaran);
            amount = (TextView) itemView.findViewById(R.id.amount);
            icon = (ImageView) itemView.findViewById(R.id.icon);
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

