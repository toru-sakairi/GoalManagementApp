package com.example.ver2.fragmentClass.confirmFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.ver2.R;
import com.example.ver2.dataClass.goalManagement.WillCanMust;
import com.example.ver2.fragmentClass.viewModels.ConfirmWillCanMustViewModel;

public class ConfirmWillCanMustEditFragment extends DialogFragment {
    private EditText willEditText;
    private EditText canEditText;
    private EditText mustEditText;
    private EditText goalEditText;

    private WillCanMust wcm;

    ConfirmWillCanMustViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.willcanmust_scroll_layout,container,false);

        viewModel = new ViewModelProvider(requireActivity()).get(ConfirmWillCanMustViewModel.class);

        willEditText = view.findViewById(R.id.WillCanMust_editText_Will);
        canEditText = view.findViewById(R.id.WillCanMust_editText_Can);
        mustEditText = view.findViewById(R.id.WillCanMust_editText_Must);
        goalEditText = view.findViewById(R.id.WillCanMust_editText_Goal);

        Bundle bundle = getArguments();
        if(bundle != null){
            wcm = bundle.getParcelable("wcm");
            if(wcm != null){
                willEditText.setText(wcm.getWill());
                canEditText.setText(wcm.getCan());
                mustEditText.setText(wcm.getMust());
                goalEditText.setText(wcm.getWillcanmustGoal());
            }
        }

        Button saveButton = view.findViewById(R.id.willCanMust_saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wcm.setWill(willEditText.getText().toString());
                wcm.setCan(canEditText.getText().toString());
                wcm.setMust(mustEditText.getText().toString());
                wcm.setWillcanmustGoal(goalEditText.getText().toString());

                viewModel.callActivityMethod_updateTextView(wcm);

                dismiss();
            }
        });

        Button backButton = view.findViewById(R.id.willCanMust_backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        return view;
    }

}
