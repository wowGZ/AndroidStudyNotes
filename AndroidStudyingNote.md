---
title: AndroidStudyNote-VideoLearning
---
## Android项目结构介绍

1. src/：包里面存放java源代码；
2. gen/：包里面存放系统自动生成的配置文件，比如R文件之类的，一般不需要我们进行修改；
3. android+"版本号"/：包里面包含android.jar文件，这是一个java归档文件，其中包含构建应用程序所需要的所有的Android SDK库（如Views，controls）和APIs
4. assets/：包里面存放资源文件，不会自动生成id且不会自动占用空间。比如：可以存放视频或者图片资源，而且在最终生成的文件的“.apk”结尾的文件里面并不会包括assets包内的文件的大小，除非当应用编译或执行过程中应用到了该目录下的资源文件。
5. bin/：开发过程中同样不需要理会这个文件夹，该文件夹是用来存放应用被编译之后生成的可执行文件“.apk”，以及应用到被打包到apk中的资源文件
6. libs/：其中放置第三方文件同3；
7. res/（就是resource，顾名思义是资源文件）：存放应用中所用到的资源文件，并且会占用最后生成的文件大小。
8. res/drawable/（存放不同密度的图片资源文件）：drawable-hdpi:h指的是对应资源文件的对应分辨率的文件，在应用于不同手机的时候根据手机的分辨率来自动选择对应文件夹下的资源文件；
9. layout/：用于存放布局文件（".xml"文件，用于布局处理，即界面的UI设计）。menu/其实也是一个存放布局文件的文件夹，但是其中是用于存放菜单布局文件的；
10. values/：用于存放字符串、主体、颜色、样式等资源文件，将所需要用到的数据或者资源放置到对应的文件中去，然后在其他需要使用的地方对该数据或资源进行引用
11. AndroidManifest.xml（清单文件）：配置一些与应用有关的重要信息，包含包名、权限、程序组件等等。程序运行的时候其实会现运行程序的Manifest清单文件，就会大概知道该应用程序有多少包，以及一下权限和程序组件的信息。

## Android控件之TextView和EditText

### 概述

1. TextView：显示文本框控件
2. EditText：输入文本框

### 控件属性解析

TextView和EditText的常用属性

#### TextView控件的常用属性

1. android:id——控件的id
2. android:layout_width——控件的宽度
3. android:layout_height——控件的高度
4. android:text——文本内容
5. android:textSize——文本大小
6. android:textColor——文本颜色
7. android:background——控件背景

#### EditText控件的常用属性

1. android:id——控件的id
2. android:layout_width——控件的宽度
3. android:layout_height——控件的高度
4. android:text——文本内容
5. android:textSize——文本大小
6. android:textColor——文本颜色
7. android:background——控件背景
8. android:hint——输入提示文本
9. android:inputType——输入文本类型

### TextView和EditText的使用

在width和height属性中有三个数值：
1. wrap_content:包裹实际文本内容（文本有多宽，控件就有多宽）；
2. fill_parent:当前控件铺满父类容器
3. match_parent:当前控件铺满父类容器（在linearlayout中的TextView，则该属性的意思就是铺满linearlayout控件）（与fill_parent没有太多的区别，只不过match_parent是在2.3版本的api之后添加的一个属性，fill_parent是在2.3api之前的一个属性值）

布局文件设置好之后，需要在对应的java源文件中与对应的布局文件进行关联
```java
public class Mainactivity extends Activity{
  @Override
  protected void onCreate(Bundle savedInstanceState){
    super.onCreate(savedInstanceState);
    //将布局文件xml引入到activity中
    setContentView(R.layout.main_activity);
  }
}
```
ps:
1. 在Activity中使用findViewById方法获取一个View对象，参数为控件的id，同时还需要把View对象强制转换成相应的类型；
2. 除了可以在xml布局文件中为控件设置字体颜色外，在Activity中也可以，一般情况下，布局属性与对象的setXXX方法一一对应，比如设置字体颜色，在xml中属性是android:textColor，而在Activity中则是setTextColor方法；
3. 设置EditText的android:inputType属性可以限制文本输入类型比如android:inputType="textPassword"为设置输入格式为密码格，	android:inputType="phone"为设置输入格式为拨号键盘；

## Android控件之ImageView

### ImageView控件概述

1. 什么是ImageView？ImageView是可以显示图片的一个控件，主要的功能就是显示图片
2. ImageView的一些属性：1、android:src="@drawable/ic_launcher"（ImageView的内容图像）2、android:background"@drawable/ic_launcher"（ImageView的背景图片，也可以设置一个RGB颜色）

### 不同分辨率下图片的显示

会根据手机或者模拟器的不同的分辨率自动的对drawable内的图片

## Android控件之按钮控件（Button和ImageButton）

### Button和ImageButton控件概述

#### Button——按钮

拥有同普通的控件类似的属性

#### ImageButton——图片按钮

额外可以设置它所引用的图片以及背景或者背景颜色

#### Button和ImageButton的特征

- 共有的特性：

都可以作为一个按钮产生点击事件

- 不同点：
1. Button有text属性（可以输入文本），ImageButton没有；
2. ImageButton有src属性（可以引用图片资源），Button没有；

- 产生明显的点击效果

PS：
在xml文件中对控件进行文本或者其他资源的使用的时候，尽量使用引用的方式。如：使用Button的text属性，会在其中显示文本内容，那么可以在values文件夹内的string文件中进行定义，然后对其进行引用。
