package com.kolllor3.testtodoapp.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.kolllor3.testtodoapp.model.TodoItem;

//init the db by adding which entities(model classes) are in the database and the iteration of the database
//this needs to be increased every time the schema of the database changes
@Database(entities = {TodoItem.class}, version = 1, exportSchema = false)
public abstract class TodoDataBase extends RoomDatabase {
    public abstract TodoItemDao getTodoItemDao();
}
