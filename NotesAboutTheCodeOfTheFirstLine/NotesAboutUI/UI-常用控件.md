---
title: UI-常用控件
---

## 常用控件的使用方法

### TextView

TextView主要用于在界面上显示一段文本信息。

1. android:layout_width
2. android:layout_height
Android中所有的控件都有这两个属性，可选值有三个
1. match_parent
2. wrap_content
3. fill_parent

其中 1 和 3 的意义相同，官方更加推荐使用 1 。 1 就是让当前控件的大小和父布局的大小一样，也就是由父布局来决定当前控件的大小。 2 就是让当前控件的大小刚好能包裹住里面的内容，也就是由当前控件的内容来决定当前控件的大小。

除了使用上述的三个属性外，你也可以定义一个大小，但是可能会出现不同手机屏幕适配方面的问题。

使用android:text属性可以设置TextView里面所显示文本内容。

TextView中的文字默认的是左上角对齐的，我们可以使用android:gravity来指定文字对齐方式，可选值有：top/bottom/left/right/center等等，可以使用“ | ”来同时指定多个值。

同时我们可以对TextView中的文字的大小和颜色进行修改，使用android:textSize和android:textColor进行修改。PS：在android中字体的大小使用sp作为单位。

### Button

Button的属性与TextView有很多相同的地方就不再仔细介绍。

仔细观察会发现我们输入的文本内容是大小写分明的，但是却在显示的时候全部都是大写的，这是因为android中默认我们的输入的文本为大写的英文，如果需要大小写都显示的话，只需要修改一个属性就好了android:textAllCaps="false"，这样就不会令显示的内容全部为英文大写了。

接下来我们可以在Button所在的对应的Activity中为Button的点击事件注册一个监听器如下所示：

```java
public class MainActivity extends AppCompatActivity {
  @Override
  protected void onCreate(Bundle savedIntenceState) {
    super.onCreate(savedIntenceState);
    setContentView(R.layout.activity_main);
    Button button = (Button) findViewById(R.id.button);
    button.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        //在这里添加该点击事件所对应的逻辑
      }
    })
  }
}
```

如果你不喜欢用匿名类的方式来注册监听器，那么还可以使用接口的方式来实现，代码如下：

```java
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
  @Override
  protected void onCreate(Bundle savedIntenceState) {
    super.onCreate(savedIntenceState);
    setContentView(R.layout.activity_main);
    Button button = (Button) findViewById(R.id.button);
    button.setOnClickListener(this);
  }

  @Override
  public void onClick(View v) {
    switch(v.getId()) {
      case R.id.button:
        //在这里添加逻辑
        break;
      default:
        break;
    }
  }
}
```

### EditText

EditText是一个和用户交互的重要控件，它允许用户输入和编辑内容并且可以在程序中对这些内容进行操控和处理。

相似的，Android中的控件的使用规律，用法基本上都很相似：给控件定义一个ID，在指定控件的高度和宽度，然后再加入一些控件特有的属性就好了。

输入框中的提示性的英文是使用属性android:hint来实现的，一旦用户输入了任何内容之后提示的内容就会消失的。

因为随着我们输入的内容不断增多EditText就会不断的被拉长，因为EditText的高度设置的是wrap_content所以总能够包裹住所输入的内容，但是当输入内容过多的时候，界面就会变得很难堪。我们可以使用android:maxline来进行限制。

android:maxline顾名思义可以指定EditText被拉伸的最大行数，这样当内容达到最大行数时，文本就会向上滚动，EditText也不不会再继续拉伸。

我们可以通过Button和EditText来实现一些功能，比如通过点击按钮来获取EditText中用户输入的内容

```java
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
  @Override
  protected void onCreate(Bundle savedIntenceState) {
    super.onCreate(savedIntenceState);
    setContentView(R.layout.activity_main);
    Button button = (Button) findViewById(R.id.button);
    EditText editText = (EditText) findViewById(R.id.editText);
    button.setOnClickListener(this);
  }

  @Override
  public void onClick(View v) {
    switch(v.getId()) {
      case R.id.button:
        String inputText = editText.getText().toString();
        Toast.markText(MainActivity.this, inputText, Toast.LENGTH_SHORT).show();
        break;
      default:
        break;
    }
  }
}
```

