package com.zone.zbanner.indicator

import android.content.Context
import androidx.viewpager.widget.ViewPager
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.zone.banner.R
import com.zone.zbanner.PagerAdapterCycle
import com.zone.zbanner.indicator.animation.DefaultAnimation
import com.zone.zbanner.indicator.animation.MoveAnimation
import com.zone.zbanner.indicator.animation.abstarct.BaseAnimation
import com.zone.zbanner.indicator.type.abstarct.BaseIndicator

/**
 * Created by Administrator on 2016/1/27.
 * viewpager linkage
 */
class IndicatorView @JvmOverloads constructor(private val context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : RelativeLayout(context, attrs, defStyleAttr), ViewPager.OnPageChangeListener {
    private var mViewPager: ViewPager? = null
    private var ll_bottom: LinearLayout? = null
    private var fl_top: FrameLayout? = null
    private var pageChangeListener: ViewPager.OnPageChangeListener? = null
    var indeciatorCount: Int = 0
        private set
    private var betweenMargin = 0
    private var animation: BaseAnimation? = null
    var snap = false
        set(snap) {
            field = snap
            if (snap)
                animation = MoveAnimation(this, betweenMargin + this.indicator!!.width)
            else
                animation = DefaultAnimation(this, betweenMargin + this.indicator!!.width)
        }

    //init ll_bottom
    //init fl_top
    //Set animation
    var indicator: BaseIndicator? = null
        set(indicator) {
            indicator.setIndicatorView(this)
            field = indicator
            this.betweenMargin = indicator.getBetweenMargin()
            ll_bottom!!.removeAllViews()
            fl_top!!.removeAllViews()
            val ll_bottom_sumWidth = initLl_bottom()
            initFl_top(ll_bottom_sumWidth)
            animation = DefaultAnimation(this, betweenMargin + indicator.getWidth())
        }
    var iv_Top: ImageView? = null
        private set

    fun setViewPager(mViewPager: ViewPager) {
        if (mViewPager.adapter == null)
            throw IllegalStateException("must be use setAdapter!")
        if (mViewPager.adapter is PagerAdapterCycle<*>) {
            indeciatorCount = (mViewPager.adapter as PagerAdapterCycle<*>).size
        } else {
            indeciatorCount = mViewPager.adapter!!.count
        }
        mViewPager.setOnPageChangeListener(this)
        this.mViewPager = mViewPager
        if (indeciatorCount == 1)
            visibility = View.INVISIBLE
        else
            visibility = View.VISIBLE
        removeAllViews()
        initView()
    }


    fun setOnPageChangeListener(pageChangeListener: ViewPager.OnPageChangeListener) {
        this.pageChangeListener = pageChangeListener
    }


    private fun initView() {
        val inflater = LayoutInflater.from(context)
        ll_bottom = inflater.inflate(R.layout.core2_linear, null, false) as LinearLayout
        fl_top = inflater.inflate(R.layout.core2_frame, null, false) as FrameLayout
        addView(ll_bottom)
        addView(fl_top)
    }

    private fun initLl_bottom(): Int {
        var ll_bottom_sumWidth = 0
        for (i in 0 until indeciatorCount) {
            val iv = ImageView(context)
            val params = LinearLayout.LayoutParams(this.indicator!!.width, this.indicator!!.height)
            ll_bottom_sumWidth += this.indicator!!.width
            if (i != indeciatorCount - 1) {
                params.rightMargin = betweenMargin
                ll_bottom_sumWidth += betweenMargin
            }
            iv.layoutParams = params
            iv.setImageBitmap(this.indicator!!.getDefaultBitmap(i))
            ll_bottom!!.addView(iv)
        }
        return ll_bottom_sumWidth
    }

    private fun initFl_top(ll_bottom_sumWidth: Int) {
        iv_Top = ImageView(context)
        val params2 = LinearLayout.LayoutParams(this.indicator!!.width, this.indicator!!.height)
        iv_Top!!.layoutParams = params2
        fl_top!!.addView(iv_Top)
        val params_ll_2 = fl_top!!.layoutParams
        params_ll_2.width = ll_bottom_sumWidth
        fl_top!!.layoutParams = params_ll_2
        //iv_Top init position
        iv_Top!!.setImageBitmap(this.indicator!!.getSelectedBitmap(mViewPager!!.currentItem))
        iv_Top!!.x = (mViewPager!!.currentItem * (betweenMargin + this.indicator!!.width)).toFloat()

    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        if (this.indicator != null)
            this.indicator!!.onPageScrolled(position, positionOffset, positionOffsetPixels)
        if (animation != null)
            animation!!.onPageScrolled(position, positionOffset, positionOffsetPixels)
        if (pageChangeListener != null)
            pageChangeListener!!.onPageScrolled(position, positionOffset, positionOffsetPixels)
    }

    override fun onPageSelected(position: Int) {
        if (this.indicator != null)
            this.indicator!!.onPageSelected(position)
        if (animation != null)
            animation!!.onPageSelected(position)
        if (pageChangeListener != null)
            pageChangeListener!!.onPageSelected(position)
    }

    override fun onPageScrollStateChanged(state: Int) {
        if (this.indicator != null)
            this.indicator!!.onPageScrollStateChanged(state)
        if (animation != null)
            animation!!.onPageScrollStateChanged(state)
        if (pageChangeListener != null)
            pageChangeListener!!.onPageScrollStateChanged(state)
    }

}
