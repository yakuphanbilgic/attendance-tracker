package com.yakuphanbigic.attendancetracker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yakuphanbigic.attendancetracker.ui.main.Course;
import com.yakuphanbigic.attendancetracker.ui.main.SectionsPagerAdapter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // YAKUP

        Intent getIntent = getIntent();
        String courseName = getIntent.getStringExtra("courseName");
        String day = getIntent.getStringExtra("day");
        String attendance = getIntent.getStringExtra("attendance");

        if(attendance != null) {
            Course newCourse = new Course(courseName, day, Integer.valueOf(attendance));

            SharedPreferences mPrefs = getSharedPreferences("myPrefs", MODE_PRIVATE);
            SharedPreferences.Editor prefsEditor = mPrefs.edit();
            String id = mPrefs.getString("id", "1");

            final SharedPreferences appSharedPrefs = getSharedPreferences("myPrefs", MODE_PRIVATE);
            String courseJson = new String();
            ObjectMapper mapper = new ObjectMapper();
            try {
                courseJson = mapper.writeValueAsString(newCourse);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            prefsEditor.putString("course" + id, courseJson);
            prefsEditor.putString("id", Integer.toString( Integer.valueOf(id) + 1));
            prefsEditor.apply();
        }

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        Context context = getApplicationContext();

        SharedPreferences.Editor prefEditor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        prefEditor.putString("1", "Pazartesi");
        prefEditor.putString("2", "Salı");
        prefEditor.putString("3", "Çarşamba");
        prefEditor.putString("4", "Perşembe");
        prefEditor.putString("5", "Cuma");
        prefEditor.apply();
    }
}