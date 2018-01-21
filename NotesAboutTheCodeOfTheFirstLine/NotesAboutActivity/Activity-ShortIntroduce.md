---
title: Activity概述
---
## 活动是什么？

活动是一种可以包含用户界面的组件，主要用于和用户的交互。
一个程序包括零个或是多个活动，但是不包含任何活动的应用程序很少

## 活动的基本用法

### 手动创建活动

1. 在对应需要创建活动的包右键鼠标，new一个Activity，并根据需要选择对应的Activity。（选项中有Generate LayoutFile和Launcher Activity两个选项，第一个选项作用是为我们所创建的活动自动生成xml布局文件，第二个选项作用是自动的将我们创建的活动设置为当前项目的主活动）（要记得勾选Backwards Compatibility选项，表示为项目启用向下兼容的模式。）
2. 项目中的任何活动都应该重写Activity的onCreate()方法，一般Android Studio会自动的帮我们重写好。
3. 当然，重写的onCreate()方法很简单，只是单纯的调用了父类的onCreate()方法，后面我们还要根据项目的需要加入自己的逻辑。

### 创建和加载布局

- 1、Android程序的设计讲究逻辑和视图分离，最好每一个活动对应一个布局文件，而布局文件就是用来显示界面内容的。
- 2、对项目的layout/目录右键new一个Layout resource file，然后填写对应的信息如布局文件名等。
- 3、wrap_content:表示当前元素的属性只要能刚好包含里面的内容就行了
- 4、通过右侧工具栏的preview来预览当前布局
- 5、布局编写完成之后，就需要回到对应的活动之中在活动中加载布局，在活动的onCreate()方法中加入如下代码：
```java
setContentView(R.layout."活动对应的布局文件的名称");
```
- 6、调用了setContentView()方法来给当前活动加载一个布局，而在setContentView()方法中，我们一般都会传入一个布局文件的id。
- 7、**在项目中添加的任何资源都会在R文件中生成一个相应的资源id，所以在代码中调用R.layout.id就可以得到所需要的xml文件的id，然后将这个值传入setContentView()方法中即可。**

### 在AndroidManifest文件中注册

- 1、所有活动都要在AndroidManifest.xml文件中进行注册才能生效。
- 2、活动的注册声明要放在<application>标签内，这里是通过<activity>标签来对活动进行注册的。
- 3、在<activity>标签中我们使用了android:name来指定具体注册哪一个活动，这里填入“."活动名"”，因为在最外层的<manifest>标签中已经通过package属性指定了程序的包名，所以这一部分在注册的时候就可以省略了。直接使用“.”+“文件名”即可。
- 4、仅仅注册了活动程序仍然是不可以运行的，还需要为程序配置主活动，即：在<activity>标签的内部加入<intent-filter>标签，并在这个标签里添加<action android:name="android.intent.action.MAIN"/>和<category android:name="android.intent.category.LAUNCHER"/>这两句声明即可。
- 5、除此之外我们还可以通过android:label指定活动中标题栏的内容，注意：**给主程序指定的label不仅会成为标题栏中的内容而且会成为启动器中应用程序显示的名称。**
- 6、另外，如果一个程序没有声明任何主活动，这个程序仍然是可以安装的，只不过你无法在启动器中看到或者打开这个程序，这种程序一般都是作为第三方服务共其他应用在内部进行调用的，如支付宝的快捷支付服务。

### 在活动中使用Toast

