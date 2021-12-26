package com.kelompok8.finance.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kelompok8.finance.R;

import java.util.ArrayList;


public class IconAdapter extends ArrayAdapter<Integer> {

    public IconAdapter(Context context, ArrayList<Integer> iconList) {
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
                    R.layout.item_category_icon, parent, false
            );
        }

        ImageView imageViewFlag = convertView.findViewById(R.id.iconView);

        Integer currentItem = getItem(position);
        imageViewFlag.setImageResource(currentItem);

        return convertView;
    }
}