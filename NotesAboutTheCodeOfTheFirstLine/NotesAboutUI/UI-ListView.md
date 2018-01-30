---
title: UI-ListView
---
## UI-ListView

ListView可以说是Android中最常用的控件了，几乎所有的应用程序都会用到它。

ListView允许用户通过手指上下华东的方式将屏幕外的数据滚动到屏幕内，同时屏幕上原有的数据则会滚出屏幕。

### ListView的简单用法

创建项目来进行演示，首先在activity_main.xml修改代码如下所示：

```xml
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context="io.github.wowgz.listviewtest.MainActivity">

    <ListView
        android:id="@+id/list_view"
        android:layout_width="match_parent"
        android:layout_height="match_parent"/>

</LinearLayout>
```

在布局中接入ListView控件，并为其指定一个id。

然后在MainActivity中修改代码如下所示：

```java
public class MainActivity extends AppCompatActivity {

    private String[] data = {"Apple", "Banana", "Orange", "Pear", "Grape", "Pineapple", "Strawberry", "Cherry", "Mango"
    ,"Apple", "Banana", "Orange", "Pear", "Grape", "Pineapple", "Strawberry", "Cherry", "Mango"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                MainActivity.this,android.R.layout.simple_list_item_1,data);
        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(adapter);
    }
}
```

既然ListView是用来展示大量的数据的，那么就应该现将数据提供好。

但是数组中的数据是无法传递给ListView的，我很还需要借助 *适配器* 来完成，Android中提供了很多的适配器的实现类，其中的ArrayAdapter相对更好一些。它可以通过泛型来指定要适配的数据类型，然后在构造函数中把要适配的数据传入。

这里我们使用了一个 *android.R.layout.simple_list_item_1* 作为List的子项布局的id，这个是一个安卓内置的布局文件，里面只有一个TextView，可以用于简单的显示文本，这样适配器对象就构建好了。

最后，只需要调用ListView对象的setAdapter()方法将构建好的适配器对象传递进去就好了，这样ListView和适配器之间的关联就建立完成了。

### 定制ListView界面

这样的界面是在是太单调了，我们可以根据自己的需要来定制ListView界面。

定义一个实体类，作为ListView的适配器的适配类型。

```java
public class Fruit {

    private String name;
    private int imageId;

    public Fruit(String name, int imageId) {
        this.name = name;
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public int getImageId() {
        return imageId;
    }
}
```

这个类中只需要有两个属性，一个名字，一个图片id就好了。
然后，我们需要为ListView的子项指定一个自定义的子布局，在layout目录下新建文件fruit_item布局，代码如下所示：

```xml
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android" android:layout_width="match_parent"
    android:layout_height="match_parent">

    <ImageView
        android:id="@+id/fruit_image_view"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content" />

    <TextView
        android:id="@+id/fruit_name_text_view"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="center_vertical"
        android:layout_marginLeft="10dp"/>
</LinearLayout>
```

接下来需要创建一个自定义的适配器，适配器继承自ArrayAdapter，然后指定它的泛型。新建FruitAdapter类，代码如下：

```java
public class FruitAdapter extends ArrayAdapter<Fruit>{

    private int resourceId;

    public FruitAdapter(@NonNull Context context,int textViewResourceId, @NonNull List<Fruit> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Fruit fruit = getItem(position);//获取当前项的Fruit实例
        View view = LayoutInflater.from(getContext()).inflate(resourceId, parent,false);
        ImageView fruitImage = (ImageView) view.findViewById(R.id.fruit_image_view);
        TextView fruitname = (TextView) view.findViewById(R.id.fruit_name_text_view);
        fruitImage.setImageResource(fruit.getImageId());
        fruitname.setText(fruit.getName());
        return super.getView(position, convertView, parent);
    }
}
```

FruitAdapter重写了父类的一组构造函数，用于将上下文、ListView子项布局的id和数据都传递进来。
另外重写了getView()方法，这个方法在每个子项被滚动到屏幕内的时候都会被调用。
在这个方法中，首先通过getId()方法得到当前Fruit实例，然后使用LayoutInflater来为这个子项加载我们传入的布局。
这里LayoutInflater的inflate()方法接受了三个参数，前两个我们之前都说过，这第三个参数指定为false，表示只有让我们在父布局中声明的layout属性生效，但是不会为这个View添加父布局，因为View一旦有了父布局就不能在添加到ListView中了。

以上的写法是ListView的标准写法。不理解可以先记住。

之后调用View的findViewByI的()方法分别获取对应控件的实例，然后调用让他们方法为他们设置对应的属性。

然后在MainActivity中修改代码如下：

```java
public class MainActivity extends AppCompatActivity {

    private List<Fruit> fruitList = new ArrayList<>();

//    private String[] data = {"Apple", "Banana", "Orange", "Pear", "Grape", "Pineapple", "Strawberry", "Cherry", "Mango"
//    ,"Apple", "Banana", "Orange", "Pear", "Grape", "Pineapple", "Strawberry", "Cherry", "Mango"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initFruits();
        FruitAdapter adapter = new FruitAdapter(
                MainActivity.this, R.layout.fruit_item, fruitList);
        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(adapter);
    }

    private void initFruits() {
        for (int i = 0; i < 2; i++) {
            Fruit apple = new Fruit("Apple", R.drawable.ic_launcher_background);
            fruitList.add(apple);
            Fruit Banana = new Fruit("Banana", R.drawable.ic_launcher_background);
            fruitList.add(Banana);
            Fruit Orange = new Fruit("Orange", R.drawable.ic_launcher_background);
            fruitList.add(Orange);
            Fruit Pear = new Fruit("Pear", R.drawable.ic_launcher_background);
            fruitList.add(Pear);
            Fruit Grape = new Fruit("Grape", R.drawable.ic_launcher_background);
            fruitList.add(Grape);
            Fruit Pineapple = new Fruit("Pineapple", R.drawable.ic_launcher_background);
            fruitList.add(Pineapple);
            Fruit Strawberry = new Fruit("Strawberry", R.drawable.ic_launcher_background);
            fruitList.add(Strawberry);
            Fruit Cherry = new Fruit("Cherry", R.drawable.ic_launcher_background);
            fruitList.add(Cherry);
            Fruit Mango = new Fruit("Mango", R.drawable.ic_launcher_background);
            fruitList.add(Mango);
        }
    }
}
```

