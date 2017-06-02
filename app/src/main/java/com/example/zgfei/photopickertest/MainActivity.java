package com.example.zgfei.photopickertest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.zgfei.photopickertest.imageselector.ImageSelecteActivity;
import com.example.zgfei.photopickertest.zhihu.MatisseTestActivity;

import java.util.ArrayList;

import me.iwf.photopicker.PhotoPicker;

public class MainActivity extends AppCompatActivity {
    private Button btPicker, btPicker1, btPicker2, btPicker3, btPicker4;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btPicker = (Button) findViewById(R.id.bt_picker);
        btPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickPhoto();
            }
        });
        btPicker1 = (Button) findViewById(R.id.bt_picker1);
        btPicker1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickPhoto1();
            }
        });
        btPicker2 = (Button) findViewById(R.id.bt_picker2);
        btPicker2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickPhoto3();
            }
        });

        btPicker3 = (Button) findViewById(R.id.bt_picker3);
        btPicker3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageSelecteActivity.start(MainActivity.this);
            }
        });

        btPicker4 = (Button) findViewById(R.id.bt_picker4);
        btPicker4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MatisseTestActivity.start(MainActivity.this);
            }
        });

        textView = (TextView) findViewById(R.id.tv_image_url);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == PhotoPicker.REQUEST_CODE) {
            if (data != null) {
                ArrayList<String> photos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
                Log.e("=======", "=======" + photos.size());

                String str = "";
                for (String url : photos) {
                    Log.e("==url====", "=======" + url);
                }
            }
        }
    }

    private void pickPhoto() {
        PhotoPicker.builder()
                .setPhotoCount(9)
                .setShowCamera(true)
                .setShowGif(true)
                .setPreviewEnabled(false)
                .start(this, PhotoPicker.REQUEST_CODE);
    }

    private void pickPhoto1() {
        PhotoPicker.builder()
                .setPhotoCount(9)
                .setGridColumnCount(4)
                .start(MainActivity.this);
    }

    private void pickPhoto2() {
        PhotoPicker.builder()
                .setPhotoCount(7)
                .setShowCamera(false)
                .setPreviewEnabled(false)
                .start(MainActivity.this);
    }

    private void pickPhoto3() {
        PhotoPicker.builder().
                setPhotoCount(9)
                .setGridColumnCount(3)
                .setShowGif(true)
                .setPreviewEnabled(true)
                .setShowCamera(true)
                .start(this, PhotoPicker.REQUEST_CODE);
    }
}
