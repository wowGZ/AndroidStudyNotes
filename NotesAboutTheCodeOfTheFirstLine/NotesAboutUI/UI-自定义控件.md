---
title: UI-自定义控件
---
## 创建自定义控件

View为TextView、ImageView、ViewGroup的父类；
TextView为EditText、Button的父类；
ViewGroup为LinearLayout、RelativeLayout等布局的父类；

View是Android中最基本的一种UI组件，它可以在屏幕上绘制一块矩形的区域，并能够响应这块区域的各种事件。

### 引入布局

以标题栏为例，一个程序的多个界面基本都需要标题栏，那么入股一个个的去写就会加大工作量，所以我们创建一个自定义的标题栏。
然后在需要的时候就可以 **引入布局** 来解决这个问题了。

新建一个布局，把你所需要的界面设计的样子设计好，然后在需要这一部分界面的布局文件中，利用include标签就可以把这个布局文件引入进去，就可以实现了，这样可以大量减少我们的代码量。

### 创建自定义控件

引入布局的技巧确实解决了重复编写布局代码的问题，但是如果布局之中有一些控件要求能够响应事件，我们还是需要在每个活动之中为这些控件单独编写一遍注册的代码。比如说标题栏的返回按钮，不管在哪一个活动当中它的功能都是一样的，这样就会增加很多没必要的重复的代码，这种情况最好的解决方式就是用 **自定义控件** 的方式来解决。

创建布局文件，进行界面设计，然后新建Java类继承对应的布局，在该类中进行初始化以及对应布局中的各个事件的响应，然后在所需引入该布局文件的布局文件中写入如下代码：

```xml
<io.github.wowgz.uicustomproject.TitleLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"/>
```

代码实例如下：

布局文件代码：

```xml
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:background="#5765ba">

    <Button
        android:id="@+id/title_back_button"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="center"
        android:layout_margin="5dp"
        android:text="Back"
        android:textAllCaps="false"
        android:textColor="#fff" />

    <TextView
        android:id="@+id/title_text_view"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:layout_gravity="center"
        android:layout_weight="1"
        android:gravity="center"
        android:text="Title Text"
        android:textAllCaps="false"
        android:textColor="#fff"
        android:textSize="24sp" />

    <Button
        android:id="@+id/title_edit_button"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="center"
        android:layout_margin="5dp"
        android:text="Edit"
        android:textAllCaps="false"
        android:textColor="#fff" />
</LinearLayout>
```

对应的Java类代码如下：

```java
public class TitleLayout extends LinearLayout {
    //构造函数中需要对标题栏布局进行动态的加载，就要借助LayoutInflater来实现
    //通过LayoutInflater的form()方法可以构建出一个LayoutInflater对象，
    //然后调用inflater()方法就可以动态的加载一个布局文件
    //inflater()方法接受两个参数，一个是需要加载的布局文件的id，第二个参数是给加载好的布局再添加一个父布局。
    public TitleLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.layout_title, this);

        Button title_back_button = (Button) findViewById(R.id.title_back_button);
        Button titlt_edit_button = (Button) findViewById(R.id.title_edit_button);
        TextView title_text_View = (TextView) findViewById(R.id.title_text_view);

        title_back_button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) getContext()).finish();
            }
        });

        titlt_edit_button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "You clicked Edit button", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
```

引用部分的代码已经在前面写过了，这里就不再编写，这样一个简单的自定义控件就完成了，可以在你所需要引用该控件的地方对其进行引用。
