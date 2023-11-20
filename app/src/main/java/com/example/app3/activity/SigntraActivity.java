package com.example.app3.activity;
/*手语翻译界面*/
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.app3.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.UUID;

public class SigntraActivity extends AppCompatActivity {

    private final static String MY_UUID = "00001101-0000-1000-8000-00805F9B34FB";   //SPP服务UUID号
    private BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter(); //获取蓝牙实例

    private EditText sendEditText; //创建发送 句柄
    private TextView tv_in; //接收显示句柄
    private ScrollView scrollView; //翻页句柄
    private String smsg = ""; //显示用数据缓存

    BluetoothDevice mBluetoothDevice = null; //蓝牙设备
    BluetoothSocket mBluetoothSocket = null; //蓝牙通信Socket

    boolean bRun = true; //运行状态
    boolean bThread = false; //读取线程状态
    private InputStream is;    //输入流，用来接收蓝牙数据
    private ImageView backhome;
    private VideoView videoView;
    private MediaController mediaController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signtra);

        //获取控件ID
        //sendEditText = findViewById(R.id.sendEditText);
        //scrollView = findViewById(R.id.receiveScrolView);
        tv_in = findViewById(R.id.in);
        /*
        videoView=findViewById(R.id.videoView1);
        String url="android.resource://"+getPackageName()+"/"+R.raw.hello;
        mediaController=new MediaController(FirstActivity.this);
        videoView.setVideoURI(Uri.parse(url));
        videoView.setMediaController(mediaController);
        */
        backhome=findViewById(R.id.backhome1);
        backhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //如果打不开蓝牙提示信息，结束程序
        if (mBluetoothAdapter == null){
            Toast.makeText(getApplicationContext(),"无法打开手机蓝牙，请确认手机是否有蓝牙功能！",Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        //连接按钮响应
        final Button connectButton = findViewById(R.id.connectButton);
        connectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mBluetoothAdapter.isEnabled() == false) {
                    Toast.makeText(getApplicationContext(), " 请先打开蓝牙", Toast.LENGTH_LONG).show();
                    return;
                }

                //如果未连接设备则打开DevicesListActivity搜索设备
                if (mBluetoothSocket == null) {
                    Intent serveIntent = new Intent(SigntraActivity.this, DeviceListActivity.class); //跳转活动
                    startActivityForResult(serveIntent, 1); //设置返回宏定义
                } else {
                    //关闭连接socket
                    try {
                        bRun = false;
                        Thread.sleep(2000);

                        is.close();
                        mBluetoothSocket.close();
                        mBluetoothSocket = null;

                        connectButton.setText("连接");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        // 设置设备可以被搜索
        new Thread(){
            @SuppressLint("MissingPermission")
            public void run(){
                if(mBluetoothAdapter.isEnabled()==false){
                    mBluetoothAdapter.enable();
                }
            }
        }.start();
    }

    //接收活动结果，响应startActivityForResult()
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:     //连接结果，由DeviceListActivity设置返回
                // 响应返回结果
                if (resultCode == Activity.RESULT_OK) {   //连接成功，由DeviceListActivity设置返回
                    // MAC地址，由DeviceListActivity设置返回
                    String address = data.getExtras().getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
                    // 得到蓝牙设备句柄
                    mBluetoothDevice = mBluetoothAdapter.getRemoteDevice(address);

                    // 用服务号得到socket
                    try {
                        mBluetoothSocket = mBluetoothDevice.createRfcommSocketToServiceRecord(UUID.fromString(MY_UUID));
                    } catch (IOException e) {
                        Toast.makeText(this, "连接失败！", Toast.LENGTH_SHORT).show();
                    }
                    //连接socket
                    Button connectButton = findViewById(R.id.connectButton);
                    try {
                        mBluetoothSocket.connect();
                        Toast.makeText(this, "连接" + mBluetoothDevice.getName() + "成功！", Toast.LENGTH_SHORT).show();
                        connectButton.setText("断开");
                    } catch (IOException e) {
                        try {
                            Toast.makeText(this, "连接失败！", Toast.LENGTH_SHORT).show();
                            mBluetoothSocket.close();
                            mBluetoothSocket = null;
                        } catch (IOException ee) {
                            Toast.makeText(this, "连接失败！", Toast.LENGTH_SHORT).show();
                        }

                        return;
                    }

                    //打开接收线程
                    try {
                        is = mBluetoothSocket.getInputStream();   //得到蓝牙数据输入流
                    } catch (IOException e) {
                        Toast.makeText(this, "接收数据失败！", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (bThread == false) {
                        readThread.start();
                        bThread = true;
                    } else {
                        bRun = true;
                    }
                }
                break;
            default:
                break;
        }
    }

    //接收数据线程
    Thread readThread=new Thread(){

        public void run(){
            int num = 0;
            byte[] buffer = new byte[1024];
            byte[] buffer_new = new byte[1024];
            int i = 0;
            int n = 0;
            bRun = true;
            //接收线程
            while(true){
                try{
                    while(is.available()==0){
                        while(bRun == false){}
                    }
                    while(true){
                        if(!bThread)//跳出循环
                            return;

                        num = is.read(buffer);         //读入数据
                        n=0;

                        String s0 = new String(buffer,0,num);
                        for(i=0;i<num;i++){
                            if((buffer[i] == 0x0d)&&(buffer[i+1]==0x0a)){
                                buffer_new[n] = 0x0a;
                                i++;
                            }else{
                                buffer_new[n] = buffer[i];
                            }
                            n++;
                        }
                        String s = new String(buffer_new,0,n);
                        smsg=s;   //写入接收缓存
                        if(is.available()==0)break;  //短时间没有数据才跳出进行显示
                    }
                    //发送显示消息，进行显示刷新

                    handler.sendMessage(handler.obtainMessage());
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
        }
    };
    private TextToSpeech tts;
    //消息处理队列
    Handler handler= new Handler(Looper.myLooper()){
        @SuppressLint("HandlerLeak")
        public void handleMessage(Message msg){
            super.handleMessage(msg);

            tv_in.setText(smsg);   //显示数据
            tts=new TextToSpeech(SigntraActivity.this, new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int i) {
                    if(i==TextToSpeech.SUCCESS){
                        tts.setLanguage(Locale.SIMPLIFIED_CHINESE);
                        // 设置音调，值越大声音越尖（女生），值越小则变成男声,1.0是常规
                        tts.setPitch(0.5f);
                        // 设置语速
                        tts.setSpeechRate(0.5f);
                        tts.speak(smsg,TextToSpeech.QUEUE_FLUSH,null);
                    }
                }
            });

        }
    };

    //关闭程序调用处理部分
    public void onDestroy(){
        super.onDestroy();
        if(mBluetoothSocket!=null)  //关闭连接socket
            try{
                mBluetoothSocket.close();
            }catch(IOException e){}
    }
}