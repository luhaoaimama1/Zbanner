package com.zone.zbanner

import android.content.Context
import androidx.viewpager.widget.PagerAdapter
import android.view.View
import android.view.ViewGroup

abstract class PagerAdapterCycle<T>(private val context: Context, val data: List<T>, isCircle: Boolean) : PagerAdapter() {

    var isCircle: Boolean = if (data.size == 1) false
    else isCircle

    val size: Int
        get() = data.size


    /**
     * Defines the total length of the viewpager.
     */
    override fun getCount(): Int {
        return if (isCircle) Integer.MAX_VALUE
        else data.size
    }

    /**
     * Determine whether to use the cache, if true,
     * the use of cached arg0 is the object to drag the object arg1 to come in.
     */
    override fun isViewFromObject(arg0: View, arg1: Any): Boolean {
        return arg0 === arg1
    }

    /**
     * The object position is the index of the object being destroyed.
     */
    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        // Removes the object from the viewpager for the current index
        container.removeView(container.findViewById(position))
    }

    /**
     * Index of item loaded with position item
     */
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val reallyPosition = position % data.size
        val view = getView(context, reallyPosition)
        view.id = position
        container.addView(view)
        return view
    }

    /**
     * Obtained by view list
     * @param position
     * @return
     */
    abstract fun getView(context: Context, position: Int): View

}
