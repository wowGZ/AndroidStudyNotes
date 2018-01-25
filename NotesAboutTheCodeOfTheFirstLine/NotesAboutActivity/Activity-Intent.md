---
title: Activity-Intent
---
## 使用 Intent 在活动之间穿梭

通过使用 Intent 来实现从主活动向其他活动的跳转。

Intent 是 Android 程序中各个组件之间进行交互的一种重要方式，它不仅可以指明当前组件想要执行的动作，还可以在不同的组件之间传递数据。 Intent 一般可被用于启动活动、启动服务以及发送广播等。

Intent 大致分为两种：显式的 Intent 和隐式的 Intent

### 使用显式 Intent

Intent 有多个构造函数重载，其中一个是 Intent(Context package, Class<?> cls) 。这个构造函数接收两个参数，第一个参数是 Context 要求提供一个启动活动的上下文，第二个参数是 class ，则是想要启动的目标活动的 java 类文件。

如何使用： Activity 类中提供了一个 stratActivity() 方法，这个方法就是专门用来启动活动的，我们只需要将我们创建好的 Intent 传入到该方法中就可以使用 startActivity() 启动对应的活动了。

使用这种方法来启动活动， Intent 的意图非常明显，因此我们称之为 **显示 Intent**。

### 使用隐式 Intent

隐式 Intent 并不明确指出我们想要启动哪一个活动，而是指定了一系列更为抽象的 action 和 category 等信息，然后交给系统去分析这个 Intent ，并帮我们找到合适的活动去启动。

首先，要在 AndroidManifest.xml 文件下，通过在 <activity> 标签下配置 <intent-filter> 的内容，可以指定当前活动能够响应的 action 和 category 。
```xml
<activity android:name=".SecondActivity">
  <intent-filter>
    <action android:name="com.example.activitytest.ACTION_START"/>
    <category android:name="android.intent.category.DEFAULT"/>
  </intent-filter>
</activity>
```
这里，在 <action> 标签中我们指明了当前活动可以相应 "com.example.activitytest.ACTION_START" 这个 action ，而 <category> 这个标签则包含了一些附加信息，更精确的指明了当前活动能够响应的 Intent 中还可能带有的 category 。

**只有当< action >和< category >中的内容同时能够（ 意味着， action 和 category 都可能有多个，只要各有一个可以匹配上这个活动就可以响应 Activity）匹配上 Intent 中指定的 action 和 category 时，这个活动才可以响应 Intent**

隐式Intent使用的时候就有了一个新的构造方法：
```java
Intent intent = new Intent("你所声明的action字符串");
```

利用这个方法来进行隐式的 Intent 调用。注：之前代码中的 category 的字符串表示的是一种默认的 category ，如果没有对Intent的 category 进行定义的话，在调用 startActivity() 方法的时候系统会默认自动生成这个默认的category。如果需要自己根据需要进行定义 category 的话，那么可以对Intent对象调用 addCategory() 方法来实现。

PS:遇到程序崩溃记得不要慌，要善于查看 logcat 日志，对自己的错误进行分析哦。

### 更多隐式 Intent 的用法

使用隐式的 Intent 不仅可以启动自己程序的活动，也可以启动别的应用程序的活动，这样就使得Android多个应用程序之间实现了功能上的共享。

```java
bt1.setOnClickListenr(new View.setOnClickListener(){
  @Override
  public void onClick(View v){
    Intent intent = new Intent(Intent.ACTION_VIEW);
    intent.setData(Uri.parse("http://www.baidu.com"));
    startActivity(intent);
  }
});
```

对以上代码进行解读：这里我们首先指定了 Intent 的 action 是 Intent.ACTION_VIEW ，这是一个Android系统内置的动作，其常量值为android.intent.action.VIEW。然后通过Uri.parse()方法，将一个网址字符串解析称为一个Uri对象，再调用Intent的setData()方法将这个Uri对象传进去就好了。

setData()方法：这个方法其实并不复杂，它接收一个Uri对象，主要用于指定当前Intent正在操作的数据，而这些数据通常都是以字符串的形式传入到Uri.parse()方法中进行解析的产生的。

于此对应的，我们还可以在<intent-filter>标签中再配置一个<data>标签，用于更为精确的指定当前活动能够响应什么类型的数据。<data>标签中主要可以配置一下内容：
- android:scheme.用于指定数据的协议部分，如之前代码中的http部分
- android:host.用于指定数据的主机名部分，如www.baidu.com部分
- android:port.用于指定数据的端口部分，一般紧随在主机名之后
- android:path.用于指定主机名和端口之后的部分，如一段网址跟在域名之后的内容
- android:mimeType.用于指定可以处理的数据类型，允许使用通配符的方式进行指定

同样的向action和category一样，只有当data标签中的内容和intent中的内容完全一致的时候，当前活动才能响应该Intent。

