package com.zone.zbanner.indicator.type

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint

import com.zone.zbanner.indicator.type.abstarct.ShapeIndicator

/**
 * Created by Administrator on 2016/1/27.
 */
class CircleIndicator private constructor(width: Int, height: Int) : ShapeIndicator(2 * width, 2 * height) {

    init {
        setBetweenMargin(width)
    }

    constructor(radius: Int) : this(radius, radius) {}

    override fun getBitmap(bitmap: Bitmap, circleStyle: ShapeIndicator.ShapeEntity) {
        val radius = width / 2 * 1f
        val canvas = Canvas(bitmap)
        //fill
        initPaint()
        paint.style = Paint.Style.FILL
        if (!circleStyle.isHaveFillColor())
            paint.color = Color.TRANSPARENT
        else
            paint.color = circleStyle.getFillColor()
        canvas.drawCircle(radius, radius, radius - circleStyle.getStrokeWidthHalf() * 2, paint)
        //stroke
        initPaint()
        paint.style = Paint.Style.STROKE
        if (!circleStyle.isHaveStrokeColor())
            paint.color = Color.TRANSPARENT
        else
            paint.color = circleStyle.getStrokeColor()
        paint.strokeWidth = circleStyle.getStrokeWidthHalf() * 2
        canvas.drawCircle(radius, radius, radius - circleStyle.getStrokeWidthHalf(), paint)
    }

    override fun getDefaultBitmap(position: Int): Bitmap {
        return defaultBitmap
    }

    override fun getSelectedBitmap(position: Int): Bitmap {
        return selectedBitmap
    }

    override fun onPageSelected(position: Int) {
        super.onPageSelected(position)
        indicatorView!!.ivTop.setImageBitmap(getSelectedBitmap(position))
    }


}
