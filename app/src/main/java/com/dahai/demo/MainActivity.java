package com.dahai.demo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.dahai.demo.video.IntentUtils;
import com.dahai.demo.video.PlayerActivity;

public class MainActivity extends AppCompatActivity {

    private String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void player(View view) {

        launchVideoPlayer();
    }

    public void launchVideoPlayer() {
        final Intent intent = new Intent(this, PlayerActivity.class);
        final String playerStreamUrl = "http://mvideo.spriteapp.cn/video/2018/1127/c7387d1cf1e911e8857f842b2b4c75ab_wpcco.mp4";

        final PlayerActivity.PlayerItem playerItem =
                new PlayerActivity.PlayerItem(playerStreamUrl, true, true, null, null);
        intent.putExtra(PlayerActivity.PLAYER_ITEM, playerItem);

        IntentUtils.safeStartActivity(this, intent);
    }
}
