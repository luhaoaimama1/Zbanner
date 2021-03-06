package com.zone.viewpage_anime;


import androidx.viewpager.widget.ViewPager;

import android.view.View;

public class DepthPageTransformer implements ViewPager.PageTransformer {
    private static final float MIN_SCALE = 0.75f;

    public void transformPage(View view, float position) {
        int pageWidth = view.getWidth();

        if (position < -1) { // [-Infinity,-1)
            // This page is way off-screen to the left.
            // view.setAlpha(0);
            view.setAlpha(0);
        } else if (position <= 0)// A pages slide to B pages; a pages from 0 -1; B pages from 1 to 0
        { // [-1,0]
            // Use the default slide transition when moving to the left page
            // view.setAlpha(1);
            view.setAlpha(1);
            // view.setTranslationX(0);
            view.setTranslationX(0);
            // view.setScaleX(1);
            view.setScaleX(1);
            // view.setScaleY(1);
            view.setScaleY(1);

        } else if (position <= 1) { // (0,1]
            // Fade the page out.
            // view.setAlpha(1 - position);
            view.setAlpha(1 - position);

            // Counteract the default slide transition
            // view.setTranslationX(pageWidth * -position);
            view.setTranslationX(pageWidth * -position);

            // Scale the page down (between MIN_SCALE and 1)
            float scaleFactor = MIN_SCALE + (1 - MIN_SCALE) * (1 - position);
            // view.setScaleX(scaleFactor);
//            ViewHelper.setScaleX(view, scaleFactor);
            // view.setScaleY(1);
//            ViewHelper.setScaleY(view, scaleFactor);

        } else { // (1,+Infinity]
            // This page is way off-screen to the right.
            // view.setAlpha(0);
            view.setAlpha(1);
        }
    }
}