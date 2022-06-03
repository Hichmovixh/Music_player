package com.hicham.musicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.widget.SeekBar;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {


    private static final String TAG = "MainActivity";
    /**ajout automatique de la lecture **/

     MediaPlayer mediaPlayer = new MediaPlayer();


     public void pause(SeekBar sbPosition) {
         mediaPlayer.pause();
        Log.i(TAG, "play");
     }

     public void play(SeekBar sbPosition){
         mediaPlayer.start();
         Log.i(TAG, "play");

    }
    
    
    private void volume(){
        SeekBar sbVolume = findViewById(R.id.sbVolume);
        AudioManager audioManager =(AudioManager) getSystemService(AUDIO_SERVICE);

        int volumeMax = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        sbVolume.setMax(volumeMax);

        int currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        sbVolume.setProgress(currentVolume);

        sbVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.i(TAG, "onProgressChanger: Volume =" + Integer.toString(progress));
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }


    private void position(){
         SeekBar sbPosition = findViewById(R.id.sbPosition);
         sbPosition.setMax(mediaPlayer.getDuration());

         // Part one de la geston du curseur par l'utilisateur

        sbPosition.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                Log.i(TAG, "posotopn dans le morceau :" + Integer.toString(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                pause(sbPosition);

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                play(sbPosition);

                mediaPlayer.seekTo(sbPosition.getProgress());

            }
        });

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                sbPosition.setProgress(mediaPlayer.getCurrentPosition());

            }
        },0, 300);

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       /** mediaPlayer = MediaPlayer.create(this, R.raw.sound);
        mediaPlayer.start();
        **/

       mediaPlayer = MediaPlayer.create(this, R.raw.sound);
       volume();
       position();
    }

    @Override
    protected void onPause(){
        super.onPause();
        mediaPlayer.stop();
    }

}