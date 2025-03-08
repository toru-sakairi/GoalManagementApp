package com.example.ver2.activityClass.confirmActivityClass;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import com.example.ver2.R;
import com.example.ver2.dataClass.GoalSaveViewModel;
import com.example.ver2.dataClass.goalManagement.Benchmarking;
import com.example.ver2.dataClass.goalManagement.Memo_Goal;
import com.example.ver2.fragmentClass.confirmFragments.ConfirmBenchmarkingEditFragment;
import com.example.ver2.fragmentClass.viewModels.ConfirmBenchmarkingViewModel;

public class ConfirmBenchmarkingActivity extends AppCompatActivity {
    private TextView goalTextView;
    private TextView targetTextView;
    private TextView benchMarkTextView;
    private TextView compareTextView;
    private Benchmarking benchmarking;

    //EditFragmentで変更した際に再ロードするために使う 再ロードするやつは取りやめ
    //private GoalSaveViewModel goalSaveViewModel;


    //EditFragmentからメソッドを呼び出されるために使用
    private ConfirmBenchmarkingViewModel confirmBenchmarkingViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirm_benchmarking_scroll_layout);

        //goalSaveViewModel = new ViewModelProvider(ConfirmBenchmarkingActivity.this).get(GoalSaveViewModel.class);

        goalTextView = findViewById(R.id.confirm_benchmarking_GoalTextView);
        targetTextView = findViewById(R.id.confirm_benchmarking_TargetTextView);
        benchMarkTextView = findViewById(R.id.confirm_benchmarking_BenchmarkTextView);
        compareTextView = findViewById(R.id.confirm_benchmarking_ComparisonTextView);

        Intent intent = getIntent();
        //デバッグ用
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Log.d("Intent Debug", "Extras: " + extras.keySet());
        }

        if (intent != null && intent.hasExtra("benchmarking")) {
            benchmarking = intent.getParcelableExtra("benchmarking"); // "benchmarking" という Key で benchmarkingオブジェクトを取得（データベースから（おそらく前のActivityで取得））
            if (benchmarking != null) {
                goalTextView.setText(benchmarking.getInitialGoal());
                targetTextView.setText(benchmarking.getTarget());
                benchMarkTextView.setText(benchmarking.getBenchMark());
                compareTextView.setText(benchmarking.getComparison());

//                Log.d("goalName is Existing", benchmarking.getName());
//                if (benchmarking.isGoalExist())
//                    Log.d("benchmarking is having goal", "having goal");
//                else
//                    Log.d("benchmarking is not having goal", "not having goal");
            }
        }

        Button editButton = findViewById(R.id.confirm_benchmarking_Edit_button);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmBenchmarkingViewModel = new ViewModelProvider(ConfirmBenchmarkingActivity.this).get(ConfirmBenchmarkingViewModel.class);
                confirmBenchmarkingViewModel.setActivity(ConfirmBenchmarkingActivity.this);

                Bundle bundle = new Bundle();
                bundle.putParcelable("benchmarking", benchmarking);

                ConfirmBenchmarkingEditFragment fragment = new ConfirmBenchmarkingEditFragment();
                fragment.setArguments(bundle);
                fragment.show(getSupportFragmentManager(), "ConfirmBenchmarkingEditFragment");


            }
        });

        Button nextButton = findViewById(R.id.confirm_benchmarking_nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ConfirmBenchmarkingActivity.this, ConfirmGoalActivity.class);
                intent.putExtra("benchmarking", benchmarking);
                startActivity(intent);
            }
        });

        Button backButton = findViewById(R.id.confirm_benchmarking_backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ConfirmBenchmarkingActivity.this, ConfirmGoalListActivity.class);
                startActivity(intent);
            }
        });
    }

    //EditFragmentで変更されたときに再ロードする(変更したオブジェクトの中身を反映するからここではデータベースをいじらない)
    public void updateTextView(Benchmarking edit_benchmarking) {
        this.benchmarking = edit_benchmarking;
        if (benchmarking != null) {
            goalTextView.setText(benchmarking.getInitialGoal());
            targetTextView.setText(benchmarking.getTarget());
            benchMarkTextView.setText(benchmarking.getBenchMark());
            compareTextView.setText(benchmarking.getComparison());
        }
    }

}
