package com.yakuphanbigic.attendancetracker.ui.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.support.annotation.NonNull;
import com.fasterxml.jackson.databind.ObjectMapper;
import android.support.v4.app.Fragment;
import android.arch.lifecycle.ViewModelProviders;
import android.widget.Toast;
import com.yakuphanbigic.attendancetracker.ClassAdapter;
import com.yakuphanbigic.attendancetracker.R;
import com.yakuphanbigic.attendancetracker.addNewCourse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import static android.content.Context.MODE_PRIVATE;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {
    private HashMap<Integer, ArrayList<Course>> scheduleData = new HashMap<>();
    private ArrayList<Course> mondayData = new ArrayList<>(5);
    private ArrayList<Course> tuesdayData = new ArrayList<>(5);
    private ArrayList<Course> wednesdayData = new ArrayList<>(5);
    private ArrayList<Course> thursdayData = new ArrayList<>(5);
    private ArrayList<Course> fridayData = new ArrayList<>(5);
    private ListView classList;
    private FloatingActionButton addNewCourse;
    private ArrayList<Course> classValues = new ArrayList<>(5);
    private static final String ARG_SECTION_NUMBER = "section_number";
    private PageViewModel pageViewModel;
    private int index;

    public static PlaceholderFragment newInstance(int index) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);

        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
        int index = 1;

        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }

        pageViewModel.setIndex(index);
        pageViewModel.setData(scheduleData.get(index));
    }

    @Override
    public View onCreateView( @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        index = 1;

        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }

        SharedPreferences mPrefs = getActivity().getSharedPreferences("myPrefs", MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();

        String count = mPrefs.getString("id", "1");

        mondayData.clear();
        tuesdayData.clear();
        wednesdayData.clear();
        thursdayData.clear();
        fridayData.clear();

        for(int i = 1; i < Integer.valueOf(count) + 1; i++) {
            Course currentCourse = new Course("", "", -1);

            final ObjectMapper mapper2 = new ObjectMapper();
            try {
                mPrefs = getActivity().getSharedPreferences("myPrefs", MODE_PRIVATE);
                String json2 = mPrefs.getString("course" + i , "");
                currentCourse = mapper2.readValue(json2, Course.class);

            } catch (IOException e) {
                e.printStackTrace();
            }

            if(currentCourse.getmDay() != "") {
                switch (currentCourse.getmDay()) {
                    case "Monday":
                        mondayData.add(currentCourse);
                        break;
                    case "Tuesday":
                        tuesdayData.add(currentCourse);
                        break;
                    case "Wednesday":
                        wednesdayData.add(currentCourse);
                        break;
                    case "Thursday":
                         thursdayData.add(currentCourse);
                         break;
                    case "Friday":
                        fridayData.add(currentCourse);
                        break;
                }
            }
        }

        scheduleData.put(1, mondayData);
        scheduleData.put(2, tuesdayData);
        scheduleData.put(3, wednesdayData);
        scheduleData.put(4, thursdayData);
        scheduleData.put(5, fridayData);

        classValues.clear();

        for(int i = 0; i < scheduleData.get(index).size(); i++){
            classValues.add(i, scheduleData.get(index).get(i));
        }

        ClassAdapter adapter = new ClassAdapter(getActivity().getApplicationContext(), classValues);

        classList = root.findViewById(R.id.selected_food_list);
        classList.setAdapter(adapter);

        classList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(getActivity().getApplicationContext(), Integer.toString(scheduleData.get(index).get(i).getmQuantity()) , Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        addNewCourse = getView().findViewById(R.id.addNewCourse);

        addNewCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentToNewCourse = new Intent(getActivity(), addNewCourse.class);
                startActivity(intentToNewCourse);
            }
        });
    }
}