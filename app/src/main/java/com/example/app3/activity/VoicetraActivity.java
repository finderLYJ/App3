package com.example.app3.activity;

import static com.example.app3.XunFeiUtil.parseIatResult;
import static com.example.app3.XunFeiUtil.startVoice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.app3.R;
import com.example.app3.XunFeiCallbackListener;
import com.example.app3.XunFeiUtil;
import com.example.app3.bean.Word;
import com.example.app3.helper.MyDBhelper;
import com.iflytek.cloud.RecognizerResult;

import java.util.List;

public class VoicetraActivity extends AppCompatActivity implements View.OnClickListener{
    private ImageButton btn_click;
    private TextView mResultText;
    private VideoView vv_play;
    private MediaController mediaController;
    private Button btn_play;
    private Button btn_pause;
    private String path = null;
    //private Button btn_database;
    private List<Word> list;
    private MyDBhelper myDBhelper;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voicetra);
        XunFeiUtil.initXunFei(this);
        toolbar=findViewById(R.id.voicestra_toolbar);
        toolbar.setTitle("语音转义");
        setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent=new Intent();
                switch (item.getItemId()){
                    case R.id.showDatabase:
                        intent.setClass(VoicetraActivity.this,ShowDatabaseActivity.class);
                        break;
                    default:
                        break;
                }
                startActivity(intent);
                return false;
            }
        });
        myDBhelper = new MyDBhelper(VoicetraActivity.this, "trahistory.db", null, 1);
        btn_click=(ImageButton) findViewById(R.id.btn_click);
        mResultText = ( findViewById(R.id.result));
        btn_click.setOnClickListener(this);
        vv_play = (VideoView) findViewById(R.id.vv_play);
        //创建MediaController对象
        mediaController = new MediaController(this);
        if(path == null){
            vv_play.setVisibility(View.INVISIBLE);
        }
        //创建播放和暂停的按钮对象
        btn_play = (Button)findViewById(R.id.btn_start);
        btn_pause = (Button) findViewById(R.id.btn_stop);
        //btn_database = (Button) findViewById(R.id.btn_database);
        btn_play.setOnClickListener(new mClick());
        btn_pause.setOnClickListener(new mClick());
        //btn_database.setOnClickListener(new dClick());

        //接收来自数据库活动的消息
        Intent get = getIntent();
        String getInfo = get.getStringExtra("index");
        if(getInfo!=null){
            mResultText.setText(getInfo);
            switch(getInfo){
                case "你好":
                    path = "android.resource://" + getPackageName() + "/" + R.raw.hello;
                    break;
                case "喝茶":
                    path = "android.resource://" + getPackageName() + "/" + R.raw.drink;
                    break;
                case"早上好":
                    path = "android.resource://" + getPackageName() + "/" + R.raw.goodmorning;
                    break;
                case"朋友":
                    path = "android.resource://" + getPackageName() + "/" + R.raw.friend;
                    break;
                case"漂亮":
                    path = "android.resource://" + getPackageName() + "/" + R.raw.beautiful;
                    break;
                case"谢谢":
                    path = "android.resource://" + getPackageName() + "/" + R.raw.thanks;
                    break;
                default:
                    break;
            }
            vv_play.setVisibility(View.VISIBLE);
            vv_play.setVideoURI(Uri.parse(path));
            mediaController.setMediaPlayer(vv_play);
            vv_play.setMediaController(mediaController);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_voicetra_showdatabase,menu);
        return true;
    }

    @Override
    public void onClick(View view) {
        vv_play.setVisibility(View.INVISIBLE);
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED){//未开启定位权限
            //开启定位权限,200是标识码
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.RECORD_AUDIO},300);
        }
        startVoice(this, new XunFeiCallbackListener() {
            @Override
            public void onFinish(RecognizerResult results) {
                String text = parseIatResult(results.getResultString());
                // 自动填写地址
                mResultText.setText(text);
                switch (text) {
                    case "你好":
                        path = "android.resource://" + getPackageName() + "/" + R.raw.hello;
                        break;
                    case "喝茶":
                        path = "android.resource://" + getPackageName() + "/" + R.raw.drink;
                        break;
                    case"早上好":
                        path = "android.resource://" + getPackageName() + "/" + R.raw.goodmorning;
                        break;
                    case"朋友":
                        path = "android.resource://" + getPackageName() + "/" + R.raw.friend;
                        break;
                    case"漂亮":
                        path = "android.resource://" + getPackageName() + "/" + R.raw.beautiful;
                        break;
                    case"谢谢":
                        path = "android.resource://" + getPackageName() + "/" + R.raw.thanks;
                        break;
                    default:
                        break;
                }
                vv_play.setVisibility(View.VISIBLE);
                vv_play.setVideoURI(Uri.parse(path));
                mediaController.setMediaPlayer(vv_play);
                vv_play.setMediaController(mediaController);
                //查询数据库中是否含有这个词语
                list = myDBhelper.queryTraHistory();
                boolean isExist = false;
                for (Word word : list) {
                    if (word.getWord().equals(text)) {
                        isExist = true;
                        break;
                    }
                }
                if(!isExist&&!text.equals("")){//如果数据库中不含，则插入
                    myDBhelper.insertTraHistory(text);
                }else{
                    Toast.makeText(VoicetraActivity.this, "该文本已添加!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    class mClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (v == btn_play) {
                vv_play.start();
            } else if (v == btn_pause) {
                vv_play.pause();
            }
        }
    }
    public  class dClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setClass(VoicetraActivity.this,ShowDatabaseActivity.class);
            startActivity(intent);
        }
    }
}