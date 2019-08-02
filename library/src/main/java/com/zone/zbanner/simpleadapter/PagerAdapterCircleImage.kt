package com.zone.zbanner.simpleadapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

import com.zone.zbanner.PagerAdapterCycle

/**
 * Created by Zone on 2016/1/27.
 */
abstract class PagerAdapterCircleImage<T>(context: Context, data: List<*>, isCircle: Boolean) : PagerAdapterCycle<*>(context, data, isCircle), SimpleAdapterSetImage {

    override fun getView(context: Context, position: Int): View {
        val iv = ImageView(context)
        iv.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        setImage(iv, position)
        return iv
    }
}
