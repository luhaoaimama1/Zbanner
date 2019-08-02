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

    override fun getDefaultBitmap(position: Int): Bitmap = defaultBitmaps[position]
    override fun getSelectedBitmap(position: Int): Bitmap = selectBitmaps[position]

    override fun onPageSelected(position: Int) {
        super.onPageSelected(position)
        indicatorView?.ivTop?.setImageBitmap(getSelectedBitmap(position))
    }

    /**
     * 如果想 解压资源文件成图片 可以自己定义 就在generateBitmaps 这里进行解压
     */
    override fun generateBitmaps() {
    }

    /**
     *  暂不支持 如果想支持 ，实现generateBitmaps 里生成bitmaps .这里仅仅克隆属性
     */
    override fun clone_(): BaseIndicator {
        TODO("not implemented")
    }

}
