package com.zone.zbanner.indicator.type.abstarct

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Paint

/**
 * Created by Zone on 2016/2/3.
 */
abstract class ShapeIndicator(width: Int, height: Int) : BaseIndicator(width, height) {
    protected var paint: Paint? = null
    protected var defaultShapeStyle: ShapeEntity
    protected var topShapeStyle: ShapeEntity
    protected var defaultBitmap: Bitmap
    protected var selectedBitmap: Bitmap

    init {
        paint = Paint()
        setShapeEntity(ShapeEntity().setFillColor(Color.WHITE), ShapeEntity().setFillColor(Color.RED))
    }

    protected fun initPaint() {
        paint!!.reset()
        paint!!.isAntiAlias = true
        paint!!.isDither = true
        paint!!.isFilterBitmap = true
    }

    fun setShapeEntity(defaultCircleStyle: ShapeEntity, topCircleStyle: ShapeEntity): ShapeIndicator {
        this.defaultShapeStyle = defaultCircleStyle
        this.topShapeStyle = topCircleStyle
        createDefalutBitmap()
        createSelectedBitmap()
        return this
    }


    private fun createDefalutBitmap() {
        defaultBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_4444)
        getBitmap(defaultBitmap, defaultShapeStyle)
    }

    private fun createSelectedBitmap() {
        selectedBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_4444)
        getBitmap(selectedBitmap, topShapeStyle)
    }

    protected abstract fun getBitmap(bitmap: Bitmap, shapeStyle: ShapeEntity)

    //fillRadius=radius-strokeWidthHalf;
    class ShapeEntity {
        //Why is half because when not half will be used will then display the division accuracy loss may be seamed
        private var strokeWidthHalf: Float = 0.toFloat()
        private var strokeColor = Color.WHITE
        private var haveStrokeColor = true
        private var fillColor = Color.WHITE
        private var haveFillColor = true

        fun isHaveStrokeColor(): Boolean {
            return haveStrokeColor
        }

        fun setHaveStrokeColor(haveStrokeColor: Boolean): ShapeEntity {
            this.haveStrokeColor = haveStrokeColor
            return this
        }

        fun isHaveFillColor(): Boolean {
            return haveFillColor
        }

        fun setHaveFillColor(haveFillColor: Boolean): ShapeEntity {
            this.haveFillColor = haveFillColor
            return this
        }

        fun getFillColor(): Int {
            return fillColor
        }

        fun setFillColor(fillColor: Int): ShapeEntity {
            this.fillColor = fillColor
            return this
        }

        fun getStrokeColor(): Int {
            return strokeColor
        }

        fun setStrokeColor(strokeColor: Int): ShapeEntity {
            this.strokeColor = strokeColor
            return this
        }

        fun getStrokeWidthHalf(): Float {
            return strokeWidthHalf
        }

        fun setStrokeWidthHalf(strokeWidthHalf: Float): ShapeEntity {
            this.strokeWidthHalf = strokeWidthHalf
            return this
        }
    }
}
