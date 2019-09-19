package com.kolllor3.testtodoapp;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.kolllor3.testtodoapp.utils.Utilities;

public class AddItemActivity extends AppCompatActivity {

    private EditText doneBeforeEditText, titleEditText;
    private Spinner reminderSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //display the back button in the top left
        if(Utilities.isNotNull(getSupportActionBar())) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        doneBeforeEditText = findViewById(R.id.done_before_input);
        titleEditText = findViewById(R.id.title_input);
        reminderSpinner = findViewById(R.id.reminder_spinner);

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1, getResources().getStringArray(R.array.reminder_spinner_options));
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_expandable_list_item_1);
        reminderSpinner.setAdapter(spinnerAdapter);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //when home is pressed(left top corner arrow) go back to previous activity
        if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
