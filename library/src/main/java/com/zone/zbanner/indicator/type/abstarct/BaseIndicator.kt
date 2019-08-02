package com.zone.zbanner.indicator.type.abstarct

import android.graphics.Bitmap
import androidx.viewpager.widget.ViewPager

import com.zone.zbanner.indicator.IndicatorView

/**
 * Created by Zone on 2016/1/28.
 * generateBitmaps 生成后
 * 才能使用 getDefaultBitmap,getSelectedBitmap
 */
abstract class BaseIndicator//    protected boolean writeLog=true;
    (var width: Int, var height: Int) : ViewPager.OnPageChangeListener {
    var betweenMargin: Int = 0
    protected var writeLog = false

    //不参与克隆  克隆仅仅克隆属性
    var indicatorView: IndicatorView? = null

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
        if (writeLog) {
            println(String.format("onPageScrollStateChanged====state:%d /t", state))
        }
    }

    abstract fun getDefaultBitmap(position: Int): Bitmap
    abstract fun getSelectedBitmap(position: Int): Bitmap

    fun setBetweenMargin(betweenMargin: Int): BaseIndicator = apply {
        this.betweenMargin = betweenMargin
    }

    abstract fun generateBitmaps()

    /**
     *  仅仅是属性的赋值
     */
    abstract fun clone_(): BaseIndicator

    /**
     * 抽象类  赋值可以继承
     * 仅仅赋值属性  不能赋值view
     */
    open fun cloneForabstract(oldObj: BaseIndicator): BaseIndicator = apply {
        width = oldObj.width
        height = oldObj.height
        betweenMargin = oldObj.betweenMargin
        writeLog = oldObj.writeLog
    }
}