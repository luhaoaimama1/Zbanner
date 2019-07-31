package com.zone.zbanner;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.zone.zbanner.indicator.IndicatorView;
import com.zone.zbanner.indicator.type.CircleIndicator;
import com.zone.zbanner.indicator.type.ImageIndicator;
import com.zone.zbanner.indicator.type.LineIndicator;
import com.zone.zbanner.indicator.type.abstarct.ShapeIndicator;
import com.zone.zbanner.simpleadapter.PagerAdapterCircle_Image;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    private ViewPagerCircle pager;
    private IndicatorView indicatorView;
    PagerAdapterCircle_Image mviewPager;
    PagerAdapterCircle_Image mviewPagerNoCircle;
    private ShapeIndicator circleIndicator;
    private ShapeIndicator lineIndicator;
    private ImageIndicator imageIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_viewpager_circle_indicator);

        pager = (ViewPagerCircle) findViewById(R.id.pager);
        indicatorView = (IndicatorView) findViewById(R.id.indicatorView);


        final List<String> list = new ArrayList<String>();
        for (int i = 0; i < 4; i++) {
            list.add(Images.imageThumbUrls[i]);
        }
        mviewPager = new PagerAdapterCircle_Image(this, list, true) {
            @Override
            public void setImage(ImageView iv, final int position) {
//                iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
                Glide.with(MainActivity.this).load(list.get(position)).centerCrop()
                        .placeholder(R.drawable.ic_stub)
                        .error(R.drawable.ic_error).dontAnimate().into(iv);
                iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        System.out.println("position:"+position);
                    }
                });
            }
        };
        mviewPagerNoCircle = new PagerAdapterCircle_Image(this, list, false) {
            @Override
            public void setImage(final ImageView iv, final int position) {
//                iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
                Glide.with(MainActivity.this).load(list.get(position)).centerCrop()
                        .placeholder(R.drawable.ic_stub)
                        .error(R.drawable.ic_error).dontAnimate().into(iv);
                iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        System.out.println("position:"+position);
                    }
                });
            }
        };

        pager.setAdapter(mviewPager, 2);
//        pager.setPageTransformer(true, new TestAnime());
        pager.setPageTransformer(true, null);
        pager.setPageMargin(50);
        pager.setOffscreenPageLimit(3);
//        new FixedSpeedScroller(this).setViewPager(pager);
        indicatorView.setViewPager(pager);
        circleIndicator = new CircleIndicator(20);
        indicatorView.setIndicator(circleIndicator);


        circleIndicator = new CircleIndicator(20).setShapeEntity
                (new ShapeIndicator.ShapeEntity().setStrokeWidthHalf(5).setStrokeColor(Color.WHITE).setHaveFillColor(false),
                        new ShapeIndicator.ShapeEntity().setStrokeWidthHalf(5).setFillColor(Color.RED).setHaveStrokeColor(false));
        lineIndicator = new LineIndicator(50, 30).setShapeEntity
                (new ShapeIndicator.ShapeEntity().setStrokeWidthHalf(2.5F).setStrokeColor(Color.BLACK).setHaveFillColor(false),
                        new ShapeIndicator.ShapeEntity().setStrokeWidthHalf(2.5F).setFillColor(Color.RED).setHaveStrokeColor(false));


        imageIndicator = new ImageIndicator(100, 100);
        List<Bitmap> defaultBitmaps = new ArrayList<Bitmap>();
        defaultBitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.perm_group_calendar_normal));
        defaultBitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.perm_group_camera_normal));
        defaultBitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.perm_group_device_alarms_normal));
        defaultBitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.perm_group_location_normal));
        List<Bitmap> selectBitmaps = new ArrayList<Bitmap>();
        selectBitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.perm_group_calendar_selected));
        selectBitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.perm_group_camera_selected));
        selectBitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.perm_group_device_alarms_selected));
        selectBitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.perm_group_location_selected));
        imageIndicator.setDefaultBitmaps(defaultBitmaps);
        imageIndicator.setSelectBitmaps(selectBitmaps);


        //如果我们要对ViewPager设置监听，用indicator设置就行了
//        indicatorView.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//
//            @Override
//            public void onPageSelected(int position) {
//                System.out.println("position");
//                System.out.printf("MainActivity onPageSelected====position:%d /t \n", position);
//            }
//
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                System.out.printf("MainActivity onPageScrolled====position:%d /tpositionOffset:%f /tpositionOffsetPixels:%d /t \n", position, positionOffset, positionOffsetPixels);
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//                System.out.printf("MainActivity onPageScrollStateChanged====state:%d /t  \n", state);
//
//            }
//        });
    }


    @Override
    protected void onDestroy() {
        pager.closeTimeCircle();
        super.onDestroy();
    }

    boolean isDefault = true;
    boolean isOpenTime = false;
    boolean isCircle = true;
    Shape shape = Shape.circle;

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_toggle:
                if (isDefault) {
                    indicatorView.setSnap(true);
                    isDefault = false;
                    ToastUtils.showLong(MainActivity.this, "变成move");
                } else {
                    indicatorView.setSnap(false);
                    isDefault = true;
                    ToastUtils.showLong(MainActivity.this, "变成Default");
                }
                break;
            case R.id.bt_toggleTime:
                if (isOpenTime) {
                    pager.closeTimeCircle();
                    isOpenTime = false;
                    ToastUtils.showLong(MainActivity.this, "手动轮播");
                } else {
                    pager.openTimeCircle();
                    isOpenTime = true;
                    ToastUtils.showLong(MainActivity.this, "定时轮播");
                }
                break;
            case R.id.togShape:
                switch (shape) {
                    case circle:
                        indicatorView.setIndicator(circleIndicator);
                        ToastUtils.showLong(MainActivity.this, "变成方形");
                        shape = Shape.line;
                        break;
                    case line:
                        indicatorView.setIndicator(lineIndicator);
                        ToastUtils.showLong(MainActivity.this, "变成方形");
                        shape = Shape.image;
                        break;
                    case image:
                        indicatorView.setIndicator(imageIndicator);
                        ToastUtils.showLong(MainActivity.this, "变成图片");
                        shape = Shape.circle;
                        break;
                }
                if (isDefault)
                    indicatorView.setSnap(false);
                else
                    indicatorView.setSnap(true);
                if (isOpenTime)
                    pager.openTimeCircle();
                else
                    pager.closeTimeCircle();
                break;
            case R.id.togCircle:
                if (isCircle) {
                    pager.setAdapter(mviewPagerNoCircle, 2);
                    isCircle = false;
                    indicatorView.setViewPager(pager);
                    indicatorView.setIndicator(circleIndicator);
                    ToastUtils.showLong(MainActivity.this, "关闭循环");
                } else {
                    pager.setAdapter(mviewPager, 2);
                    isCircle = true;
                    indicatorView.setViewPager(pager);
                    indicatorView.setIndicator(circleIndicator);
                    ToastUtils.showLong(MainActivity.this, "开启循环");
                }
                if (isDefault)
                    indicatorView.setSnap(false);
                else
                    indicatorView.setSnap(true);
                if (isOpenTime)
                    pager.openTimeCircle();
                else
                    pager.closeTimeCircle();
                break;
        }
    }

    enum Shape {
        circle, line, image;
    }
}
