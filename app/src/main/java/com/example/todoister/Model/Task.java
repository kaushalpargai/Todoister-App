package com.example.todoister.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "task_table")
public class Task {

    @ColumnInfo(name = "task_id")
    @PrimaryKey(autoGenerate = true)
    public  long taskId;

    public String task;

    public Priority priorirty;

    @ColumnInfo(name ="due_date")
    public Date dueDate;

    @ColumnInfo(name = "created_at")
    public Date isCreated;

    @ColumnInfo(name ="is_done")
    public  boolean isDone;

    public Task(String task, Priority priorirty, Date dueDate,  Date isCreated,boolean isDone ) {
        this.task = task;
        this.priorirty = priorirty;
        this.dueDate = dueDate;
        this.isDone = isDone;
        this.isCreated = isCreated;
    }

    public long getTaskId() {
        return taskId;
    }

    public void setTaskId(long taskId) {
        this.taskId = taskId;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public Priority getPriorirty() {
        return priorirty;
    }

    public void setPriorirty(Priority priorirty) {
        this.priorirty = priorirty;
    }

    public Date getIsCreated() {
        return isCreated;
    }

    public void setIsCreated(Date isCreated) {
        this.isCreated = isCreated;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    @Override
    public String toString() {
        return "Task{" +
                "taskId=" + taskId +
                ", task='" + task + '\'' +
                ", priorirty=" + priorirty +
                ", dueDate=" + dueDate +
                ", isCreated=" + isCreated +
                ", isDone=" + isDone +
                '}';
    }
}
