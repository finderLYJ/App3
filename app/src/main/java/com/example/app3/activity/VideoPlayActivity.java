package com.example.app3.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.app3.R;

public class VideoPlayActivity extends AppCompatActivity {

    private VideoView videoView;
    private TextView title;
    private Button btn_play,btn_pause;
    private boolean isClicked=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play);
        Intent intent=getIntent();
        Uri uri=Uri.parse(intent.getStringExtra("uri"));
        title=findViewById(R.id.title_videoplay);
        title.setText(intent.getStringExtra("title"));
        videoView=findViewById(R.id.videoView);
        videoView.setVideoURI(uri);
        videoView.setMediaController(new MediaController(VideoPlayActivity.this));

        btn_play=findViewById(R.id.btn_play);
        btn_pause=findViewById(R.id.btn_pause);

        if(!isClicked){
            videoView.setVisibility(View.INVISIBLE);
        }
        btn_play.setOnClickListener(new VideoPlayActivity.mClick());
        btn_pause.setOnClickListener(new VideoPlayActivity.mClick());
    }
    class mClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (v == btn_play) {
                isClicked=true;
                videoView.setVisibility(View.VISIBLE);
                videoView.start();
            } else if (v == btn_pause) {
                videoView.pause();
            }
        }
    }
}