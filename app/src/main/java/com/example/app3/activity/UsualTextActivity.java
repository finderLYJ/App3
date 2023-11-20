package com.example.app3.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.app3.R;
import com.example.app3.adapter.MyAdapter;
import com.example.app3.bean.Word;
import com.example.app3.helper.MyDBhelper;

import java.util.List;
import java.util.Locale;

public class UsualTextActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView backHome, add, more;
    private MyDBhelper myDBhelper;
    private MyAdapter myAdapter;
    private List<Word> resultList;
    private ListView listView;
    private TextToSpeech tts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usual_text);
        init();
        backHome.setOnClickListener(this);
        add.setOnClickListener(this);
        //more.setOnClickListener(this);
        //设置列表项的点击监听器
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                //当列表项被点击时对该项的内容进行语音播报
                String text;
                Word item=(Word) listView.getItemAtPosition(position);
                text= item.getWord();
                tts=new TextToSpeech(UsualTextActivity.this, new TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(int i) {
                        if(i==TextToSpeech.SUCCESS){
                            tts.setLanguage(Locale.CHINESE);
                            // 设置音调，值越大声音越尖（女生），值越小则变成男声,1.0是常规
                            tts.setPitch(0.5f);
                            // 设置语速
                            tts.setSpeechRate(0.5f);
                            tts.speak(text,TextToSpeech.QUEUE_FLUSH,null);
                        }
                    }
                });
            }
        });
        //设置列表项的长按监听器
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog dialog = null;
                AlertDialog.Builder builder = new AlertDialog.Builder(UsualTextActivity.this);
                builder.setTitle("删除记录")
                        .setMessage("你确定要删除这条记录吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //确定要删除记录是哪一条
                                Word word = (Word) myAdapter.getItem(position);
                                String deleteId = word.getId();
                                //不友好，应该用对话框的形式
                                if (myDBhelper.deleteWord(deleteId)) {
                                    //resultList.remove(deleteId);
                                    //myAdapter.notifyDataSetChanged();
                                    //删除成功就刷新一次
                                    refresh();
                                    Toast.makeText(UsualTextActivity.this, "删除成功！", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(UsualTextActivity.this, "删除失败！", Toast.LENGTH_LONG).show();
                                }
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                dialog = builder.create();
                dialog.show();
                return true;
            }
        });
    }

    private void init() {
        myDBhelper = new MyDBhelper(UsualTextActivity.this, "word.db", null, 1);
        backHome = findViewById(R.id.backhome2);
        add = findViewById(R.id.add);
        //more = findViewById(R.id.clear_all);
        listView = findViewById(R.id.second_listview);
        refresh();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backhome2:
                finish();
                break;
            case R.id.add:
                Intent intent = new Intent(UsualTextActivity.this, AddSomethingActivity.class);
                startActivityForResult(intent, 1);
                break;
            //case R.id.clear_all:
            //clearall();
            //break;
            default:
                break;
        }
    }

    private void clearall() {
        myDBhelper.clear();
        refresh();
    }

    private void refresh() {
        resultList = myDBhelper.queryWord();
        myAdapter = new MyAdapter(resultList, UsualTextActivity.this);
        listView.setAdapter(myAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 2) {
            refresh();
        }
    }
}