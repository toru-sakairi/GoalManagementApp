package com.example.ver2.activityClass.confirmActivityClass;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.ver2.R;
import com.example.ver2.dataClass.goalManagement.SMART;
import com.example.ver2.fragmentClass.confirmFragments.ConfirmSMARTEditFragment;
import com.example.ver2.fragmentClass.viewModels.ConfirmSMARTViewModel;

public class ConfirmSMARTActivity extends AppCompatActivity {
    private TextView specificTextView;
    private TextView measurableTextView;
    private TextView achiveableTextView;
    private TextView relevantTextView;
    private TextView timeBoundTextView;
    private TextView goalTextView;

    private SMART smart;

    ConfirmSMARTViewModel confirmSMARTViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirm_smart_scroll_layout);

        specificTextView = findViewById(R.id.confirm_smart_textView_Specific);
        measurableTextView = findViewById(R.id.confirm_smart_textView_Measurable);
        achiveableTextView = findViewById(R.id.confirm_smart_textView_Achievable);
        relevantTextView = findViewById(R.id.confirm_smart_textView_relevant);
        timeBoundTextView = findViewById(R.id.confirm_smart_textView_timeBound);
        goalTextView = findViewById(R.id.confirm_smart_textView_goal);

        Intent intent = getIntent();
        if(intent != null && intent.hasExtra("smart")){
            smart = intent.getParcelableExtra("smart");
            if(smart != null){
                specificTextView.setText(smart.getSpecific());
                measurableTextView.setText(smart.getMeasurable());
                achiveableTextView.setText(smart.getAchievable());
                relevantTextView.setText(smart.getRelevant());
                timeBoundTextView.setText(smart.getTimeBound());
                goalTextView.setText(smart.getSmartGoal());
            }
        }

        Button editButton = findViewById(R.id.confirm_smart_Edit_button);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmSMARTViewModel = new ViewModelProvider(ConfirmSMARTActivity.this).get(ConfirmSMARTViewModel.class);
                confirmSMARTViewModel.setActivity(ConfirmSMARTActivity.this);

                Bundle bundle = new Bundle();
                bundle.putParcelable("smart", smart);

                ConfirmSMARTEditFragment fragment = new ConfirmSMARTEditFragment();
                fragment.setArguments(bundle);
                fragment.show(getSupportFragmentManager(), "ConfirmSMARTEditFragment");
            }
        });

        Button nextButton = findViewById(R.id.confirm_smart_nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ConfirmSMARTActivity.this, ConfirmGoalActivity.class);
                intent.putExtra("smart",smart);
                startActivity(intent);
            }
        });

        Button backButton = findViewById(R.id.confirm_smart_backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ConfirmSMARTActivity.this, ConfirmGoalListActivity.class);
                startActivity(intent);
            }
        });
    }

    public void updateTextView(SMART smart){
        this.smart = smart;
        if(smart != null){
            specificTextView.setText(smart.getSpecific());
            measurableTextView.setText(smart.getMeasurable());
            achiveableTextView.setText(smart.getAchievable());
            relevantTextView.setText(smart.getRelevant());
            timeBoundTextView.setText(smart.getTimeBound());
            goalTextView.setText(smart.getSmartGoal());
        }
    }
}
