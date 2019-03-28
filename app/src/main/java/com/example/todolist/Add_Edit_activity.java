package com.example.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

public class Add_Edit_activity extends AppCompatActivity {
    public static final String EXTRA_ID =
            "EXTRA_ID";
    public static final String EXTRA_TITLE =
            "EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION =
            "EXTRA_DESCRIPTION";
    public static final String EXTRA_PRIORITY =
            "EXTRA_PRIORITY";


    private EditText editTextTitle;
    private EditText editTextDescription;
    private NumberPicker numberPickerPriority;
    private ToDoListViewModel toDoListViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_activity);

        editTextTitle = findViewById(R.id.ev_addwhattodo);
        editTextDescription = findViewById(R.id.ev_addwhentodo);
        numberPickerPriority = findViewById(R.id.np_first);
        toDoListViewModel = ViewModelProviders.of(this).get(ToDoListViewModel.class);

        numberPickerPriority.setMinValue(1);
        numberPickerPriority.setMaxValue(10);
        numberPickerPriority.setValue(toDoListViewModel.getPriporityValue());
        numberPickerPriority.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                toDoListViewModel.setPriorityValue(newVal);
            }
        });
        Intent data = getIntent();
        if (data.hasExtra(EXTRA_ID))
        {
         setTitle("Edit Note");
         editTextTitle.setText(data.getStringExtra(EXTRA_TITLE));
         editTextDescription.setText(data.getStringExtra(EXTRA_DESCRIPTION));
         numberPickerPriority.setValue(data.getIntExtra(EXTRA_PRIORITY, 1));
        }else {
            setTitle("Add Note");
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.to_do_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.iv_save:
                saveNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }

    }

    private void saveNote() {
        String whattoDo = editTextTitle.getText().toString().trim();
        String whentoDo = editTextDescription.getText().toString().trim();
        String priority = String.valueOf(numberPickerPriority.getValue());

        if (whattoDo.trim().isEmpty() || whentoDo.trim().isEmpty()) {
            Toast.makeText(this, "Please insert a title and description", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE, whattoDo);
        data.putExtra(EXTRA_DESCRIPTION, whentoDo);
        data.putExtra(EXTRA_PRIORITY, priority);

        int id = getIntent().getIntExtra(EXTRA_ID , -1);
        if (id != -1){
            data.putExtra(EXTRA_ID , id);
        }
        setResult(RESULT_OK, data);
        finish();

    }

}




