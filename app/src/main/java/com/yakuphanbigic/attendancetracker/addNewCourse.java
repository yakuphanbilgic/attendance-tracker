package com.yakuphanbigic.attendancetracker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.yakuphanbigic.attendancetracker.ui.main.Course;

public class addNewCourse extends AppCompatActivity {

    private EditText courseName;
    private Spinner daySpinner;
    private Spinner attendanceSpinner;
    private Button saveButton;
    private Boolean flagName;
    private Boolean flagDay;
    private Boolean flagAttendance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_course);

        courseName = findViewById(R.id.courseName);
        daySpinner = findViewById(R.id.daySpinner);
        attendanceSpinner = findViewById(R.id.attendanceSpinner);
        saveButton = findViewById(R.id.buttonAdd);

        String[] days = new String[] {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
        final Integer[] attendance = new Integer[] {0, 1, 2, 3, 4};

        flagDay = false;
        flagName = false;
        flagAttendance = false;

        courseName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b){
                    courseName.setText("");
                    flagName = true;
                }
            }
        });

        final ArrayAdapter<String> dayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, days);
        final ArrayAdapter<Integer> attendanceAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, attendance);

        daySpinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                daySpinner.setAdapter(dayAdapter);
                flagDay = true;
                return false;
            }
        });

        attendanceSpinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                attendanceSpinner.setAdapter(attendanceAdapter);
                flagAttendance = true;
                return false;
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flagDay && flagName &&  flagAttendance && !courseName.getText().toString().equals("")) {
                    Intent intentToMain = new Intent(getApplicationContext(), MainActivity.class);
                    intentToMain.putExtra("courseName", courseName.getText().toString());
                    intentToMain.putExtra("day", daySpinner.getSelectedItem().toString());
                    intentToMain.putExtra("attendance", attendanceSpinner.getSelectedItem().toString());

                    startActivity(intentToMain);
                }
                else {
                    Toast.makeText(addNewCourse.this, "Please complete the form!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
