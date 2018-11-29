package com.dahai.demo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.dahai.demo.photoview2.GalleryActivity;
import com.dahai.demo.photoview2.MediaEntity;
import com.dahai.demo.video.IntentUtils;
import com.dahai.demo.video.PlayerActivity;
import com.dahai.demo.expandablelayout.ExpandableActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void player(View view) {
        final Intent intent = new Intent(this, PlayerActivity.class);
        final String playerStreamUrl = "http://mvideo.spriteapp.cn/video/2018/1127/c7387d1cf1e911e8857f842b2b4c75ab_wpcco.mp4";

        final PlayerActivity.PlayerItem playerItem =
                new PlayerActivity.PlayerItem(playerStreamUrl, true, true, null, null);
        intent.putExtra(PlayerActivity.PLAYER_ITEM, playerItem);

        IntentUtils.safeStartActivity(this, intent);
    }

    public void lookPhoto(View view) {
//        final Intent intent = new Intent(this, ImageListActivity.class);
//        IntentUtils.safeStartActivity(this, intent);
        e();
    }

    public void e() {
        final Intent intent = new Intent(this, GalleryActivity.class);
        ArrayList<MediaEntity> entities = new ArrayList<>();
        entities.add(new MediaEntity("", "", "",
        1, "", "", "http://p0.so.qhmsg.com/t01858e02e454173cd5.jpg", null,
        1, "", "", null,
                ""));
        entities.add(new MediaEntity("", "", "",
                2, "", "", "http://p1.so.qhimgs1.com/t01454f1e6e8e42e69d.jpg", null,
                2, "", "", null,
                ""));
        final GalleryActivity.GalleryItem item = new GalleryActivity.GalleryItem(0, entities);
        intent.putExtra(GalleryActivity.GALLERY_ITEM, item);
        IntentUtils.safeStartActivity(this, intent);
    }

    public void expandable(View view) {
        startActivity(new Intent(this,ExpandableActivity.class));
    }
}
