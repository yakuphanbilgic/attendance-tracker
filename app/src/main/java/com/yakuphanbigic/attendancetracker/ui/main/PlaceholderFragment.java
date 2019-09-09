package com.yakuphanbigic.attendancetracker.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.arch.lifecycle.ViewModelProviders;
import android.widget.Toast;

import com.yakuphanbigic.attendancetracker.ClassAdapter;
import com.yakuphanbigic.attendancetracker.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

    private HashMap<Integer, ArrayList<Course>> scheduleData = new HashMap<>();
    private ArrayList<Course> mondayData = new ArrayList<Course>(2);
    private ArrayList<Course> tuesdayData = new ArrayList<Course>(2);
    private ArrayList<Course> wednesdayData = new ArrayList<Course>(2);
    private ArrayList<Course> thursdayData = new ArrayList<Course>(2);
    private ArrayList<Course> fridayData = new ArrayList<Course>(2);
    private ListView classList;
    private ArrayList<Course> classValues = new ArrayList<>(2);
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
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        index = 1;

        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }

        mondayData.add(0, new Course("pazartesi1"));
        mondayData.add(1, new Course("pazartesi2"));

        tuesdayData.add(0, new Course("salı1"));
        tuesdayData.add(1, new Course("salı2"));

        wednesdayData.add(0, new Course("çarşamba1"));
        wednesdayData.add(1, new Course("çarşamba2"));

        thursdayData.add(0, new Course("perşembe1"));
        thursdayData.add(1, new Course("perşembe2"));

        fridayData.add(0, new Course("cuma1"));
        fridayData.add(1, new Course("cuma2"));

        scheduleData.put(1, mondayData);
        scheduleData.put(2, tuesdayData);
        scheduleData.put(3, wednesdayData);
        scheduleData.put(4, thursdayData);
        scheduleData.put(5, fridayData);

        classValues.clear();

        classValues.add(0, scheduleData.get(index).get(0));
        classValues.add(1, scheduleData.get(index).get(1));

        ClassAdapter adapter = new ClassAdapter(getActivity().getApplicationContext(), classValues);

        classList = root.findViewById(R.id.selected_food_list);
        classList.setAdapter(adapter);

        classList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getActivity().getApplicationContext(), Integer.toString(scheduleData.get(index).get(i).getmQuantity()) , Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }
}