package com.example.app3.fragment;

import static com.iflytek.speech.UtilityConfig.CHANNEL_ID;
import static com.iflytek.speech.UtilityConfig.CHANNEL_NAME;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.app3.R;
import com.example.app3.activity.AddSomethingActivity;
import com.example.app3.activity.HeathActivity;
import com.example.app3.activity.NewsActivity;
import com.example.app3.activity.StudyActivity;
import com.example.app3.adapter.LVAdapter;
import com.example.app3.adapter.LVAdapter1;
import com.example.app3.adapter.VPAdapter;
import com.example.app3.bean.Item1;
import com.example.app3.util.StepService;
import com.example.app3.util.StepUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements AdapterView.OnItemClickListener, View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ViewPager viewPager;
    private List<View> viewList;//viewpager数据源
    private List<Item1> data;
    private ListView listview;
    private LinearLayout pointLayout;
    private VPAdapter adapter;
    private LVAdapter1 adapter1;
    //private ImageView study,heath;
    private int[] picIds={R.drawable.vp1,R.drawable.vp2,R.drawable.vp3};
    private List<ImageView> pointList;//存放显示器小点点的集合
    private String[] texts={"让听障人士“看见”新闻，上海新增两档新闻直播节目手语播报",
            "浙江省首届手语大赛在杭举办 百名参赛选手同打手语《国歌》","我校在天津市2022国家通用手语推广校园手语国歌大赛中喜获一等奖"};
    private int[] imgPath={R.drawable.list1,R.drawable.list2,R.drawable.list3};
    private String[] titles={"让听障人士“看见”新闻，上海新增两档新闻直播节目手语播报","浙江省首届手语大赛在杭举办 百名参赛选手同打手语《国歌》","我校在天津市2022国家通用手语推广校园手语国歌大赛中喜获一等奖"};
    private int[] imgs={R.drawable.img1,R.drawable.img2,R.drawable.img3};
    private TextView steps;
    private int countstep=1000;
    private String[] contents= {"今年的上海两会正在审议讨论《上海市无障碍环境建设条例（草案）》。除了硬件水平的不断提高，如何从软环境上不断提升无障碍城市的温度，也是代表委员们关注的话题。根据市委宣传部的统一部署，从1月13日开始，上海广播电视台在东方卫视《东方新闻》和上视新闻综合频道《新闻报道》这两档主新闻栏目中增设手语播报，让听障人士也能“看见”新闻。\n" +
            "化妆、试镜、读稿、调音，手语翻译寇辰珠早早来到新闻直播间做准备。在午间新闻时段通过手语为听障观众“播报”新闻资讯，已经成为她和手语播报团队与上海8万多名听障人士的约定。寇辰珠加入手语播报团队已有近3年。她告诉记者，手语和汉语是两种语言体系，相比我们熟悉的汉语，手语相当于听障人士的母语，通过手语能帮助他们更好地了解新闻内容。\n" +
            "2000年，上海电视台《时事传真》栏目首次将手语翻译搬上电视荧屏；2015年3月，手语播报以直播的方式加入上海电视台《午间新闻》；2020年2月22日，上海市第30场疫情发布会直播首次增设手语翻译；一年之后，手语播报登陆东方卫视《午间30分》向全球播出。经过多年的打磨，上海正逐步培养起一支经验丰富的新闻直播手语翻译团队。\n" +
            "在收到今年上海两会代表委员关于加大无障碍服务、电视节目配套手语的建议之后，上海广播电视台融媒体中心紧急落实上级要求，积极研究配套技术方案。从1月13日起，东方卫视每天18点《东方新闻》、上视新闻综合频道每天18点30分《新闻报道》这两档主新闻栏目中增设手语播报，使得上海广播电视台配有手语播报的新闻直播栏目增加到三档。\n" +
            "上海广播电视台融媒体中心主任吴茜透露，为了配合手语播报，融媒体中心对两个新闻演播室进行了技术改造，辟出独立演播区域、增设手语翻译专属的音视频线路，确保他们的翻译播报不受外界因素干扰；同时，新闻节目包装样式也做了相应的调整，在确保原有电视画面信息的前提下，尽可能为听障人士看清手语翻译留出空间。“无障碍城市的建设，需要全社会的积极参与。作为主流媒体，我们更应该主动作为，尽到我们的社会责任。所以这次我们在收视率最高的主新闻推出手语播报，希望向全社会传递关心残障人士，提升无障碍城市软环境的理念。”\n" +
            "上海市残联宣传文体处处长孙献忠表示，增设手语播报，不仅保障了残疾人平等获取公共信息的基本权利，也充分体现了上海国际大都市的人文关怀和城市温度。随着手语播报节目时长的增加，手语翻译团队的规模也亟待扩充。为此上海市残联也在积极筹划，希望挖掘一批高端手语人才，充实到手语翻译保障工作中来。",
    "6月28日-30日，浙江省首届手语大赛在浙江特殊教育职业学院举行。来自全省各地的100名专（兼）职手语翻译和手语爱好者参加了比赛。省残联党组书记、理事长蔡国春、中国聋人协会主席杨洋等，为获奖选手颁奖并为“手语助残志愿服务团”授旗。\n" +
            "本次大赛由省残联、团省委、浙江广播电视集团和2022年第四届亚残运会组委会联合主办，分为职业组和业余组。参赛者通过“手语演讲视频评审”和“笔译、手译现场考试”两个环节的激烈角逐，最终分别评选出了各组别一等奖3名，二等奖6名，三等奖10名，以及优秀奖28名。\n" +
            "为庆祝中国共产党建党100周年，手语大赛把国家通用手语版《国歌》作为必考题，还特别策划制作了《11地市通用版手语国歌》宣传片，由11地市优秀选手在当地地标前拍摄剪辑而成。在颁奖典礼现场，百名参赛者在宣传片的氛围烘托下，同打共“唱”手语《国歌》，将活动推向高潮。\n" +
            "据统计，截至2020年底，我省就业年龄段残疾人总数545132人，其中听力残疾人49971人，言语残疾8922人。近年来，我省在加快推进手语规范化建设、深入宣传国家通用手语，推进手语助残志愿服务等方面深耕细作，不断提升我省听障人士的安全感、获得感和幸福感，为我省争创社会主义现代化先行省和建设高质量发展共同富裕示范区贡献了残联力量。\n" +
            "\n",
            "4月24日下午，天津市残联、市教委在天津理工大学图书馆报告厅举办总结2022“残疾人就业创业十大标兵”推选、“残疾学生创新创业竞赛\"活动及2022国家通用手语推广校园手语国歌大赛，启动2023“残疾人就业创业十大标兵”推选、“残疾学生创新创业竞赛\"，国家通用手语推广的综合活动。\n" +
                    "我校参赛作品《心手相连 青春向党》荣获2022国家通用手语推广校园手语国歌大赛活动——天津市大中小学“指尖传承爱国心，青春献礼新时代”手语国歌大赛大学组一等奖，软件与通信学院辅导员郝丽真获“优秀指导教师”称号。\n" +
                    "天津市大中小学“指尖传承爱国心，青春献礼新时代”手语国歌大赛，由市残联、市教委、市语委共同举办，天津理工大学、市聋协具体承办。大赛自2022年6月份正式启动，面向全市大中小学开展，旨在进一步深化广大学生的爱党爱国爱社会主义教育，扎实推动第二期国家通用手语推广行动计划，在校园内形成残健融合育人的和谐氛围，在全社会形成关心关爱残疾人、扶残助残风尚。经过初赛，共45个作品进入决赛，其中大学组27个作品入围，我校参赛作品《心手相连 青春向党》（包含《中华人民共和国国歌》、《国家》两首歌曲表演）经过激烈角逐，最终荣获大学组一等奖。\n" +
                    "天津中德应用技术大学一直以来高度重视思想政治教育工作的创新发展，此次获奖作品由软件与通信学院教师和学生共同创新策划、编排、演绎，融合理想信念教育、爱国主义教育、社会主义核心价值观教育、美育教育为一体，参赛学子用最朴实真挚的肢体语言，抒发出青年大学生对党和国家的热爱，用实际行动推广国家通用手语，让家国之爱走进无声世界，共同发出“请党放心 强国有我”的时代强音，充分展现了我校创新性开展思想政治教育工作的成果。"};

    private Button refresh;
    private int PERMISSION_REQUEST_ACTIVITY_RECOGNITION=100;
    @SuppressLint("HandlerLeak")
    Handler handler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            if(msg.what==1){
                //接收到消息后，页面滑动一面
                int currentItem=viewPager.getCurrentItem();
                viewPager.setCurrentItem(currentItem+1);
                handler.sendEmptyMessageDelayed(1,5000);
            }else if(msg.what==2){
                String counts= StepUtil.getTodayStep(getContext()) + "步";
                steps.setText(counts);
                Log.e("step",counts);
                handler.sendEmptyMessageDelayed(2,3000);
            }
        }
    };

    public HomeFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_home, container, false);
        initPager(view);
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACTIVITY_RECOGNITION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACTIVITY_RECOGNITION},
                    PERMISSION_REQUEST_ACTIVITY_RECOGNITION);
        }
        initStepService();

        return view;
    }
    private void initPager(View view){
        viewPager=view.findViewById(R.id.home_vp);
        pointLayout=view.findViewById(R.id.layout_point);
        listview=view.findViewById(R.id.home_list);

        //study=view.findViewById(R.id.study);
        //heath=view.findViewById(R.id.heath);

        steps=view.findViewById(R.id.steps);

        refresh=view.findViewById(R.id.refresh);
        refresh.setOnClickListener(this);
        //study.setOnClickListener(this);
        //heath.setOnClickListener(this);

        data=new ArrayList<>();
        pointList=new ArrayList<>();
        viewList=new ArrayList<>();
        //初始化页面信息
        for(int i=0;i<picIds.length;i++){
            View v= LayoutInflater.from(getContext()).inflate(R.layout.item_vp,null);
            ImageView iv=v.findViewById(R.id.item_vp_iv);
            iv.setImageResource(picIds[i]);
            viewList.add(v);

            //创建指示器内容
            ImageView pointIv=new ImageView(getContext());
            //在代码中设置控件的属性
            LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(0,0,20,0);
            //将布局参数设置给ImageView
            pointIv.setLayoutParams(lp);
            pointIv.setImageResource(R.drawable.vp_point1);
            //小圆点添加到集合中
            pointList.add(pointIv);
            //将小圆点添加到布局中
            pointLayout.addView(pointIv);
        }
        pointList.get(0).setImageResource(R.drawable.vp_point2);
        setVPListener();
        //创建适配器对象
        adapter=new VPAdapter(viewList);
        //设置适配器
        viewPager.setAdapter(adapter);
        //发送切换页面信息
        handler.sendEmptyMessageDelayed(1,5000);

        for(int i=0;i<texts.length;i++){
            data.add(new Item1(texts[i],imgPath[i]));
        }
        //listview 适配器
        adapter1=new LVAdapter1(data,getContext());
        listview.setAdapter(adapter1);
        listview.setOnItemClickListener(this);

        String count = StepUtil.getTodayStep(getActivity()) + "步";
        steps.setText(count);

        //
        //handler.sendEmptyMessageDelayed(2,3000);
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
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent=new Intent(getActivity(), NewsActivity.class);
        intent.putExtra("title",titles[i]);
        intent.putExtra("img",imgs[i]);
        intent.putExtra("content",contents[i]);
        startActivity(intent);
    }
    private void initStepService() {
        Intent intent = new Intent(getActivity(), StepService.class);
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            getActivity().startForegroundService(intent);
        } else {
            getActivity().startService(intent);
        }*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("CHANNEL_DESCRIPTION");

            NotificationManager notificationManager = getActivity().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    @Override
    public void onClick(View view) {
        Random random=new Random();
        countstep += random.nextInt(10);
        String counts=StepUtil.getTodayStep(getActivity())+countstep+"步";
        steps.setText(counts);
    }
}