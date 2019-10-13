package com.kolllor3.testtodoapp.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "todoItems")
public class TodoItem {

    @PrimaryKey(autoGenerate = true) //by setting autoGenerate to true auto increment the primary key
    public int id;

    public int reminderId;

    public boolean isDone;

    public String title;

    public long endDate;

    @Ignore
    public String endDateString;

    public TodoItem() {
    }

    @Ignore
    public TodoItem(int reminderId, String title, long endDate) {
        this.reminderId = reminderId;
        this.title = title;
        this.endDate = endDate;
    }

    public boolean isEqual(TodoItem item){
        return reminderId == item.reminderId && isDone == item.isDone && title.equals(item.title) && endDate == item.endDate;
    }

}
