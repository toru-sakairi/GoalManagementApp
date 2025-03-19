package com.example.ver2.recyclerViewClass;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.ver2.BooleanUtils;
import com.example.ver2.DateUtils;
import com.example.ver2.R;
import com.example.ver2.dataClass.GoalDataViewModel;
import com.example.ver2.dataClass.goalManagement.Goal;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewGoalListAdapter extends RecyclerView.Adapter<RecyclerViewGoalListAdapter.GoalViewHolder> {

    private List<Goal> goalList;

    private OnItemClickListener listener;

    //データベース用ViewModel
    private GoalDataViewModel viewModel;

    //クリックリスナを設定するメソッド
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    //コンストラクタ
    public RecyclerViewGoalListAdapter(List<Goal> goalList, GoalDataViewModel viewModel) {
        if (goalList == null) {
            goalList = new ArrayList<>();
        } else {
            this.goalList = goalList;
        }

        this.viewModel = viewModel;
    }

    //ViewHolderを作成するメソッド
    @Override
    public RecyclerViewGoalListAdapter.GoalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Task１つ分のレイアウトをViewとして読み込む
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.goal_itemlist_layout, parent, false);
        return new RecyclerViewGoalListAdapter.GoalViewHolder(view, this);
    }

    //ViewHolderとデータを紐づけるメソッド
    @Override
    public void onBindViewHolder(RecyclerViewGoalListAdapter.GoalViewHolder holder, int position) {
        Goal goal = goalList.get(position);
        if (goal.getType() == null) {
            holder.goalTypeTextView.setText("フレームワーク：" + "null");
        } else {
            holder.goalTypeTextView.setText("フレームワーク：" + goal.getType().toString());
        }
        holder.goalNameTextView.setText("目標名：" + goal.getName());
        holder.goalDescriptionTextView.setText("詳細：" + goal.getDescription());
        holder.createDateTextView.setText("作成日：" + DateUtils.formatDate_Japanese(goal.getCreateDate()));
        holder.startDateTextView.setText("開始日：" + DateUtils.formatDate_Japanese(goal.getStartDate()));
        holder.finishDateTextView.setText("終了日：" + DateUtils.formatDate_Japanese(goal.getFinishDate()));
        holder.stateTextView.setText("達成状況：" + BooleanUtils.formatBoolean(goal.isState()));

        //Activity遷移させるリスナ
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int adapterPosition = holder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION && listener != null) {
                    listener.onItemClick(adapterPosition);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        //return Math.min(taskList.size(), 3); // 5個まで
        return goalList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int positoin);
    }

    public static class GoalViewHolder extends RecyclerView.ViewHolder {
        public TextView goalTypeTextView;
        public TextView goalNameTextView;
        public TextView goalDescriptionTextView;
        public TextView createDateTextView;
        public TextView startDateTextView;
        public TextView finishDateTextView;
        public TextView stateTextView;

        public Button deleteButton;

        private RecyclerViewGoalListAdapter adapter;

        public GoalViewHolder(View view, RecyclerViewGoalListAdapter adapter) {
            super(view);
            goalTypeTextView = view.findViewById(R.id.goalList_goalType);
            goalNameTextView = view.findViewById(R.id.goalList_goalName);
            goalDescriptionTextView = view.findViewById(R.id.goalList_goalDescription);
            createDateTextView = view.findViewById(R.id.goalList_goalcreateDate);
            startDateTextView = view.findViewById(R.id.goalList_goalStartDate);
            finishDateTextView = view.findViewById(R.id.goalList_goalfinishDate);
            stateTextView = view.findViewById(R.id.goalList_goalState);

            this.adapter = adapter;

            deleteButton = view.findViewById(R.id.goalList_deleteButton);

            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION && adapter != null){
                        adapter.removeGoal(position);
                    }
                }
            });
        }
    }

    //データベースから削除するメソッド
    public void removeGoal(int position){
        if(position >= 0 && position < goalList.size()){
            //クリックされたGoalオブジェクトを取得
            Goal clickedGoal = goalList.get(position);
            //そのGoalオブジェクトのIDを参照してViewModelを使用し削除（サブクラスごとに分ける）
            if(clickedGoal != null && clickedGoal.getType() != null) {
                switch (clickedGoal.getType()) {
                    case SMART: {
                        viewModel.deleteSMARTByID(clickedGoal.getID());
                        break;
                    }
                    case WILL_CAN_MUST: {
                        viewModel.deleteWCMByID(clickedGoal.getID());
                        break;
                    }
                    case BENCHMARKING: {
                        viewModel.deleteBenchmarkingByID(clickedGoal.getID());
                        break;
                    }
                    case MEMO_GOAL: {
                        viewModel.deleteMemoGoalByID(clickedGoal.getID());
                        break;
                    }
                }
                //goalListから削除を行う
                goalList.remove(position);
                notifyItemRemoved(position);
            }
        }
    }
}
