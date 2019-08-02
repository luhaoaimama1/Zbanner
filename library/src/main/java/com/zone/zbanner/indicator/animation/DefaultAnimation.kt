package com.zone.zbanner.indicator.animation

import com.zone.zbanner.indicator.IndicatorView
import com.zone.zbanner.indicator.animation.abstarct.BaseAnimation

/**
 * Created by Zone on 2016/1/28.
 */
class DefaultAnimation(indicatorView: IndicatorView, itemLength: Int) : BaseAnimation(indicatorView, itemLength) {
    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

    override fun onPageSelected(position: Int) {
        super.onPageSelected(position)
        indicatorView.ivTop.x = (itemLength * position).toFloat()
    }
}