这里定义了一个initFruit()方法，用来初始化数据。

这就是简单的使用自定义ListView的方法，如果需要不同的界面，可以在子项布局中进行界面的设计，就可以实现更加复杂的界面了。

### 提高ListView的运行效率

ListView中有很多细节可以优化，所以这个控件比较难用。

在FruitAdapter中的getView()方法中，每次都要将布局重新加载一遍，这样的话，当ListView快速滚动的时候就会成为提高性能的瓶颈。

仔细观察getView()方法中还有一个参数叫做convertView参数，这个参数用于将之前加载好的布局进行缓存，一遍之后可以进行重用。修改FruitAdapter中的代码如下：
```java
@NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Fruit fruit = getItem(position);//获取当前项的Fruit实例
        View view;
        if (convertView == null){
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
        } else {
            view = convertView;
        }
        ImageView fruitImage = (ImageView) view.findViewById(R.id.fruit_image_view);
        TextView fruitname = (TextView) view.findViewById(R.id.fruit_name_text_view);
        fruitImage.setImageResource(fruit.getImageId());
        fruitname.setText(fruit.getName());
        return view;
    }
```

我们在getView()方法中进行判断，如果缓存的布局为空，那么就加载一个布局，如果不为空，那么就调用之前缓存的布局。

不过这部分代码我们还是可以继续优化的，虽然现在已经不会重复的去加载布局，但是每次在getView()方法中还是会调用findViewById()方法来获取控件的实例。

这次我们可以借助一个ViewHolder来对这部分性能进行优化。修改FruitAdapter中的代码：

```java
public class FruitAdapter extends ArrayAdapter<Fruit>{

    private int resourceId;

    public FruitAdapter(@NonNull Context context, int textViewResourceId, @NonNull List<Fruit> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Fruit fruit = getItem(position);//获取当前项的Fruit实例
        View view;
        ViewHolder viewHolder = new ViewHolder();
        if (convertView == null){
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.fruitImage = (ImageView) view.findViewById(R.id.fruit_image_view);
            viewHolder.fruitName = (TextView) view.findViewById(R.id.fruit_name_text_view);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
//        注释掉的代码是修改之前的部分。
//        ImageView fruitImage = (ImageView) view.findViewById(R.id.fruit_image_view);
//        TextView fruitname = (TextView) view.findViewById(R.id.fruit_name_text_view);
//        fruitImage.setImageResource(fruit.getImageId());
//        fruitname.setText(fruit.getName());
        viewHolder.fruitImage.setImageResource(fruit.getImageId());
        viewHolder.fruitName.setText(fruit.getName());
        return view;
    }

    class ViewHolder {
        ImageView fruitImage;
        TextView fruitName;
    }
}
```

新增了一个内部类ViewHolder类，用于对控件的实例进行缓存。当之前缓存的布局为空的时候，就创建一个ViewHolder并且将实例存放在ViewHolder里面，然后将ViewHolder通过View的setTAG()方法来缓存在View里面，之后在需要调用的时候，再使用getTAG()方法，将ViewHolder取出就好了。

### ListView的点击事件

修改MainActivity中的代码：

```java
public class MainActivity extends AppCompatActivity {

    private List<Fruit> fruitList = new ArrayList<>();

//    private String[] data = {"Apple", "Banana", "Orange", "Pear", "Grape", "Pineapple", "Strawberry", "Cherry", "Mango"
//    ,"Apple", "Banana", "Orange", "Pear", "Grape", "Pineapple", "Strawberry", "Cherry", "Mango"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initFruits();
        FruitAdapter adapter = new FruitAdapter(
                MainActivity.this, R.layout.fruit_item, fruitList);
        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Fruit fruit = fruitList.get(position);
                Toast.makeText(MainActivity.this, fruit.getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initFruits() {
        for (int i = 0; i < 2; i++) {
            Fruit apple = new Fruit("Apple", R.drawable.ic_launcher_background);
            fruitList.add(apple);
            Fruit Banana = new Fruit("Banana", R.drawable.ic_launcher_background);
            fruitList.add(Banana);
            Fruit Orange = new Fruit("Orange", R.drawable.ic_launcher_background);
            fruitList.add(Orange);
            Fruit Pear = new Fruit("Pear", R.drawable.ic_launcher_background);
            fruitList.add(Pear);
            Fruit Grape = new Fruit("Grape", R.drawable.ic_launcher_background);
            fruitList.add(Grape);
            Fruit Pineapple = new Fruit("Pineapple", R.drawable.ic_launcher_background);
            fruitList.add(Pineapple);
            Fruit Strawberry = new Fruit("Strawberry", R.drawable.ic_launcher_background);
            fruitList.add(Strawberry);
            Fruit Cherry = new Fruit("Cherry", R.drawable.ic_launcher_background);
            fruitList.add(Cherry);
            Fruit Mango = new Fruit("Mango", R.drawable.ic_launcher_background);
            fruitList.add(Mango);
        }
    }
}
```