### ImageView

ImageView是用来在界面上展示一个图片的控件。

图片通常都是放在以“drawble/”开头的目录下的。

android:src属性可以为ImageView来设置一张图片。我们还可以在程序中通过代码动态的来实现图片的变换，修改之前的代码，如下所示：

```java
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

  private EditText editText;

  private ImageView imageView;

  @Override
  protected void onCreate(Bundle savedIntenceState) {
    super.onCreate(savedIntenceState);
    setContentView(R.layout.activity_main);
    Button button = (Button) findViewById(R.id.button);
    editText = (EditText) findViewById(R.id.edit_text);
    imageView = (ImageView) findViewById(R.id.image_view);
    button.setOnClickListener(this);
  }

  @Override
  public void onClick(View v) {
    switch(v.getId()) {
      case R.id.button:
        imageView.setImageResource(R.drawable.img_2);
        break;
      default:
        break;
    }
  }
}
```

通过setImageResource()方法来实现对ImageView的src属性，也就可以在逻辑中实现对显示的图片的变换。

### ProgressBar

ProgressBar是用于在界面上显示一个进度条，表示我们的程序正在加载一些数据。

通过setVisibility()方法可以设置旋转的进度条是否出现，一共有三个可用值，View.VISIBLE/View.INVISIBLE/View.GONE，分别是可见/不可见（但是占据屏幕空间，也就是并没有消失而是透明了）/不见（也不占据屏幕空间）

之前我们设置的进度条的样式是一个转圈的，我们也可以设置成一条直线的，只需要在progressbar的属性中，对style进行设置就好了，但是除此之外还需要设置一下android:max属性，来确定进度条的最大值。
代码如下：

```java
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

  private EditText editText;

  private ImageView imageView;

  @Override
  protected void onCreate(Bundle savedIntenceState) {
    super.onCreate(savedIntenceState);
    setContentView(R.layout.activity_main);
    Button button = (Button) findViewById(R.id.button);
    editText = (EditText) findViewById(R.id.edit_text);
    imageView = (ImageView) findViewById(R.id.image_view);
    button.setOnClickListener(this);
  }

  @Override
  public void onClick(View v) {
    switch(v.getId()) {
      case R.id.button:
        int progress = progressBar.getProgress();
        progress = progress + 10;
        progressBar.setProgress(progress);
        break;
      default:
        break;
    }
  }
}
```
当然除此之外ProgressBar还有其他几种样式可以自己去尝试一下。

### AlertDialog

AlertDialog可以在当前界面弹出一个对话框，这个对话框是置顶于所有界面元素之上的，能够屏蔽掉其他控件的交互能力，所以这个控件一般用来提示比较重要的内容或者警告信息。

代码如下：

```java
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

  private EditText editText;

  private ImageView imageView;

  @Override
  protected void onCreate(Bundle savedIntenceState) {
    super.onCreate(savedIntenceState);
    setContentView(R.layout.activity_main);
    Button button = (Button) findViewById(R.id.button);
    editText = (EditText) findViewById(R.id.edit_text);
    imageView = (ImageView) findViewById(R.id.image_view);
    button.setOnClickListener(this);
  }

  @Override
  public void onClick(View v) {
    switch(v.getId()) {
      case R.id.button:
        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
        dialog.setTitle("This is a Dialog");
        dialog.setMessage("Something important!");
        dialog.setCancelable(false);//设置可否用Back键关闭
        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {

          }
        });
        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {

          }
        });
        dialog.show();
        break;
      default:
        break;
    }
  }
}
```

dialog.setCancelable(false);是用来设置可否用Back键关闭的。


### ProgressDialog

ProgressDialog和AlertDialog有点类似，都可以在界面上弹出一个对话框，能够屏蔽掉其他控件的交互能力。不同的是，ProgressDialog会在对话框中显示一个进度条，一般用于表示当前操作比较耗时，让用户耐心等待。用法和AlertDialog类似。

注意如果在setCancelable()中设置了false那么就必须要在代码中做好控制，数据加载完成之后就要使用dismiss()方法把他关闭掉，否则ProgressDialog会一直存在。
