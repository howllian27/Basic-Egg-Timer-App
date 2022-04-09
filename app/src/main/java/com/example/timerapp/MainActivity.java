package com.example.timerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    int timeInSeconds;
    MediaPlayer player;
    CountDownTimer countDownTimer;
    SeekBar seekBar;


    public void timerClick(View view){
        Button timerButton = findViewById(R.id.goButton);
        String currentButtonText = timerButton.getText().toString();
        if (currentButtonText.equals("Go!")){
            String stopText = "Stop!";
            timerButton.setText(stopText.toCharArray(), 0, stopText.length());
            countDownTimer = new CountDownTimer(timeInSeconds * 1000, 1000) {
                @Override
                public void onTick(long l) {
                    timeInSeconds--;
                    changeTimeRemaining(timeInSeconds);
                }

                @Override
                public void onFinish() {
                    player.start();
                    timerButton.setText(("Go!").toCharArray(), 0, ("Go!").length());
                    seekBar.setEnabled(true);

                }
            }.start();
            seekBar.setEnabled(false);
        } else{
            String goText = "Go!";
            timerButton.setText(goText.toCharArray(), 0, goText.length());
            countDownTimer.cancel();
            seekBar.setEnabled(true);
        }
    }

    public void changeTimeRemaining(int time){
        TextView timeRemaining = findViewById(R.id.timeRemaining);
        int minuteComponent = time/60;
        int secondComponent = time - minuteComponent * 60;
        String minuteComponentText = Integer.toString(minuteComponent);
        String secondComponentText;
        if (secondComponent < 10){
            secondComponentText = "0" + Integer.toString(secondComponent);
        } else {
            secondComponentText = Integer.toString(secondComponent);
        }
        String newTime = minuteComponentText + ":" + secondComponentText;
        timeRemaining.setText(newTime.toCharArray(), 0, newTime.length());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        seekBar = findViewById(R.id.timeSetter);
        player = MediaPlayer.create(this, R.raw.roar);



        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                timeInSeconds = 6 * i;
                changeTimeRemaining(timeInSeconds);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        new CountDownTimer(10000, 1000){
            @Override
            public void onTick(long millisecondsUntilDone) {
                Log.i("Seconds left!", String.valueOf(millisecondsUntilDone/1000));
            }

            @Override
            public void onFinish() {
                Log.i("We're done!", "No more countdown");
            }
        }.start();
