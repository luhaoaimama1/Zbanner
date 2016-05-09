# ZBanner
一个开启和关闭的无限循环的 banner 并且可以开启与关闭轮播
### 已解决的问题
- [x] 支持无限循环的和不无限循环banner
- [x] 可以随时开启定时轮播(解决自动轮播 切换速度问题FixedSpeedScroller)与关闭定时轮播
- [x] 支持简单的indicatorView 并且可以与viewPager关联(方形 圆形 图片)
- [x] indicatorView的动画有两种方式：过渡，没过度的
- [x] 支持(Build.VERSION.SDK_INT < 11)的页面切换动画 并且父类是ViewPager 而不是一个新的类 
好处就是 可以与很多支持ViewPager的框架连用

# Usage

### Jcenter
gradle

    compile 'com.zone:zbanner:1.0.0'
pom.xml

    <dependency>
      <groupId>com.zone</groupId>
      <artifactId>zbanner</artifactId>
      <version>1.0.0</version>
      <type>pom</type>
    </dependency>
# Preview
### incatorshape:
![](./demo/incatorshape.gif)
### movemode:
![](./demo/movemode.gif)

# Easy use:
1.banner的初始化使用 PagerAdapterCycle  PagerAdapterCircle_Image则是继承PagerAdapterCycle子view仅仅是ImageView
     
     //   构造器第三个参数 则是viewpager是否可以循环
      mviewPager = new PagerAdapterCircle_Image(this, list, true) {
            @Override
            public void setImage(ImageView iv, int position) {
    //          iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
                Glide.with(MainActivity.this).load(list.get(position)).centerCrop()
                        .placeholder(R.drawable.ic_stub)
                        .error(R.drawable.ic_error).dontAnimate().into(iv);
            }
        };
  2.开启与关闭轮播
  
    pager.closeTimeCircle();
    pager.openTimeCircle();

3.indicatorView 的动画有两种方式
    
     indicatorView.setSnap(false);
     indicatorView.setSnap(true);
 
 4.indicatorView的关联与背景(方形 圆形 图片)
 参考demo
  

# Reference&Thanks：
https://github.com/daimajia/AndroidImageSlider

