package com.dahai.demo.video;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;
import android.view.ViewPropertyAnimator;

class AnimationUtils {
    public static ViewPropertyAnimator fadeOut(final View from, int duration) {
        if (from.getVisibility() == View.VISIBLE) {
            from.clearAnimation();
            final ViewPropertyAnimator animator = from.animate();
            animator.alpha(0f)
                    .setDuration(duration)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            from.setVisibility(View.INVISIBLE);
                            from.setAlpha(1f);
                        }
                    });
            return animator;
        }
        return null;
    }

    public static ViewPropertyAnimator fadeIn(View to, int duration) {
        if (to.getVisibility() != View.VISIBLE) {
            to.setAlpha(0f);
            to.setVisibility(View.VISIBLE);
        }
        to.clearAnimation();
        final ViewPropertyAnimator animator = to.animate();
        animator.alpha(1f)
                .setDuration(duration)
                .setListener(null);
        return animator;
    }
}
