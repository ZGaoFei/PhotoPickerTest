package com.example.zgfei.photopickertest.zhihu;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.zgfei.photopickertest.R;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.PicassoEngine;
import com.zhihu.matisse.filter.Filter;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MatisseTestActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_CHOOSE = 23;

    private Button button;
    private RecyclerView recyclerView;

    private List<Uri> list = new ArrayList<>();

    private Matisse from;

    public static void start(Context context) {
        Intent intent = new Intent(context, MatisseTestActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matisse_test);

        button = (Button) findViewById(R.id.bt_matisse_picker);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                pickerForPicasso();
//                picker();
                pickerDefault();

            }
        });
    }

    private void pickerForPicasso() {
        Matisse.from(MatisseTestActivity.this)
                .choose(MimeType.allOf())
                .countable(true)
                .maxSelectable(9)
                .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(0.85f)
                .imageEngine(new PicassoEngine())
                .forResult(REQUEST_CODE_CHOOSE);
    }

    private void picker() {
        Matisse.from(MatisseTestActivity.this)
                .choose(MimeType.allOf())
                .countable(false)
                .maxSelectable(9)
                .theme(R.style.Matisse_Zhihu)
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(0.85f)
                .imageEngine(new PicassoEngine())
                .forResult(REQUEST_CODE_CHOOSE);
    }

    private void pickerDefault() {
        from = Matisse.from(MatisseTestActivity.this);
        from.choose(MimeType.allOf())
                .countable(false)
                .capture(true)
                .captureStrategy(new CaptureStrategy(true, "com.example.zgfei.photopickertest.provider"))
                .maxSelectable(9)//最大选择数
                .theme(R.style.Matisse_Zhihu)//主题
                .spanCount(4)//行数
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(0.85f)
                .imageEngine(new PicassoEngine())//选择图片加载器
                .forResult(REQUEST_CODE_CHOOSE);//设置回调

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {

            list = Matisse.obtainResult(data);

            scan(list.get(0));

            for (Uri url : list) {
                Log.e("=====url====", "========" + url.toString());
            }
        }
    }

    private void scan(Uri uri) {
        String path = uri.getPath();
        String[] split = path.split("/");

        File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        String a = file.getAbsolutePath() + File.separatorChar + split[split.length - 1];
        Log.e("==a====", "=====" + a);

        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);

        if (TextUtils.isEmpty(file.getAbsolutePath())) {
            return;
        }

        File f = new File(a);
        Uri contentUri = Uri.fromFile(f);
        Log.e("===contentUri====", "=====" + contentUri);
        mediaScanIntent.setData(contentUri);
        sendBroadcast(mediaScanIntent);
    }


    private void scan2(Uri uri) {
        Log.e("===uri====", "===uri====" + uri);

        File file1 = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        Log.e("==file1====", "======" + file1.getAbsolutePath());

        File file2 = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        Log.e("===file2===", "======" + file2.getAbsolutePath());

        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        mediaScanIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        mediaScanIntent.setData(uri);
        sendBroadcast(mediaScanIntent);
    }
}
