---
title: AndroidTraining-ForTheSecondDay
---
1. Button 属性 ：textAllCaps = "true/false"（Button的文本内容默认英文大写，可以修改此属性进行调整）

### 通过设置按钮进行页面跳转

1. 在MainActivity的布局文件中设置一个按钮；
2. 在对应的源文件中设置他的监听器对点击事件进行监听
3. 代码如下：
```java
startActivity(new Intent(this, DialogActivity.class));
```

### AlertDialog

- 1、声明一个AlertDialog对象
```java
AlertDialog dialog = new AlertDialog();
```
- 2、调用该对象的方法对所需的窗体进行设置代码如下：
```java
//这里是使用的Builder方法对其方法进行调用，优点是使代码变得更加整洁，对方法调用也更加方便
AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("This is a title")
                .setMessage("This is message")
                //两个参数，第一个是button的名字，第二个是点击事件
                //PositiveButton是指在右边的按钮，可以根据需求不同进行设置
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                //NegativeButton是指在左边的按钮，可以根据需求不同进行设置
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();
```
- 3、列表形式的窗体，代码如下：
```java
final String[] items = {"Swimming", "Basketball", "Hiking", "Learning"};
AlertDialog dialog = new AlertDialog.Builder(this)
        .setTitle("Please choose your hobby!")
        .setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String item = items[which];
                Toast.makeText(DialogActivity.this, item, Toast.LENGTH_SHORT).show();
            }
        })
        .show();
```
- 4、单选形式的窗体，代码如下：
```java
final String[] items = {"Swimming", "Basketball", "Hiking", "Learning"};
final String[] item = new String[1];
AlertDialog dialog = new AlertDialog.Builder(this)
        .setTitle("Single choice dialog:Please choose your hobby")
        .setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                item[0] = items[which];
            }
        })
        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(DialogActivity.this, item[0], Toast.LENGTH_SHORT).show();
            }
        })
        .show();
```
- 5、自定义形式的窗体
1. 在layout目录下建立对应的xml布局文件，并对其进行编辑和设置
然后进行源文件的代码编辑：
```java
//首先需要找到我们之前建立的对应的xml布局文件，即创建一个View控件进行赋值。
View view = LayoutInflater.from(this)
        .inflate(R.layout.layout_custom_dialog, null, false);
//和之前一样对窗体进行简单的初始化设置，不过最后不是show(),而是create()，所以最后当所有的设置完成之后需要调用一下show()方法，使得窗体可以正常显示出来在界面上。
final AlertDialog dialog = new AlertDialog.Builder(this)
        .setTitle("Custom Dialog")
        .setView(view)
        .create();
//创建对象，并通过findViewById ()方法将布局文件中的控件赋值给我们所创建的对象
final EditText input = view.findViewById(R.id.inputEditText);
Button ok = view.findViewById(R.id.okButton);
//此代码是实现将输入的字符串在对按钮进行点击之后进行输出，以Toast的形式显示在手机桌面上
//所以这里需要对按钮设置监听器监听其点击事件
ok.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
      //因为获取的文本内容的格式可能不唯一，所以使用toString()将其转换成字符串的形式，并赋值给一个对象然后进行调用
        String text = input.getText().toString();
        Toast.makeText(DialogActivity.this, text, Toast.LENGTH_SHORT).show();
        //使窗体在使用结束之后消失
        dialog.dismiss();
    }
});
//最后，显示窗体，不然点击按钮之后依然不会有任何效果
dialog.show();
```
### Acticity的生命活动周期

如图：
![Activity生命活动周期](https://images2015.cnblogs.com/blog/776204/201511/776204-20151130161336030-1114688010.png)
