package com.kelompok8.finance.adapter;

import android.content.Context;
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

import com.kelompok8.finance.R;
import com.kelompok8.finance.model.Category;
import com.kelompok8.finance.model.Pengeluaran;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder>{

    private ArrayList<Category> categoryHolder = new ArrayList<>();
    private Context context;
    private RecyclerView recyclerView;

    public CategoryAdapter(ArrayList<Category> categoryHolder, Context context, SQLiteDatabase sqLiteDatabase) {
        this.categoryHolder = categoryHolder;
        this.context = context;
    }

    @NonNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        return new CategoryAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.pengeluaran.setText(categoryHolder.get(position).getNamaKategori());
        int colorSelected = Color.parseColor(categoryHolder.get(position).getWarna());
        holder.card.setCardBackgroundColor(colorSelected);
        holder.icon.setImageResource(categoryHolder.get(position).getIcon());
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
}

