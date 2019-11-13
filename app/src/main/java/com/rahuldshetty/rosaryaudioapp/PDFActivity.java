package com.rahuldshetty.rosaryaudioapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Build;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.rahuldshetty.rosaryaudioapp.models.SubTitles;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Timer;
import java.util.TimerTask;

public class PDFActivity extends AppCompatActivity {

    private TextView textView;

    private SeekBar seekBar;
    private ImageView playBtn;
    private TextView title,start,end;

    private Timer timer;
    private Player player;

    private final int PERMISSION_CODE = 41;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);


        textView = findViewById(R.id.textView);
        seekBar = findViewById(R.id.player_seek);
        playBtn = findViewById(R.id.player_play);
        title = findViewById(R.id.player_title);
        start= findViewById(R.id.player_start);
        end = findViewById(R.id.player_end);

        textView.setMovementMethod(new ScrollingMovementMethod());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {

                // Should we show an explanation?
                if (shouldShowRequestPermissionRationale(
                        Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    // Explain to the user why we need to read the contacts
                }

                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        PERMISSION_CODE);

                // MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE is an
                // app-defined int constant that should be quite unique

                return;
            } else {
                final SubTitles subTitle = (SubTitles) getIntent().getParcelableExtra("SUB");

                try {
                    AssetFileDescriptor fd = getAssets().openFd(subTitle.getSongPath());
                    player = new Player(fd);
                } catch (Exception e) {
                    System.out.println("EXCEPTION:" + e);
                }

                title.setText(subTitle.getTitle());
                if (!subTitle.getType().equals("eng")) {
                    Typeface typeface = ResourcesCompat.getFont(getApplicationContext(), R.font.kannadafont);
                    title.setTypeface(typeface);
                    title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f);
                }


                try {
                    String filepath = (subTitle.getPath());
                    String data = readFileAsString(filepath);

                    if (!subTitle.getType().equals("eng")) {
                        Typeface typeface = ResourcesCompat.getFont(getApplicationContext(), R.font.nudi01ebold);
                        textView.setTypeface(typeface);
                    }
                    textView.setText(Html.fromHtml(data));


                } catch (Exception e) {
                    System.out.println("EXCEPTION:" + e);
                }

                try {

                    player.getMediaPlayer().setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            end.setText(milliSecondsToTimer(mp.getDuration()));
                        }
                    });

                    player.getMediaPlayer().setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            try {
                                AssetFileDescriptor fd = getAssets().openFd(subTitle.getSongPath());
                                player.getMediaPlayer().reset();
                                player.setDataSource(fd);
                                seekBar.setProgress(0);
                                playBtn.setImageResource(R.drawable.play);
                            } catch (Exception e) {
                                System.out.println("EXCEPTION:" + e);
                            }

                        }
                    });

                    playBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            player.start();
                            if (player.isPlaying()) {
                                playBtn.setImageResource(R.drawable.stop);
                            } else {
                                playBtn.setImageResource(R.drawable.play);
                            }
                            start.post(mUpdatetimer);
                        }
                    });

                    seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                        @Override
                        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                            if (fromUser) {
                                float done = progress / 100;
                                boolean playing = player.isPlaying();
                                player.pause();
                                player.getMediaPlayer().seekTo((player.getMediaPlayer().getDuration() * progress) / 100);
                                long currentDur = player.getMediaPlayer().getCurrentPosition();
                                String current = milliSecondsToTimer(currentDur);
                                start.setText(current);
                                if (playing)
                                    player.start();
                            }
                        }

                        @Override
                        public void onStartTrackingTouch(SeekBar seekBar) {
                        }

                        @Override
                        public void onStopTrackingTouch(SeekBar seekBar) {
                        }
                    });

                    timer = new Timer();
                    timer.scheduleAtFixedRate(new TimerTask() {
                        @Override
                        public void run() {
                            try {
                                seek();

                            } catch (Exception e) {
                                timer.cancel();
                            }
                        }
                    }, 1000, 500);
                }
                catch(Exception e){


                }

                }

            }






    }

    public String readFileAsString(String path)throws Exception
    {
        BufferedReader br = new BufferedReader(new InputStreamReader( getAssets().open(path) ));
        String st;
        String data = "";
        while ((st = br.readLine()) != null){
            data += st + "<br>";
        }


        return data;
    }

    public static String milliSecondsToTimer(long milliseconds) {
        String finalTimerString = "";
        String secondsString = "";

        // Convert total duration into time
        int hours = (int) (milliseconds / (1000 * 60 * 60));
        int minutes = (int) (milliseconds % (1000 * 60 * 60)) / (1000 * 60);
        int seconds = (int) ((milliseconds % (1000 * 60 * 60)) % (1000 * 60) / 1000);
        // Add hours if there
        if (hours > 0) {
            finalTimerString = hours + ":";
        }

        // Prepending 0 to seconds if it is one digit
        if (seconds < 10) {
            secondsString = "0" + seconds;
        }   else {
            secondsString = "" + seconds;
        }

        finalTimerString = finalTimerString + minutes + ":" + secondsString;

        // return timer string
        return finalTimerString;
    }

    @Override
    public void onRequestPermissionsResult(final int requestCode,final String[] permissions,final int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted.

                final SubTitles subTitle = (SubTitles) getIntent().getParcelableExtra("SUB");

                try {
                    AssetFileDescriptor fd = getAssets().openFd(subTitle.getSongPath());
                    player = new Player(fd);
                }
                catch (Exception e){
                    System.out.println("EXCEPTION:"+e);
                }

                title.setText(subTitle.getTitle());
                if(!subTitle.getType().equals("eng"))
                {
                    Typeface typeface = ResourcesCompat.getFont(getApplicationContext(), R.font.kannadafont);
                    title.setTypeface(typeface);
                    title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f);
                }



                try {
                    String filepath = (subTitle.getPath());
                    String data = readFileAsString(filepath);
                    if(!subTitle.getType().equals("eng"))
                    {
                        Typeface typeface = ResourcesCompat.getFont(getApplicationContext(), R.font.nudi01ebold);
                        textView.setTypeface(typeface);
                    }
                    textView.setText(Html.fromHtml(data));
                }
                catch (Exception e){
                    System.out.println("EXCEPTION:"+e);
                }

                player.getMediaPlayer().setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        end.setText( milliSecondsToTimer(mp.getDuration()));
                    }
                });


                player.getMediaPlayer().setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        try {
                            AssetFileDescriptor fd = getAssets().openFd(subTitle.getSongPath());
                            player.getMediaPlayer().reset();
                            player.setDataSource(fd);
                            seekBar.setProgress(0);
                            playBtn.setImageResource(R.drawable.play);
                        }
                        catch (Exception e){
                            System.out.println("EXCEPTION:"+e);
                        }

                    }
                });

                playBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        player.start();
                        if (player.isPlaying()) {
                            playBtn.setImageResource(R.drawable.stop);
                        } else {
                            playBtn.setImageResource(R.drawable.play);
                        }
                        start.post(mUpdatetimer);
                    }
                });

                seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                        if(fromUser){
                            float done=progress/100;
                            boolean playing=player.isPlaying();
                            player.pause();
                            player.getMediaPlayer().seekTo((player.getMediaPlayer().getDuration()*progress)/100);
                            long currentDur=player.getMediaPlayer().getCurrentPosition();
                            String current=milliSecondsToTimer(currentDur);
                            start.setText(current);
                            if(playing)
                                player.start();
                        }
                    }
                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {}
                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {}
                });

                timer = new Timer();
                timer.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        try {
                            seek();

                        }
                        catch (Exception e){
                            timer.cancel();
                        }
                    }
                }, 1000, 500);




            } else {
                // User refused to grant permission.
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        start.removeCallbacks(mUpdatetimer);
        if(player!=null)
            player.destroy();
    }

    private Runnable mUpdatetimer=new Runnable() {
        @Override
        public void run() {
            if(player.isPlaying())
            {
                long currentDur=player.getMediaPlayer().getCurrentPosition();
                String current=milliSecondsToTimer(currentDur);
                start.setText(current);
                start.postDelayed(this,1000);
            }
            else{
                start.removeCallbacks(this);
            }
        }
    };

    void seek(){
        if(player.isPlaying()){
            int length = player.getMediaPlayer().getCurrentPosition()*100;
            seekBar.setProgress(length/player.getMediaPlayer().getDuration());}
    }

}
