package com.example.ver2.fragmentClass.confirmFragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.ver2.R;
import com.example.ver2.activityClass.confirmActivityClass.ConfirmBenchmarkingActivity;
import com.example.ver2.dataClass.GoalSaveViewModel;
import com.example.ver2.dataClass.goalManagement.Benchmarking;
import com.example.ver2.fragmentClass.viewModels.ConfirmBenchmarkingViewModel;

public class ConfirmBenchmarkingEditFragment extends DialogFragment {
    private EditText goalEditText;
    private EditText targetEditText;
    private EditText benchMarkEditText;
    private EditText compareEditText;
    private Benchmarking benchmarking;

    ConfirmBenchmarkingViewModel confirmBenchmarkingViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        //目標作成時に用いたLayoutを流用
        View view = inflater.inflate(R.layout.benchmarking_scroll_layout, container, false);

        //ConfirmBenchmarkingのメソッド呼び出しに使用
        confirmBenchmarkingViewModel = new ViewModelProvider(requireActivity()).get(ConfirmBenchmarkingViewModel.class);

        goalEditText = view.findViewById(R.id.benchmarking_2_editText);
        targetEditText = view.findViewById(R.id.benchmarking_3_editText);
        benchMarkEditText = view.findViewById(R.id.benchmarking_4_editText);
        compareEditText = view.findViewById(R.id.benchmarking_5_editText);

        //Activityから送られてくる情報をセット
        Bundle bundle = getArguments();
        if (bundle != null) {
            benchmarking = bundle.getParcelable("benchmarking"); // "benchmarking" という Key で WillCanMust オブジェクトを取得
            if (benchmarking != null) {
                // Benchmarking オブジェクトが存在する場合、EditText に値を設定
                goalEditText.setText(benchmarking.getInitialGoal());
                targetEditText.setText(benchmarking.getTarget());
                benchMarkEditText.setText(benchmarking.getBenchMark());
                compareEditText.setText(benchmarking.getComparison());
            }
        }
        //debugよう
        Log.d(" FragmentDebug is Existing Type ",benchmarking.getType().toString());

        //セーブ＆Activityでのロード
        Button saveButton = view.findViewById(R.id.benchmarking_saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ここではこの四つしか変更できないから四つだけセットする
                benchmarking.setInitialGoal(goalEditText.getText().toString());
                benchmarking.setTarget(targetEditText.getText().toString());
                benchmarking.setBenchMark(benchMarkEditText.getText().toString());
                benchmarking.setComparison(compareEditText.getText().toString());

                Log.d(" FragmentDebug is Existing Type ",benchmarking.getType().toString());

                //変更したオブジェクトをActivity側で再ロードする（変更したオブジェクトもここでロードされる）
                confirmBenchmarkingViewModel.callActivityMethod_updateTextView(benchmarking);

                //Fragmentの終了
                dismiss();
            }
        });

        Button backButton = view.findViewById(R.id.benchmarking_backButton);
        //戻るボタンは何もしないで終了させる
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });


        return view;
    }
}
