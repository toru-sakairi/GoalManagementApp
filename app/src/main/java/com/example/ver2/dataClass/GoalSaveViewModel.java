package com.example.ver2.dataClass;

import static com.example.ver2.dataClass.goalManagement.GoalType.BENCHMARKING;
import static com.example.ver2.dataClass.goalManagement.GoalType.WILL_CAN_MUST;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.ver2.dataClass.goalManagement.Benchmarking;
import com.example.ver2.dataClass.goalManagement.Goal;
import com.example.ver2.dataClass.goalManagement.Memo_Goal;
import com.example.ver2.dataClass.goalManagement.SMART;
import com.example.ver2.dataClass.goalManagement.WillCanMust;
import com.example.ver2.dataClass.goalManagement.GoalType;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GoalSaveViewModel extends AndroidViewModel {
    private final AppDatabase db;
    private final ExecutorService executorService;
    private final MutableLiveData<List<Goal>> allGoals = new MutableLiveData<>();

    public GoalSaveViewModel(Application application) {
        super(application);
        db = AppDatabase.getInstance(application);
        executorService = Executors.newSingleThreadExecutor();
    }

    //外部から変更させないため戻り値をLiveDataに
    public LiveData<List<Goal>> getAllGoals() {
        return allGoals;
    }

    public void loadGoalListFromDatabase() {
        executorService.execute(() -> {
            List<SMART> smartGoals = db.smartDao().getAllSMART();
            List<Benchmarking> benchmarkingGoals = db.benchmarkingDao().getAllBenchmarking();
            List<WillCanMust> wcms = db.wcmDao().getAllWillCanMust();
            List<Memo_Goal> memoGoals = db.memoGoalDao().getAllMemoGoals();

            List<Goal> newGoalList = new ArrayList<>();
            newGoalList.addAll(smartGoals);
            newGoalList.addAll(benchmarkingGoals);
            newGoalList.addAll(wcms);
            newGoalList.addAll(memoGoals);

            allGoals.postValue(newGoalList);
        });
    }

    public LiveData<SMART> getSMARTByID(int id) {
        return db.smartDao().getSMARTById(id);
    }

    public LiveData<WillCanMust> getWCMByID(int id) {
        return db.wcmDao().getWillCanMustById(id);
    }

    public LiveData<Benchmarking> getBenchmarkingByID(int id) {
        return db.benchmarkingDao().getBenchmarkingById(id);
    }

    public LiveData<Memo_Goal> getMemoGoalByID(int id) {
        return db.memoGoalDao().getMemoGoalById(id);
    }

    public void updateSMART(SMART smart){
        executorService.execute(() -> {
            db.smartDao().update(smart);
        });
    }

    public void updateBenchmarking(Benchmarking benchmarking){
        executorService.execute(() -> {
            db.benchmarkingDao().update(benchmarking);
        });
    }

    public void updateWCM(WillCanMust wcm){
        executorService.execute(() -> {
            db.wcmDao().update(wcm);
        });
    }

    public void updateMemo_Goal(Memo_Goal memo){
        executorService.execute(() -> {
            db.memoGoalDao().update(memo);
        });
    }


    public void insertGoal(Goal goal, WillCanMust wcm, Benchmarking benchmarking, SMART smart, Memo_Goal memo) {
        executorService.execute(() -> {
            if (wcm != null) {
                wcm.setGoal(goal);
                wcm.setType(GoalType.WILL_CAN_MUST);
                db.wcmDao().insert(wcm);
            } else if (benchmarking != null) {
                benchmarking.setGoal(goal);
                benchmarking.setType(GoalType.BENCHMARKING);
                db.benchmarkingDao().insert(benchmarking);
            } else if (smart != null) {
                smart.setGoal(goal);
                smart.setType(GoalType.SMART);
                db.smartDao().insert(smart);
            } else if (memo != null) {
                memo.setGoal(goal);
                memo.setType(GoalType.MEMO_GOAL);
                db.memoGoalDao().insert(memo);
            }
        });
    }
}
