package com.example.todoister.Model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {
    private final MutableLiveData<Task> selectedItem = new MutableLiveData<>();

    public void  selectedItem(Task task) {
        selectedItem.setValue(task);
    }

    public LiveData<Task> getSelectedItem(){
        return selectedItem;
        }

    }

