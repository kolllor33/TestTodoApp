package com.kolllor3.testtodoapp.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.kolllor3.testtodoapp.model.TodoItem;

import java.util.List;

@Dao
public interface TodoItemDao {

    //LiveData is used for updating ui automatically whit observers
    @Query("SELECT * FROM todoItems")
    LiveData<List<TodoItem>> getAllTodoItems();

    @Query("SELECT * FROM todoItems ORDER BY endDate")
    LiveData<List<TodoItem>> getAllTodoItemsOrderEndDate();

    @Insert
    void insertAll(TodoItem... TodoLists);

    @Delete
    void delete(TodoItem item);

    @Query("UPDATE todoItems SET isDone=:state WHERE id=:id")
    void updateDoneStateById(int id, boolean state);

}
