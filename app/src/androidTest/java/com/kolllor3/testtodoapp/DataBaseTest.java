package com.kolllor3.testtodoapp;

import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.kolllor3.testtodoapp.database.TodoDataBase;
import com.kolllor3.testtodoapp.database.TodoItemDao;
import com.kolllor3.testtodoapp.model.TodoItem;
import com.kolllor3.testtodoapp.testUtils.LiveDataTestUtil;
import com.kolllor3.testtodoapp.testUtils.TodoItemsFactory;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class DataBaseTest {

    private TodoItemDao todoItemDao;
    private TodoDataBase db;

    @Rule
    public InstantTaskExecutorRule rule = new InstantTaskExecutorRule();

    @Before
    public void initDb(){
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, TodoDataBase.class)
                .allowMainThreadQueries()
                .build();
        todoItemDao = db.getTodoItemDao();
    }

    @After
    public void closeDb() {
        db.close();
    }

    @Test
    @LargeTest
    public void TodoDbFunctionsTest() throws InterruptedException {

        TodoItem[] todoItems = TodoItemsFactory.createTestTodoList();

        todoItemDao.insertAll(todoItems);

        List<TodoItem> byId = LiveDataTestUtil.getValue(todoItemDao.getAllTodoItems());
        if (byId != null) {
            assertTrue(byId.get(0).isEqual(todoItems[0]));
            assertTrue(byId.get(1).isEqual(todoItems[1]));
            assertTrue(byId.get(2).isEqual(todoItems[2]));
        }

        List<TodoItem> byDate = LiveDataTestUtil.getValue(todoItemDao.getAllTodoItemsOrderEndDate());
        if (byDate != null) {
            assertTrue(byDate.get(0).isEqual(todoItems[0]));
            assertTrue(byDate.get(1).isEqual(todoItems[2]));
            assertTrue(byDate.get(2).isEqual(todoItems[1]));
        }

        if (byId != null) {
            todoItemDao.updateDoneStateById(byId.get(0).id, true);
            byId = LiveDataTestUtil.getValue(todoItemDao.getAllTodoItems());
            assertTrue(byId.get(0).isDone);
            todoItemDao.updateDoneStateById(byId.get(0).id, false);
        }
        todoItems[2].id = byId.get(2).id;
        todoItemDao.delete(todoItems[2]);

        byId = LiveDataTestUtil.getValue(todoItemDao.getAllTodoItems());
        if (byId != null) {
            assertEquals(2, byId.size());
            assertTrue(byId.get(0).isEqual(todoItems[0]));
            assertTrue(byId.get(1).isEqual(todoItems[1]));
        }

    }


}
