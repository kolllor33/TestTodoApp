package com.kolllor3.testtodoapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kolllor3.testtodoapp.database.TodoDataBase;
import com.kolllor3.testtodoapp.databinding.ContentMainBindingImpl;
import com.kolllor3.testtodoapp.model.TodoItem;
import com.kolllor3.testtodoapp.model.TodoItemModelView;
import com.kolllor3.testtodoapp.utils.Utilities;

public class MainActivity extends AppCompatActivity {

    TodoDataBase dataBase;
    private final int ADD_ITEM_ACTIVITY_REQUEST_CODE = 9000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //this returns null(needed to bind adapter to recycelerView via xml)
        ContentMainBindingImpl activityBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        dataBase = Room.databaseBuilder(this, TodoDataBase.class, "todoDb").build();

        TodoItemModelView todoItemModelView = ViewModelProviders.of(this).get(TodoItemModelView.class);

        if(savedInstanceState == null)
            todoItemModelView.init();

        activityBinding.setModelView(todoItemModelView);

        todoItemModelView.getTodoItems(dataBase.getTodoItemDao()).observe(this, todoItems -> {
            todoItemModelView.getAdapter().setTodoItems(todoItems);
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

        if(resultCode == ADD_ITEM_ACTIVITY_REQUEST_CODE){
            if(Utilities.isNotNull(data) && Utilities.isNotNull(data.getExtras())){
                Bundle b = data.getExtras();
                TodoItem item = new TodoItem(b.getInt("reminderId"),b.getString("title"), b.getLong("endDate"));
                Utilities.doInBackground(() -> dataBase.getTodoItemDao().insertAll(item));
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
}
