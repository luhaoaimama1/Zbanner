package com.zone.zbanner

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.zone.zbanner.indicator.IndicatorView
import com.zone.zbanner.indicator.type.CircleIndicator
import com.zone.zbanner.indicator.type.ImageIndicator
import com.zone.zbanner.indicator.type.LineIndicator
import com.zone.zbanner.indicator.type.abstarct.BaseIndicator
import com.zone.zbanner.indicator.type.abstarct.ShapeIndicator
import com.zone.zbanner.simpleadapter.PagerAdapterCircleImage
import kotlinx.android.synthetic.main.a_viewpager_circle_indicator.*


class MainActivity : Activity() {

    companion object {
        internal var IS_DEFAULT_TRANSFORMER = true
        internal var SNAP_IS_DEFAULT = true
        internal var IS_AUTO_PLAY = false
        internal var IS_CIRCLE = true
    }

    private lateinit var pagerAdapterList: ArrayList<PagerAdapterCircleImage<String>>
    private lateinit var indicatorList: ArrayList<BaseIndicator>
    lateinit var pager: ViewPagerCircle
    lateinit var indicatorView: IndicatorView
    lateinit var viewPager: PagerAdapterCircleImage<String>
    lateinit var viewPagerNoCircle: PagerAdapterCircleImage<String>
    lateinit var circleIndicator: ShapeIndicator
    lateinit var lineIndicator: ShapeIndicator
    lateinit var imageIndicator: ImageIndicator

