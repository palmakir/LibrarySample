package com.example.hp.internship_library;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomAdapter extends ArrayAdapter<String>{
    CustomAdapter(Context context, String[] book,int[] pics){
        super(context,R.layout.custom_row,book);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater infl =LayoutInflater.from(getContext());
        View customView=infl.inflate(R.layout.custom_row,parent,false);
        String singleitem=getItem(position);
        TextView btext=customView.findViewById(R.id.btext);
        ImageView bimg=customView.findViewById(R.id.bimg);
        btext.setText(singleitem);
        int[] pics={R.drawable.img1,R.drawable.img2,R.drawable.img3};
        bimg.setImageResource(pics[position]);
        return customView;
    }
}
