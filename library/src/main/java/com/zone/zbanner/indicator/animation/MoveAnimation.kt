package com.zone.zbanner.indicator.animation

import com.zone.zbanner.indicator.IndicatorView
import com.zone.zbanner.indicator.animation.abstarct.BaseAnimation

/**
 * Created by Zone on 2016/1/28.
 */
class MoveAnimation(indicatorView: IndicatorView, itemLength: Int) : BaseAnimation(indicatorView, itemLength) {

    private var scrolledPosition = -1


    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        super.onPageScrolled(position, positionOffset, positionOffsetPixels)
        scrolledPosition = position
        if (position != indicatorView.indicatorCount - 1) {
            //The last one before the operation
            indicatorView.ivTop.x = itemLength * (position + positionOffset)
        }
    }

    override fun onPageSelected(position: Int) {
        super.onPageSelected(position)
        //1->0  last-2->last-1
        // Does not need to be set because of the set of words will interact with the onpagescrolled and cause flicker
        if (!(scrolledPosition == 0 && position == 0 || scrolledPosition == indicatorView.indicatorCount - 2 && position == indicatorView.indicatorCount - 1)) {
            if (position == 0 || position == indicatorView.indicatorCount - 1) {
                //0, last-1  Set select because there is no move at this time
                indicatorView.ivTop.x = (itemLength * position).toFloat()
            }
        }
    }
}
