package com.kolllor3.testtodoapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kolllor3.testtodoapp.model.TodoItem;
import com.kolllor3.testtodoapp.model.TodoItemViewModel;
import com.kolllor3.testtodoapp.utils.Utilities;

public class MainActivity extends AppCompatActivity {

    private final int ADD_ITEM_ACTIVITY_REQUEST_CODE = 9000;
    private TodoItemViewModel todoItemViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        todoItemViewModel = ViewModelProviders.of(this).get(TodoItemViewModel.class);

        RecyclerView todoRecyclerView = findViewById(R.id.todo_items_list);
        TextView noItemTextView = findViewById(R.id.error_list_message);

        if(Utilities.isNull(savedInstanceState))
            todoItemViewModel.init();

        todoRecyclerView.setAdapter(todoItemViewModel.getAdapter());
        todoRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        todoItemViewModel.getTodoItems().observe(this, todoItems -> {
            if(todoItems.size() > 0)
                noItemTextView.setVisibility(View.GONE);
            todoItemViewModel.getAdapter().setTodoItems(todoItems);
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view ->{
            Intent i = new Intent(this, AddItemActivity.class);
            startActivityForResult(i, ADD_ITEM_ACTIVITY_REQUEST_CODE);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == ADD_ITEM_ACTIVITY_REQUEST_CODE){
            if(Utilities.isNotNull(data) && Utilities.isNotNull(data.getExtras())){
                Bundle b = data.getExtras();
                TodoItem item = new TodoItem(b.getInt("reminderId"),b.getString("title"), b.getLong("endDate"));
                todoItemViewModel.insert(item);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public TodoItemViewModel getTodoItemViewModel() {
        return todoItemViewModel;
    }
}
