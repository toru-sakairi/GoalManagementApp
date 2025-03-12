package com.example.ver2.activityClass.createActivityClass;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ver2.R;
import com.example.ver2.dataClass.goalManagement.WillCanMust;

public class WillCanMustActivity extends AppCompatActivity {
    private EditText willEditText;
    private EditText canEditText;
    private EditText mustEditText;
    private EditText goalEditText;
    private WillCanMust willCanMust;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.willcanmust_scroll_layout);

        willEditText = findViewById(R.id.WillCanMust_editText_Will);
        canEditText = findViewById(R.id.WillCanMust_editText_Can);
        mustEditText = findViewById(R.id.WillCanMust_editText_Must);
        goalEditText = findViewById(R.id.WillCanMust_editText_Goal);
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("willCanMust")) {
            willCanMust = intent.getParcelableExtra("willCanMust"); // "WillCanMust" という Key で WillCanMust オブジェクトを取得
            if (willCanMust != null) {
                // WillCanMust オブジェクトが存在する場合、EditText に値を設定
                willEditText.setText(willCanMust.getWill());
                canEditText.setText(willCanMust.getCan());
                mustEditText.setText(willCanMust.getMust());
                goalEditText.setText(willCanMust.getWcmGoal());
            }
        }

        Button saveButton = findViewById(R.id.willCanMust_saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String will = willEditText.getText().toString();
                String can = canEditText.getText().toString();
                String must = mustEditText.getText().toString();
                String goal = goalEditText.getText().toString();

                if (willCanMust == null) {
                    willCanMust = new WillCanMust(will, can, must, goal);
                } else {
                    willCanMust.setWill(will);
                    willCanMust.setCan(can);
                    willCanMust.setMust(must);
                    willCanMust.setWcmGoal(goal);
                }

                Intent intent = new Intent(WillCanMustActivity.this, SaveGoalActivity.class);
                intent.putExtra("willCanMust", willCanMust);
                startActivity(intent);
            }
        });

        Button backButton = findViewById(R.id.willCanMust_backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WillCanMustActivity.this, CreateGoalChooseFrameworkActivity.class);
                startActivity(intent);
            }
        });
    }
}
