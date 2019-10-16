package com.kolllor3.testtodoapp;

import android.content.Context;
import android.widget.DatePicker;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.contrib.PickerActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import com.kolllor3.testtodoapp.database.TodoDataBase;
import com.kolllor3.testtodoapp.model.TodoItem;
import com.kolllor3.testtodoapp.testUtils.LiveDataTestUtil;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class AddItemTest {

    private TodoDataBase db;

    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setupDataBase() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, TodoDataBase.class)
                .allowMainThreadQueries()
                .build();
    }

    @Test
    public void addItem() {
        activityRule.getActivity().getTodoItemViewModel().getTodoItemRepository().setTodoItemDao(db.getTodoItemDao());
        onView(withId(R.id.fab)).perform(click());
        onView(withId(R.id.title_input)).perform(typeText("lol")).perform(closeSoftKeyboard());
        onView(withId(R.id.done_before_input)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(2019, 10, 30));
        onView(withText("OK")).perform(click());
        onView(withId(R.id.done_before_input)).check(matches(withText("30/10/19")));
        onView(withId(R.id.fab)).perform(click());
        //from this point on this isn't working
        activityRule.getActivity().runOnUiThread(()-> {
            try {
                List<TodoItem> todoItems = LiveDataTestUtil.getValue(activityRule.getActivity().getTodoItemViewModel().getTodoItemRepository().getAllTodoItems());
                activityRule.getActivity().getTodoItemViewModel().getAdapter().setTodoItems(todoItems);
                onView(withText(R.id.todo_items_list));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    @After
    public void closeDb(){
        db.close();
    }


}
