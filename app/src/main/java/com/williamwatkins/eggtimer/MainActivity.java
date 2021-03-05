package com.williamwatkins.eggtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    SeekBar timerSeekBar;
    TextView timerTextView;
    Button controllerButton;
    Boolean counterIsActive = false;
    CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         timerSeekBar = findViewById(R.id.timerSeekBar);
         timerTextView = findViewById(R.id.timerTextView);
         controllerButton = findViewById(R.id.timerButton);

        //Max is ten minutes
        timerSeekBar.setMax(600);
        // initially set at 30 seconds
        timerSeekBar.setProgress(30);

        timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateTimer(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });



    }

    public void controlTimer(View view){

        if (counterIsActive == false){

        counterIsActive = true;
        timerSeekBar.setEnabled(false);
        controllerButton.setText("Stop");

        countDownTimer = new CountDownTimer(timerSeekBar.getProgress() * 1000 + 100, 1000) {


            @Override
            public void onTick(long millisUntilFinished) {
                updateTimer((int) millisUntilFinished / 1000);
            }

            @Override
            public void onFinish() {

                MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.airhorn);
                mediaPlayer.start();
                resetTimer();
            }
        }.start();
        } else {
            resetTimer();
        }

    }

    public void updateTimer(int secondsLeft){
        int minutes = (int) secondsLeft/60;

        //Calculates the number of seconds leftover.
        int seconds = secondsLeft - minutes * 60;

        timerTextView.setText(String.format("%d:%02d", minutes, seconds));
    }

    public void resetTimer(){
        timerSeekBar.setEnabled(true);
        timerTextView.setText("0:30");
        timerSeekBar.setProgress(30);
        countDownTimer.cancel();
        controllerButton.setText("Go");
        counterIsActive = false;
        Toast.makeText(this, "Timer has finished", Toast.LENGTH_SHORT).show();
    }
}