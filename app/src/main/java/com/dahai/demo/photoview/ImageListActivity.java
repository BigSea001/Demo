package com.dahai.demo.photoview;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.dahai.demo.R;

/**
 * 作者： 大海
 * 时间： 2018/11/27
 * 描述：
 */
public class ImageListActivity extends AppCompatActivity {

    private static final String imageUrl1 = "http://p0.so.qhmsg.com/t01858e02e454173cd5.jpg";
    private static final String imageUrl2 = "http://p1.so.qhimgs1.com/t01454f1e6e8e42e69d.jpg";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_list);

        final ImageView image1 = findViewById(R.id.image1);
        final ImageView image2 = findViewById(R.id.image2);

        Glide.with(this).load(imageUrl1).into(image1);
        Glide.with(this).load(imageUrl2).into(image2);

        image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String transitionName = ViewCompat.getTransitionName(image1);
                Intent intent = new Intent(ImageListActivity.this, ImageShowingActivity.class);
                intent.putExtra(ImageShowingActivity.ARGS_IMAGE_URL, imageUrl1);
                intent.putExtra(ImageShowingActivity.ARGS_TRANSITION_NAME, transitionName);
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        ImageListActivity.this, image1, transitionName);

                ActivityCompat.startActivity(ImageListActivity.this, intent, options.toBundle());
            }
        });

        image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String transitionName = ViewCompat.getTransitionName(image2);
                Intent intent = new Intent(ImageListActivity.this, ImageShowingActivity.class);
                intent.putExtra(ImageShowingActivity.ARGS_BOTTOM_MENU_RES_ID, R.menu.navigation);
                intent.putExtra(ImageShowingActivity.ARGS_IMAGE_URL, imageUrl2);
                intent.putExtra(ImageShowingActivity.ARGS_TRANSITION_NAME, transitionName);

                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        ImageListActivity.this, image2, transitionName);

                ActivityCompat.startActivity(ImageListActivity.this, intent, options.toBundle());
            }
        });
    }
}
