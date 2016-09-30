package com.example.stacjonarny.testapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

public class VideoDetailsActivity extends AppCompatActivity {

    private MediaController mMediaController;
    TextView mTextViewDescription;
    VideoView mVideo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent i = getIntent();
        String messageReceived = i.getStringExtra(VideoGalleryActivity.EXTRA_POSITION);
        mTextViewDescription = (TextView) findViewById(R.id.video_description);
        mTextViewDescription.setText("video number "+messageReceived);

        if(mMediaController == null){
            mMediaController = new MediaController(this);
        }

        Uri uri = Uri.parse("rtsp://r2---sn-a5m7zu76.c.youtube.com/Ck0LENy73wIaRAnTmlo5oUgpQhMYESARFEgGUg5yZWNvbW1lbmRhdGlvbnIhAWL2kyn64K6aQtkZVJdTxRoO88HsQjpE1a8d1GxQnGDmDA==/0/0/0/video.3gp");
        mVideo =(VideoView) findViewById(R.id.video);
        mVideo.setMediaController(mMediaController);
        mVideo.setVideoURI(uri);
        mVideo.requestFocus();
        mVideo.start();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
