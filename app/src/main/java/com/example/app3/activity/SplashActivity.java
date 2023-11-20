package com.example.app3.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.app3.R;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Timer timer=new Timer();
        timer.schedule(timertask,2000);
    }
    TimerTask timertask=new TimerTask() {
        @Override
        public void run() {
            SharedPreferences.Editor editor = getSharedPreferences("data",MODE_PRIVATE).edit();
            SharedPreferences pref = getSharedPreferences("data",MODE_PRIVATE);
            if(pref.getString("login",null)==null){
                editor.putString("login","1");
                editor.commit();
            }
            SharedPreferences.Editor editor2 = getSharedPreferences("data",MODE_PRIVATE).edit();
            SharedPreferences pref2 = getSharedPreferences("data",MODE_PRIVATE);
            if(pref2.getString("login","").equals("1")){
                startActivity(new Intent(SplashActivity.this,InitActivity.class));
                editor2.putString("login","2");
                editor2.commit();
                finish();
            }else{
                startActivity(new Intent(SplashActivity.this,MainActivity.class));
                finish();
            }
        }
    };
}