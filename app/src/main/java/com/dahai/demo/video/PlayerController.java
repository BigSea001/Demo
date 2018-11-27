package com.dahai.demo.video;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dahai.demo.R;

public class PlayerController {
    private static final String TAG = "PlayerController";
    private final VideoView videoView;
    private final VideoControlView videoControlView;
    private final ProgressBar videoProgressView;
    private final TextView callToActionView;
    private final View rootView;
    private int seekPosition;
    private boolean isPlaying = true;
    private final SwipeToDismissTouchListener.Callback callback;

    public PlayerController(View rootView, SwipeToDismissTouchListener.Callback callback) {
        this.rootView = rootView;
        this.videoView = rootView.findViewById(R.id.video_view);
        this.videoControlView = rootView.findViewById(R.id.video_control_view);
        this.videoProgressView = rootView.findViewById(R.id.video_progress_view);
        this.callToActionView = rootView.findViewById(R.id.call_to_action_view);
        this.callback = callback;
    }

    public void prepare(PlayerActivity.PlayerItem item) {
        try {
            setUpCallToAction(item);
            setUpMediaControl(item.looping, item.showVideoControls);
            final View.OnTouchListener listener = SwipeToDismissTouchListener
                    .createFromView(videoView, callback);
            videoView.setOnTouchListener(listener);
            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    videoProgressView.setVisibility(View.GONE);
                }
            });
            videoView.setOnInfoListener(new MediaPlayer.OnInfoListener() {
                @Override
                public boolean onInfo(MediaPlayer mediaPlayer, int what, int extra) {
                    if (what == MediaPlayer.MEDIA_INFO_BUFFERING_END) {
                        videoProgressView.setVisibility(View.GONE);
                        return true;
                    } else if (what == MediaPlayer.MEDIA_INFO_BUFFERING_START) {
                        videoProgressView.setVisibility(View.VISIBLE);
                        return true;
                    }
                    return false;
                }
            });
            final Uri uri = Uri.parse(item.url);
            videoView.setVideoURI(uri, item.looping);
            videoView.requestFocus();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onResume() {
        if (seekPosition != 0) {
            videoView.seekTo(seekPosition);
        }
        if (isPlaying) {
            videoView.start();
            videoControlView.update();
        }
    }

    public void onPause() {
        isPlaying = videoView.isPlaying();
        seekPosition = videoView.getCurrentPosition();
        videoView.pause();
    }

    public void onDestroy() {
        videoView.stopPlayback();
    }

    private void setUpMediaControl(boolean looping, boolean showVideoControls) {
        if (looping && !showVideoControls) {
            setUpLoopControl();
        } else {
            setUpMediaControl();
        }
    }

    private void setUpLoopControl() {
        videoControlView.setVisibility(View.INVISIBLE);
        videoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (videoView.isPlaying()) {
                    videoView.pause();
                } else {
                    videoView.start();
                }
            }
        });
    }

    private void setUpMediaControl() {
        videoView.setMediaController(videoControlView);
    }

    private void setUpCallToAction(PlayerActivity.PlayerItem item) {
        if (item.callToActionText != null && item.callToActionUrl != null) {
            callToActionView.setVisibility(View.VISIBLE);
            callToActionView.setText(item.callToActionText);
            setUpCallToActionListener(item.callToActionUrl);
            setUpRootViewOnClickListener();
        }
    }

    private void setUpCallToActionListener(final String callToActionUrl) {

        callToActionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Uri uri = Uri.parse(callToActionUrl);
                final Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                IntentUtils.safeStartActivity(callToActionView.getContext(), intent);
            }
        });
    }

    private void setUpRootViewOnClickListener() {
        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callToActionView.getVisibility() == View.VISIBLE) {
                    callToActionView.setVisibility(View.GONE);
                } else {
                    callToActionView.setVisibility(View.VISIBLE);
                }
            }
        });
    }


}
