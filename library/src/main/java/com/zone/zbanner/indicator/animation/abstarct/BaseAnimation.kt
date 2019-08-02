package com.zone.zbanner.indicator.animation.abstarct

import androidx.viewpager.widget.ViewPager
import android.widget.ImageView

import com.zone.zbanner.indicator.IndicatorView

/**
 * Created by Zone on 2016/1/28.
 */
abstract class BaseAnimation(protected var indicatorView: IndicatorView, protected var itemLength: Int) : ViewPager.OnPageChangeListener {
    protected var state: Int = 0
    protected var writeLog = false

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        if (writeLog) {
            println(String.format("onPageScrolled====position:%d /tpositionOffset:%f /tpositionOffsetPixels:%d /t", position, positionOffset, positionOffsetPixels))
        }
    }

    override fun onPageSelected(position: Int) {
        if (writeLog) {
            println(String.format("onPageSelected====position:%d /t", position))
        }
    }

    override fun onPageScrollStateChanged(state: Int) {
        this.state = state
        if (writeLog) {
            println( String.format("onPageScrollStateChanged====state:%d /t", state))
        }
    }
}
