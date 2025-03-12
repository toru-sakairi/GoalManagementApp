package com.example.ver2.activityClass.confirmActivityClass;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ver2.R;
import com.example.ver2.recyclerViewClass.RecyclerViewGoalListAdapter;
import com.example.ver2.dataClass.goalManagement.Benchmarking;
import com.example.ver2.dataClass.goalManagement.Goal;
import com.example.ver2.dataClass.GoalDataViewModel;
import com.example.ver2.dataClass.goalManagement.Memo_Goal;
import com.example.ver2.dataClass.goalManagement.SMART;
import com.example.ver2.dataClass.goalManagement.WillCanMust;

import java.util.ArrayList;
import java.util.List;

public class ConfirmGoalListActivity extends AppCompatActivity {
    private List<Goal> goalList = new ArrayList<>(); // 初期化しておく
    private RecyclerView recyclerView;
    private RecyclerViewGoalListAdapter adapter;
    private GoalDataViewModel goalDataViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirm_goal_list);

        recyclerView = findViewById(R.id.confirm_goal_lists_RecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        goalDataViewModel = new ViewModelProvider(this).get(GoalDataViewModel.class);
        goalDataViewModel.getAllGoals().observe(this, goals -> {
            goalList.clear();
            goalList.addAll(goals);
            adapter.notifyDataSetChanged();
        });


        adapter = new RecyclerViewGoalListAdapter(goalList, goalDataViewModel);
        recyclerView.setAdapter(adapter);

        // データ取得開始
        goalDataViewModel.loadGoalListFromDatabase();

        //RecyclerViewのクリックの実装
        adapter.setOnItemClickListener(new RecyclerViewGoalListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Goal clickedGoal = goalList.get(position);
                //それぞれのタイプごとに表示するレイアウトを分ける
                switch (clickedGoal.getType()) {
                    //鍵かっこでやるとスコープがその範囲に限定される
                    case SMART: {
                        Intent intent = new Intent(ConfirmGoalListActivity.this, ConfirmSMARTActivity.class);
                        LiveData<SMART> smartLiveData = goalDataViewModel.getSMARTByID(clickedGoal.getID());
                        smartLiveData.observe(ConfirmGoalListActivity.this, smart -> {
                            if(smart != null){
                                intent.putExtra("smart",smart);
                                startActivity(intent);
                            }else{

                            }
                        });

                        break;
                    }
                    case WILL_CAN_MUST: {
                        Intent intent = new Intent(ConfirmGoalListActivity.this, ConfirmWillCanMustActivity.class);
                        LiveData<WillCanMust> wcmLiveData = goalDataViewModel.getWCMByID(clickedGoal.getID());
                        wcmLiveData.observe(ConfirmGoalListActivity.this, wcm ->{
                            if(wcm != null){
                                intent.putExtra("willCanMust", wcm);
                                startActivity(intent);
                            }else{

                            }
                        });
                        break;
                    }
                    case BENCHMARKING: {
                        Intent intent = new Intent(ConfirmGoalListActivity.this, ConfirmBenchmarkingActivity.class);
                        LiveData<Benchmarking> benchmarkingLiveData = goalDataViewModel.getBenchmarkingByID(clickedGoal.getID());
                        benchmarkingLiveData.observe(ConfirmGoalListActivity.this, benchmarking -> {
                            if(benchmarking != null){
                                intent.putExtra("benchmarking", benchmarking);
                                startActivity(intent);
                            }else {

                            }
                        });
                        break;
                    }
                    case MEMO_GOAL: {
                        Intent intent = new Intent(ConfirmGoalListActivity.this, ConfirmMemoGoalActivity.class);
                        LiveData<Memo_Goal> memoLiveData = goalDataViewModel.getMemoGoalByID(clickedGoal.getID());
                        memoLiveData.observe(ConfirmGoalListActivity.this, memo -> {
                            if(memo != null){
                                intent.putExtra("memo_goal", memo);
                                startActivity(intent);
                            }else{

                            }
                        });

                        break;
                    }
                }

            }
        });

        Button backButton = findViewById(R.id.confirm_goal_list_backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ConfirmGoalListActivity.this, ConfirmTopChooseActivity.class);
                startActivity(intent);
            }
        });
    }


}