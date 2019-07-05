package com.example.codetest;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.integration.webp.decoder.WebpDrawable;
import com.bumptech.glide.integration.webp.decoder.WebpDrawableTransformation;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.codetest.mainpage.TestActivity3;
import com.example.codetest.xiami.TestActivity4;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {


    /*

    处理大厂的gif按需再动的需求
    public void setStartPlayGif(final boolean startPlayGif) {
        Glide.with(context)
            .asGif()
            .load("gifFile")
            .into(new ImageViewTarget<GifDrawable>(imageView) {
                @Override
                protected void setResource(@Nullable GifDrawable resource) {
                    if (resource != null) {
                        if(startPlayGif){
                            //resource.setLoopCount(1);
                            view.setImageDrawable(resource);
                        } else {
                            view.setImageBitmap(resource.getFirstFrame());
                        }
                    }
                }
            });
    }
     */


    /**
     * 这个是apng 图片的支持
     * link {https://github.com/penfeizhou/APNG4Android}
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT > 22) {
            List<String> permissionList = new ArrayList<>();
            // 检查权限
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            } else {
                // 开始截图监听
                doStart();
            }
            if (permissionList != null && (permissionList.size() != 0)) {
                ActivityCompat.requestPermissions(this, permissionList.toArray(new String[permissionList.size()]), 0);
            }
        }
        String url = "https://ctkj-1256675270.cos.ap-shanghai.myqcloud.com/4bd7e162626e45cc96827900f08ee3a0.jpg";
//这个是webp格式的支持
        ImageView imageView1 = findViewById(R.id.image_view);
//        Transformation<Bitmap> circleCrop = new CenterCrop();
        Glide.with(this)
//                .asBitmap()
//                .applyDefaultRequestOptions(new RequestOptions().placeholder(R.drawable.loadingtwo))
                .load(R.drawable.loadingtwo)
//                .optionalTransform(circleCrop)
//                .optionalTransform(WebpDrawable.class, new WebpDrawableTransformation(circleCrop))
                .into(imageView1);


    }

    private ScreenShotListenManager manager;

    private void doStart() {
        manager = ScreenShotListenManager.newInstance(this);
        manager.setListener(
                new ScreenShotListenManager.OnScreenShotListener() {
                    public void onShot(String imagePath) {
                        // do something
                        Log.i("@@@@", "onShot: " + imagePath);
                    }
                }
        );
    }

    public void test_one(View v) {
        ImageView imageView1 = findViewById(R.id.image_view);
        Transformation<Bitmap> circleCrop = new CenterCrop();
        Glide.with(this)
                .load(R.drawable.refresh_anim)
                .optionalTransform(circleCrop)
                .optionalTransform(WebpDrawable.class, new WebpDrawableTransformation(circleCrop))
                .into(imageView1);
//        startActivity(new Intent(this, TestActivity1.class));
    }

    public void test_two(View v) {
        String url = "https://ctkj-1256675270.cos.ap-shanghai.myqcloud.com/4bd7e162626e45cc96827900f08ee3a0.jpg";
        ImageView imageView1 = findViewById(R.id.image_view);
        Glide.with(this)
                .applyDefaultRequestOptions(new RequestOptions().placeholder(R.drawable.loadingtwo))
                .load(url)
                .into(imageView1);
//        startActivity(new Intent(this, TestActivity2.class));
    }

    public void test_three(View v) {
        startActivity(new Intent(this, TestActivity3.class));
    }

    public void test_four(View v) {
        startActivity(new Intent(this, TestActivity4.class));
    }
}
