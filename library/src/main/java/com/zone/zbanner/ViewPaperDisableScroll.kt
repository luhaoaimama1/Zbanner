package com.zone.zbanner

import android.content.Context
import androidx.viewpager.widget.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent

class ViewPaperDisableScroll : ViewPager {
    private var mDisableSroll = true

    constructor(context: Context) : super(context) {}
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    fun setDisableScroll(bDisable: Boolean) {
        mDisableSroll = bDisable
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        return if (mDisableSroll) false
        else super.onInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        return if (mDisableSroll) false
        else super.onTouchEvent(ev)
    }
}