package com.rahuldshetty.rosaryaudioapp.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.github.barteksc.pdfviewer.util.FileUtils;
import com.rahuldshetty.rosaryaudioapp.PDFActivity;
import com.rahuldshetty.rosaryaudioapp.R;
import com.rahuldshetty.rosaryaudioapp.adapters.SubAdapter;
import com.rahuldshetty.rosaryaudioapp.models.SubTitles;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Scanner;

public class SubMainActivity extends AppCompatActivity {

    private ListView subListView;
    private SubAdapter adapter;

    private Resources res;
    private AssetManager am;

    public static Activity activity;
    public static Context context;

    private String PATH = "docs/";

    private String divine_text = "zÉÊ«Pï PÁPÀÄ½ÛZÉÆ vÉÃ¸ïð";
    private String litany_text = "¸Á¬ÄâtÂa ®¢£ï";
    private String mysteries_text[] = {"¸ÀAvÉÆ¸ÁZÉ «Ä¸ÉÛgï","zÀÄBTZÉ «Ä¸ÉÛgï","GeÁéqÁZÉ «Ä¸ÉÛgï","D£ÀAzÁZÉ «Ä¸ÉÛgï"};
    private String evening_text[] = {"ಸಾಂಜೆಚಿಂ ಮಾಗ್ಣಿಂ" , "ಸಾಂಜೆಚಿಂ ಮಾಗ್ಣಿಂ"};
    private String morning_text[] = {"ಸಕಾಳಿಂಚಿಂ ಮಾಗ್ಣಿಂ" , "ಸಕಾಳಿಂಚಿಂ ಮಾಗ್ಣಿಂ"};
    private String myster_desc[] = {"¸ÉÆªÀiÁgÁ D¤ ¸À£ÁégÁ","ªÀÄAUÁîgÁ D¤ ¸ÀÄPÁægÁ","¨Éæ¸ÁÛgÁ","§ÄzÁégÁ D¤ DAiÀiÁÛgÁ"};

    private String divine_text1= "Daivik Kalkulthiso Thers";
    private String litany_text1 = "Saibinichi Ladin";
    private String mysteries_text1[] = {"Sontosache Mister",
            "Dukhiche Mister",
            "Uzvaddache Mister",
            "Mohimeche Mister"};
    private String evening_text1[] = {"Sanjechin Magni",
            "Sanjechin Magni"};
    private String morning_text1[] = {"Sakalichin Magni",
            "Sakalichin Magni"};


    String readFile(InputStream inputStream){

        String res="";
        try {
            Reader in = new InputStreamReader(inputStream, "UTF-8");
            Scanner sc = new Scanner(in);
            res = sc.nextLine();
            sc.close();
        }
        catch (Exception e){

        }
        return res;
    }


    ArrayList<SubTitles> getSubTitles(String path,String subPath,String type,int id){
        res = getResources();
        am = res.getAssets();



        ArrayList<SubTitles> subTitles = new ArrayList<>();
        try
        {
            int j=0;
            for(String file: am.list(path+subPath)){
                String compPath = path+subPath+"/"+file;
                if(file.contains(".txt")) {
                    SubTitles sub = new SubTitles();

                    sub.setPath(compPath);
                    sub.setFileName(file);
                    String title = file.replace(".txt", "").replace(".","").replaceAll("[0-9]", "");
                    if(!type.equals("eng")){
                        switch (id){
                            case 0:
                                title = morning_text[j];
                                break;

                            case 1:
                                title = evening_text[j];
                                break;

                            case 2:
                                title=mysteries_text[j];
                                break;

                            case 3:
                                title=litany_text;
                                break;
                            case 4:
                                title = divine_text;
                                break;


                        }

                    }
                    else{
                        switch (id){
                            case 0:
                                title = morning_text1[j];
                                break;

                            case 1:
                                title = evening_text1[j];
                                break;

                            case 2:
                                title=mysteries_text1[j];
                                break;

                            case 3:
                                title=litany_text1;
                                break;
                            case 4:
                                title = divine_text1;
                                break;


                        }
                    }

                    sub.setTitle(title);

                    String songPath = path + file.replace(".txt",".mp3");
                    sub.setSongPath(songPath);

                    String imagePath = path + "images/" + file.replace(".txt",".jpg");
                    InputStream ims = am.open(imagePath);
                    Bitmap bitmap = BitmapFactory.decodeStream(ims);
                    sub.setImage(bitmap);

                    String descPath = path+ subPath + "/desc/" + file.replace(".txt",".txt");
                    String text = readFile(am.open(descPath));

                    if(!type.equals("eng") && id==2)
                        text = myster_desc[j];
                    sub.setDesc(text);


                    if(subPath.equals("romi"))
                        sub.setType("eng");
                    else
                        sub.setType("kan");

                    subTitles.add(sub);
                    j++;
                }
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        return subTitles;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_main);

        activity = this;
        context = getApplicationContext();

        subListView = findViewById(R.id.sub_list_view);

        String type = getIntent().getStringExtra("type");
        int id = getIntent().getIntExtra("id",-1);


        String relPath = PATH + id + "/";

        ArrayList<SubTitles> titles;

        if(type.equals("eng"))
        {
            titles = getSubTitles(relPath,"romi",type,id);
        }else{
            titles = getSubTitles(relPath,"konkani",type,id);
        }
        System.out.println("SIZE:"+titles.size());
        adapter = new SubAdapter(this,titles);
        subListView.setAdapter(adapter);

        if(titles.size()==1){
            // go inside sub
            Intent subHeading = new Intent(SubMainActivity.activity, PDFActivity.class);
            subHeading.putExtra("SUB",titles.get(0));
            startActivity(subHeading);
            finish();
        }

    }
}
