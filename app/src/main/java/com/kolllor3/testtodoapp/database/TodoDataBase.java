package com.kolllor3.testtodoapp.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.kolllor3.testtodoapp.model.TodoItem;
import com.kolllor3.testtodoapp.utils.Utilities;

//init the db by adding which entities(model classes) are in the database and the iteration of the database
//this needs to be increased every time the schema of the database changes
@Database(entities = {TodoItem.class}, version = 1, exportSchema = false)
public abstract class TodoDataBase extends RoomDatabase {
    public abstract TodoItemDao getTodoItemDao();

    private static TodoDataBase INSTANCE;

    public static TodoDataBase getDatabase(Context context) {
        if (Utilities.isNull(INSTANCE )) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), TodoDataBase.class, "todoDb").build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
