package com.example.stacjonarny.testapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

/**
 * Created by Stacjonarny on 2016-09-28.
 */

public class RecyclerViewArticlesAdapter extends RecyclerView.Adapter<RecyclerViewArticlesAdapter.ViewHolder> {
    private static Context context;
    private String[] mDataSet= {
            "Pies pogryzł małego węża bardzo dotkliwie i mocno fes",
            "art2",
            "art3",
            "art4",
    };
    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView mTextViewTitle;
        public ImageView mImageViewThumb;




        public ViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            mTextViewTitle = (TextView)itemView.findViewById(R.id.article_title);
            mImageViewThumb = (ImageView)itemView.findViewById(R.id.article_image);
            mImageViewThumb.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
    }



    @Override
    public RecyclerViewArticlesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.article_card,parent,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerViewArticlesAdapter.ViewHolder holder, int position) {
//        holder.mImageViewThumb.setImageResource(R.drawable.dixi_1);

        try{
            Picasso.with(context).
                    load("http://i.stack.imgur.com/vk4sZ.png").
                    placeholder(R.drawable.placeholder).
                    error(R.drawable.placeholder_error).memoryPolicy(MemoryPolicy.NO_CACHE).
                    into(holder.mImageViewThumb);
        }catch(Exception e){
            e.printStackTrace();
        }


        if(mDataSet[position].length()>35){
            holder.mTextViewTitle.setText(mDataSet[position].substring(0,35));
            holder.mTextViewTitle.append("...");
        }else {
            holder.mTextViewTitle.setText(mDataSet[position]);

        }


    }

    @Override
    public int getItemCount() {
        return mDataSet.length;
    }
}
