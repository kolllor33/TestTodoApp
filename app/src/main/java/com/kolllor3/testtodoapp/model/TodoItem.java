package com.kolllor3.testtodoapp.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "todoItems")
public class TodoItem {

    @PrimaryKey(autoGenerate = true) //by setting autoGenerate to true auto increment the primary key
    public int id;

    public int reminderId;

    public boolean isDone;

    public String title;

    public long endDate;

}
