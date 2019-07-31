package com.zone.zbanner.indicator;
import android.content.Context;
import androidx.viewpager.widget.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.zone.banner.R;
import com.zone.zbanner.PagerAdapterCycle;
import com.zone.zbanner.indicator.animation.DefaultAnimation;
import com.zone.zbanner.indicator.animation.MoveAnimation;
import com.zone.zbanner.indicator.animation.abstarct.BaseAnimation;
import com.zone.zbanner.indicator.type.abstarct.BaseIndicator;
/**
 * Created by Administrator on 2016/1/27.
 * viewpager linkage
 */
public class IndicatorView extends RelativeLayout implements ViewPager.OnPageChangeListener {
    private Context context;
    private ViewPager mViewPager;
    private LinearLayout ll_bottom;
    private FrameLayout fl_top;
    private ViewPager.OnPageChangeListener pageChangeListener;
    private int indeciatorCount, betweenMargin = 0;
    private BaseAnimation animation;
    private boolean snap = false;

    public IndicatorView(Context context) {
        this(context, null);
    }

    public IndicatorView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IndicatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    public void setViewPager(ViewPager mViewPager) {
        if(mViewPager.getAdapter()==null)
            throw new IllegalStateException("must be use setAdapter!");
        if (mViewPager.getAdapter() instanceof PagerAdapterCycle) {
            indeciatorCount = ((PagerAdapterCycle) mViewPager.getAdapter()).getSize();
        } else {
            indeciatorCount = mViewPager.getAdapter().getCount();
        }
        mViewPager.setOnPageChangeListener(this);
        this.mViewPager = mViewPager;
        if(indeciatorCount==1)
            setVisibility(View.INVISIBLE);
        else
            setVisibility(View.VISIBLE);
        removeAllViews();
        initView();
    }


    public void setOnPageChangeListener(ViewPager.OnPageChangeListener pageChangeListener) {
        this.pageChangeListener = pageChangeListener;
    }


    private void initView() {
        LayoutInflater inflater = LayoutInflater.from(context);
        ll_bottom = (LinearLayout) inflater.inflate(R.layout.core2_linear, null, false);
        fl_top = (FrameLayout) inflater.inflate(R.layout.core2_frame, null, false);
        addView(ll_bottom);
        addView(fl_top);
    }

    private BaseIndicator indicator;
    private ImageView iv_Top;

    public BaseIndicator getIndicator() {
        return indicator;
    }
    public void setIndicator(BaseIndicator indicator) {
        indicator.setIndicatorView(this);
        this.indicator = indicator;
        this.betweenMargin = indicator.getBetweenMargin();
        ll_bottom.removeAllViews();
        fl_top.removeAllViews();
        //init ll_bottom
        int ll_bottom_sumWidth = initLl_bottom();
        //init fl_top
        initFl_top(ll_bottom_sumWidth);
        //Set animation
        animation = new DefaultAnimation(this, betweenMargin + indicator.getWidth());
    }

    private int initLl_bottom() {
        int ll_bottom_sumWidth = 0;
        for (int i = 0; i < indeciatorCount; i++) {
            ImageView iv = new ImageView(context);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(indicator.getWidth(), indicator.getHeight());
            ll_bottom_sumWidth += indicator.getWidth();
            if (i != indeciatorCount - 1) {
                params.rightMargin = betweenMargin;
                ll_bottom_sumWidth += betweenMargin;
            }
            iv.setLayoutParams(params);
            iv.setImageBitmap(indicator.getDefaultBitmap(i));
            ll_bottom.addView(iv);
        }
        return ll_bottom_sumWidth;
    }

    private void initFl_top(int ll_bottom_sumWidth) {
        iv_Top = new ImageView(context);
        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(indicator.getWidth(), indicator.getHeight());
        iv_Top.setLayoutParams(params2);
        fl_top.addView(iv_Top);
        ViewGroup.LayoutParams params_ll_2 = fl_top.getLayoutParams();
        params_ll_2.width = ll_bottom_sumWidth;
        fl_top.setLayoutParams(params_ll_2);
        //iv_Top init position
        iv_Top.setImageBitmap(indicator.getSelectedBitmap(mViewPager.getCurrentItem()));
        iv_Top.setX( mViewPager.getCurrentItem() * (betweenMargin + indicator.getWidth()));

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (indicator != null)
            indicator.onPageScrolled(position, positionOffset, positionOffsetPixels);
        if (animation != null)
            animation.onPageScrolled(position, positionOffset, positionOffsetPixels);
        if (pageChangeListener != null)
            pageChangeListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
    }

    @Override
    public void onPageSelected(int position) {
        if (indicator != null)
            indicator.onPageSelected(position);
        if (animation != null)
            animation.onPageSelected(position);
        if (pageChangeListener != null)
            pageChangeListener.onPageSelected(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (indicator != null)
            indicator.onPageScrollStateChanged(state);
        if (animation != null)
            animation.onPageScrollStateChanged(state);
        if (pageChangeListener != null)
            pageChangeListener.onPageScrollStateChanged(state);
    }

    public void setSnap(boolean snap) {
        this.snap = snap;
        if (snap)
            animation = new MoveAnimation(this, betweenMargin + indicator.getWidth());
        else
            animation = new DefaultAnimation(this, betweenMargin + indicator.getWidth());
    }

    public boolean getSnap() {
        return snap;
    }

    public ImageView getIv_Top() {
        return iv_Top;
    }

    public int getIndeciatorCount() {
        return indeciatorCount;
    }
    //TODO If more animations are enabled, this method is enabled.
//    public void setAnimation(Class<? extends AbstractAnimation> aniClass) {
//        try {
//            Constructor<? extends AbstractAnimation> method = aniClass.getDeclaredConstructor(ImageView.class, int.class, int.class);
//            animation = method.newInstance(iv_Top, betweenMargin + indicator.getWidth(), indeciatorCount);
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        } catch (InstantiationException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }
//    }

}
