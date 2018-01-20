package com.example.wintertraining_lab.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.wintertraining_lab.R;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.dialog_acticity:
                startActivity(new Intent(this, DialogActivity.class));
                break;
            case R.id.acticity_learning:
                startActivity(new Intent(this, LearnForActivity.class));
                break;
        }
    }
}
