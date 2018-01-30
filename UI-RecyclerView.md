---
title: UI-RecyclerView
---
## UI-RecycleView

### RecycleView的基本用法

RecycleView也属于新增的控件，所以想要使用这个控件一样需要在build.gradle文件中添加响应的依赖库才能正常使用。

同样在dependencies闭包中添加如下代码：
```java
dependencies {
  compile 'com.android.support:recycleview-v7:24.2.1'
}
```

添加完成之后要记得点击Sync Now来进行同步。

修改activity_main.xml文件的代码如下：
```xml
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context="io.github.wowgz.recycleviewtest.MainActivity">

    <android.support.v7.widget.RecyclerView
        android:layout_width="match_parent"
        android:layout_height="match_parent"/>

</LinearLayout>
```

加入RecyclerView控件也是很简单的，先为它指定一个ID，然后将高度和宽度都制定为match_parent这样就会占满整个屏幕。
为了方便起见，我们可以把之前所写的Fruit类和fruit_item.xml文件直接复制过来这样就省的重新写一遍了。

接下来需要为RecyclerView准备一个适配器，代码如下所示：
```java
public class FruitAdapter extends RecyclerView.Adapter<FruitAdapter.ViewHolder> {

    private List<Fruit> mFruitList;

    public FruitAdapter(List<Fruit> mFruitList) {
        this.mFruitList = mFruitList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fruit_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Fruit fruit = mFruitList.get(position);
        holder.fruitImage.setImageResource(R.drawable.ic_launcher_background);
        holder.fruitName.setText(fruit.getName());
    }

    @Override
    public int getItemCount() {
        return mFruitList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView fruitImage;
        TextView fruitName;

        public ViewHolder(View itemView) {
            super(itemView);
            fruitImage = (ImageView) itemView.findViewById(R.id.fruit_image_view);
            fruitName = (TextView) itemView.findViewById(R.id.fruit_name_text_view);
        }
    }
}
```

1. 这里我们首先定义了一个内部类ViewHolder，ViewHolder要继承自RecyclerView.ViewHolder。
2. 然后，ViewHolder的构造函数中要传入一个View的参数，这个参数通常是RecyclerView子项的最外层的布局，那么我们就可以通过findViewById()方法来找到对应布局中的控件的实例。
3. 再看FruitAdapter中也有一个构造函数，这个方法用于把要展示的数据源传进来，并且复制给一个全局变量，之后的后续操作就都在这个数据源的基础上进行。
4. 继续往下看，由于FruitAdapter是继承自RecyclerView.Adapter的，那么久必须重载 **onCreateViewHolder()、onBindViewHolder()、getItemCount()** 这三个方法。
5. onCreateViewHolder()方法适用于创建ViewHolder实例的，我们在这个方法中将fruit_item.xml布局加载进来，然后创建一个ViewHolder实例，并把加载进来的布局传入到构造函数中，最后将ViewHolder的实例返回。
6. onBindViewHolder()方法适用于对RecyclerView子项的数据进行赋值的， *会在每个子项被滚动到屏幕内的时候执行* ，这里我们通过position参数得到当前项的Fruit实力i，然后再将数据设置到ViewHolder的对应的控件当中即可。
7. getItemCount()方法用于获取RecyclerView有多少子项，所以这个函数直接返回数据源的长度即可。

适配器准备好了，我们就可以使用RecyclerView了，修改MainActivity中的代码如下所示：

