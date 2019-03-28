package com.example.todolist;

import android.content.Context;
import android.os.AsyncTask;
//import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = ToDoListData.class , version = 1)
public abstract class ToDoListDataBase extends RoomDatabase {

    private static ToDoListDataBase instance;

    public abstract TodoListInterface todoListInterface();

    public static synchronized ToDoListDataBase getInstance(Context context)
    {   if (instance == null)
        {
        instance = Room.databaseBuilder(context.getApplicationContext() , ToDoListDataBase.class
                ,"to_do_database").fallbackToDestructiveMigration()
                .addCallback(callback)
                .build();
        }

        return instance;
    }

    private static RoomDatabase.Callback callback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDb(instance).execute();
        }
    };

    private static class PopulateDb extends AsyncTask<Void,Void,Void>
    {   private TodoListInterface todoListInterface;

        public PopulateDb( ToDoListDataBase toDoListDataBase)
        {
            this.todoListInterface = toDoListDataBase.todoListInterface();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            todoListInterface.insert(new ToDoListData("Use + Button to add what to" , "do and when to do also it's Priority" , 1));
            return null;
        }
    }


}
