package com.example.ver2.activityClass.confirmActivityClass;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.ver2.R;
import com.example.ver2.dataClass.goalManagement.Memo_Goal;
import com.example.ver2.fragmentClass.confirmFragments.ConfirmMemoGoalEditFragment;
import com.example.ver2.fragmentClass.viewModels.ConfirmMemoGoalViewModel;

public class ConfirmMemoGoalActivity extends AppCompatActivity {
    private TextView memoGoalTextView;

    private Memo_Goal memo;

    ConfirmMemoGoalViewModel confirmMemoGoalViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirm_memo_goal_layout);

        memoGoalTextView = findViewById(R.id.confirm_memo_goal_textView);

        confirmMemoGoalViewModel = new ViewModelProvider(this).get(ConfirmMemoGoalViewModel.class);
        confirmMemoGoalViewModel.setActivity(this);

        Intent intent = getIntent();
        if(intent != null && intent.hasExtra("memo_goal")){
            memo = intent.getParcelableExtra("memo_goal");
            if(memo != null){
                memoGoalTextView.setText(memo.getMemo());
            }
        }

        Button nextButton = findViewById(R.id.confirm_memo_goal_nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ConfirmMemoGoalActivity.this, ConfirmGoalActivity.class);
                intent.putExtra("memo_goal",memo);
                startActivity(intent);
            }
        });

        Button backButton = findViewById(R.id.confirm_memo_goal_backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ConfirmMemoGoalActivity.this, ConfirmGoalListActivity.class);
                startActivity(intent);
            }
        });

        Button editButton = findViewById(R.id.confirm_memo_goal_Edit_button);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putParcelable("memo_goal",memo);

                ConfirmMemoGoalEditFragment fragment = new ConfirmMemoGoalEditFragment();
                fragment.setArguments(bundle);
                fragment.show(getSupportFragmentManager(), "ConfirmMemoGoalEditFragment");

            }
        });
    }

    public void updateTextView(Memo_Goal memo){
        this.memo = memo;

        memoGoalTextView.setText(memo.getMemo());
    }
}
