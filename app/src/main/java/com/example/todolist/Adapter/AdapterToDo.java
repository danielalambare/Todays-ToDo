package com.example.todolist.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist.AddNewTask;
import com.example.todolist.Data.Database;
import com.example.todolist.MainActivity;
import com.example.todolist.Model.ToDoModel;
import com.example.todolist.R;

import java.util.List;

public class AdapterToDo extends RecyclerView.Adapter<AdapterToDo.ViewHolder> {

    private List<ToDoModel> todoList;
    private MainActivity mainActivity;
    private Database db;

    public AdapterToDo(Database db, MainActivity mainActivity){
        this.db = db;
        this.mainActivity = mainActivity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_layout, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position){

        //search for item position in todo list
        db.startDatabase();

        ToDoModel item = todoList.get(position);
        viewHolder.task.setText(item.getTask());
        viewHolder.task.setChecked(toBoolean(item.getStatus()));
        viewHolder.task.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    db.updateStatus(item.getId(), 1);
                }
                else{
                    db.updateStatus(item.getId(), 0);
                }
            }
        });
    }

    //return size of todo list
    public int getItemCount(){
        return todoList.size();
    }
    private boolean toBoolean(int i){
        return i != 0;
    }

    //update recycler view
    public void setTasks(List<ToDoModel> todoList){
        this.todoList = todoList;
        notifyDataSetChanged();
    }

    public Context getContext(){
        return mainActivity;
    }

    public void deleteItem(int position) {
        ToDoModel item = todoList.get(position);
        db.deleteTask(item.getId());
        todoList.remove(position);
        notifyItemRemoved(position);
    }

    public void editItems(int position){
        ToDoModel item = todoList.get(position);
        Bundle bundle = new Bundle();
        bundle.putInt("id", item.getId());
        bundle.putString("task", item.getTask());
        AddNewTask fragment = new AddNewTask();
        fragment.setArguments(bundle);
        fragment.show(mainActivity.getSupportFragmentManager(), AddNewTask.TAG);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        CheckBox task;
        ViewHolder(View view){
            super(view);
            task = view.findViewById(R.id.todoCheck);
        }
    }
}
