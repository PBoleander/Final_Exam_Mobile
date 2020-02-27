package com.example.finalexam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class NewTaskActivity extends AppCompatActivity {

    private Intent self;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);

        self = new Intent();
    }

    public void save(View view) {
        String name = ((EditText) findViewById(R.id.editTaskDetails)).getText().toString();

        if (!name.isEmpty()) {

            self.putExtra("name", name);

            setResult(RESULT_OK, self);
            finish();
        } else {
            setResult(RESULT_CANCELED, self);
            Toast.makeText(this, "Name cannot be empty!", Toast.LENGTH_SHORT).show();
        }

    }

    public void cancel(View view) {
        setResult(RESULT_CANCELED, self);

        finish();
    }
}