同样的可以使用这种隐式的Intent来进行系统拨号的调用，代码如下：
```java
bt1.setOnClickListener(new View.OnClickListener(){
  @Override
  public void onClick(View v){
    Intent intent = new Intent(Intent.ACTION_DIAL);
    intent.setData(Uri.parse("tel:10086"));
    startActivity(intent);
  }
});
```
首先，指定了Intent的action是Intent.ACTION_DIAL，这又是一个Android系统的内置动作。然后在data部分指定了协议是tel，号码是10086.

### 向下一个活动传递数据

传递数据的思路很简单，就是对Intent中一系列PutExtra()方法的重载，可以把我们想要传递的数据暂时存储在Intent中，启动了另外一个活动之后，就可以再把这些数据从Intent中取出来就好了。

比如：
```java
bt1.setOnClickListener(new View.OnClickListener(){
  @Override
  public void onClick(View v){
    String data = "Hello SecondActivity";
    Intent intent = new Intent(FirstACtivity.this, SecondActivity.class);
    intent.putExtra("extra_data", data);
    startActivity(intent);
  }
})
```

这里还是通过显示Intent的方法来启动活动，并且通过了putExtra()方法传递了一个字符串。
**putExtra()方法接收两个参数，第一个参数是键，用于从后面的Intent中取值，第二个参数才是真正要传递的值**
取出数据的代码如下：

```java
public class SecondActivity extends AppCompatActivity{
  @Override
  protected void onCreate(Bundle savedInstanceState){
    super.onCreate(savedInstanceState);
    setContentView(R.layout.second_layout);
    Intent intent = getIntent();
    String data = intent.getStringExtra("extra_data");
    Log.d("SecondActivity", data);//用于在logcat中打印数据，便于用来检验
  }
}
```

首先，这里使用getIntent()方法来获取到用于启动SecondActivity的Intent，然后调用getStringExtra()方法，传入相对应的键值来获取传递的数据。根据数据类型的不同使用不同的方法来获取不同的传递过来的值。

### 返回数据给上一个活动

既然可以传递数据给下一个活动自然也是可以把数据返回之前的活动的。但是并不存在一个用于启动活动的Intent来传递数据。

通过查阅文档你可以发现，在Activity中还有个startActivityForResult()方法也是用于启动活动的，但是这个方法期望在活动销毁的时候可以返回一个结果给上一个活动。这个，自然就是我们所需要的方法。

startActivityForResult()方法接收两个参数，第一个参数还是Intent，第二个参数是请求码，用于在之后的回调中进行判断数据的来源。

修改之前的代码来看一下：
```java
bt1.setOnClickListener(new View.OnClickListener(){
  @Override
  public void onClick(View v){
    Intent intent = new Intent(FirstActivity.this, SecondActivity.class);
    startActivityForResult(intent, 1);
  }
});
```
**请求码只要是一个唯一值就可以了**

下面在SecondActivity中添加逻辑：

```java
public class SecondActivity extends AppCompatActivity{
  @Override
  protected void onCreate(Bundle savedInstanceState){
    super.onCreate(savedInstanceState);
    setContentView(R.layout.second_layout);
    Button bt2 = (Button) findViewById(R.id.bt2);
    bt1.setOnClickListener(new View.OnClickListener(){
      @Override
      public void onClick(View v){
        Intent intent = new Intent();
        intent.putExtra("data_return", "Hello FirstActivity");
        setResult(RESULT_OK, intent);
        finish();
      }
    });
  }
}
```

这里我们还是创建了一个Intent但是这里的Intent仅仅是用来保存传递数据的并没有什么意图。把要传递的数据存放在Intent中，然后调用了setResult()方法，这个方法是专门用来向上一个方法返回数据的。
**setResult()方法接收两个参数，第一个参数是用于向上一个活动返回处理结果的一般只是用RESULT_OK或者RESULT_CANCELED这两个值，第二个参数则把带有数据的Intent传递回去**
然后调用finish()方法来销毁当前的活动。

因为我们是使用的startActivityForResult()方法来启动SecondActivity的，所以在SecondActivity被销毁之后，会自动的调用上一个活动的onActivityResult()方法，所以我们需要在FirstActivity中实现对应的方法来获取Intent所传递回来的数据。
代码如下：

```java
@Override
protected void onActivityResult(int requestcode, int resultcode, Intent data){
  switch(requestcode){
    case 1:
      if (resultcode == RESULT_OK) {
        String returnData = data.getStringExtra("data_return");
        log.d("FirstActivity", returnData);
      }
      break;
    default:

  }
}
```

onActivityResult()方法有三个参数，第一个参数是requestcode就是请求码，第二个参数是resultcode，即我们在返回数据的时候传入的处理结果，第三个参数就是带着我们所保存的数据的Intent。因为可能会有多个活动返回该活动，所以我们通过requestcode来判断数据来源。确定了数据来源之后，在通过判断resultcode的值来判断处理结果是否成功。最后从data中取值并打印出来，这样一次完整的回调工作就完成了。

为了解决用户按Back键，而不用按钮返回的话就午饭将值传递回去的问题，我们可以不重写点击事件，而是重写onBackPressed()方法来解决这一个问题。
