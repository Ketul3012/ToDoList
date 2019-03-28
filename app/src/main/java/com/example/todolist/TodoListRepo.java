package com.example.todolist;

        import android.app.Application;
        import android.os.AsyncTask;

        import java.util.List;

        import androidx.lifecycle.LiveData;

public class TodoListRepo {
    private TodoListInterface todoListInterface;
    private LiveData<List<ToDoListData>> allnotes;

    public TodoListRepo(Application application)
    {
        ToDoListDataBase dataBase = ToDoListDataBase.getInstance(application);
        todoListInterface = dataBase.todoListInterface();
        allnotes = todoListInterface.getAllnotes();
    }
     public void insert(ToDoListData data)
    {
        new InsertNoteAsynce(todoListInterface).execute(data);
    }

    public void update(ToDoListData data)
    {
        new UpdateNoteAsynce(todoListInterface).execute(data);
    }

    public void delete(ToDoListData data)
    {
        new DeleteNoteAsynce(todoListInterface).execute(data);
    }
    public void deleteAll()
    {
        new DeleteAllNoteAsynce(todoListInterface).execute();
    }

    public LiveData<List<ToDoListData>> getAllnotes()
    {
        return allnotes;
    }

    private static class InsertNoteAsynce extends AsyncTask<ToDoListData , Void , Void>
    {
        private TodoListInterface todoListInterface;

        public InsertNoteAsynce(TodoListInterface todoListInterface)
        {
            this.todoListInterface = todoListInterface;
        }

        @Override
        protected Void doInBackground(ToDoListData... toDoListData) {
            todoListInterface.insert(toDoListData[0]);
            return null;
        }
    }

    private static class DeleteNoteAsynce extends AsyncTask<ToDoListData , Void , Void>
    {
        private TodoListInterface todoListInterface;

        public DeleteNoteAsynce(TodoListInterface todoListInterface)
        {
            this.todoListInterface = todoListInterface;
        }

        @Override
        protected Void doInBackground(ToDoListData... toDoListData) {
            todoListInterface.delete(toDoListData[0]);
            return null;
        }
    }

    private static class UpdateNoteAsynce extends AsyncTask<ToDoListData , Void , Void>
    {
        private TodoListInterface todoListInterface;

        public UpdateNoteAsynce(TodoListInterface todoListInterface)
        {
            this.todoListInterface = todoListInterface;
        }

        @Override
        protected Void doInBackground(ToDoListData... toDoListData) {
            todoListInterface.update(toDoListData[0]);
            return null;
        }
    }

    private static class DeleteAllNoteAsynce extends AsyncTask<Void , Void , Void>
    {
        private TodoListInterface todoListInterface;

        public DeleteAllNoteAsynce(TodoListInterface todoListInterface)
        {
            this.todoListInterface = todoListInterface;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            todoListInterface.deleteAll();
            return null;
        }
    }


}
