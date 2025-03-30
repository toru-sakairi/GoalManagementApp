/*
    アプリケーションのホーム画面
 */

package com.example.ver2.activityClass;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ver2.R;
import com.example.ver2.activityClass.confirmActivityClass.ConfirmTopChooseActivity;
import com.example.ver2.activityClass.createActivityClass.CreateTopChooseActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_top);

        //目的、目標を新しく作る
        Button createButton = findViewById(R.id.top_create_Button);
        createButton.setOnClickListener(view -> {
                Intent intent = new Intent(MainActivity.this, CreateTopChooseActivity.class);
                startActivity(intent);
        });

        //目的、目標を確認する
        Button confirmButton = findViewById(R.id.top_confirm_Button);
        confirmButton.setOnClickListener(view -> {
                Intent intent = new Intent(MainActivity.this, ConfirmTopChooseActivity.class);
                startActivity(intent);
        });
    }
}