package com.example.finalexam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class DetailActivity extends AppCompatActivity {

    private Intent self;
    private EditText titleTask;
    private CheckBox completedTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        titleTask = findViewById(R.id.editTaskDetails);
        completedTask = findViewById(R.id.checkboxCompleteDetails);

        titleTask.setText(getIntent().getStringExtra("name"));
        completedTask.setChecked(getIntent().getBooleanExtra("completed", false));

        self = new Intent();
    }

    public void save(View view) {
        String name = titleTask.getText().toString();

        if (!name.isEmpty()) {
            boolean completed = completedTask.isChecked();

            self.putExtra("id", getIntent().getIntExtra("id", 0));
            self.putExtra("name", name);
            self.putExtra("completed", completed);

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
