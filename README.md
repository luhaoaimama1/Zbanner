# ZBanner
An infinite loop for the opening and closing of the banner and can open and close the carousel
#### [中文版文档](./README-cn.md)

### Solved problems 
- [x] Supports infinite loop and non infinite loop banner 
- [x] You can always open the carousel (automatic timing switching speed of fixedspeedscroller to solve the carousel) and closing timing carousel 
- [x] Support simple indicatorview and can be associated with the viewpager (square round picture)
- [x] Indicatorview animation has two ways: the transition, not excessive 
- [x] Support (build.version.sdk_int < 11) page switching animation and the parent class is viewpager instead of a new class of benefits that can be used with a lot of support for the viewpager framework.


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
![](./demo/shape.gif)
### movemode:
![](./demo/move.gif)

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
  
# Reference&Thanks：
https://github.com/daimajia/AndroidImageSlider