```java
public class MainActivity extends AppCompatActivity {

    private List<Fruit> fruitList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initFruits();//初始化传入的数据
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        FruitAdapter adapter = new FruitAdapter(fruitList);
        recyclerView.setAdapter(adapter);
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

这里和ListView类似的initFruits()方法是用来初始化要传入的数据的。
接着我们要在onCreate()方法中获取一个RecyclerView的实例，然后创建一个LinearLayoutManager对象，并将它设置到RecyclerView当中。LayoutManager是用于指定RecyclerView的布局方式，这里使用LinearLayoutManager是线性布局的意思，可以实现和ListView的相类似的效果。
接下来我们创建了FruitAdapter的实例，并将水果数据传入到FruitAdapter的构造函数当中，最后调用RecyclerView的setAdapter()方法来完成适配器的配置，这样RecyclerView和数据之间的关联就建立完成了。

### 实现横向滚动和瀑布流布局

我们已经知道ListView的扩展性不好，只能实现纵向滚动的效果，而RecyclerView可以实现横向滚动。

首先对fruit_item.xml文件的代码进行修改，代码如下所示：

```xml
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="100dp"
    android:layout_height="wrap_content"
    android:orientation="vertical">

    <ImageView
        android:id="@+id/fruit_image_view"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="center_horizontal" />

    <TextView

        android:id="@+id/fruit_name_text_view"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="center_horizontal"
        android:layout_marginLeft="10dp" />

</LinearLayout>
```

可以看到我们把LinearLayout改为垂直方向排列的了，并且把宽度设置为了100dp。这里设置为固定值是因为每个水果的名字长度不同，如果是wrap_content的话，就会有长有短很不美观，而如果用match_parent的话，就会导致宽度过长，一个子项占满一个屏幕。

然后我们将控件设置成了在布局中水平居中，并且使用layout_marginTop属性来将文本和图片分割开一定的距离。

接下来修改MainActivity中的代码，如下所示：

```java
@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initFruits();//初始化传入的数据
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);//比之前添加了这条语句
        recyclerView.setLayoutManager(layoutManager);

        FruitAdapter adapter = new FruitAdapter(fruitList);
        recyclerView.setAdapter(adapter);


    }
```

因为默认是纵向排列的，所以我们设置一下方向，改为LinearLayoutManager.HORIZONTAL就可以实现横向的滚动了。

**RecyclerView的布局排列交给了LayoutManager，在LayoutManager中定义了一套可扩展的布局排列接口，子类只需要按照接口的规范来实现，就可以定制出各种不同的排列方式的布局了**

除了我们用到的LinearLayoutManager之外，RecyclerView还为我们提供了GirdLayoutManager和StaggeredGirdLayout这两种内置的布局排列方式。

GirdLayoutManager实现的是网格布局，StaggeredGirdLayout实现的是瀑布流布局。

来尝试一下瀑布流布局，首先来修改一下fruit_item.xml布局中的文件，代码如下：

```xml
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_margin="5dp"
    android:layout_height="wrap_content"
    android:orientation="vertical">

    <ImageView
        android:id="@+id/fruit_image_view"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="center_horizontal" />

    <TextView
        android:id="@+id/fruit_name_text_view"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="left"
        android:layout_marginTop="10dp" />

</LinearLayout>
```

调整了几个小地方，首先是将线性布局的宽度有100dp改为了match_parent，因为 **瀑布流布局的宽度应该是根据布局的列数来自动适配的，而不是一个固定值。** 添加了layout_margin属性，让各个子项之间互留一点间距。还有就是将TextView的对其属性改为了居左对齐，因为待会我们会将文字的长度变长，如果还是居中对齐的话，会看着怪怪的。

现在修改MainActivity中的代码，如下所示：

```java
public class MainActivity extends AppCompatActivity {

