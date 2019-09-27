package com.kolllor3.testtodoapp.model;

import com.kolllor3.testtodoapp.R;
import com.kolllor3.testtodoapp.adapter.TodoListAdapter;
import com.kolllor3.testtodoapp.database.TodoItemDao;
import com.kolllor3.testtodoapp.utils.Utilities;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TodoItemModelView extends ViewModel {

    private LiveData<List<TodoItem>> todoItems;
    private TodoListAdapter adapter;


    public void init(){
        adapter = new TodoListAdapter(this, R.layout.todo_list_item);
    }

    public TodoListAdapter getAdapter() {
        return adapter;
    }

    public LiveData<List<TodoItem>> getTodoItems(TodoItemDao todoItemDao){
        if (Utilities.isNull(todoItems)){
            Utilities.doInBackground(() -> todoItems = todoItemDao.getAllTodoItems());
        }
        return todoItems;
    }

}
