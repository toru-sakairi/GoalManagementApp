package com.example.ver2.activityClass.confirmActivityClass;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.ver2.R;
import com.example.ver2.dataClass.goalManagement.WillCanMust;
import com.example.ver2.fragmentClass.confirmFragments.ConfirmWillCanMustEditFragment;
import com.example.ver2.fragmentClass.viewModels.ConfirmWillCanMustViewModel;

import org.w3c.dom.Text;

public class ConfirmWillCanMustActivity extends AppCompatActivity {
    private TextView willTextView;
    private TextView canTextView;
    private TextView mustTextView;
    private TextView goalTextView;

    private WillCanMust wcm;

    private ConfirmWillCanMustViewModel confirmWillCanMustViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirm_willcanmust_scroll_layout);

        confirmWillCanMustViewModel = new ViewModelProvider(this).get(ConfirmWillCanMustViewModel.class);
        confirmWillCanMustViewModel.setActivity(this);

        willTextView = findViewById(R.id.confirm_WillCanMust_textView_Will);
        canTextView = findViewById(R.id.confirm_WillCanMust_textView_Can);
        mustTextView = findViewById(R.id.confirm_WillCanMust_textView_Must);
        goalTextView = findViewById(R.id.confirm_WillCanMust_textView_Goal);

        Intent intent = getIntent();
        if(intent != null && intent.hasExtra("willCanMust")){
            wcm = intent.getParcelableExtra("willCanMust");
            if(wcm != null){
                willTextView.setText(wcm.getWill());
                canTextView.setText(wcm.getCan());
                mustTextView.setText(wcm.getMust());
                goalTextView.setText(wcm.getWillcanmustGoal());
            }
        }

        Button nextButton = findViewById(R.id.confirm_willCanMust_nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ConfirmWillCanMustActivity.this, ConfirmGoalActivity.class);
                intent.putExtra("wcm",wcm);
                startActivity(intent);
            }
        });

        Button backButton = findViewById(R.id.confirm_willCanMust_backButton);
        backButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(ConfirmWillCanMustActivity.this, ConfirmGoalListActivity.class);
                startActivity(intent);
            }
        });

        Button editButton = findViewById(R.id.confirm_willCanMust_Edit_button);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putParcelable("wcm", wcm);

                ConfirmWillCanMustEditFragment fragment = new ConfirmWillCanMustEditFragment();
                fragment.setArguments(bundle);
                fragment.show(getSupportFragmentManager(), "ConfirmWillCanMustEditFragment");
            }
        });
    }

    public void updateTextView(WillCanMust wcm){
        this.wcm = wcm;

        if(wcm != null) {
            willTextView.setText(wcm.getWill());
            canTextView.setText(wcm.getCan());
            mustTextView.setText(wcm.getMust());
            goalTextView.setText(wcm.getWillcanmustGoal());
        }
    }
}
