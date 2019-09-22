package com.kolllor3.testtodoapp;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.kolllor3.testtodoapp.utils.Utilities;

import java.util.Calendar;
import java.util.Date;

public class AddItemActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private EditText titleEditText;
    private Button doneBeforeButton;
    private Spinner reminderSpinner;

    private String title;
    private int reminderId;
    private long endDate;

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

        Calendar c = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, AddItemActivity.this, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_YEAR));

        doneBeforeButton = findViewById(R.id.done_before_input);
        doneBeforeButton.setOnClickListener(click ->{
            datePickerDialog.show();
        });
        titleEditText = findViewById(R.id.title_input);
        reminderSpinner = findViewById(R.id.reminder_spinner);

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1, getResources().getStringArray(R.array.reminder_spinner_options));
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_expandable_list_item_1);
        reminderSpinner.setAdapter(spinnerAdapter);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> getDataFromInputs());
    }

    private void getDataFromInputs(){
        title = titleEditText.getText().toString();
        reminderId = reminderSpinner.getSelectedItemPosition();
        if(isDataValid(title, reminderId, endDate)){
            Intent returnIntent = new Intent();
            Bundle b = new Bundle();
            b.putString("title", title);
            b.putInt("reminderId", reminderId);
            b.putLong("endDate", endDate);
            returnIntent.putExtras(b);
            setResult(Activity.RESULT_OK, returnIntent);
            finish();
        }
    }

    private boolean isDataValid(String title, int reminderId, long endDate){
        if(!(Utilities.isNotNull(title) && !title.isEmpty())){
            titleEditText.setError(getString(R.string.title_error_string));
            return false;
        }
        if(reminderId < 0 || reminderId > reminderSpinner.getCount() - 1){
            reminderSpinner.setBackgroundColor(Color.RED);
            return false;
        }
        if(endDate < 0){
            doneBeforeButton.setTextColor(Color.RED);
            return false;
        }
        return true;
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

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(year, month, dayOfMonth);
        endDate = c.getTimeInMillis();
    }
}
