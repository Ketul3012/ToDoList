package com.example.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

public class MainActivity extends AppCompatActivity {
    public static final int ADD_NOTE_CODE = 1;
    public static final int EDIT_NOTE_CODE = 2;
    private ToDoListViewModel toDoListViewModel;
    private LiveData<List<ToDoListData>> allnotes;
    private FloatingActionButton floatingActionButton;

    private static long backtime;
    private Toast backtoast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        floatingActionButton = findViewById(R.id.fa_first);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Add_Edit_activity.class);
                startActivityForResult(intent, ADD_NOTE_CODE);

            }
        });

        RecyclerView recyclerView = findViewById(R.id.rv_main);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        final ToDOAdapter toDOAdapter = new ToDOAdapter();
        recyclerView.setAdapter(toDOAdapter);
        toDoListViewModel = ViewModelProviders.of(this).get(ToDoListViewModel.class);
        toDoListViewModel.getAllnotes().observe(this, new Observer<List<ToDoListData>>() {
            @Override
            public void onChanged(List<ToDoListData> toDoListData) {
                toDOAdapter.submitList(toDoListData);

            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.ANIMATION_TYPE_DRAG|
                ItemTouchHelper.ACTION_STATE_DRAG|ItemTouchHelper.UP|ItemTouchHelper.LEFT
                ,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                ToDoListData data = toDOAdapter.getNote(viewHolder.getAdapterPosition());
                toDoListViewModel.delete(data);
                Toast.makeText(MainActivity.this, "Note Deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        toDOAdapter.setItemClickListner(new ToDOAdapter.onItemClickListner() {
            @Override
            public void onItemClick(ToDoListData data) {
                Intent intent = new Intent(MainActivity.this , Add_Edit_activity.class);
                intent.putExtra(Add_Edit_activity.EXTRA_TITLE,data.getWhat_to_do());
                intent.putExtra(Add_Edit_activity.EXTRA_DESCRIPTION ,data.getWhen_to_do());
                intent.putExtra(Add_Edit_activity.EXTRA_PRIORITY ,data.getPriority());
                intent.putExtra( Add_Edit_activity.EXTRA_ID ,data.getId());
                startActivityForResult(intent , EDIT_NOTE_CODE);

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (resultCode == RESULT_OK && requestCode == ADD_NOTE_CODE) {
            String whattodo = data.getStringExtra(Add_Edit_activity.EXTRA_TITLE);
            String whentodo = data.getStringExtra(Add_Edit_activity.EXTRA_DESCRIPTION);
            String priority = data.getStringExtra(Add_Edit_activity.EXTRA_PRIORITY);

            ToDoListData data1 = new ToDoListData(whattodo, whentodo, Integer.parseInt(priority));
            toDoListViewModel.insert(data1);
            Toast.makeText(this, "Note Saved", Toast.LENGTH_SHORT).show();
        }else if(resultCode == RESULT_OK && requestCode == EDIT_NOTE_CODE) {
            int id = data.getIntExtra(Add_Edit_activity.EXTRA_ID , -1);

            if (id == -1)
            {
                Toast.makeText(this, "Note not Updated", Toast.LENGTH_SHORT).show();
                return;
            }
            String whattodo = data.getStringExtra(Add_Edit_activity.EXTRA_TITLE);
            String whentodo = data.getStringExtra(Add_Edit_activity.EXTRA_DESCRIPTION);
            String priority = data.getStringExtra(Add_Edit_activity.EXTRA_PRIORITY);

            ToDoListData data1 = new ToDoListData(whattodo, whentodo, Integer.parseInt(priority));
            data1.setId(id);
            toDoListViewModel.update(data1);
            Toast.makeText(this, "Note Updated", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "Note Not Saved", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_all:
                toDoListViewModel.deleteAll();
                Toast.makeText(this, "Notes Deleted", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        if(backtime + 2000 > System.currentTimeMillis())
        {
            backtoast.cancel();
            super.onBackPressed();
        }else
        {
            backtoast = Toast.makeText(getBaseContext(), "Press back again to exit",Toast.LENGTH_LONG);
            backtoast.show();
        }
        backtime = System.currentTimeMillis();
    }
}
