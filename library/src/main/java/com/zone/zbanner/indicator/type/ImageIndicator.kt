package com.zone.zbanner.indicator.type

import android.graphics.Bitmap
import com.zone.zbanner.indicator.type.abstarct.BaseIndicator
import java.util.ArrayList

/**
 * Created by Zone on 2016/2/3.
 */
class ImageIndicator(width: Int, height: Int) : BaseIndicator(width, height) {
    var defaultBitmaps: List<Bitmap> = ArrayList()
    var selectBitmaps: List<Bitmap> = ArrayList()

    override fun getDefaultBitmap(position: Int): Bitmap {
        return defaultBitmaps[position]
    }

    override fun getSelectedBitmap(position: Int): Bitmap {
        return selectBitmaps[position]
    }

    override fun onPageSelected(position: Int) {
        super.onPageSelected(position)
        indicatorView!!.ivTop.setImageBitmap(getSelectedBitmap(position))
    }
}
