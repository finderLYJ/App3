package com.example.app3.activity;

import static com.iflytek.speech.UtilityConfig.CHANNEL_ID;
import static com.iflytek.speech.UtilityConfig.CHANNEL_NAME;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.app3.R;
import com.example.app3.util.StepService;
import com.example.app3.util.StepUtil;


public class HeathActivity extends AppCompatActivity {

    private TextView mTvSteps;
    private Button mBtRefresh;
    private int PERMISSION_REQUEST_ACTIVITY_RECOGNITION=100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heath);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACTIVITY_RECOGNITION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACTIVITY_RECOGNITION},
                    PERMISSION_REQUEST_ACTIVITY_RECOGNITION);
        }

        initStepService();
        initView();
        initData();
    }

    /**
     * 初始化计步服务
     * 注：因初始化需要过程，正常项目中，初始化应该放在进入到主界面之前的activity中，比如闪屏页中进行初始化
     * 因此本demo在第一次安装时会提示"手机暂不支持计步功能"，杀死进程再次打开即可正常显示
     */
    private void initStepService() {
        Intent intent = new Intent(this, StepService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(intent);
        } else {
            startService(intent);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("CHANNEL_DESCRIPTION");

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    /**
     * 初始化view
     */
    private void initView() {
        mTvSteps = findViewById(R.id.tv_steps);
        mBtRefresh = findViewById(R.id.bt_refresh);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        if (!StepUtil.isSupportStep(this)) {
            mTvSteps.setText("手机暂不支持计步功能");
            return;
        }
        mBtRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String steps = StepUtil.getTodayStep(HeathActivity.this) + "步";
                mTvSteps.setText(steps);
            }
        });
    }

}