- 1、Toast是Android系统提供的一种非常好的提醒方式，用来将一些短小的信息通知给用户，而且信息会在一段时间后消失，同时不会占用任何屏幕空间。
- 2、首先需要定义一个Toast的触发点。为我们的Button控件设置一个监听点击事件，让用户在点击该按钮之后显示一个Toast消息提醒。
```java
protected void onCreate(Bundle savedInstanceState) {
  super.onCreate(savedInstanceState);
  setContentView(R.layout.first_activity);
  Button bt1 = (Button) findViewById(R.id.bt1);
  bt1.setOnClickListener (new View.OnClickListener() {
    @Override
    public void onClick(View v){
      Toast.makeText(FirstActivity.this, "You click button1.", Toast.LENGTH_SHORT).show();
    }
  });
}
```
- 3、通过findViewById()方法来找到在布局文件中定义的元素。需要注意的是：**findViewById()方法返回的是一个View对象，我们需要向下转型将它转化成对应的对象类型。**
- 4、通过调用setOnClickListener()方法来注册一个监听器，点击控件的时候就会执行监听器中的onClick()方法。所以我们的Toast要在onClick()方法中编写。
- 5、Toast的用法非常简单，通过静态方法makeText()创建出一个Toast对象，然后调用show()方法将Toast进行显示。这里需要注意：**makeText()方法要传入三个参数。第一个参数是Context，也就是Toast要求的“上下文”，由于活动本身就是Context对象，所以这里可以直接传入FirstActivity.this；第二个参数是Toast显示的文本内容；第三个参数是Toast显示的时长，有两个内置常量可以选择：Toast.LENGTH_SHORT和Toast.LENGTH_LONG。**

### 在活动中使用Menu

- 1、首先在res/目录下新建一个menu文件夹，res/ -> New -> Directory，输入文件夹名menu，点击OK。
- 2、在menu文件夹下在新建一个叫做main的菜单文件，New一个Menu resource file。
- 3、然后在main.xml添加代码：
```xml
<menu xmlns:android="http://schemas.android.com/apk/res/android">
  <item
    android:id="@+id/add_item"
    android:title="Add"/>
  <item
    android:id="@+id/remove_item"
    android:title="Remove"/>
</menu>
```
- 4、在以上代码中我们创建了两个菜单项，其中item就是用来创建一个具体的某一个菜单项，通过id属性为该菜单项指定一个唯一的标识符，通过title来为其指定一个名称（即显示的文字）
- 5、设置完成menu布局文件之后返回活动中来重写 **onCreateOptionsMenu()方法** ，重写方法可以使用Ctrl + O快捷键。然后在该方法中编写如下代码：
```java
//在活动中创建菜单并且允许我们所创建的菜单可以正常显示出来
public boolean onCreateOptionsMenu(Menu menu){
  getMenuInflater().inflate(R.menu.main, menu);
  return true;
}
```
- 6、通过getMenuInflater()方法能够得到MenuInflater对象，再调用它的inflate()方法就可以给当前活动创建菜单。
- 7、inflate()方法接受两个参数，第一个参数用于指定我们通过哪个资源文件来创建菜单，所以传入R.menu.main；第二个参数用于指定我们的菜单项将添加到拿一个Menu对象当中，这里直接使用方法中传入的参数menu。返回值为true，表示允许创建的菜单显示出来，相反的false表示不允许创建的菜单显示出来
- 8、但是仅仅将菜单显示出来是远远不够的，还需让我们的菜单真正的可以使用才行，因此还需要定义 **菜单响应事件** 。通过在活动中重写onOptionsItemSelected()方法来实现，代码如下：
```java
public boolean onOptionsItemSelected(MenuItem item){
  switch(item.getItemId()){
    case R.id.add_item:
      Toast.makeText(this, "You clicked Add", Toast.LENGTH_SHORT).show();
      break;
    case R.id.remove_item:
      Toast.makeText(this, "You clicked Remove", Toast.LENGTH_SHORT).show();
      break;
    default:
  }
  return true;
}
```
- 9、在onOptionsItemSelected()方法中，通过一个switch语句来进行多个菜单项的判断，通过item.getItemId()来获取所点击菜单项的标识符，然后在对应的case中加入自己的逻辑处理即可。
- 10、这个时候运行程序就会发现界面的右上角有一个三点的符号，这个就是菜单按钮了，单击菜单按钮就会显示我们设置的菜单项，再点击对应的菜单项的时候就会触发我们在onOptionsItemSelected()方法中所写入的对应的逻辑。

### 销毁一个活动

- 1、其实只需要按一下手机的Back键就可以销毁当前的活动了。
- 2、也可以通过Activity中提供的finish()方法来销毁当前程序。修改按钮监听器中的代码如下：
```java
bt1.setOnClickListener(new View.OnClickListener(){
  @Override
  public void onClick(View view){
    finish();
  }
})
```
- 3、运行程序，然后点击按钮就可以发现点击按钮当前的活动就会被销毁，其作用和点击Back键一样。
