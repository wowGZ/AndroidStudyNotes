---
title: Fragment-概述
---
## 碎片是什么

碎片(Fragment)是一种可以嵌入在活动当中的UI片段，它可以让程序更加合理和充分的利用大屏幕的空间，所以在平板上的应用非常广泛。

可以将碎片理解为一个迷你的活动。

## 碎片的使用方式

### 碎片的简单用法

在一个活动中添加两个碎片，并让这两个碎片平分活动空间。

先新建一个左侧碎片布局left_fragment.xml，代码如下：

```xml
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:orientation="vertical"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <Button
        android:id="@+id/button"
        android:layout_gravity="center_horizontal"
        android:layout_width="wrap_content"
        android:text="Button"
        android:textAllCaps="false"
        android:layout_height="wrap_content" />
</LinearLayout>
```

然后在写一个，右边碎片的文件，代码如下所示：

```xml
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="#00ff00"
    android:orientation="vertical">

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="center_horizontal"
        android:text="This is right fragment!"
        android:textAllCaps="false"
        android:textSize="20sp" />

</LinearLayout>
```

接下来新建一个LeftFragment类，并让它继承自Fragment。注意这里的Fragment会有两个，分别在不同的包下，我们最好选用support包下的，兼容性更好一些。

LeftFragment中的代码如下：

```java
public class LeftFragment extends Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_leftfragment, container, false);
        return view;
    }
}
```

RightFragment中的代码类似

```java
public class RightFragment extends Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rightfragment, container, false);
        return view;
    }
}
```

接下来修改activity_main.xml中的代码，如下所示：

```xml
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="horizontal"
    tools:context="com.example.fragmenttest.MainActivity">

    <fragment
        android:id="@+id/left_fragment"
        android:name="com.example.fragmenttest.LeftFragment"
        android:layout_width="0dp"
        android:layout_height="match_parent"
        android:layout_weight="1" />

    <fragment
        android:id="@+id/right_fragment"
        android:name="com.example.fragmenttest.RightFragment"
        android:layout_width="0dp"
        android:layout_height="match_parent"
        android:layout_weight="1" />

</LinearLayout>
```

我们使用<fragment>标签来添加碎片，只不过要使用android:name属性来显式的指明要添加的碎片名，注意一定要把包名都加上。

### 动态添加碎片

继续在之前的代码上进行完善，新建一个右边碎片的xml布局文件，代码如下：

```xml
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="#ffff00"
    android:orientation="vertical">

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="center_horizontal"
        android:text="This is another right fragment!"
        android:textAllCaps="false"
        android:textSize="20sp" />
</LinearLayout>
```

同样为这个布局文件建立一个Fragment类，代码如下所示：

```java
public class AnotherRightFragment extends Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_anotherrightfragment, container, false);
        return view;
    }
}
```

然后将activity_main.xml中的代码进行修改，如下所示：

```xml
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="horizontal"
    tools:context="com.example.fragmenttest.MainActivity">

    <fragment
        android:id="@+id/left_fragment"
        android:name="com.example.fragmenttest.LeftFragment"
        android:layout_width="0dp"
        android:layout_height="match_parent"
        android:layout_weight="1" />

    <FrameLayout
        android:id="@+id/right_layout"
        android:layout_width="0dp"
        android:layout_weight="1"
        android:layout_height="match_parent"/>

</LinearLayout>
```

将右侧的碎片替换成了一个FrameLayout，因为我们只需要在布局中放入一个碎片所以并不需要任何定位，所以非常适合用FrameLayout。

下面我们将在代码中向FrameLayout里添加内容，从而实现动态的添加碎片的功能。修改MainActivity中的代码，如下所示：

```java
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.button:
                        replaceFragment(new AnotherRightFragment());
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.right_layout, fragment);
        transaction.commit();
    }
}
```

首先我们给左侧碎片中的按钮注册了一个点击事件，然后调用replaceFragment()方法动态的添加了RightFragment这个碎片。

当点击左侧的按钮的时候，又会继续调用replaceFragment()这个方法将右侧碎片替换成AnotherRightFragment。

这个代码可以看出，动态的添加碎片，主要分为5步：

1. 创建待添加的碎片的实例；
2. 获取FragmentManager，在活动中可以直接通过调用getSupportFragmentManager()方法得到；
3. 开启一个 **事务** ，通过调用beginTransaction()方法开启；
4. 向容器内添加或者替换碎片，一般使用replace()方法来实现，需要传入容器的id和待添加的碎片实例；
5. 提交事务，调用commit()方法来完成。

### 在碎片中模拟返回栈

这个时候我们按下返回键，是会直接退出程序的，如果我们想实现点击Back键能够重新显示之前的碎片怎么办呢？

FragmentTransaction中提供了一个addToBackStack()方法，可以用于将一个事务添加到返回栈中，修改MainActivity中的代码，如下所示：

```java
private void replaceFragment(Fragment fragment) {
    FragmentManager fragmentManager = getSupportFragmentManager();
    FragmentTransaction transaction = fragmentManager.beginTransaction();
    transaction.replace(R.id.right_layout, fragment);
    transaction.addToBackStack(null);//接受参数用于描述返回栈的状态，一般为null即可。
    transaction.commit();
}
```

### 碎片和活动之间进行通信

碎片和活动都是各自存在一个独立的类中，之间并没有什么明显的方式来进行通信。

如何实现在活动中调用碎片中的方法，或者在碎片中调用活动中的方法呢？

为了方便在碎片和活动之间进行通信，FragmentManager中提供了一个类似于findViewById()的方法。

在活动中调用碎片的代码如下：

```java
RightFragment rightFragment = (RightFragment) getSupportFragmentManager()
  .findFragmentById(R.id.right_layoput);
```

在碎片中调用活动的代码如下：

```java
MainActivity activity = (MainActivity) getActivity();
```

有了活动实例之后，在碎片中调用活动里的方法就变得轻而易举了。
另外当碎片中需要使用context()对象的时候，也可以使用getActivity()方法，因为获取到的活动本身就是一个Context对象

**碎片与碎片之间的通信可以借助他们共同关联的活动来进行通信**
