# ZBanner
An infinite loop for the opening and closing of the banner and can open and close the carousel
#### [中文版文档](./README-cn.md)

### Solved problems 
- [x] Supports infinite loop and non infinite loop banner 
- [x] You can always open the carousel (automatic timing switching speed of fixedspeedscroller to solve the carousel) and closing timing carousel 
- [x] Support simple indicatorview and can be associated with the viewpager (square round picture)
- [x] Indicatorview animation has two ways: the transition, not excessive 
- [x] Support (build.version.sdk_int < 11) page switching animation and the parent class is viewpager instead of a new class of benefits that can be used with a lot of support for the viewpager framework.


### JicPack
Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url "https://jitpack.io" }
		}
	}
Step 2. Add the dependency
>  compile 'com.github.luhaoaimama1:Zbanner:[Latest release](https://github.com/luhaoaimama1/Zbanner/releases)'

# Preview

![](http://tva1.sinaimg.cn/large/007S8ZIlgy1gdsbcae6iog30780egqvb.gif)

video preview address：https://github.com/luhaoaimama1/VideoDemo/blob/master/zbanner_demo_gif.mp4

# Easy use:
1.Banner initialization of the use of pageradaptercircle_image pageradaptercycle is to inherit the view child pageradaptercycle is just ImageView 
     
     //  The third constructor parameter is whether viewpager can be recycled 
      mviewPager = new PagerAdapterCircle_Image(this, list, true) {
            @Override
            public void setImage(ImageView iv, int position) {
    //          iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
                Glide.with(MainActivity.this).load(list.get(position)).centerCrop()
                        .placeholder(R.drawable.ic_stub)
                        .error(R.drawable.ic_error).dontAnimate().into(iv);
            }
        };
2.Open and close the carousel 
  
    pager.closeTimeCircle();
    pager.openTimeCircle();

3.There are two ways of indicatorview animation 
    
     indicatorView.setSnap(false);
     indicatorView.setSnap(true);
 
4.Indicatorview association with the background (square round picture) reference demo

5.Global configuration   <  class's configuration

``` java
   IndicatorView.config = IndicatorView.Config().apply {
            mIsSnap = true
            mShape = LineIndicator(50, 30).setShapeEntity(
                ShapeIndicator.ShapeEntity().setStrokeWidthHalf(2.5f).setStrokeColor(Color.BLACK).setHaveFillColor(false),
                ShapeIndicator.ShapeEntity().setFillColor(Color.RED)
            )
        }
```

# Reference&Thanks：
https://github.com/daimajia/AndroidImageSlider

