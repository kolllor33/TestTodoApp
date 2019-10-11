package com.kolllor3.testtodoapp.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.kolllor3.testtodoapp.model.TodoItem;
import com.kolllor3.testtodoapp.utils.Utilities;

import java.util.List;

public class TodoItemRepository {
    private TodoItemDao todoItemDao;
    private LiveData<List<TodoItem>> todoItems;

    public TodoItemRepository(Application application) {
        TodoDataBase db = TodoDataBase.getDatabase(application);
        todoItemDao = db.getTodoItemDao();
        todoItems = todoItemDao.getAllTodoItems();
    }

    public LiveData<List<TodoItem>> getAllTodoItems() {
        return todoItems;
    }

    public void insertTodoItem(TodoItem item){
        Utilities.doInBackground(() -> todoItemDao.insertAll(item));
    }
}
