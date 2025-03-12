package com.example.ver2.recyclerViewClass;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.ver2.R;
import com.example.ver2.dataClass.Task;

import java.util.List;

public class RecyclerViewTaskListAdapter extends RecyclerView.Adapter<RecyclerViewTaskListAdapter.TaskViewHolder>{

    private OnItemClickListener listener;
    private List<Task> taskList;

    //コンストラクタ
    public RecyclerViewTaskListAdapter(List<Task> taskList){
        this.taskList = taskList;
    }

    //ViewHolderを作成するメソッド
    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        //Task１つ分のレイアウトをViewとして読み込む
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_itemlist_layout, parent, false);
        return new TaskViewHolder(view);
    }

    //ViewHolderとデータを紐づけるメソッド
    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position){
        Task task = taskList.get(position);
        holder.taskNameTextView.setText(task.getName());
        holder.taskDescriptionTextView.setText(task.getDescription());
        //他のTaskの情報をTextViewに設定

        //リスナをつけて編集可能にする
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener != null){
                    int adapterPosition = holder.getAdapterPosition();
                    if(adapterPosition != RecyclerView.NO_POSITION &&  listener != null){
                        listener.onItemClick(adapterPosition);
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount(){
        //return Math.min(taskList.size(), 3); // 5個まで
        return taskList.size();
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    //ViewHolderクラス、Taskデータを表示するViewを保持する
    public static class TaskViewHolder extends RecyclerView.ViewHolder{
        public TextView taskNameTextView;
        public TextView taskDescriptionTextView;
        //他のTaskの情報を表示するTextViewを追加
        public TaskViewHolder(View view){
            super(view);
            taskNameTextView = view.findViewById(R.id.taskName);
            taskDescriptionTextView = view.findViewById(R.id.taskDescription);
        }
    }
}
