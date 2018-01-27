---
title: Activity-BestPractice
---
## 活动的最佳实践

### 知晓当前是在哪一个活动

一个简单技巧让你快速的找到当前是在执行哪一个活动。
首先需要新建一个BaseActivity，只是需要新建一个普通的Java类就可以了，让它继承AppCompatActivity，并且重写onCreate()方法。
代码如下所示：
```java
public class BaseActivity extends AppCompatActivity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Log.d("BaseActivity", getClass().getSimpleName());
  }
}
```
我们在onCreate()方法里面获取了当前实例的类名，然后让BaseActivity类称为活动的父类，这样在每个活动运行的时候，logcat中就会打印出来所运行的活动对应的类名，我们就可以轻松的知道当前运行的活动是哪一个活动了。

### 随时随地退出程序

需要用一个专门的集合类对所有的活动进行管理就可以了，下面我们就来实现一下。

新建一个ActivityCollector类作为活动管理器，代码如下所示：

```java
public class ActivityCollector {
  public static List<Activity> activities = new ArrayList<>();

  public static void addActivity(Activity activity) {
    activities.add(activity);
  }

  public static void removeActivity(Activity activity) {
    activity.remove(activity);
  }

  public static void finishAll() {
    for (Activity activity : activities) {
      if (!activity.isFinishing()) {
        activity.finish();
      }
    }
    activities.clear();
  }
}
```

在活动管理器中，我们通过一个List来暂存活动，然后提供一个addActivity()方法来添加活动，同理removeActivity()方法用来移除一个活动，最后提供一个finishAll()方法来销毁掉List中的所有的活动。
接下来修改BaseActivity中的代码，如下所示：

```java
public class BaseActivity extends AppCompatActivity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Log.d("BaseActivity", getClass().getSimpleName());
    ActivityCollector.addActivity(this);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    ActivityCollector.removeActivity(this);
  }
}
```

这样就可以使得我们所有的活动都会在我们设置的专门的活动管理器中有所记录，我们也可以对其进行更为方便有效的控制。

这样的话，无论我们想要在哪里直接退出程序，都只是需要调用一下我们管理器中的finishAll()方法就可以正常退出了，就不需要一个一个的关闭所有在返回栈里面的活动才能退出了。

### 启动活动的最佳写法

启动活动的基本方法已经烂熟于心，就是首先通过Intent来构建出当前的意图，然后在调用startActivity()方法来启动活动，如果有数据需要传递那么也可以借助Intent来完成。

假设一个活动中需要用到两个非常重要的字符串参数，在启动该活动的时候必须要传递过来，那么我们会很容易写出以下代码：

```java
Intent intent = new Intent(FirstActivity.this, SecondActivity.class);
intent.putExtra("param1", "data1");
intent.putExtra("param2", "data2");
startActivity(intent);
```

这样写是完全正确的，但是当开发项目的时候会出现对接的问题出现。

比如：SecondActivity并不是有你开发的，但是现在你负责的部分需要有启动SecondActivity这个功能，而你却不清楚启动这二个活动需要传递哪些数据，这样就很麻烦了。

其实，我们只需要换一种写法就可以了。

```java
public class SecondActivity extends AppCompatActivity {
  //省略部分正常功能的代码，只写所需要修改的代码

  public static void actionStart(Context context, String data1, String data2) {
    Intent intent = new Intent(context, SecondActivity.class);
    intent.putExtra("param1", "data1");
    intent.putExtra("param2", "data2");
    context.startActivity(intent);
  }
}
```

这样不仅使得SecondActivity中所需要传递的参数全部都体现出来，而且还简化了启动活动的代码。现在启动SecondActivity就只需要一行代码就够了

```java
SecondActivity.actionStart(FirstACtivity.this, "data1", "data2");
```

养成一个良好的习惯，为每一个你编写的活动都添加类似的方法。
