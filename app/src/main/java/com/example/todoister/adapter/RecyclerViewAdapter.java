package com.example.todoister.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoister.Model.Task;
import com.example.todoister.R;
import com.example.todoister.Util.Utils;
import com.google.android.material.chip.Chip;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{
    private  final List<Task> taskList;
    private  final OnTodoClickListener onTodoClickListener;

    public RecyclerViewAdapter(List<Task> taskList, OnTodoClickListener onTodoClickListener) {
        this.taskList = taskList;
        this.onTodoClickListener = onTodoClickListener;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.todo_row,parent,false);

        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
    Task task=taskList.get(position);
    String formatted= Utils.formatDate(task.getDueDate());
    holder.task.setText(task.getTask());
    holder.todayChip.setText(formatted);
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener{
        public RadioButton radioButton;
        public TextView task;
        public Chip todayChip;
        private final OnTodoClickListener onTodoClickListener;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            radioButton=itemView.findViewById(R.id.todo_radio_button);
            task=itemView.findViewById(R.id.todo_row_todo);
            todayChip=itemView.findViewById(R.id.todo_row_chip);
            this.onTodoClickListener=RecyclerViewAdapter.this.onTodoClickListener;

            itemView.setOnClickListener(this);
            radioButton.setOnClickListener(this);



        }

        @Override
        public void onClick(View view) {
            int id= view.getId();
            Task currTask=taskList.get(getAdapterPosition());


            if(id==R.id.todo_row_layout){

                onTodoClickListener.onTodoClick(currTask);

            }
            else if(id==R.id.todo_radio_button){

                onTodoClickListener.onTodoRadioButtonClick(currTask);


            }
        }
    }
}
