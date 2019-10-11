package com.kolllor3.testtodoapp.model;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.kolllor3.testtodoapp.R;
import com.kolllor3.testtodoapp.adapter.TodoListAdapter;
import com.kolllor3.testtodoapp.database.TodoItemRepository;

import java.util.List;

public class TodoItemViewModel extends AndroidViewModel {

    private LiveData<List<TodoItem>> todoItems;
    private TodoListAdapter adapter;
    private TodoItemRepository todoItemRepository;

    public TodoItemViewModel(Application application) {
        super(application);
        todoItemRepository = new TodoItemRepository(application);
        todoItems = todoItemRepository.getAllTodoItems();
    }

    public void init(){
        adapter = new TodoListAdapter(this, R.layout.todo_list_item);
    }

    public TodoListAdapter getAdapter() {
        return adapter;
    }

    public LiveData<List<TodoItem>> getTodoItems(){
        return todoItems;
    }

    public void insert(TodoItem item) { todoItemRepository.insertTodoItem(item); }

}
