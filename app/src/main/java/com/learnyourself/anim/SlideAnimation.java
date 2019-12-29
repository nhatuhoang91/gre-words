package com.learnyourself.anim;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.support.design.widget.CollapsingToolbarLayout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by Nha on 12/6/2015.
 */
public class SlideAnimation {
    Context mContext;
    View wordLayout;
    View wordMeaning;
    View example;
    View synonym;
    public SlideAnimation(Context mContext, View wordLayout, View wordMeaning,
                          View example, View synonym){
        this.mContext = mContext;
        this.wordLayout = wordLayout;
        this.wordMeaning = wordMeaning;
        this.example = example;
        this.synonym = synonym;
    }

    public void slideDown(){
        showTextView();
        expand(wordLayout);
    }

    public void slideUp(){
        hideTextView();
        collapse(wordLayout);
    }

    private void hideTextView(){
        this.wordLayout.setVisibility(RelativeLayout.GONE);
        this.wordMeaning.setVisibility(TextView.GONE);
        this.example.setVisibility(TextView.GONE);
        this.synonym.setVisibility(TextView.GONE);
    }
    private void showTextView(){
        this.wordLayout.setVisibility(RelativeLayout.VISIBLE);
        this.wordMeaning.setVisibility(RelativeLayout.VISIBLE);
        this.example.setVisibility(RelativeLayout.VISIBLE);
        this.synonym.setVisibility(RelativeLayout.VISIBLE);
    }

    public static void expand(final View v) {
        v.measure(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        final int targetHeight = v.getMeasuredHeight();

        // Older versions of android (pre API 21) cancel animations for views with a height of 0.
        v.getLayoutParams().height = 1;
        v.setVisibility(View.VISIBLE);
        Animation a = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = interpolatedTime == 1
                        ? LayoutParams.WRAP_CONTENT
                        : (int)(targetHeight * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int)(targetHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }
    public static void collapse(final View v) {
        final int initialHeight = v.getMeasuredHeight();

        Animation a = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if(interpolatedTime == 1){
                    v.setVisibility(View.GONE);
                }else{
                    v.getLayoutParams().height = initialHeight - (int)(initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int)(initialHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }
}
