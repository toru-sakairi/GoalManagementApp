package com.example.ver2.activityClass.confirmActivityClass;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ver2.R;
import com.example.ver2.activityClass.MainActivity;

public class ConfirmTopChooseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirm_top_choose);

        Button chooseGoalButton = findViewById(R.id.confirm_choose_goalButton);
        chooseGoalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ConfirmTopChooseActivity.this, ConfirmGoalListActivity.class);
                startActivity(intent);
            }
        });

        Button backButton = findViewById(R.id.confirm_top_choose_backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ConfirmTopChooseActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