    private List<Fruit> fruitList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initFruits();//初始化传入的数据
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        StaggeredGridLayoutManager layoutManager = new
                StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        FruitAdapter adapter = new FruitAdapter(fruitList);
        recyclerView.setAdapter(adapter);


    }

    private void initFruits() {
        for (int i = 0; i < 2; i++) {
            Fruit apple = new
                    Fruit(getRandomLengthName("Apple"), R.drawable.ic_launcher_background);
            fruitList.add(apple);
            Fruit Banana = new
                    Fruit(getRandomLengthName("Banana"), R.drawable.ic_launcher_background);
            fruitList.add(Banana);
            Fruit Orange = new
                    Fruit(getRandomLengthName("Orange"), R.drawable.ic_launcher_background);
            fruitList.add(Orange);
            Fruit Pear = new
                    Fruit(getRandomLengthName("Pear"), R.drawable.ic_launcher_background);
            fruitList.add(Pear);
            Fruit Grape = new
                    Fruit(getRandomLengthName("Grape"), R.drawable.ic_launcher_background);
            fruitList.add(Grape);
            Fruit Pineapple = new
                    Fruit(getRandomLengthName("Pineapple"), R.drawable.ic_launcher_background);
            fruitList.add(Pineapple);
            Fruit Strawberry = new
                    Fruit(getRandomLengthName("Strawberry"), R.drawable.ic_launcher_background);
            fruitList.add(Strawberry);
            Fruit Cherry = new
                    Fruit(getRandomLengthName("Cherry"), R.drawable.ic_launcher_background);
            fruitList.add(Cherry);
            Fruit Mango = new
                    Fruit(getRandomLengthName("Mango"), R.drawable.ic_launcher_background);
            fruitList.add(Mango);
        }
    }

    private String getRandomLengthName(String name) {
        Random random = new Random();
        int length = random.nextInt(20) + 1;
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            builder.append(name);
        }
        return builder.toString();
    }

}
```

首先是在onCreate()方法中，我们创建了一个StaggeredGridLayoutManager的实例。它的构造函数接受两个参数，第一个参数用于指定布局的列数，第二个参数用于指定布局的排列方向。

### 设置RecyclerView的点击事件

RecyclerView也是必须要响应点击事件才可以。

ListView只能设置点击某一个子项所响应的事件，但是如果想要实现点击子项中的某一控件就不能实现了。而RecyclerView可以实现，所以RecyclerView直接摒弃了子项点击事件的监听器，所有的点击事件都由具体的view去注册。

RecyclerView中注册点击事件，首先修改FruitAdapter中的代码，如下所示：

```java
public class FruitAdapter extends RecyclerView.Adapter<FruitAdapter.ViewHolder> {

    private List<Fruit> mFruitList;

    public FruitAdapter(List<Fruit> mFruitList) {
        this.mFruitList = mFruitList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fruit_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);//此处为修改的地方
        holder.fruitView.setOnClickListener(new View.OnClickListener() {//此处为修改的地方
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Fruit fruit = mFruitList.get(position);
                Toast.makeText(v.getContext(), "you clicked image" + fruit.getName(),
                        Toast.LENGTH_SHORT).show();
            }
        });
        holder.fruitImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Fruit fruit = mFruitList.get(position);
                Toast.makeText(v.getContext(), "you clicked image" + fruit.getName(),
                        Toast.LENGTH_SHORT).show();
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Fruit fruit = mFruitList.get(position);
        holder.fruitImage.setImageResource(R.drawable.ic_launcher_background);
        holder.fruitName.setText(fruit.getName());
    }

    @Override
    public int getItemCount() {
        return mFruitList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        View fruitView;//此处为修改的地方
        ImageView fruitImage;
        TextView fruitName;

        public ViewHolder(View itemView) {
            super(itemView);
            fruitView = itemView;//此处为修改的地方
            fruitImage = (ImageView) itemView.findViewById(R.id.fruit_image_view);
            fruitName = (TextView) itemView.findViewById(R.id.fruit_name_text_view);
        }
    }
}
```

我们首先是修改了ViewHolder，在其中添加了一个view变量来保存子项最外层布局的实例，然后为这个布局进行设置注册点击事件的监听器就可以了。RecyclerView的强大之处就在于它可以轻松的为子项布局中的任意控件或者布局注册点击事件的监听器。

我们在两个点击事件中先是获取了用户点击的子项的position，然后通过position来获取子项的实例，最后用Toast来分别弹出两种不同的弹出内容。
