package com.zone.zbanner

import android.content.Context
import androidx.viewpager.widget.ViewPager
import android.view.animation.Interpolator
import android.widget.Scroller

import java.lang.reflect.Field

class FixedSpeedScroller : Scroller {
    var mDuration = 1500

    constructor(context: Context) : super(context) {}

    constructor(context: Context, interpolator: Interpolator) : super(context, interpolator) {}

    override fun startScroll(startX: Int, startY: Int, dx: Int, dy: Int, duration: Int) {
        // Ignore received duration, use fixed one instead
        super.startScroll(startX, startY, dx, dy, mDuration)
    }

    override fun startScroll(startX: Int, startY: Int, dx: Int, dy: Int) {
        // Ignore received duration, use fixed one instead
        super.startScroll(startX, startY, dx, dy, mDuration)
    }

    fun setViewPager(viewPager: ViewPager) {
        var mField: Field? = null
        try {
            mField = ViewPager::class.java.getDeclaredField("mScroller")
            mField?.isAccessible = true
            mField?.set(viewPager, this)
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }

    }
}
