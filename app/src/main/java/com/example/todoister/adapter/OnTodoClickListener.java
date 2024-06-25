package com.example.todoister.adapter;

import com.example.todoister.Model.Task;

public interface OnTodoClickListener {
    void onTodoClick(int adapterposition, Task task);
    void onTodoRadioButtonClick(Task task);

}
