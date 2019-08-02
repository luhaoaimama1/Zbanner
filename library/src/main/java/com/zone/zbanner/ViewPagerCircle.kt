package com.zone.zbanner

import android.content.Context
import android.os.Handler
import androidx.viewpager.widget.ViewPager
import android.util.AttributeSet

// Mainly used for banner pages and boot boot page
class ViewPagerCircle @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : ViewPager(context, attrs) {
    var delayMillis: Long = 3000
    private var isTimeDelay = false
    private val initCircle = 200
    private var adapter: PagerAdapterCycle<*>? = null
    private var mListener: ViewPager.OnPageChangeListener? = null
    private val run = Runnable { nextPage() }
    private val currentItemZone: Int
        get() = super.getCurrentItem()

    @JvmOverloads
    fun setAdapter(adapter: PagerAdapterCycle<*>, offset: Int = 0) {
        super.setAdapter(adapter)
        this.adapter = adapter
        if (offset >= adapter.size)
            throw IllegalArgumentException("offset must be < adapter.getSize()!")
        if (adapter.isCircle)
            currentItem = adapter.size * initCircle + offset
        else
            currentItem = offset
        setOnPageChangeListener(null)
        FixedSpeedScroller(context).setViewPager(this)
    }

    fun nextPage() {
        if (adapter!!.isCircle)
            setCurrentItem(currentItemZone + 1, true)
        else {
            if (currentItem != adapter!!.size - 1)
                setCurrentItem(currentItemZone + 1, true)
            else
                pauseCircle()
        }
    }

    fun previousPage() {
        if (adapter!!.isCircle)
            setCurrentItem(currentItemZone - 1, true)
        else {
            if (currentItem != 0)
                setCurrentItem(currentItemZone - 1, true)
        }
    }

    private fun againTiming() {
        if (isTimeDelay) {
            handler.removeCallbacks(run)
            handler.postDelayed(run, delayMillis)
        }
    }

    override fun setOnPageChangeListener(listener: ViewPager.OnPageChangeListener?) {
        mListener = listener
        if (adapter == null) throw IllegalStateException("setAdapter must be use before setOnPageChangeListener!")
        val listenerSet = object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                val reallyPosition = position % adapter!!.size
                if (mListener != null)
                    mListener!!.onPageScrolled(reallyPosition, positionOffset, positionOffsetPixels)
            }

            override fun onPageSelected(position: Int) {
                val reallyPosition = position % adapter!!.size
                if (mListener != null)
                    mListener!!.onPageSelected(reallyPosition)
            }

            override fun onPageScrollStateChanged(state: Int) {
                if (state != 0)
                    pauseCircle()
                else
                    againTiming()
                if (mListener != null)
                    mListener!!.onPageScrollStateChanged(state)
            }
        }
        super.setOnPageChangeListener(listenerSet)
    }

    /**
     * Open the carousel and set the carousel time
     * @param delayMillis
     */
    @JvmOverloads
    fun openTimeCircle(delayMillis: Long = -1) {
        if (-1L != delayMillis)
            this.delayMillis = delayMillis
        if (adapter!!.size == 1)
            return
        isTimeDelay = true
        againTiming()
    }

    fun closeTimeCircle() {
        if (isTimeDelay) {
            handler.removeCallbacks(run)
            isTimeDelay = false
        }
    }

    private fun pauseCircle() {
        if (isTimeDelay) {
            handler.removeCallbacks(run)
        }
    }

    override fun getCurrentItem(): Int {
        return super.getCurrentItem() % adapter!!.size
    }
}
