package com.example.app3.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.app3.R;

public class InitActivity extends AppCompatActivity implements View.OnClickListener {
    private Button go_login,go_register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init);

        go_login=findViewById(R.id.go_login);
        go_register=findViewById(R.id.go_register);
        go_login.setOnClickListener(this);
        go_register.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent=new Intent();
        switch(view.getId()){
            case R.id.go_login:
                intent.setClass(InitActivity.this,LoginActivity.class);
                break;
            case R.id.go_register:
                intent.setClass(InitActivity.this,RegisterActivity.class);
                break;
        }
        startActivity(intent);
        finish();
    }
}