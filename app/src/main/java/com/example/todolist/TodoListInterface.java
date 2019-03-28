package com.example.todolist;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface TodoListInterface {

    @Insert
    void insert(ToDoListData data);

    @Delete
    void delete(ToDoListData data);

    @Update
    void update(ToDoListData data);

    @Query("Delete From to_do_list")
    void deleteAll();

    @Query("Select * From to_do_list Order BY priority Desc")
    LiveData<List<ToDoListData>> getAllnotes();



}
