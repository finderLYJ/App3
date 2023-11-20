package com.example.app3.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app3.R;
import com.example.app3.helper.MyDBhelper;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText et_username,et_password,et_password_again;
    private Button btn_register;
    MyDBhelper myDBhelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
        btn_register.setOnClickListener(this);
    }
    private void init(){
        et_username=findViewById(R.id.username_register);
        et_password=findViewById(R.id.password_register);
        et_password_again=findViewById(R.id.password_again);
        btn_register=findViewById(R.id.btn_register);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btn_register:
                String username=et_username.getText().toString().trim();
                String password=et_password.getText().toString().trim();
                String password_again=et_password_again.getText().toString().trim();
                if(username.equals("") || password.equals("")||password_again.equals("")){
                    Toast.makeText(RegisterActivity.this,"用户名或密码不能为空！",Toast.LENGTH_LONG).show();
                }else if(!password.equals(password_again)){
                    Toast.makeText(RegisterActivity.this, "两次密码不一致！", Toast.LENGTH_LONG).show();
                }else if(password.equals(password_again)){
                    myDBhelper=new MyDBhelper(RegisterActivity.this,"user.db",null,1);
                    if(myDBhelper.insertUser(username,password)){
                        Toast.makeText(RegisterActivity.this,"注册成功",Toast.LENGTH_LONG).show();
                        Intent toLogin=new Intent(RegisterActivity.this,LoginActivity.class);
                        toLogin.putExtra("username",username);
                        toLogin.putExtra("password",password);
                        startActivity(toLogin);
                        finish();
                    }
                }
                break;
        }
    }
}