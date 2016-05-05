package com.zone.zbanner;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public abstract class PagerAdapterCycle<T> extends PagerAdapter {

    private  boolean isCircle;
    private  Context context;
    private List<T> data = null;


    public PagerAdapterCycle(Context context,List<T> data,boolean isCircle) {
        this.data = data;
        this.context=context;
        if(data.size()==1)
            this.isCircle=false;
        else
            this.isCircle=isCircle;
    }


    /**
     * Defines the total length of the viewpager.
     */
    @Override
    public int getCount() {
        if (isCircle)
            return Integer.MAX_VALUE;
        else
            return data.size();
    }

    /**
     * Determine whether to use the cache, if true,
     * the use of cached arg0 is the object to drag the object arg1 to come in.
     */
    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    /**
     * The object position is the index of the object being destroyed.
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // Removes the object from the viewpager for the current index
        container.removeView(container.findViewById(position));
    }

    /**
     * Index of item loaded with position item
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        int reallyPosition = position % data.size();
        View view = getView(context,reallyPosition);
        view.setId(position);
        container.addView(view);
        return view;
    }

    public int getSize() {
        return data.size();
    }
    public boolean isCircle() {
        return isCircle;
    }

    /**
     *Obtained by view list
     * @param position
     * @return
     */
    public abstract View getView(Context context,int position);

}