    init {

        //全局设置可以打开 这个
        IndicatorView.config = IndicatorView.Config().apply {
            mIsSnap = true
            mShape = LineIndicator(50, 30).setShapeEntity(
                ShapeIndicator.ShapeEntity().setStrokeWidthHalf(2.5f).setStrokeColor(Color.BLACK).setHaveFillColor(false),
                ShapeIndicator.ShapeEntity().setFillColor(Color.RED)
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.a_viewpager_circle_indicator)

        pager = findViewById<View>(R.id.pager) as ViewPagerCircle
        indicatorView = findViewById<View>(R.id.indicatorView) as IndicatorView

        val list = ArrayList<String>()
        for (i in 0..3) {
            list.add(Images.imageThumbUrls[i])
        }
        viewPager = object : PagerAdapterCircleImage<String>(this, list, true) {
            override fun setImage(iv: ImageView, position: Int) {
                //  iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
                Glide.with(this@MainActivity).load(list[position]).centerCrop()
                    .placeholder(R.drawable.ic_stub)
                    .error(R.drawable.ic_error).dontAnimate().into(iv)
                iv.setOnClickListener { println("position:$position") }
            }
        }
        viewPagerNoCircle = object : PagerAdapterCircleImage<String>(this, list, false) {
            override fun setImage(iv: ImageView, position: Int) {
                //                iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
                Glide.with(this@MainActivity).load(list[position]).centerCrop()
                    .placeholder(R.drawable.ic_stub)
                    .error(R.drawable.ic_error).dontAnimate().into(iv)
                iv.setOnClickListener { println("position:$position") }
            }
        }

        pager.setAdapter(viewPagerNoCircle, 2)
        pager.setPageTransformer(true, null)
        pager.pageMargin = 50
        pager.offscreenPageLimit = 3
        //        new FixedSpeedScroller(this).setViewPager(pager);
        circleIndicator = CircleIndicator(20)
        indicatorView.setViewPager(pager)


        circleIndicator = CircleIndicator(20).setShapeEntity(ShapeIndicator.ShapeEntity().setStrokeWidthHalf(5f).setStrokeColor(Color.WHITE).setHaveFillColor(false),
            ShapeIndicator.ShapeEntity().setStrokeWidthHalf(5f).setFillColor(Color.RED).setHaveStrokeColor(false))
        lineIndicator = LineIndicator(50, 30).setShapeEntity(ShapeIndicator.ShapeEntity().setStrokeWidthHalf(2.5f).setStrokeColor(Color.BLACK).setHaveFillColor(false),
            ShapeIndicator.ShapeEntity().setStrokeWidthHalf(2.5f).setFillColor(Color.RED).setHaveStrokeColor(false))


        imageIndicator = ImageIndicator(100, 100)
        val defaultBitmaps = ArrayList<Bitmap>()
        defaultBitmaps.add(BitmapFactory.decodeResource(resources, R.drawable.perm_group_calendar_normal))
        defaultBitmaps.add(BitmapFactory.decodeResource(resources, R.drawable.perm_group_camera_normal))
        defaultBitmaps.add(BitmapFactory.decodeResource(resources, R.drawable.perm_group_device_alarms_normal))
        defaultBitmaps.add(BitmapFactory.decodeResource(resources, R.drawable.perm_group_location_normal))
        val selectBitmaps = ArrayList<Bitmap>()
        selectBitmaps.add(BitmapFactory.decodeResource(resources, R.drawable.perm_group_calendar_selected))
        selectBitmaps.add(BitmapFactory.decodeResource(resources, R.drawable.perm_group_camera_selected))
        selectBitmaps.add(BitmapFactory.decodeResource(resources, R.drawable.perm_group_device_alarms_selected))
        selectBitmaps.add(BitmapFactory.decodeResource(resources, R.drawable.perm_group_location_selected))
        imageIndicator.defaultBitmaps = defaultBitmaps
        imageIndicator.selectBitmaps = selectBitmaps
        indicatorList = arrayListOf<BaseIndicator>(circleIndicator, lineIndicator, imageIndicator)
        pagerAdapterList = arrayListOf<PagerAdapterCircleImage<String>>(viewPager, viewPagerNoCircle)


        initPropertySelections()
        //如果我们要对ViewPager设置监听，用indicator设置就行了
        //        indicatorView.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
        //
        //            @Override
        //            public void onPageSelected(int position) {
        //                System.out.println("position");
        //                System.out.printf("MainActivity onPageSelected====position:%d /t \n", position);
        //            }
        //
        //            @Override
        //            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        //                System.out.printf("MainActivity onPageScrolled====position:%d /tpositionOffset:%f /tpositionOffsetPixels:%d /t \n", position, positionOffset, positionOffsetPixels);
        //            }
        //
        //            @Override
        //            public void onPageScrollStateChanged(int state) {
        //                System.out.printf("MainActivity onPageScrollStateChanged====state:%d /t  \n", state);
        //
        //            }
        //        });
    }

    private fun initPropertySelections() {
        SelectionDatas.selectionList.forEach { item ->
            //生成group布局 并填充到父布局里面
            addGroup(item)
        }
    }

    private fun addGroup(item: Group) {
        val viewSection = LayoutInflater
            .from(this)
            .inflate(R.layout.item_group, ll_group, false) as ViewGroup

        //group布局 设置title
        viewSection.findViewById<TextView>(R.id.tv_group_title).text = item.titleText

        //对group区域  分解成 lines 的属性
        item.list.forEach { line ->
            //生成line布局  并填充到父布局里面
            addLine(viewSection, line)
        }
        ll_group.addView(viewSection)
    }

    private fun addLine(viewSection: ViewGroup, line: Line) {
        val viewSectionLine = LayoutInflater
            .from(this)
            .inflate(R.layout.item_line, viewSection, false)

        //line布局 设置title
        viewSectionLine.findViewById<TextView>(R.id.tv_title_line).text = line.hintText

        //对line布局  生成选项selections
        viewSectionLine.findViewById<LinearLayout>(R.id.ll_selections)?.apply {
            line.list.forEachIndexed { index, selection ->
                //生成line布局  并填充到父布局里面
                addSelect(this, selection, index)
            }
        }
        viewSection.addView(viewSectionLine)
    }

    private fun addSelect(llSelections: LinearLayout, selection: BaseSelection, index: Int) {
        val tv = LayoutInflater
            .from(this)
            .inflate(R.layout.item_tv_selection, llSelections, false)
                as TextView
        if (index == 0) {
            setSelected(llSelections, tv)
        }
        tv.text = selection.text
        tv.setOnClickListener {
            if (!setSelected(llSelections, tv)) selection.doWhat(pager, indicatorView, indicatorList, this@MainActivity, pagerAdapterList)
        }
        llSelections.addView(tv)
    }

    /**
     *  @return  true 跳过
     */
    private fun setSelected(llSelections: LinearLayout, tv: TextView): Boolean {
        if (llSelections.tag == tv) return true
        //找到之前的设置成黑色
        (llSelections.tag as? TextView)?.setTextColor(resources.getColor(R.color.black))
        //当前的选中设置成选中色
        tv.setTextColor(resources.getColor(R.color.selectColor))
        //选中的tv存起来
        llSelections.tag = tv
        return false
    }

    override fun onDestroy() {
        //todo 注意清空 这个定时播放的任务
        pager.closeTimeCircle()
        super.onDestroy()
    }
}
