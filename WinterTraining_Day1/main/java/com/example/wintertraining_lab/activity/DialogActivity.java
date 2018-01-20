package com.example.wintertraining_lab.activity;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.wintertraining_lab.R;

public class DialogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);

    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.showDialogButton:
                showDialog();
                break;
            case R.id.showListDialogButton:
                showListDialog();
                break;
            case R.id.showSingleChoiceDialog:
                showSingleChoiceDialog();
                break;
            case R.id.showCustomDialogButton:
                showCustomDialogButton();
                break;
        }
    }

    private void showCustomDialogButton() {
        View view = LayoutInflater.from(this)
                .inflate(R.layout.layout_custom_dialog, null, false);

        final AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Custom Dialog")
                .setView(view)
                .create();

        final EditText input = view.findViewById(R.id.inputEditText);
        Button ok = view.findViewById(R.id.okButton);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = input.getText().toString();
                Toast.makeText(DialogActivity.this, text, Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void showSingleChoiceDialog() {
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
    }

    private void showListDialog(){
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
    }

    private void showDialog(){
        //选择support包内的方法，支持库，兼容性好
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("This is a title")
                .setMessage("This is message")
                //两个参数，第一个是button的名字，第二个是点击事件
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();
    }
}
