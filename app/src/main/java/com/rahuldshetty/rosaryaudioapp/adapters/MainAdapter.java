package com.rahuldshetty.rosaryaudioapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.content.res.ResourcesCompat;
import android.text.Layout;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.rahuldshetty.rosaryaudioapp.MainActivity;
import com.rahuldshetty.rosaryaudioapp.R;
import com.rahuldshetty.rosaryaudioapp.activities.SubMainActivity;
import com.rahuldshetty.rosaryaudioapp.models.Title;

import java.util.ArrayList;

public class MainAdapter extends BaseAdapter {
    Context context;
    ArrayList<Title> titles;
    LayoutInflater inflater;

    public MainAdapter(Context ctx,ArrayList<Title> tlts){
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
        convertView = inflater.inflate(R.layout.single_main_item,null);

        TextView textView = convertView.findViewById(R.id.single_main_text);
        ImageView imageView = convertView.findViewById(R.id.single_main_image);

        final Title t = titles.get(position);

        if(t.getType().equals("kan")){
            Typeface typeface = ResourcesCompat.getFont(context, R.font.nudi01ebold);
            textView.setTypeface(typeface);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f);
        }

        textView.setText(t.getText());
        imageView.setImageBitmap(t.getAlbum());



        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent subHeading = new Intent(MainActivity.activity, SubMainActivity.class);
                subHeading.putExtra("type",t.getType());
                subHeading.putExtra("id",t.getId());
                subHeading.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                MainActivity.mainContext.startActivity(subHeading);
            }
        });
        return convertView;
    }
}
