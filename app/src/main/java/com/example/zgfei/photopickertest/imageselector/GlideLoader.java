package com.example.zgfei.photopickertest.imageselector;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.zgfei.photopickertest.R;
import com.yancy.imageselector.ImageLoader;

/**
 * Created by zgfei on 2017/5/8.
 */

public class GlideLoader implements ImageLoader {

    @Override
    public void displayImage(Context context, String path, ImageView imageView) {
        Glide.with(context)
                .load(path)
                .placeholder(R.mipmap.ic_launcher)// 占位图片
                .centerCrop()
                .into(imageView);
    }
}
