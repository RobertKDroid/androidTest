package com.example.stacjonarny.testapp.adapters;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.DimenRes;
import android.support.annotation.Dimension;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.stacjonarny.testapp.R;

/**
 * Created by Stacjonarny on 2016-09-28.
 */

public class VideoGridAdapter extends BaseAdapter {
    private Context mContext;


    public VideoGridAdapter(Context c) {
        mContext = c;
    }

    @Override
    public int getCount() {
        return mThumbsId.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v;
        ImageView imageView;
        TextView textView;


        LayoutInflater layoutInflater = LayoutInflater.from(mContext.getApplicationContext());
        v = layoutInflater.inflate(R.layout.video_main, null);


        imageView = (ImageView) v.findViewById(R.id.video_image);


        textView = (TextView) v.findViewById(R.id.video_title);

        textView.setText("bardzo dlugi opi filmu ktory znajduje sie na obrazku z psem w bluzie bo dlaczego nieoriginalk  " + position);
        imageView.setAdjustViewBounds(true);
        imageView.setScaleType(ImageButton.ScaleType.CENTER_CROP);
        imageView.setPadding(5, 5, 5, 5);
        imageView.setImageResource(mThumbsId[position]);

        return v;
    }

    private Integer[] mThumbsId = {
            R.drawable.dixi_1,
            R.drawable.dixi_1,
            R.drawable.dixi_1,
            R.drawable.dixi_1,
            R.drawable.dixi_1,
            R.drawable.dixi_1,
            R.drawable.dixi_1,
            R.drawable.dixi_1,
            R.drawable.dixi_1, R.drawable.dixi_1,
            R.drawable.dixi_1,
            R.drawable.dixi_1,
            R.drawable.dixi_1,
            R.drawable.dixi_1,
            R.drawable.dixi_1,
            R.drawable.dixi_1,
            R.drawable.dixi_1,
            R.drawable.dixi_1, R.drawable.dixi_1,
            R.drawable.dixi_1,
            R.drawable.dixi_1,
            R.drawable.dixi_1,
            R.drawable.dixi_1,
            R.drawable.dixi_1,
            R.drawable.dixi_1,
            R.drawable.dixi_1,
            R.drawable.dixi_1, R.drawable.dixi_1,
            R.drawable.dixi_1,
            R.drawable.dixi_1,
            R.drawable.dixi_1,
            R.drawable.dixi_1,
            R.drawable.dixi_1,
            R.drawable.dixi_1,
            R.drawable.dixi_1,
            R.drawable.dixi_1,
            R.drawable.dixi_1
    };
}
