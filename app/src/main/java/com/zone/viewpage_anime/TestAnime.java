package com.zone.viewpage_anime;

import androidx.viewpager.widget.ViewPager;
import android.view.View;


/**
 * Simple transparency to combine
 * Created by Zone on 2016/1/28.
 */
public class TestAnime implements ViewPager.PageTransformer {
    @Override
    public void transformPage(View page, float position) {
//     Position page of the a change is  ( 0, -1] 1-0
//      Position page of the B change is [ 1 , 0 ]0-1
        if (position <= 0 && position >= -1) {
            page.setAlpha(position + 1);
            page.setTranslationX(0);
        }
        if (position > 0 && position <= 1) {
            page.setAlpha(1 - position);
            page.setTranslationX(page.getWidth() * -position);
//            ViewHelper.animate(page).setDuration(2000).rotationYBy(720).x(100).y(100);
        }

    }
}
