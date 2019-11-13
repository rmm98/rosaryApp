package com.rahuldshetty.rosaryaudioapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.text.Layout;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.github.barteksc.pdfviewer.PDFView;
import com.rahuldshetty.rosaryaudioapp.MainActivity;
import com.rahuldshetty.rosaryaudioapp.PDFActivity;
import com.rahuldshetty.rosaryaudioapp.R;
import com.rahuldshetty.rosaryaudioapp.activities.SubMainActivity;
import com.rahuldshetty.rosaryaudioapp.models.SubTitles;

import java.util.ArrayList;

public class SubAdapter extends BaseAdapter {
    Context context;
    ArrayList<SubTitles> titles;
    LayoutInflater inflater;

    public SubAdapter(Context ctx,ArrayList<SubTitles> tlts){
        context = ctx;
        titles = tlts;
        inflater = (LayoutInflater.from(ctx));
    }

    @Override
    public int getCount() {
        return titles.size();
    }

    @Override
    public Object getItem(int position) {
        return titles.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.single_sub_item,null);

        TextView textView = convertView.findViewById(R.id.single_sub_text);
        TextView descView = convertView.findViewById(R.id.single_sub_desc);
        ImageView imageView = convertView.findViewById(R.id.single_sub_image);


        final SubTitles t = titles.get(position);

        if(t.getType().equals("kan")){
            Typeface typeface = ResourcesCompat.getFont(context, R.font.nudi01ebold);
            textView.setTypeface(typeface);
            descView.setTypeface(typeface);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f);
            descView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f);
        }

        textView.setText(t.getTitle());
        imageView.setImageBitmap(t.getImage());
        descView.setText(t.getDesc());



        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent subHeading = new Intent(SubMainActivity.activity, PDFActivity.class);
                subHeading.putExtra("SUB",t);
                subHeading.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                SubMainActivity.context.startActivity(subHeading);
            }
        });
        return convertView;
    }
}
