package com.example.ver2.activityClass.createActivityClass;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ver2.R;
import com.example.ver2.dataClass.goalManagement.Benchmarking;

public class BenchmarkingActivity extends AppCompatActivity{
    private EditText goalEditText;
    private EditText targetEditText;
    private EditText benchMarkEditText;
    private EditText compareEditText;
    private Benchmarking benchmarking;

    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.benchmarking_scroll_layout);

        goalEditText = findViewById(R.id.benchmarking_2_editText);
        targetEditText = findViewById(R.id.benchmarking_3_editText);
        benchMarkEditText = findViewById(R.id.benchmarking_4_editText);
        compareEditText = findViewById(R.id.benchmarking_5_editText);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("benchmarking")) {
            benchmarking = intent.getParcelableExtra("benchmarking"); // "benchmarking" という Key で WillCanMust オブジェクトを取得
            if (benchmarking != null) {
                // Benchmarking オブジェクトが存在する場合、EditText に値を設定
                goalEditText.setText(benchmarking.getInitialGoal());
                targetEditText.setText(benchmarking.getTarget());
                benchMarkEditText.setText(benchmarking.getBenchMark());
                compareEditText.setText(benchmarking.getComparison());
            }
        }

        Button saveButton = findViewById(R.id.benchmarking_saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String goal = goalEditText.getText().toString();
                String target = targetEditText.getText().toString();
                String benchMark = benchMarkEditText.getText().toString();
                String compare = compareEditText.getText().toString();

                if (benchmarking == null) {
                    benchmarking = new Benchmarking(goal, target, benchMark, compare);
                } else {
                    benchmarking.setInitialGoal(goal);
                    benchmarking.setTarget(target);
                    benchmarking.setBenchMark(benchMark);
                    benchmarking.setComparison(compare);
                }

                Intent intent = new Intent(BenchmarkingActivity.this, SaveGoalActivity.class);
                intent.putExtra("benchmarking", benchmarking);
                startActivity(intent);
            }
        });

        Button backButton = findViewById(R.id.benchmarking_backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BenchmarkingActivity.this, CreateGoalChooseFrameworkActivity.class);
                startActivity(intent);
            }
        });

    }
}
