package com.example.stacjonarny.testapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

public class VideoGalleryActivity extends AppCompatActivity {
    private final static String TAG = "GALLERY_ACTIVITY";
    public final static String EXTRA_POSITION = "com.example.stacjonarny.laptop.VideoGalleryDetailsActivity";
    GridView gridView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        gridView = (GridView) findViewById(R.id.main_grid_videos);
        gridView.setAdapter(new VideoGridAdapter(this));


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(VideoGalleryActivity.this, "Clicked video number "+position, Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getBaseContext(),VideoDetailsActivity.class);
//                i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);

                i.putExtra(EXTRA_POSITION,String.valueOf(position));
                startActivity(i);
            }
        });
    }
}
