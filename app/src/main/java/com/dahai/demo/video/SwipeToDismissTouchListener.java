package com.dahai.demo.video;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

public class SwipeToDismissTouchListener implements View.OnTouchListener {
    private int touchSlop;
    private float initialY;
    private final float maxTranslate;
    private final float closeThreshold;
    private Callback callback;
    private float lastX;
    private float lastY;
    private int pointerIndex;
    private boolean isMoving;

    public static SwipeToDismissTouchListener createFromView(View view, Callback listener) {
        return new SwipeToDismissTouchListener(listener,
                ViewConfiguration.get(view.getContext()).getScaledTouchSlop(),
                view.getContext().getResources().getDisplayMetrics().heightPixels * 0.5f);
    }

    private SwipeToDismissTouchListener(Callback listener, int touchSlop, float maxTranslate) {
        // If swiping more than 20% of the max distance, trigger the dismiss listener.
        this(listener, touchSlop, maxTranslate, maxTranslate * 0.2f);
    }

    private SwipeToDismissTouchListener(Callback listener, int touchSlop, float maxTranslate,
                                        float closeThreshold) {
        setCallback(listener);
        this.touchSlop = touchSlop;
        this.maxTranslate = maxTranslate;
        this.closeThreshold = closeThreshold;
    }

    @Override
    @SuppressLint("ClickableViewAccessibility")
    public boolean onTouch(View v, MotionEvent event) {
        boolean viewClosed = false;
        if (!(v instanceof SwipeableViewProvider) || ((SwipeableViewProvider) v).canBeSwiped() ||
                isMoving()) {
            viewClosed = handleTouchEvent(v, event);
        }

        // If the view is not being closed due to the touch event, give the touch event back to the
        // target view in case they have their own custom touch handling
        return viewClosed || v.onTouchEvent(event);
    }

    /**
     * Handles the incoming motion event, possibly translating or closing the swipeable view.
     *
     * @param event The incoming motion event.
     * @return true if the motion event results in the view closing, false otherwise.
     */
    private boolean handleTouchEvent(View swipeableView, MotionEvent event) {
        boolean viewClosed = false;

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN: {
                lastX = event.getRawX();
                initialY = lastY = event.getRawY();
                isMoving = false;
                pointerIndex = event.getPointerId(event.getPointerCount() - 1);
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                final float currentX = event.getRawX();
                final float currentY = event.getRawY();
                final float initialDeltaY = currentY - initialY;
                final float deltaX = currentX - lastX;
                final float deltaY = currentY - lastY;
                lastX = currentX;
                lastY = currentY;
                if (isValidPointer(event) &&
                        (isMoving || (hasMovedEnoughInProperYDirection(initialDeltaY) &&
                                hasMovedMoreInYDirectionThanX(deltaX, deltaY)))) {
                    isMoving = true;
                    moveView(swipeableView, deltaY);
                }
                break;
            }
            case MotionEvent.ACTION_POINTER_DOWN:{
                settleView(swipeableView);
                isMoving = false;
                pointerIndex = -1; // invalidate pointer index until next ACTION_DOWN
                break;
            }
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL: {
                if (isValidPointer(event)) {
                    if (isMoving) {
                        viewClosed = settleOrCloseView(swipeableView);
                    }
                }
                isMoving = false;
                break;
            }
            default: {
                break;
            }
        }

        return viewClosed;
    }

    /**
     * @param initialDeltaY the delta between the initial Y position and the current Y position.
     * @return if the scroll has moved far enough in the proper direction to start tracking a swipe to dismiss.
     */
    private boolean hasMovedEnoughInProperYDirection(float initialDeltaY) {
        return Math.abs(initialDeltaY) > touchSlop;
    }

    /**
     * @param deltaX the delta between the last X position and the current X position.
     * @param deltaY the delta between the last Y position and the current Y position.
     * @return if the swipe gesture has moved more in the Y direction than the X direction.
     */
    private boolean hasMovedMoreInYDirectionThanX(float deltaX, float deltaY) {
        return Math.abs(deltaY) > Math.abs(deltaX);
    }

    /**
     * @return if we are currently tracking a swipe to dismiss gesture.
     */
    private boolean isMoving() {
        return isMoving;
    }

    private boolean isValidPointer(MotionEvent event) {
        return pointerIndex >= 0 && event.getPointerCount() == 1;
    }

    /**
     * Determines whether or not to settle or close the view based on the current view translation.
     *
     * @return true if the view was closed, otherwise false.
     */
    private boolean settleOrCloseView(View swipeableView) {
        final float currentY = swipeableView.getTranslationY();
        if (currentY > closeThreshold || currentY < -closeThreshold) {
            if (callback != null) {
                callback.onDismiss();
            }

            return true;
        } else {
            settleView(swipeableView);
            return false;
        }
    }

    private void settleView(View swipeableView) {
        if (swipeableView.getTranslationY() != 0) {
            final ObjectAnimator animator =
                    ObjectAnimator.ofFloat(swipeableView, View.TRANSLATION_Y, 0).setDuration(100);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    final float targetY = (Float) animation.getAnimatedValue();
                    if (callback != null) {
                        callback.onMove(targetY);
                    }
                }
            });
            animator.start();
        }
    }

    private void moveView(View swipeableView, float deltaY) {
        final float currentY = swipeableView.getTranslationY();
        final float deltaWithTension = (float) (deltaY * calculateTension(currentY));
        final float targetY = bound(currentY + deltaWithTension);
        swipeableView.setTranslationY(targetY);
        if (callback != null) {
            callback.onMove(targetY);
        }
    }

    private double calculateTension(float targetY) {
        // energy = 1 / 2 * k * x^2
        // but since we only want a coefficient from 0 to 1 we can ignore the constants.
        final float distance = Math.abs(targetY);
        final float maxDistance = closeThreshold * 2f;
        final double tension = Math.pow(distance, 2);
        final double maxTension = Math.pow(maxDistance, 2);
        final double tensionCoeff = 1 - (tension / maxTension);
        return tensionCoeff;
    }

    private float bound(float y) {
        if (y < -maxTranslate) {
            return -maxTranslate;
        } else if (y > maxTranslate) {
            return maxTranslate;
        }
        return y;
    }

    private void setCallback(Callback listener) {
        this.callback = listener;
    }

    public interface Callback {
        void onDismiss();

        void onMove(float translationY);
    }

    /**
     * Implement this interface to allow or disallow swipe to dismiss behavior
     */
    public interface SwipeableViewProvider {
        /**
         * Determines if a view can be swiped away
         * ATTENTION: this will be called on every touchEvent, don't do any expensive operation in this method
         *
         * @return true if the view can be moved/dismissed, false otherwise
         */
        boolean canBeSwiped();
    }
}
