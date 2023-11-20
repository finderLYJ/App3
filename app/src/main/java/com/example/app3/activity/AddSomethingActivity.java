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

public class AddSomethingActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView backSecond;
    private TextView title,showTime;
    private EditText et_input;
    private Button save;
    private MyDBhelper myDBhelper;
    String sendId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addsome);
        init();
        backSecond.setOnClickListener(this);
        et_input.setOnClickListener(this);
        save.setOnClickListener(this);
        Intent intent=this.getIntent();
        sendId=intent.getStringExtra("id");
        if(sendId==null){
            title.setText("添加常用文字");
        }else{
            title.setText("修改常用文字");
            //让显示时间的控件可见
            String time=intent.getStringExtra("add_time");
            String content=intent.getStringExtra("content");
            showTime.setVisibility(View.VISIBLE);
            showTime.setText(time);
            //把原来的文本信息显示在编辑框内
            et_input.setText(content);
        }
    }
    private void init(){
        backSecond=findViewById(R.id.backSecond);
        et_input=findViewById(R.id.input_usually_word);
        save=findViewById(R.id.second_save);
        title=findViewById(R.id.add_word_title);
        showTime=findViewById(R.id.showTime);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.backSecond:
//                Intent intent=new Intent(AddsSmethingActivity.this,HomeActivity.class);
//                startActivity(intent);
                finish();
                break;
            case R.id.second_save:
                String word=et_input.getText().toString();
                if(sendId==null){
                    if(word.equals("")){
                        Toast.makeText(AddSomethingActivity.this, "内容不能为空", Toast.LENGTH_SHORT).show();
                    }else{
                        //数据添加
                        myDBhelper=new MyDBhelper(AddSomethingActivity.this,"word.db",null,1);
                        boolean flag=myDBhelper.insertWord(word);
                        if(flag){
                            setResult(2);
                            Toast.makeText(AddSomethingActivity.this, "添加成功！！！", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(AddSomethingActivity.this, "添加失败！！！", Toast.LENGTH_SHORT).show();
                        }
                    }
                }else{
                    if(word.equals("")){
                        Toast.makeText(AddSomethingActivity.this, "内容不能为空", Toast.LENGTH_SHORT).show();
                    }else{
                        myDBhelper=new MyDBhelper(AddSomethingActivity.this,"word.db",null,1);
                        if(myDBhelper.updateWord(sendId,word)){
                            setResult(2);
                            Toast.makeText(AddSomethingActivity.this, "更新成功！！！", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(AddSomethingActivity.this, "更新失败！！！", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                break;
        }
    }
}