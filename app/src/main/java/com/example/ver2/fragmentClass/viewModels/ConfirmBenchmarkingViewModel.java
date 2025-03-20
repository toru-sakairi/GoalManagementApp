/*
    ConfirmBenchmarkingActivityクラスにおいてFragmentとの情報を更新するためのViewModel
 */

package com.example.ver2.fragmentClass.viewModels;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ver2.dataClass.goalManagement.Benchmarking;

public class ConfirmBenchmarkingViewModel extends ViewModel {
    private final MutableLiveData<Benchmarking> benchmarkingLiveData = new MutableLiveData<>();

    public LiveData<Benchmarking> getBenchmarkingLiveData() {
        return benchmarkingLiveData;
    }

    public void updateBenchmarking(Benchmarking updatedBenchmarking) {
        benchmarkingLiveData.setValue(updatedBenchmarking);
    }
}
