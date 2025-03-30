package com.example.ver2.fragmentClass.modalFragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.ver2.R;
import com.example.ver2.fragmentClass.BackConfirmListener;

public class BackConfirmFragment extends DialogFragment {

    private BackConfirmListener listener;

    @Override
    public void onAttach(@NonNull Context context){
        super.onAttach(context);
        if(context instanceof BackConfirmListener){
            listener = (BackConfirmListener)context;
        }else{
            throw new RuntimeException(context.toString() + " must implement BackConfirmListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.back_confirm_fragment, container, false);
        return view;
    }
    @Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        Button positiveButton = view.findViewById(R.id.back_confirm_dialog_positive_button);
        Button negativeButton = view.findViewById(R.id.back_confirm_dialog_negative_button);
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener != null){
                    listener.onPositiveButtonClicked();
                }
            }
        });
        negativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener != null){
                    listener.onNegativeButtonClicked();
                }
            }
        });


    }
}
