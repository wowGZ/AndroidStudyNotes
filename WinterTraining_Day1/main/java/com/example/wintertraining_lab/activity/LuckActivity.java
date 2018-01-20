package com.example.wintertraining_lab.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.wintertraining_lab.R;

import java.util.Random;

public class LuckActivity extends AppCompatActivity {

    private static final String EXTRA_LUCKY = "lucky";
    private TextView luck_number_text_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_luck);
        luck_number_text_view = findViewById(R.id.luck_number_text_view);

        int base = getIntent().getIntExtra(EXTRA_LUCKY, 0);

        Random random = new Random();
        int lucky = base + random.nextInt(101);
        luck_number_text_view.setText(lucky + "");
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("lucky", luck_number_text_view.getText().toString());
        setResult(Activity.RESULT_OK, intent);
        super.onBackPressed();
    }
    public static Intent newIntent(Context context, int lucky){
        Intent intent = new Intent(context, LuckActivity.class);
        intent.putExtra(EXTRA_LUCKY, lucky);
        return intent;
    }
}
