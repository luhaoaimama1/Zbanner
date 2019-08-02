package com.zone.zbanner

import com.zone.viewpage_anime.HorizontalStackTransformerWithRotation
import com.zone.zbanner.indicator.IndicatorView
import com.zone.zbanner.indicator.type.abstarct.BaseIndicator
import com.zone.zbanner.simpleadapter.PagerAdapterCircleImage
import kotlin.collections.ArrayList

/**
 *[2019-07-31] by Zone
 */

class Group(var titleText: String, val list: ArrayList<Line> = ArrayList<Line>())

class Line(
    var hintText: String,
    val list: ArrayList<BaseSelection> = ArrayList<BaseSelection>()
)

abstract class BaseSelection(var text: String) {
    abstract fun doWhat(viewPager: ViewPagerCircle, indicatorView: IndicatorView, indicatorList: ArrayList<BaseIndicator>, mainActivity: MainActivity, pagerAdapterList: ArrayList<PagerAdapterCircleImage<String>>)
}

class SelectionDatas {
    companion object {
        @JvmField
        var selectionList = ArrayList<Group>().apply {
            add(Group("Indicator").apply {
                list.addAll(
                    arrayListOf(
                        Line("Move").apply {
                            list.apply {
                                add(object : BaseSelection("default") {
                                    override fun doWhat(viewPager: ViewPagerCircle, indicatorView: IndicatorView, indicatorList: ArrayList<BaseIndicator>, mainActivity: MainActivity, pagerAdapterList: ArrayList<PagerAdapterCircleImage<String>>) {
                                        indicatorView.snap = false
                                        MainActivity.SNAP_IS_DEFAULT = true
                                    }
                                })

                                add(object : BaseSelection("move") {
                                    override fun doWhat(viewPager: ViewPagerCircle, indicatorView: IndicatorView, indicatorList: ArrayList<BaseIndicator>, mainActivity: MainActivity, pagerAdapterList: ArrayList<PagerAdapterCircleImage<String>>) {
                                        indicatorView.snap = true
                                        MainActivity.SNAP_IS_DEFAULT = false
                                    }
                                })
                            }
                        },
                        Line("Shape").apply {
                            list.apply {
                                add(object : BaseSelection("circle") {
                                    override fun doWhat(viewPager: ViewPagerCircle, indicatorView: IndicatorView, indicatorList: ArrayList<BaseIndicator>, mainActivity: MainActivity, pagerAdapterList: ArrayList<PagerAdapterCircleImage<String>>) {
                                        indicatorView.setIndicator(indicatorList[0])
                                        indicatorView.snap = !MainActivity.SNAP_IS_DEFAULT
                                    }
                                })

                                add(object : BaseSelection("line") {
                                    override fun doWhat(viewPager: ViewPagerCircle, indicatorView: IndicatorView, indicatorList: ArrayList<BaseIndicator>, mainActivity: MainActivity, pagerAdapterList: ArrayList<PagerAdapterCircleImage<String>>) {
                                        indicatorView.setIndicator(indicatorList[1])
                                        indicatorView.snap = !MainActivity.SNAP_IS_DEFAULT
                                    }
                                })

                                add(object : BaseSelection("image") {
                                    override fun doWhat(viewPager: ViewPagerCircle, indicatorView: IndicatorView, indicatorList: ArrayList<BaseIndicator>, mainActivity: MainActivity, pagerAdapterList: ArrayList<PagerAdapterCircleImage<String>>) {
                                        indicatorView.setIndicator(indicatorList[2])
                                        indicatorView.snap = !MainActivity.SNAP_IS_DEFAULT
                                    }
                                })
                            }
                        }
                    )
                )

            })
            add(Group("ViewPager").apply {
                list.addAll(
                    arrayListOf(
                        Line("AutoPlay").apply {
                            list.apply {
                                add(object : BaseSelection("no") {
                                    override fun doWhat(viewPager: ViewPagerCircle, indicatorView: IndicatorView, indicatorList: ArrayList<BaseIndicator>, mainActivity: MainActivity, pagerAdapterList: ArrayList<PagerAdapterCircleImage<String>>) {
                                        viewPager.closeTimeCircle()
                                        MainActivity.IS_AUTO_PLAY = false
                                    }
                                })

                                add(object : BaseSelection("on") {
                                    override fun doWhat(viewPager: ViewPagerCircle, indicatorView: IndicatorView, indicatorList: ArrayList<BaseIndicator>, mainActivity: MainActivity, pagerAdapterList: ArrayList<PagerAdapterCircleImage<String>>) {
                                        viewPager.openTimeCircle()
                                        MainActivity.IS_AUTO_PLAY = true
                                    }
                                })
                            }
                        },
                        Line("Loop").apply {
                            list.apply {
                                add(object : BaseSelection("no") {
                                    override fun doWhat(viewPager: ViewPagerCircle, indicatorView: IndicatorView, indicatorList: ArrayList<BaseIndicator>, mainActivity: MainActivity, pagerAdapterList: ArrayList<PagerAdapterCircleImage<String>>) {
                                        setLoopNo(viewPager, pagerAdapterList, indicatorView)
                                    }
                                })

                                add(object : BaseSelection("on") {
                                    override fun doWhat(viewPager: ViewPagerCircle, indicatorView: IndicatorView, indicatorList: ArrayList<BaseIndicator>, mainActivity: MainActivity, pagerAdapterList: ArrayList<PagerAdapterCircleImage<String>>) {
                                        setLoopOn(viewPager, pagerAdapterList, indicatorView)
                                    }
                                })
                            }
                        },
                        Line("Animate").apply {
                            list.apply {
                                add(object : BaseSelection("no") {
                                    override fun doWhat(viewPager: ViewPagerCircle, indicatorView: IndicatorView, indicatorList: ArrayList<BaseIndicator>, mainActivity: MainActivity, pagerAdapterList: ArrayList<PagerAdapterCircleImage<String>>) {
                                        MainActivity.IS_DEFAULT_TRANSFORMER = !MainActivity.IS_DEFAULT_TRANSFORMER
                                        viewPager.setPageTransformer(true, null)
                                        setLoop(viewPager, pagerAdapterList, indicatorView)
                                    }
                                })

                                add(object : BaseSelection("on") {
                                    override fun doWhat(viewPager: ViewPagerCircle, indicatorView: IndicatorView, indicatorList: ArrayList<BaseIndicator>, mainActivity: MainActivity, pagerAdapterList: ArrayList<PagerAdapterCircleImage<String>>) {
                                        MainActivity.IS_DEFAULT_TRANSFORMER = !MainActivity.IS_DEFAULT_TRANSFORMER
                                        viewPager.setPageTransformer(true, HorizontalStackTransformerWithRotation(viewPager))
                                        setLoop(viewPager, pagerAdapterList, indicatorView)
                                    }
                                })
                            }
                        }
                    )
                )
            })
        }

        private fun setLoop(viewPager: ViewPagerCircle, pagerAdapterList: ArrayList<PagerAdapterCircleImage<String>>, indicatorView: IndicatorView) {
            if (MainActivity.IS_CIRCLE) setLoopOn(viewPager, pagerAdapterList, indicatorView)
            else setLoopNo(viewPager, pagerAdapterList, indicatorView)
        }

        private fun setLoopOn(viewPager: ViewPagerCircle, pagerAdapterList: ArrayList<PagerAdapterCircleImage<String>>, indicatorView: IndicatorView) {
            viewPager.setAdapter(pagerAdapterList[0], viewPager.currentItem)
            MainActivity.IS_CIRCLE = true
            val indicator = indicatorView.getIndicator()
            indicatorView.setViewPager(viewPager)
            indicatorView.setIndicator(indicator)
            indicatorView.snap = !MainActivity.SNAP_IS_DEFAULT
        }

        private fun setLoopNo(viewPager: ViewPagerCircle, pagerAdapterList: ArrayList<PagerAdapterCircleImage<String>>, indicatorView: IndicatorView) {
            viewPager.setAdapter(pagerAdapterList[1], viewPager.currentItem)
            MainActivity.IS_CIRCLE = false
            val indicator = indicatorView.getIndicator()
            indicatorView.setViewPager(viewPager)//不设置indicator 会给默认的
            indicatorView.setIndicator(indicator)
            indicatorView.snap = !MainActivity.SNAP_IS_DEFAULT
        }
    }
}