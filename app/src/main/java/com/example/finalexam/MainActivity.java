package com.example.finalexam;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Date;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {

    private DBAssistant db;
    private LinkedList<Task> linkedList;
    private RecyclerAdapter mAdapter;
    protected boolean viewCompleted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);

        viewCompleted = false;

        db = new DBAssistant(this);
        linkedList = new LinkedList<>();
        mAdapter = new RecyclerAdapter(this, linkedList);

        populateList();

        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onDestroy() {
        db.close();
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.changeVisibility) {
            viewCompleted = !viewCompleted;
            linkedList.clear();
            populateList();
            mAdapter.notifyDataSetChanged();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void addNewTask(View view) {
        Intent intent = new Intent(this, NewTaskActivity.class);
        startActivityForResult(intent, 0);
    }

    public void openDetailActivity(View view) {
        Intent intent = new Intent(this, DetailActivity.class);
        int id = (int) view.getTag();
        Task task = getTaskWithId(id);

        intent.putExtra("id", id);
        intent.putExtra("name", task.get_name());
        intent.putExtra("completed", task.is_completed());

        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 0 && resultCode == RESULT_OK) { // add task
            String name = intent.getStringExtra("name");
            insert(name);
        } else if (requestCode == 1 && resultCode == RESULT_OK) {// edit task
            int id = intent.getIntExtra("id", 0);
            String name = intent.getStringExtra("name");
            boolean completed = intent.getBooleanExtra("completed", false);

            update(id, name, completed);
        }

        super.onActivityResult(requestCode, resultCode, intent);
    }

    private void insert(String name) {
        Task newTask = db.addTask(name);
        if (newTask != null) {
            linkedList.add(newTask);
            mAdapter.notifyDataSetChanged();
        }
    }

    private void update(int id, String name, boolean completed) {
        Task updatedTask = db.updateTask(id, name, completed);
        if (updatedTask != null) {
            linkedList.remove(getTaskWithId(id));
            linkedList.add(updatedTask);
            mAdapter.notifyDataSetChanged();
        }
    }

    private Task getTaskWithId(int id) {
        for (Task task: linkedList) {
            if (task.get_id() == id) return task;
        }
        return null;
    }

    private void populateList() {
        Cursor cursor = db.getEntries();
        int sizeCursor = cursor.getCount();

        if (sizeCursor != 0) {
            if (cursor.moveToFirst()) {
                do {
                    int id = cursor.getInt(cursor.getColumnIndex("_id"));
                    String name = cursor.getString(cursor.getColumnIndex("name"));
                    Date creationDate = new Date(cursor.getLong(cursor.getColumnIndex("creationDate")));
                    int completed = cursor.getInt(cursor.getColumnIndex("completed"));
                    boolean bCompleted = (completed == 1);
                    if (bCompleted == viewCompleted)
                        linkedList.add(new Task(id, name, creationDate, bCompleted));
                } while (cursor.moveToNext());
            }
        }
        cursor.close();
    }


}
