---
title: UI-基本布局
---
## 四种基本布局

### 线性布局(LinearLayout)

线性布局，是一种非常常用的布局。这个布局会将它所包含的控件在线性方向上依次排列。

可以通过调用线性布局的android:orientation属性来指定排列方向是vertical还是horizontal。

这里需要注意，如果排列方向是horizontal那么控件的宽度就绝对不能是match_parent，同理，方向是vertical那么高度就不能是match_parent。

android:gravity和android:layout_gravity的区别：

android:gravity用来指定文字在控件中的对齐方式，android:layout_gravity则是指定控件在布局中的对齐方式。

注意：LinearLayout的排列方向是horizontal的时候，只有垂直方向上的对齐方式才会生效，因为水平方向上的长度是不固定的。同理，当LinearLayout的排列方向是vertical的时候，只有水平方向上的对齐方式才会生效。

重要属性 **android:layout_weight**

这个属性，可以理解为，该控件在布局中所占的比重，利用这个来确定它的大小，而不用指定确定的值，这样在手机屏幕适配方面会有很大的作用。

### 相对布局(RelativeLayout)

相对布局也是一种非常常用的布局。相对于线性布局来说，相对布局会显得更加随意一点，属性相对较多，但是有规律可循.
android:layout_alignParentTop;
android:layout_alignParentLeft;
android:layout_alignParentRight;
android:layout_alignParentBottom;
android:layout_centerInParent;
顾名思义。

以上的属性都是用来确定控件在父布局中的位置的，其实我们还可以根据控件之间的位置关系来确定控件的属性。
android:layout_above;
android:layout_toLeftOf;
android:layout_toRightOf;
android:layout_below;
这些属性的赋值为某一控件的Id，意为位于该控件的某个位置。

相对布局中还有一组相对于控件进行定位的属性，android:layout_alignLeft,android:layout_alignRight，表示让一个控件的左/右边缘和另外一个控件的左/右边缘对齐，当然也有android:layout_alignTop和android:layout_alignBottom属性，用法相同。

### 帧布局(FrameLayout)

FrameLayout又称作帧布局，它比前两种布局要简单得多，但是应用的场景也很少。

该布局没用方便的定位方式，默认情况下所有的控件都会在布局的左上角。

当然除了这种默认的方式，我们还可以使用andorid:layout_gravity来指定控件在布局中的对齐方式，这个和线性布局中的用法是类似的。

我们在使用碎片的时候会用到这个。

### 百分比布局(PercentFrameLayout、PercentRelativeLayout)

前面的三种布局都是从Android 1.0版本就开始支持了，一直到现在，可以说是满足了绝大多数的场景的界面设计需求。

**只有LinearLayout布局支持使用layout_weight的属性来实现按比例指定控件的大小的功能，其他的两种布局都不支持。**

为此，Android引入了百分比布局来解决这个问题。

在这个布局中我们可以不再使用wrap_content或者match_parent来指定控件的大小，而是可以直接使用控件所占的百分比这样的话就可以轻松的实现平分布局甚至是任意比例分割布局的效果。

因为百分比布局属于新添加的布局，所以我们需要在app/build.gradle文件在dependencies闭包中添加如下内容：
compile 'com.android.support:percent:24.2.1'
每次修改之后都需要重新同步gradle

因为PercentFrameLayout并不是内置在系统的SDK中，所以需要完整的把包的路径写出来。
