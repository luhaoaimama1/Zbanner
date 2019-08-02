package com.zone.zbanner.indicator.type

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.zone.zbanner.indicator.type.abstarct.BaseIndicator
import com.zone.zbanner.indicator.type.abstarct.ShapeIndicator

/**
 * Created by Administrator on 2016/1/27.
 */
class LineIndicator(width: Int, height: Int) : ShapeIndicator(width, height) {

    init {
        setBetweenMargin(width / 2)
    }

    override fun getBitmap(bitmap: Bitmap, shapeStyle: ShapeEntity) {
        val canvas = Canvas(bitmap)
        //fill
        initPaint()
        paint.style = Paint.Style.FILL
        if (!shapeStyle.isHaveFillColor())
            paint.color = Color.TRANSPARENT
        else
            paint.color = shapeStyle.getFillColor()
        canvas.drawRect(0 + shapeStyle.getStrokeWidthHalf() * 2, 0 + shapeStyle.getStrokeWidthHalf() * 2,
            width - shapeStyle.getStrokeWidthHalf() * 2, height - shapeStyle.getStrokeWidthHalf() * 2, paint)
        //stroke
        initPaint()
        paint.style = Paint.Style.STROKE
        if (!shapeStyle.isHaveStrokeColor())
            paint.color = Color.TRANSPARENT
        else
            paint.color = shapeStyle.getStrokeColor()
        paint.strokeWidth = shapeStyle.getStrokeWidthHalf() * 2
        canvas.drawRect(0 + shapeStyle.getStrokeWidthHalf(), 0 + shapeStyle.getStrokeWidthHalf(),
            width - shapeStyle.getStrokeWidthHalf(), height - shapeStyle.getStrokeWidthHalf(), paint)
    }

    override fun getDefaultBitmap(position: Int): Bitmap = defaultBitmap

    override fun getSelectedBitmap(position: Int): Bitmap = selectedBitmap

    override fun onPageSelected(position: Int) {
        super.onPageSelected(position)
        indicatorView?.ivTop?.setImageBitmap(getSelectedBitmap(position))
    }

    override fun clone_(): BaseIndicator = LineIndicator(0, 0)

}
