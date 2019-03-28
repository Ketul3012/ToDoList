package com.example.todolist;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class ToDoListViewModel extends AndroidViewModel {
    private TodoListRepo todoListRepo;
    private LiveData<List<ToDoListData>> allnotes;
    private MutableLiveData<Integer> priorityValueLiveData = new MutableLiveData<>();
    public ToDoListViewModel(@NonNull Application application) {
        super(application);
        todoListRepo = new TodoListRepo(application);
        allnotes = todoListRepo.getAllnotes();
        priorityValueLiveData.setValue(1);
    }

    public void setPriorityValue(int priorityValue){
        priorityValueLiveData.setValue(priorityValue);
    }

    public int getPriporityValue(){
        return priorityValueLiveData.getValue();
    }

    public void insert(ToDoListData data)
    {
        todoListRepo.insert(data);
    }

    public void update(ToDoListData data)
    {
        todoListRepo.update(data);
    }

    public void delete(ToDoListData data)
    {
        todoListRepo.delete(data);
    }
    public void deleteAll()
    {
        todoListRepo.deleteAll();
    }

    public LiveData<List<ToDoListData>> getAllnotes()
    {
        return allnotes;
    }

}
