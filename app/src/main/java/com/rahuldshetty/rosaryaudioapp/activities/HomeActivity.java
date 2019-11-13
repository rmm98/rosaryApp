package com.rahuldshetty.rosaryaudioapp.activities;

import android.content.DialogInterface;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.rahuldshetty.rosaryaudioapp.R;
import com.rahuldshetty.rosaryaudioapp.main_fragments.MainFragment;

public class HomeActivity extends AppCompatActivity {

    private FrameLayout frameLayout;
    private BottomNavigationView bottomNavigationView;
    private Toolbar toolbar;

    private MainFragment konkani,english;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Bundle bundleEng = new Bundle();
        bundleEng.putString("type", "eng");
        Bundle bundleKan = new Bundle();
        bundleKan.putString("type", "kan");

        english = new MainFragment();
        english.setArguments(bundleEng);

        konkani = new MainFragment();
        konkani.setArguments(bundleKan);

        frameLayout = findViewById(R.id.home_frame_layout);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        toolbar = findViewById(R.id.toolbar);

        toolbar.setTitle("Naman");
        toolbar.setTitleTextColor(0xFFFFFFFF);
        setSupportActionBar(toolbar);


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.navKonkani:
                        loadFragment(konkani);
                        return true;

                    case R.id.navRomi:
                        loadFragment(english);
                        return true;
                }
                return false;
            }
        });

        bottomNavigationView.setSelectedItemId(R.id.navKonkani);
        loadFragment(konkani);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.home_nav,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId())
        {
            case R.id.about_us:

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(
                                "For feedback, kindly mail to:\nnamanapp2019@gmail.com\n\nAbout:\n" +
                                        "Naman is the first Konkani Catholic audio and text prayers app. You can use it while travelling or at home. You can teach prayers to your children just by listening to these prayers, that too without any internet connection since it's available offline. While using the app, you can switch between Romi or Konkani script. This App consists of Morning and Evening prayers, Holy Rosary, Litany, Chaplet of Divine Mercy, etc.\n\n"+
                                        "This app is jointly developed by Fr Joswin D'Souza & Fr Rohan Lobo of Mangalore Diocese. \n\n"+
                                        "Special Thanks to,\n" +
                                        "Dr Roshan Fernandes, NMAMIT, Nitte\n" +
                                        "Mr Rahul D Shetty\n" +
                                        "Mr Reevan Mario Miranda\n"+
                                        "Mr Sushanth Kille"
                        );
                builder.setNegativeButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();


                return true;

            default:return false;
        }
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.home_frame_layout, fragment);
        fragmentTransaction.commit();
    }

}
