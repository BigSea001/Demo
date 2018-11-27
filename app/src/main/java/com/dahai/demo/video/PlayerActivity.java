package com.dahai.demo.video;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.dahai.demo.R;

import java.io.Serializable;

/**
 * 作者： 大海
 * 时间： 2018/11/27
 * 描述：
 */
public class PlayerActivity extends AppCompatActivity {

    public static final String PLAYER_ITEM = "PLAYER_ITEM";

    PlayerController playerController;
    private String TAG = "HHH";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);

        final PlayerItem item = (PlayerItem) getIntent().getSerializableExtra(PLAYER_ITEM);
        final View rootView = findViewById(android.R.id.content);
        playerController = new PlayerController(rootView,
                new SwipeToDismissTouchListener.Callback(){

                    @Override
                    public void onDismiss() {
                        PlayerActivity.this.finish();
                        overridePendingTransition(0, R.anim.video_slide_out);
                    }

                    @Override
                    public void onMove(float translationY) {

                    }
                });
        playerController.prepare(item);

        Log.e(TAG, "onCreate: " );
    }

    @Override
    protected void onResume() {
        super.onResume();
        playerController.onResume();
        Log.e(TAG, "onResume: " );
    }

    @Override
    protected void onPause() {
        playerController.onPause();
        super.onPause();

        Log.e(TAG, "onPause: " );
    }

    @Override
    public void onDestroy() {
        playerController.onDestroy();
        super.onDestroy();
        Log.e(TAG, "onDestroy: " );
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, R.anim.video_slide_out);
    }

    public static class PlayerItem implements Serializable {
        public final String url;
        public final boolean looping;
        public final boolean showVideoControls;
        public final String callToActionUrl;
        public final String callToActionText;

        public PlayerItem(String url, boolean looping, boolean showVideoControls,
                          String callToActionText, String callToActionUrl) {
            this.url = url;
            this.looping = looping;
            this.showVideoControls = showVideoControls;
            this.callToActionText = callToActionText;
            this.callToActionUrl = callToActionUrl;
        }
    }
}

