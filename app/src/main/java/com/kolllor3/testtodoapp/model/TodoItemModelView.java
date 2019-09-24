package com.kolllor3.testtodoapp.model;

import com.kolllor3.testtodoapp.database.TodoItemDao;
import com.kolllor3.testtodoapp.utils.Utilities;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TodoItemModelView extends ViewModel {

    private LiveData<List<TodoItem>> todoItems;

    public LiveData<List<TodoItem>> getTodoItems(TodoItemDao todoItemDao){
        if (Utilities.isNull(todoItems)){
            Utilities.doInBackground(() -> todoItems = todoItemDao.getAllTodoItems());
        }
        return todoItems;
    }

}
