package com.kelompok8.finance.adapter;

import android.content.Context;
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
import com.kelompok8.finance.model.Dompet;
import com.kelompok8.finance.model.Pengeluaran;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class DompetAdapter extends RecyclerView.Adapter<DompetAdapter.ViewHolder> {

    private ArrayList<Dompet> dompetHolder;
    private Context context;
    private RecyclerView recyclerView;

    DBHelper databaseHelper;

    public DompetAdapter(Context context, ArrayList<Dompet> dompetHolder) {
        this.dompetHolder = dompetHolder;
        this.context = context;

        databaseHelper = new DBHelper(context);
    }

    @NonNull
    @Override
    public DompetAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dompet, parent, false);
        return new DompetAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DompetAdapter.ViewHolder holder, int position) {
        Dompet dompet = dompetHolder.get(position);
        int id = dompet.getId_dompet();
        int idUser = dompet.getId_user();
        String nama_dompet = dompet.getNama_dompet();
        int saldo_awal = dompet.getSaldo_awal();

        holder.namaDompet.setText(nama_dompet);
        holder.jumlahDompet.setText("Rp " + NumberFormat.getNumberInstance(Locale.US).format(saldo_awal));
    }

    @Override
    public int getItemCount() {
        return dompetHolder.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView namaDompet, jumlahDompet;
        CardView cardDompet;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            namaDompet = (TextView) itemView.findViewById(R.id.textNamaDompet);
            jumlahDompet = (TextView) itemView.findViewById(R.id.textJumlah);
            cardDompet = (CardView) itemView.findViewById(R.id.cardDompet);
        }
    }
}
