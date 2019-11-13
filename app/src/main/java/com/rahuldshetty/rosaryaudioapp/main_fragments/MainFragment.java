package com.rahuldshetty.rosaryaudioapp.main_fragments;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.rahuldshetty.rosaryaudioapp.R;
import com.rahuldshetty.rosaryaudioapp.adapters.MainAdapter;
import com.rahuldshetty.rosaryaudioapp.models.Title;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    private int image_ids[] = {R.drawable.j9,R.drawable.s2,R.drawable.j1,R.drawable.litany,R.drawable.s1};
    private View mView;
    private MainAdapter adapter;
    private ListView listView;

    public MainFragment() {
        // Required empty public constructor
    }

    ArrayList<Title> get_romi(){
        String names[] = {"Sakalichin Magni",
                "Sanjechin Magni",
                "Thers",
                "Saibinichi Ladin",
                "Kalkulthiso Thers"};
        ArrayList<Title> titles = new ArrayList<>();
        for(int i=0;i<5;i++){
            Title t = new Title();
            t.setId(i);
            t.setText(names[i]);
            t.setType("eng");
            t.setAlbum(BitmapFactory.decodeResource(getContext().getResources(),image_ids[i]));
            titles.add(t);
        }
        return titles;
    }

    ArrayList<Title> get_konkani(){
        String names[] = {"ಸಕಾಳಿಂಚಿಂ ಮಾಗ್ಣಿಂ" ,
                "ಸಾಂಜೆಚಿಂ ಮಾಗ್ಣಿಂ" ,
                "ತೇರ್ಸ್" ,
                "ಸೈಬಿಣಿಚಿ ಲದಿನ್" ,
                "ಕಾಕುಳ್ತಿಚೊ ತೇರ್ಸ್ "};
        ArrayList<Title> titles = new ArrayList<>();
        for(int i=0;i<5;i++){
            Title t = new Title();
            t.setId(i);
            t.setText(names[i]);
            t.setType("kan");
            t.setAlbum(BitmapFactory.decodeResource(getContext().getResources(),image_ids[i]));
            titles.add(t);
        }
        return titles;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView =  inflater.inflate(R.layout.fragment_main, container, false);
        listView = mView.findViewById(R.id.main_list_view);
        String type = getArguments().getString("type");


        ArrayList<Title> titles = type == "eng" ? get_romi() : get_konkani();

        adapter = new MainAdapter(getContext(),titles);
        listView.setAdapter(adapter);

        return mView;
    }

}
