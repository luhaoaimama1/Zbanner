package com.zone.zbanner.indicator

import android.content.Context
import androidx.viewpager.widget.ViewPager
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.zone.banner.R
import com.zone.zbanner.PagerAdapterCycle
import com.zone.zbanner.indicator.animation.DefaultAnimation
import com.zone.zbanner.indicator.animation.MoveAnimation
import com.zone.zbanner.indicator.animation.abstarct.BaseAnimation
import com.zone.zbanner.indicator.type.CircleIndicator
import com.zone.zbanner.indicator.type.abstarct.BaseIndicator

/**
 * Created by Administrator on 2016/1/27.
 * viewpager linkage
 *
 * use example 1:
 *  1：setViewPager （viewPager,indicator）
 *
 * use example 2:
 *  1：setViewPager(viewPager)
 *  2: indicator
 */
class IndicatorView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : RelativeLayout(context, attrs, defStyleAttr), ViewPager.OnPageChangeListener {
    private lateinit var mViewPager: ViewPager
    private lateinit var llBottom: LinearLayout
    private lateinit var flTop: FrameLayout
    lateinit var ivTop: ImageView
    var pageChangeListener: ViewPager.OnPageChangeListener? = null
    var indicatorCount: Int = 0
    private var betweenMargin = 0
    private var animation: BaseAnimation? = null

    class Config {
        var mIsSnap = false
        var mShape: BaseIndicator? = null
    }

    companion object {
        var config: Config? = null
    }

    //init llBottom
    //init flTop
    //Set animation
    //Tips: 这里 mViewPager  llBottom fl_top都是有值的 是在setViewPager之后使用
    private lateinit var indicator: BaseIndicator

    fun getIndicator() = indicator

    fun setIndicator(indicator: BaseIndicator) {
        this.indicator = indicator
        indicator.indicatorView = this
        indicator.generateBitmaps()

        this.betweenMargin = indicator.betweenMargin
        llBottom.removeAllViews()
        flTop.removeAllViews()
        initFLTop(initLLBottom())

        initSnap()
    }

    var snap = false
        set(snap) {
            field = snap
            generateMoveAnimate(snap)
        }


    //默认的
    val circleIndicator = CircleIndicator(20)

    /**
     * 不设置indicator 会给默认值的
     */
    @JvmOverloads
    fun setViewPager(mViewPager: ViewPager, indicator: BaseIndicator = circleIndicator) {
        this.mViewPager = mViewPager
        if (this.mViewPager.adapter == null) throw IllegalStateException("must be use setAdapter!")
        if (this.mViewPager.adapter is PagerAdapterCycle<*>) {
            indicatorCount = (this.mViewPager.adapter as? PagerAdapterCycle<*>)?.size ?: 0
        } else {
            indicatorCount = this.mViewPager.adapter?.count ?: 0
        }
        this.mViewPager.setOnPageChangeListener(this)

        visibility = if (indicatorCount == 1) View.INVISIBLE
        else View.VISIBLE
        removeAllViews()
        initView()

        initIndicator(indicator)
    }


    private fun generateMoveAnimate(snap: Boolean) {
        animation = if (snap) MoveAnimation(this, betweenMargin + this.indicator.width)
        else DefaultAnimation(this, betweenMargin + this.indicator.width)
    }

    fun initSnap(){
        //如果全局设置的指示器的移动方式 则采用全局
        if (config != null) {
            config?.let {
                generateMoveAnimate(it.mIsSnap)
            }
        } else animation = DefaultAnimation(this, betweenMargin + indicator.width)
    }

    //全局indicator不为空  并且第二个参数indicator没有设置  则启用全局
    private fun initIndicator(indicator: BaseIndicator) {
        if (indicator == circleIndicator && config?.mShape != null) {
            config?.mShape?.let {
                setIndicator(it.clone_().cloneForabstract(it))
            }
        } else {
            setIndicator(indicator)
        }
    }


    private fun initView() {
        val inflater = LayoutInflater.from(context)
        llBottom = inflater.inflate(R.layout.core2_linear, this, false) as LinearLayout
        flTop = inflater.inflate(R.layout.core2_frame, this, false) as FrameLayout
        addView(llBottom)
        addView(flTop)
    }

    private fun initLLBottom(): Int {
        var llBottomSumWidth = 0
        for (i in 0 until indicatorCount) {
            val iv = ImageView(context)
            val params = LinearLayout.LayoutParams(this.indicator.width, this.indicator.height)
            llBottomSumWidth += this.indicator.width
            if (i != indicatorCount - 1) {
                params.rightMargin = betweenMargin
                llBottomSumWidth += betweenMargin
            }
            iv.layoutParams = params
            iv.setImageBitmap(this.indicator.getDefaultBitmap(i))
            llBottom.addView(iv)
        }
        return llBottomSumWidth
    }

    private fun initFLTop(ll_bottom_sumWidth: Int) {
        ivTop = ImageView(context)
        val params2 = LinearLayout.LayoutParams(this.indicator.width, this.indicator.height)
        ivTop.layoutParams = params2
        flTop.addView(ivTop)
        val flTopLp = flTop.layoutParams
        flTopLp.width = ll_bottom_sumWidth
        flTop.layoutParams = flTopLp
        //ivTop init position
        ivTop.setImageBitmap(this.indicator.getSelectedBitmap(mViewPager.currentItem))
        ivTop.x = (mViewPager.currentItem * (betweenMargin + this.indicator.width)).toFloat()

    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        indicator.onPageScrolled(position, positionOffset, positionOffsetPixels)
        animation?.onPageScrolled(position, positionOffset, positionOffsetPixels)
        pageChangeListener?.onPageScrolled(position, positionOffset, positionOffsetPixels)
    }

    override fun onPageSelected(position: Int) {
        indicator.onPageSelected(position)
        animation?.onPageSelected(position)
        pageChangeListener?.onPageSelected(position)
    }

    override fun onPageScrollStateChanged(state: Int) {
        indicator.onPageScrollStateChanged(state)
        animation?.onPageScrollStateChanged(state)
        pageChangeListener?.onPageScrollStateChanged(state)
    }

}
