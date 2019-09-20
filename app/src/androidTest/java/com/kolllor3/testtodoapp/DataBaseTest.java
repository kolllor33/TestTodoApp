package com.kolllor3.testtodoapp;

import android.content.Context;

import com.kolllor3.testtodoapp.database.TodoDataBase;
import com.kolllor3.testtodoapp.database.TodoItemDao;
import com.kolllor3.testtodoapp.model.TodoItem;
import com.kolllor3.testtodoapp.testUtils.LiveDataTestUtil;
import com.kolllor3.testtodoapp.utils.Utilities;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;
import java.util.List;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

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
        long date = new Date().getTime();

        TodoItem item = new TodoItem();
        item.endDate = date;
        item.reminderId = 0;
        item.title = Utilities.getRandomString(10);

        TodoItem item2 = new TodoItem();
        item2.endDate = date + 2;
        item2.reminderId = 1;
        item2.title = Utilities.getRandomString(10);

        TodoItem item3 = new TodoItem();
        item3.endDate = date + 1;
        item3.reminderId = 2;
        item3.title = Utilities.getRandomString(10);

        todoItemDao.insertAll(item, item2, item3);

        List<TodoItem> byId = LiveDataTestUtil.getValue(todoItemDao.getAllTodoItems());
        if (byId != null) {
            assertTrue(byId.get(0).isEqual(item));
            assertTrue(byId.get(1).isEqual(item2));
            assertTrue(byId.get(2).isEqual(item3));
        }

        List<TodoItem> byDate = LiveDataTestUtil.getValue(todoItemDao.getAllTodoItemsOrderEndDate());
        if (byDate != null) {
            assertTrue(byDate.get(0).isEqual(item));
            assertTrue(byDate.get(1).isEqual(item3));
            assertTrue(byDate.get(2).isEqual(item2));
        }

        if (byId != null) {
            todoItemDao.updateDoneStateById(byId.get(0).id, true);
            byId = LiveDataTestUtil.getValue(todoItemDao.getAllTodoItems());
            assertTrue(byId.get(0).isDone);
            todoItemDao.updateDoneStateById(byId.get(0).id, false);
        }
        item3.id = byId.get(2).id;
        todoItemDao.delete(item3);

        byId = LiveDataTestUtil.getValue(todoItemDao.getAllTodoItems());
        if (byId != null) {
            assertEquals(2, byId.size());
            assertTrue(byId.get(0).isEqual(item));
            assertTrue(byId.get(1).isEqual(item2));
        }

    }


}
