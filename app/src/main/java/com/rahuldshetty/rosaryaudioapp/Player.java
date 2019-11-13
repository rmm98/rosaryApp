package com.rahuldshetty.rosaryaudioapp;

import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;

import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.InputStream;

public class Player {

    private MediaPlayer mediaPlayer;

    public Player(AssetFileDescriptor fd){
        mediaPlayer = new MediaPlayer();
        setDataSource(fd);
    }


    public void setDataSource(AssetFileDescriptor fd)
    {
        mediaPlayer.reset();
        try {
            mediaPlayer.setDataSource(fd.getFileDescriptor(),fd.getStartOffset(),fd.getLength());
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.prepare();
        }
        catch (Exception e){
            System.out.println("PEXCEPTION:"+e);
        }
    }

    public boolean isPlaying(){
        return mediaPlayer.isPlaying();
    }

    public void play(){
        if(!mediaPlayer.isPlaying())
            mediaPlayer.start();
    }

    public void start(){
        if(mediaPlayer.isPlaying()){
            pause();
        }
        else
            play();
    }


    public void pause(){
        if(mediaPlayer.isPlaying())
            mediaPlayer.pause();
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public  void destroy(){
        if(isPlaying())
            mediaPlayer.stop();
        mediaPlayer.reset();
        mediaPlayer.release();

    }
}
