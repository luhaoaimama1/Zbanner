package com.zone.zbanner;
import android.content.Context;
import android.os.Handler;
import androidx.viewpager.widget.ViewPager;
import android.util.AttributeSet;
import com.zone.zbanner.viewpager.ViewPagerCompat;

// Mainly used for banner pages and boot boot page
public class ViewPagerCircle extends ViewPagerCompat {
	private final Context context;
	private long delayMillis=3000;
	private boolean isTimeDelay=false;
	private int initCircle=200;
	private Handler handler;
	private PagerAdapterCycle adapter;
	private ViewPager.OnPageChangeListener mListener;
	public ViewPagerCircle(Context context, AttributeSet attrs) {
		super(context, attrs);
		handler=new Handler();
		this.context=context;
	}

	public ViewPagerCircle(Context context) {
		this(context, null);
	}
	/**
	 * @param adapter
	 */
	public void setAdapter(PagerAdapterCycle adapter) {
		setAdapter(adapter, 0);
	}
	/**
	 * @param adapter
	 */
	public void setAdapter(PagerAdapterCycle adapter,int offset) {
		super.setAdapter(adapter);
		this.adapter=adapter;
		if(offset>=adapter.getSize())
			throw new IllegalArgumentException("offset must be < adapter.getSize()!");
		if (adapter.isCircle())
			setCurrentItem(adapter.getSize() * initCircle + offset);
		else
			setCurrentItem(offset);
		setOnPageChangeListener(null);
		new FixedSpeedScroller(context).setViewPager(this);
	}

	public void nextPage() {
		if (adapter.isCircle())
			setCurrentItem(getCurrentItemZone() + 1, true);
		else{
			if(getCurrentItem()!=adapter.getSize()-1)
				setCurrentItem(getCurrentItemZone() + 1, true);
			else
				pauseCircle();
		}
	}

	public void previousPage() {
		if (adapter.isCircle())
			setCurrentItem(getCurrentItemZone() - 1,true);
		else{
			if(getCurrentItem()!=0)
				setCurrentItem(getCurrentItemZone() - 1, true);
		}
	}
	/**
	 * Call in adapter
	 */
	private void againTiming(){
		if (isTimeDelay) {
			handler.removeCallbacks(run);
			handler.postDelayed(run, delayMillis);
		}
	}

	private Runnable run=new Runnable() {
		@Override
		public void run() {
			nextPage();
		}
	};

	@Override
	public void setOnPageChangeListener(final ViewPager.OnPageChangeListener listener) {
		mListener=listener;
		if (adapter==null)
			throw new IllegalStateException("setadapter must be use before setOnPageChangeListener!");
			ViewPager.OnPageChangeListener listenerSet = new ViewPager.OnPageChangeListener() {
				@Override
				public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
					if(positionOffsetPixels!=0)
						pauseCircle();
					else
						againTiming();
					int reallyPosition = position % adapter.getSize();
					if (mListener!=null)
						mListener.onPageScrolled(reallyPosition, positionOffset, positionOffsetPixels);
				}

				@Override
				public void onPageSelected(int position) {
					int reallyPosition = position % adapter.getSize();
					if (mListener!=null)
						mListener.onPageSelected(reallyPosition);
				}

				@Override
				public void onPageScrollStateChanged(int state) {
					if (mListener!=null)
						mListener.onPageScrollStateChanged(state);
				}
		};
		super.setOnPageChangeListener(listenerSet);
	}

	public long getDelayMillis() {
		return delayMillis;
	}

	public void setDelayMillis(long delayMillis) {
		this.delayMillis = delayMillis;
	}
	/**
	 * Open the carousel carousel time is the default time
	 */
	public void openTimeCircle() {
		openTimeCircle(-1);
	}
	/**
	 * Open the carousel and set the carousel time
	 * @param delayMillis
	 */
	public void openTimeCircle(long delayMillis) {
		if (-1!=delayMillis)
			this.delayMillis = delayMillis;
		if(adapter.getSize()==1)
			return;
		isTimeDelay=true;
		againTiming();
	}
    public void closeTimeCircle(){
		if (isTimeDelay) {
			handler.removeCallbacks(run);
			isTimeDelay=false;
		}
    }
	private void pauseCircle(){
		if (isTimeDelay) {
			handler.removeCallbacks(run);
		}
	}


    @Override
    public int getCurrentItem() {
        return super.getCurrentItem()%adapter.getSize();
    }
    private int getCurrentItemZone() {
        return super.getCurrentItem();
    }
}
