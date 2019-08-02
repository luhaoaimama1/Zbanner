package com.zone.zbanner.indicator.animation.abstarct

import androidx.viewpager.widget.ViewPager
import android.widget.ImageView

import com.zone.zbanner.indicator.IndicatorView

/**
 * Created by Zone on 2016/1/28.
 */
abstract class BaseAnimation(protected var indicatorView: IndicatorView, protected var itemLength: Int) : ViewPager.OnPageChangeListener {
    protected var state: Int = 0
    protected var iv_top: ImageView? = null
    protected var childCount: Int = 0
    //    public AbstractAnimation(ImageView iv_top, int itemLength, int childCount) {
    //        this.iv_top=iv_top;
    //        this.itemLength=itemLength;
    //        this.childCount=childCount;
    //    }


    protected var writeLog = false
    //    protected boolean writeLog=true;
    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        if (writeLog) {
            val s = String.format("onPageScrolled====position:%d /tpositionOffset:%f /tpositionOffsetPixels:%d /t", position, positionOffset, positionOffsetPixels)
            println(s)
        }
    }

    override fun onPageSelected(position: Int) {
        if (writeLog) {
            val s = String.format("onPageSelected====position:%d /t", position)
            println(s)
        }
    }

    override fun onPageScrollStateChanged(state: Int) {
        this.state = state
        if (writeLog) {
            val s = String.format("onPageScrollStateChanged====state:%d /t", state)
            println(s)
        }
    }
}
