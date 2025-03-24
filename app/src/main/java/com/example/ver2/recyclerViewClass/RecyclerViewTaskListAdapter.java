/*
    Taskをリストとして表示するためのRecyclerView
    それぞれにリスナをつけて確認したり編集したり、削除ボタンを追加してTaskListから削除できるようにする
 */
package com.example.ver2.recyclerViewClass;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ver2.R;
import com.example.ver2.dataClass.Task;

import java.util.List;

public class RecyclerViewTaskListAdapter extends RecyclerView.Adapter<RecyclerViewTaskListAdapter.TaskViewHolder>{

    private OnItemClickListener itemClickListener;

    //このリスト表示の内容のList＜Task＞ finalは参照を固定するだけだから値は変更できる
    private final List<Task> taskList;
    //削除リスナー
    private OnTaskRemoveListener deleteListener;

    //コンストラクタ
    public RecyclerViewTaskListAdapter(List<Task> taskList){
        this.taskList = taskList;
    }

    //ViewHolderを作成するメソッド
    @NonNull
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


        //リスナをつけて編集可能にする
        // アイテムクリック時にリスナーを呼ぶ
        holder.itemView.setOnClickListener(v -> {
            if (itemClickListener != null) {
                int currentPosition = holder.getAdapterPosition();
                if (currentPosition != RecyclerView.NO_POSITION) {
                    itemClickListener.onItemClick(currentPosition);
                }
            }
        });
        // 削除ボタンのリスナー
        holder.taskDeleteButton.setOnClickListener(v -> {
            if (deleteListener != null) {
                int currentPosition = holder.getAdapterPosition(); // 最新のpositionを取得
                if (currentPosition != RecyclerView.NO_POSITION) {
                    deleteListener.onTaskRemoved(currentPosition);
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
        this.itemClickListener = listener;
    }

    //ViewHolderクラス、Taskデータを表示するViewを保持する
    public static class TaskViewHolder extends RecyclerView.ViewHolder{
        public TextView taskNameTextView;
        public TextView taskDescriptionTextView;
        public Button taskDeleteButton;
        //他のTaskの情報を表示するTextViewを追加
        public TaskViewHolder(View view){
            super(view);
            taskNameTextView = view.findViewById(R.id.taskName);
            taskDescriptionTextView = view.findViewById(R.id.taskDescription);
            taskDeleteButton = view.findViewById(R.id.taskList_deleteButton);
        }
    }

    // インターフェース: 削除イベントを Fragment に伝える
    public interface OnTaskRemoveListener {
        void onTaskRemoved(int position);
    }

    // 削除リスナーのセット
    public void setOnTaskRemoveListener(OnTaskRemoveListener listener) {
        this.deleteListener = listener;
    }
}
