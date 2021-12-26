package com.kelompok8.finance.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kelompok8.finance.R;
import com.kelompok8.finance.model.Dompet;

import java.util.ArrayList;


public class DompetSelectAdapter extends ArrayAdapter<Dompet> {

    public DompetSelectAdapter(Context context, ArrayList<Dompet> iconList) {
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
                    R.layout.item_dompet_select, parent, false
            );
        }

        TextView textNamaDompet = convertView.findViewById(R.id.textView);
        textNamaDompet.setText(getItem(position).getNama_dompet());

        return convertView;
    }
}