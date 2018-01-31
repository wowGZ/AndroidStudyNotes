---
title: UI-编写界面的最佳实践
---
## UI-编写界面的最佳实践

### 编写精美的聊天界面

聊天界面自然少不了使用RecyclerView，所以还是先添加依赖库。

然后开始编写主界面，修改activity_main.xml中的代码，如下所示：

```xml
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="#d8e0e8"
    android:orientation="vertical"
    tools:context="io.github.wowgz.uibestpractice.MainActivity">

    <android.support.v7.widget.RecyclerView
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:layout_weight="1" />

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content">

        <EditText
            android:id="@+id/input_edit_text"
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_weight="1"
            android:hint="You Can Type Something Here"
            android:maxLines="2" />

        <Button
            android:id="@+id/send_message_button"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="Send"
            android:textAllCaps="false" />

    </LinearLayout>
</LinearLayout>
```

我们在主界面中放置了一个RecyclerView来显示聊天界面，一个EditText用于输入消息，一个Button用于发送消息。

之后定义实体类Message类，代码如下所示：

```java
public class Message {

    public static final int TYPE_RECEIVED = 0;
    public static final int TYPE_SENT = 1;

    private String content;

    private int type;

    public Message(String content, int type) {
        this.content = content;
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public int getType() {
        return type;
    }
}
```

实体类中只有两个属性，一个是content用于表示消息的内容，另一个type用于表示消息的类型，是收到的消息还是发送的消息。

下面来编写RecyclerView子项的布局，新建message_item.xml，代码如下所示：

```xml
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:padding="10dp"
    android:orientation="vertical">

    <LinearLayout
        android:id="@+id/left_linear_layout"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_gravity="left"
        android:background="@drawable/message_left">

        <TextView
            android:id="@+id/left_message_text_view"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_gravity="center"
            android:layout_margin="10dp"
            android:textColor="#fff" />
    </LinearLayout>

    <LinearLayout
        android:id="@+id/right_linear_layout"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_gravity="right"
        android:background="@drawable/message_right">

        <TextView
            android:id="@+id/right_message_text_view"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_gravity="center"
            android:layout_margin="10dp"
            android:textColor="#fff" />
    </LinearLayout>

</LinearLayout>
```

这里我们让收到的消息居左对齐，发出的消息居右对齐，并且分别使用对应的背景图片。

接下来需要创建RecyclerView的适配器类型，新建类MessageAdapter，代码如下所示：

```java
public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    private List<Message> messageList;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.message_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Message message = messageList.get(position);
        if (message.getType() == Message.TYPE_RECEIVED) {
            holder.leftLinearLayout.setVisibility(View.VISIBLE);
            holder.rightLinearLayout.setVisibility(View.GONE);
            holder.leftMessageTextView.setText(message.getContent());
        } else if (message.getType() == Message.TYPE_SENT){
            holder.rightLinearLayout.setVisibility(View.VISIBLE);
            holder.leftLinearLayout.setVisibility(View.GONE);
            holder.rightMessageTextView.setText(message.getContent());
        }
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        LinearLayout leftLinearLayout;
        LinearLayout rightLinearLayout;

        TextView leftMessageTextView;
        TextView rightMessageTextView;

        public ViewHolder(View itemView) {
            super(itemView);

            leftLinearLayout = (LinearLayout)itemView.findViewById(R.id.left_linear_layout);
            rightLinearLayout = (LinearLayout)itemView.findViewById(R.id.right_linear_layout);

            leftMessageTextView = (TextView)itemView.findViewById(R.id.left_message_text_view);
            rightMessageTextView = (TextView)itemView.findViewById(R.id.right_message_text_view);

        }
    }
}
```

方法和正常使用RecyclerView的方法一样，不过是在onBindViewHolder()方法中添加了对消息类型的判断。

最后修改MainActivity中的代码，来为RecyclerView初始化一些数据，并且给发送按钮注册点击事件监听器。

代码如下所示：

```java
public class MainActivity extends AppCompatActivity {

    private List<Message> messageList = new ArrayList<>();

    private EditText inputEditText;

    private Button sendMessageButton;

    private RecyclerView recyclerView;

    private MessageAdapter messageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initMessages();//初始化消息数据

        inputEditText = findViewById(R.id.input_edit_text);
        sendMessageButton = findViewById(R.id.send_message_button);
        recyclerView = findViewById(R.id.recycler_view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        messageAdapter = new MessageAdapter(messageList);
        recyclerView.setAdapter(messageAdapter);

        sendMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = inputEditText.getText().toString();
                if (!"".equals(content)) {
                    Message message = new Message(content, Message.TYPE_SENT);
                    messageList.add(message);

                    messageAdapter.notifyItemInserted(messageList.size() - 1);

                    recyclerView.scrollToPosition(messageList.size() - 1);

                    inputEditText.setText("");
                }
            }
        });
    }

    private void initMessages() {
        Message message1 = new Message("Hello guy!", Message.TYPE_RECEIVED);
        messageList.add(message1);
        Message message2 = new Message("Hello, who is that?", Message.TYPE_SENT);
        messageList.add(message2);
        Message message3 = new Message("This is Tom, nice to meet you!", Message.TYPE_RECEIVED);
        messageList.add(message3);
    }

}
```
