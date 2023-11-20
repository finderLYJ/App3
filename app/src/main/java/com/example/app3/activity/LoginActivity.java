package com.example.app3.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.app3.R;
import com.example.app3.bean.UserInfo;
import com.example.app3.helper.MyDBhelper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText et_username,et_password;
    private ImageView eye;
    private Button btn_login;
    private boolean visible=false;
    private MyDBhelper myDBhelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        btn_login.setOnClickListener(this);
    }
    private void init(){
        et_username=findViewById(R.id.username_login);
        et_password=findViewById(R.id.password_login);
        eye=findViewById(R.id.eye);
        eye.setOnClickListener(this);
        Intent intent=getIntent();
        if(intent!=null){
            et_username.setText(intent.getStringExtra("username"));
            et_password.setText(intent.getStringExtra("password"));
        }
        btn_login=findViewById(R.id.btn_login);
    }

    private void login(){
        String username=et_username.getText().toString().trim();
        String password=et_password.getText().toString().trim();
        myDBhelper=new MyDBhelper(LoginActivity.this,"user.db",null,1);
        //从数据库中找用户信息
        UserInfo user=myDBhelper.query(username);
        if(user==null){
            Toast.makeText(LoginActivity.this, "该用户不存在！", Toast.LENGTH_LONG).show();
        }else{
            if(user.getUsername().equals(username)&&user.getPassword().equals(password)){
                Toast.makeText(LoginActivity.this, "登录成功！", Toast.LENGTH_LONG).show();
                Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }else{
                Toast.makeText(LoginActivity.this, "用户名或密码错误！", Toast.LENGTH_LONG).show();
            }
        }
    }
    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btn_login:
                login();
                break;
            case R.id.eye:
                if(!visible){
                    eye.setImageResource(R.drawable.kejian);
                    HideReturnsTransformationMethod method=HideReturnsTransformationMethod.getInstance();
                    et_password.setTransformationMethod(method);

                    int textLength = et_password.getText().length();
                    et_password.setSelection(textLength, textLength);

                    visible=true;
                }else{
                    eye.setImageResource(R.drawable.bukejian);
                    TransformationMethod method= PasswordTransformationMethod.getInstance();
                    et_password.setTransformationMethod(method);

                    int textLength = et_password.getText().length();
                    et_password.setSelection(textLength, textLength);

                    visible=false;
                }
                break;
        }
    }
}