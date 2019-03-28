package com.example.todolist;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "to_do_list")
public class ToDoListData {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String what_to_do;
    private String when_to_do;
    private int priority;

    public ToDoListData(String what_to_do, String when_to_do, int priority) {
        this.what_to_do = what_to_do;
        this.when_to_do = when_to_do;
        this.priority = priority;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWhat_to_do() {
        return what_to_do;
    }

    public String getWhen_to_do() {
        return when_to_do;
    }

    public int getPriority() {
        return priority;
    }
}
