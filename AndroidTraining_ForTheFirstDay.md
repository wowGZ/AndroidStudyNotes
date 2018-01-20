---
title: AndroidTraining-ForTheFirstDay
---

## AS常用快捷键

- ctrl + alt + L:格式化代码；
- alt + 鼠标左键:选中一块区域；
- shift + F6:重命名，全局替换变量、方法名、文件名等；
- shift + shift:双击shift可以全局查找文件，包括sdk或者第三方库文件；
- ctrl + F12:可以查看当前文件结构，可以搜索方法名并跳转到对应的方法；

## 常用控件及属性

### View

就是控件，是所有控件的父类；

- android:layout_width：宽度；
- android:layout_height：高度；
- android:id ：id ，在同一个布局文件中id应该是唯一的；
- android:layout_margin：外边距，通俗的说就是当前控件与周围的控件和父布局的距离；
- android:padding：内边距，通俗的说就是当前控件与自身内容的距离；
- android:background：背景；
- android:layout_gravity：在当前布局的位置，该属性跟布局有关；
- android:gravity：内容的位置；

### ViewGroup

是布局的父类，比如线性布局或者相对布局等，同时ViewGroup的父类是View，所以View所拥有的属性ViewGroup都有。

### TextView

用于显示文本信息

- android:text：需要显示出来的文字；
- android:textSize：文字的大小；
- android:textColor：显示出来的文字的颜色；
- android:textStyle：文字的字体（normal 默认、bold 加粗、italic 斜体）

### Button

按钮，直接父类是TextView，所以TextView的属性Button都有；

### EditText

顾名思义，是输入框，直接父类也是TextView

- android:hint：输入框的提示信息；
- android:inputType：输入的类型（比如，设置密码类型，会不被直接显示出来）

### ImageView

- android:src：设置图片资源；
- android:scaleType：图片规模类型；

这里src和background的区别：

- 两者都能显示图片，但是src显示的是前景图片，background显示的是背景图片，前者会把后者覆盖；
- scaleType只能对src有用；

## 几种布局

### LinearLayout

线性布局，可以设置为水平线性或者垂直线性

- android:orientation：设置线性方向（horizontal 水平（默认）、vertical（垂直））；

### RelativeLayout

属性：

- android:layout_alignParentLeft；
- android:layout_alignParentTop；
- android:layout_alignParentRight；
- android:layout_alignParentBottom；
- android:layout_alignParentBottom；

以上的所有属性，接受的参数均为boolean类型。分别表示在整个RelativeLayout布局中的左、下、右、上、中；

- android:layout_above；
- android:layout_below；
- android:layout_toRightOf；
- android:layout_toLeftOf；

以上属性，接受的参数均为控件，即某一控件的id，这些属性的含义为处于所传入参数控件的某一个方位（方位看所调用的属性）；

## 常用的消息提醒方式

### Toast

一般用于简短信息提示，一小段时间过后会消失，不长期存在；

- Toast.make(MainActivity.this, "Hello World", Toast.LENGTH_SHORT).show();

所接受的三个参数：

1. 上下文context，一般指的是当前类的实例；
2. 字符串或者字符串资源id；
3. 显示时长（Toast.LENGTH_LONG 长时间、Toast.LENGTH_SHORT 短时间）；

注意只有调用show()方法才能显示toast；

## 配置文件AndroidManifest

### 配置文件

- 为四大组件（Activity、Service、Content Provider、Broadcast Receiver）进行声明
- 申请权限
