package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import com.example.todolist.Adapter.AdapterToDo;
import com.example.todolist.Data.Database;
import com.example.todolist.Model.ToDoModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DialogCloseListener {

    private RecyclerView tasksRV;
    private AdapterToDo adapterToDo;

    private List<ToDoModel> taskList;
    private Database db;

    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        db = new Database(this);
        db.startDatabase();

        tasksRV = findViewById(R.id.RecyclerView);
        tasksRV.setLayoutManager(new LinearLayoutManager(this));
        adapterToDo = new AdapterToDo(db, this);
        tasksRV.setAdapter(adapterToDo);

        fab.findViewById(R.id.fab);

        taskList = db.getAllTasks();
        Collections.reverse(taskList);
        adapterToDo.setTasks(taskList);

        fab.setOnClickListener(view -> AddNewTask.newIstance().show(getSupportFragmentManager(), AddNewTask.TAG));
    }

    @Override
    public void handleDialogClose(DialogInterface dialog) {
        taskList = db.getAllTasks();
        Collections.reverse(taskList);
        adapterToDo.setTasks(taskList);
        adapterToDo.notifyDataSetChanged();
    }
}