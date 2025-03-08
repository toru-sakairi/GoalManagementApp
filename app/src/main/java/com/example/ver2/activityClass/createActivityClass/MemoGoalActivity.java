package com.example.ver2.activityClass.createActivityClass;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.ver2.fragmentClass.BackConfirmListener;
import com.example.ver2.R;
import com.example.ver2.dataClass.goalManagement.Memo_Goal;

public class MemoGoalActivity extends AppCompatActivity implements BackConfirmListener {

    private EditText memoEditText;
    private Memo_Goal memoGoal;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.memo_goal_1);

        memoEditText = findViewById(R.id.memo_goal_1_editText);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("memo_goal")) {
            memoGoal = intent.getParcelableExtra("memo_goal");
            if (memoGoal != null) {
                memoEditText.setText(memoGoal.getMemo());
            }
        }

        Button saveButton = findViewById(R.id.memo_goal_1_saveButton);

        //saveボタンのリスナ
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String memo = memoEditText.getText().toString();
                //Memoオブジェクトを作成(name,descriptionなどはnullで初期化)
                if (memoGoal == null) {
                    memoGoal = new Memo_Goal(memo);
                } else {
                    memoGoal.setMemo(memo);
                }
                //Intentを作成し、Memo_Goalオブジェクトをセット
                Intent intent = new Intent(MemoGoalActivity.this, SaveGoalActivity.class);
                intent.putExtra("memo_goal", memoGoal);
                //次のActivityを開始
                startActivity(intent);
            }
        });

        Button backButton = findViewById(R.id.memo_goal_1_backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MemoGoalActivity.this, CreateGoalChooseFrameworkActivity.class);
                startActivity(intent);
            }
        });
    }

    //Fragmentを表示して戻るときに警告っぽいのを出す
    @Override
    public void onPositiveButtonClicked() {
        Intent intent = new Intent(this, CreateGoalChooseFrameworkActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onNegativeButtonClicked() {
        //何もしない
    }
}
