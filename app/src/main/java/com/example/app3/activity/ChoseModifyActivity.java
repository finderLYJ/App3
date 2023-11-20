package com.example.app3.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.app3.R;
import com.example.app3.helper.MyDBhelper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ChoseModifyActivity extends AppCompatActivity implements View.OnClickListener {
    private RelativeLayout psd,name;
    private MyDBhelper myDBhelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chose_modify);
        psd=findViewById(R.id.modify_psd);
        name=findViewById(R.id.modify_nickname);
        psd.setOnClickListener(this);
        name.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent=new Intent(this,ModifyActivity.class);
        switch(view.getId()){
            case R.id.modify_psd:
                //intent.putExtra("id",R.id.modify_psd+"");
                //showInputDialog();
                break;
            case R.id.modify_nickname:
                //intent.putExtra("id",R.id.modify_nickname+"");
                break;
        }
        //startActivity(intent);
    }
    private void showInputDialog(){
        View view= LayoutInflater.from(this).inflate(R.layout.mydialog,null);

        EditText psd=view.findViewById(R.id.modify_psd_et);
        EditText psd_again=view.findViewById(R.id.modify_psd_et_again);

        AlertDialog.Builder inputDialog=new AlertDialog.Builder(ChoseModifyActivity.this);
        inputDialog.setTitle("修改密码").setView(view);

        inputDialog.setPositiveButton("保存", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
               String psd1=psd.getText().toString().trim();
               String psd2=psd_again.getText().toString().trim();
               if(!psd1.equals(psd2)){
                   Toast.makeText(ChoseModifyActivity.this, "两次密码不一致", Toast.LENGTH_SHORT).show();
                   showInputDialog();
               }else{
                   Toast.makeText(ChoseModifyActivity.this, "两次密码不一致", Toast.LENGTH_SHORT).show();
               }
            }
        }).show();
    }
}