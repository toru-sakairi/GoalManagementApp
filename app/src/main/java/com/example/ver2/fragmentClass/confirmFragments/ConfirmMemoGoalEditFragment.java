package com.example.ver2.fragmentClass.confirmFragments;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.ver2.R;
import com.example.ver2.dataClass.goalManagement.Memo_Goal;
import com.example.ver2.fragmentClass.viewModels.ConfirmMemoGoalViewModel;

public class ConfirmMemoGoalEditFragment extends DialogFragment {
    private EditText memoEditText;
    
    Memo_Goal memo;
    
    ConfirmMemoGoalViewModel confirmMemoGoalViewModel;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        //作成時に用いたLayoutを流用
        View view = inflater.inflate(R.layout.memo_goal_1, container, false);
        
        confirmMemoGoalViewModel = new ViewModelProvider(requireActivity()).get(ConfirmMemoGoalViewModel.class);
        
        memoEditText = view.findViewById(R.id.memo_goal_1_editText);
        
        Bundle bundle = getArguments();
        if(bundle != null){
            memo = bundle.getParcelable("memo_goal");
            if(memo != null){
                memoEditText.setText(memo.getMemo());
            }
        }
        
        Button saveButton = view.findViewById(R.id.memo_goal_1_saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                memo.setMemo(memoEditText.getText().toString());

                //変更したオブジェクトをActivity側で再ロード（変更したのをActivityのMemoGoalオブジェクトに入れるのも兼ねている）
                confirmMemoGoalViewModel.callActivityMethod_updateTextView(memo);
                
                dismiss();
            }
        });

        Button backButton = view.findViewById(R.id.memo_goal_1_backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        return view;
    }
}
