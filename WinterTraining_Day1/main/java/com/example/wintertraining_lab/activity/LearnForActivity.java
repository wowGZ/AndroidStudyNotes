package com.example.wintertraining_lab.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.wintertraining_lab.R;

import java.util.Random;

public class LearnForActivity extends AppCompatActivity {
    //声明周期的方法一般都是protected修饰的
//logt
    private static final String TAG = "LearnForActivity";
    
    private Button testLuckButton;
    private TextView luckNumberTextView;
    private final int REQUEST_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn_for);

        Log.i(TAG, "onCreate: is called");

        testLuckButton = findViewById(R.id.test_luck_button);
        luckNumberTextView = findViewById(R.id.luck_number_text_view);

        testLuckButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random random = new Random();
                int lucky = random.nextInt(101);
                Intent intent = LuckActivity.newIntent(LearnForActivity.this, lucky);
//                Intent intent = new Intent(LearnForActivity.this, LuckActivity.class);
//                intent.putExtra("lucky", lucky);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart: is called");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.i(TAG, "onRestoreInstanceState: is called");
        luckNumberTextView.setText(savedInstanceState.getString(EXTRA_SAVE));
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: is called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause: is called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop: is called");
    }

    private final String EXTRA_SAVE = "save lucky";
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "onSaveInstanceState: is called");
        String lucky = luckNumberTextView.getText().toString();
        outState.putString(EXTRA_SAVE, lucky);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: is called");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart: is called");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != Activity.RESULT_OK)
            return;
        if (requestCode == REQUEST_CODE){
            String lucky = data.getStringExtra("lucky");
            luckNumberTextView.setText(lucky);
        }
    }
}
