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
import com.kolllor3.testtodoapp.utils.Utilities;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;

import java.util.Date;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class DataBaseTest {

    private TodoItemDao todoItemDao;
    private TodoDataBase db;

    @Rule
    public TestRule rule = new InstantTaskExecutorRule();

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
        item2.endDate = date + 100;
        item2.reminderId = 1;
        item2.title = Utilities.getRandomString(10);

        TodoItem item3 = new TodoItem();
        item3.endDate = date + 90;
        item3.reminderId = 2;
        item3.title = Utilities.getRandomString(10);

        todoItemDao.insertAll(item, item2, item3);

        List<TodoItem> byId = LiveDataTestUtil.getValue(todoItemDao.getAllTodoItems());
        if (byId != null) {
            assertEquals(byId.get(0), item);
            assertEquals(byId.get(1), item2);
            assertEquals(byId.get(2), item3);
        }

        List<TodoItem> byDate = todoItemDao.getAllTodoItemsOrderEndDate().getValue();
        if (byDate != null) {
            assertEquals(byDate.get(0), item2);
            assertEquals(byDate.get(1), item3);
            assertEquals(byDate.get(2), item);
        }

        if (byId != null) {
            todoItemDao.updateDoneStateById(byId.get(0).id, true);
            byId = todoItemDao.getAllTodoItems().getValue();
            assertTrue(byId.get(0).isDone);
        }

        todoItemDao.delete(item3);

        byId = todoItemDao.getAllTodoItems().getValue();
        if (byId != null) {
            assertEquals(byId.size(),2);
            assertEquals(byId.get(0), item2);
            assertEquals(byId.get(1), item3);
        }

    }


}
