package com.example.eggtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView timerTextView;
    SeekBar timerSeekBar;
    Button goButton;
    Boolean buttonActive=false;
    CountDownTimer countDownTimer;

    public void reset(){
        timerTextView.setText("00:30");
        timerSeekBar.setProgress(30);
        timerSeekBar.setEnabled(true);
        countDownTimer.cancel();
        goButton.setText("GO!");
        buttonActive=false;
    }

    public void go(View view){
        if(buttonActive){
            reset();

        }
        else {
            buttonActive = true;
            timerSeekBar.setEnabled(false);
            goButton.setText("Stop!");

           countDownTimer= new CountDownTimer(timerSeekBar.getProgress() * 1000 + 50, 1000) {

                @Override
                public void onTick(long millisUntilFinished) {
                    updateTimer((int) millisUntilFinished / 1000);
                }

                @Override
                public void onFinish() {
                    MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.tone);
                    mediaPlayer.start();
                    reset();
                }
            }.start();
        }

    }

    public void updateTimer(int secLeft){
        int minutes=secLeft/60;
        int sec = secLeft-(minutes*60);
        String secString=Integer.toString(sec);
        if(sec<=9){
            secString="0"+secString;
        }
        timerTextView.setText(Integer.toString(minutes)+":"+secString);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerSeekBar=findViewById(R.id.timerSeekBar);
        timerTextView=findViewById(R.id.countDownTextView);
        goButton=findViewById(R.id.goButton);

        timerSeekBar.setMax(600);
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
}