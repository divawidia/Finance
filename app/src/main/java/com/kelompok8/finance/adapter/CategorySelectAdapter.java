package com.kelompok8.finance.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.kelompok8.finance.R;
import com.kelompok8.finance.model.Category;
import com.kelompok8.finance.model.Dompet;

import java.util.ArrayList;


public class CategorySelectAdapter extends ArrayAdapter<Category> {

    public CategorySelectAdapter(Context context, ArrayList<Category> iconList) {
        super(context, 0, iconList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    private View initView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.item_category_select, parent, false
            );
        }

        TextView tvNamaCategory = convertView.findViewById(R.id.pengeluaran);
        tvNamaCategory.setText(getItem(position).getNamaKategori());
        CardView card = (CardView) convertView.findViewById(R.id.cardpiw);
        ImageView icon = (ImageView) convertView.findViewById(R.id.roundedImageView);
        int colorSelected = Color.parseColor(getItem(position).getWarna());
        card.setCardBackgroundColor(colorSelected);
        icon.setImageResource(getItem(position).getIcon());

        return convertView;
    }
}