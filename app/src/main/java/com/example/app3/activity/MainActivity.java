package com.example.app3.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import com.example.app3.R;
import com.example.app3.adapter.VPAdapter;
import com.example.app3.fragment.FuncFragment;
import com.example.app3.fragment.HomeFragment;
import com.example.app3.fragment.MyFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener{
    private ViewPager viewPager;
    private RadioGroup rg;
    private List<View> viewList;//viewpager数据源
    private LinearLayout pointLayout;
    private VPAdapter adapter;
    private int[] picIds={R.drawable.vp1,R.drawable.vp2,R.drawable.vp3};
    private List<ImageView> pointList;//存放显示器小点点的集合

    private Fragment homeFrag,funcFrag,myFrag;
    private FragmentManager manager;
    @SuppressLint("HandlerLeak")
    Handler handler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            if(msg.what==1){
                //接收到消息后，页面滑动一面
                int currentItem=viewPager.getCurrentItem();
                viewPager.setCurrentItem(currentItem+1);
                handler.sendEmptyMessageDelayed(1,3000);
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }
    private void init() {
        rg=findViewById(R.id.rg);
        rg.setOnCheckedChangeListener(this);
        /*
        viewPager = findViewById(R.id.home_vp);
        pointLayout = findViewById(R.id.layout_point);
        pointList = new ArrayList<>();
        viewList = new ArrayList<>();
        //初始化页面信息
        for (int i = 0; i < picIds.length; i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.item_vp, null);
            ImageView iv = view.findViewById(R.id.item_vp_iv);
            iv.setImageResource(picIds[i]);
            viewList.add(view);

            //创建指示器内容
            ImageView pointIv = new ImageView(this);
            //在代码中设置控件的属性
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(0, 0, 20, 0);
            //将布局参数设置给ImageView
            pointIv.setLayoutParams(lp);
            pointIv.setImageResource(R.drawable.vp_point1);
            pointList.add(pointIv);
            pointLayout.addView(pointIv);
        }
        pointList.get(0).setImageResource(R.drawable.vp_point1);
        setVPListener();
        //创建适配器对象
        adapter = new VPAdapter(viewList);
        //设置适配器
        viewPager.setAdapter(adapter);
        //发送切换页面信息
        handler.sendEmptyMessageDelayed(1, 5000);
        */
        //创建碎片对象
        homeFrag=new HomeFragment();
        funcFrag=new FuncFragment();
        myFrag=new MyFragment();
        //将3个Fragment进行动态加载，一起加载到布局中
        addFragmentPage();
    }
    //将主页当中碎片加载进行进入布局，有用的显示，没用的隐藏
    private void addFragmentPage() {
        //碎片管理者对象
        manager=getSupportFragmentManager();
        //创建碎片处理事务的对象
        FragmentTransaction transaction=manager.beginTransaction();
        transaction.add(R.id.main_layout_center,homeFrag);
        transaction.add(R.id.main_layout_center,funcFrag);
        transaction.add(R.id.main_layout_center,myFrag);
        //隐藏后两个
        transaction.hide(funcFrag);
        transaction.hide(myFrag);
        //提交碎片改变后的事务
        transaction.commit();
    }

    private void setVPListener(){
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {
                for(int i=0;i<pointList.size();i++){
                    pointList.get(i).setImageResource(R.drawable.vp_point1);
                }
                pointList.get(position%pointList.size()).setImageResource(R.drawable.vp_point2);
            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        FragmentTransaction transaction=manager.beginTransaction();
        switch (checkedId){
            case R.id.rb_home:
                transaction.hide(funcFrag);
                transaction.hide(myFrag);
                transaction.show(homeFrag);
                break;
            case R.id.rb_func:
                transaction.hide(homeFrag);
                transaction.hide(myFrag);
                transaction.show(funcFrag);
                break;
            case R.id.rb_my:
                transaction.hide(funcFrag);
                transaction.hide(homeFrag);
                transaction.show(myFrag);
                break;
        }
        transaction.commit();
    }
}