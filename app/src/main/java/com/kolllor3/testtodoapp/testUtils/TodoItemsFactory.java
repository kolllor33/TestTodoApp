package com.kolllor3.testtodoapp.testUtils;

import com.kolllor3.testtodoapp.model.TodoItem;
import com.kolllor3.testtodoapp.utils.Utilities;

import java.util.Date;

public class TodoItemsFactory {

    public static TodoItem[] createTestTodoList(){
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

        return new TodoItem[]{item, item2, item3};
    }
}
