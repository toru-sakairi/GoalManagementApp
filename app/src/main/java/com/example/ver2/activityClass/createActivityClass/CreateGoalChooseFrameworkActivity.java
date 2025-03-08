package com.example.ver2.activityClass.createActivityClass;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ver2.R;

public class CreateGoalChooseFrameworkActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.create_goal_choose_framework);

        Button memoButton =  findViewById(R.id.Memo_Goal_Button);
        memoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateGoalChooseFrameworkActivity.this, MemoGoalActivity.class);
                startActivity(intent);
            }
        });
        Button smartButton =  findViewById(R.id.SMART_Button);
        smartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateGoalChooseFrameworkActivity.this, SMARTActivity.class);
                startActivity(intent);
            }
        });
        Button wcmButton =  findViewById(R.id.WillCanMust_Button);
        wcmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateGoalChooseFrameworkActivity.this, WillCanMustActivity.class);
                startActivity(intent);
            }
        });
        Button benchMarkingButton =  findViewById(R.id.Benchmarking_Button);
        benchMarkingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateGoalChooseFrameworkActivity.this, BenchmarkingActivity.class);
                startActivity(intent);
            }
        });

        Button backButton = findViewById(R.id.create_goal_choose_frameWork_backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CreateGoalChooseFrameworkActivity.this, CreateTopChooseActivity.class);
                startActivity(intent);
            }
        });
    }
}
