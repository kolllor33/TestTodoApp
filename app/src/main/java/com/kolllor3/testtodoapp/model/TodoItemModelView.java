package com.kolllor3.testtodoapp.model;

import com.kolllor3.testtodoapp.utils.Utilities;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TodoItemModelView extends ViewModel {

    private MutableLiveData<List<TodoItem>> todoItems;

    public LiveData<List<TodoItem>> getTodoItems(){
        if (Utilities.isNull(todoItems)){
            todoItems = new MutableLiveData<>();
        }
        return todoItems;
    }

}
