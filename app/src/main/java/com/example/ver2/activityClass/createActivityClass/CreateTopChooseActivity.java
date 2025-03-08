package com.example.ver2.activityClass.createActivityClass;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.example.ver2.R;
import com.example.ver2.activityClass.MainActivity;

public class CreateTopChooseActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.create_top_choose);

        Button goalButton = findViewById(R.id.choose_goal);
        goalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CreateTopChooseActivity.this, CreateGoalChooseFrameworkActivity.class);
                startActivity(intent);
            }
        });

        Button backButton = findViewById(R.id.create_top_choose_backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateTopChooseActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
