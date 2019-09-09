package com.yakuphanbigic.attendancetracker.ui.main;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import java.util.ArrayList;

public class PageViewModel extends ViewModel {

    private ArrayList<Course> scheduleData;
    private MutableLiveData<Integer> mIndex = new MutableLiveData<>();
    private LiveData<ArrayList<Course>> mText = Transformations.map(mIndex, new Function<Integer, ArrayList<Course>>() {
        @Override
        public ArrayList<Course> apply(Integer input) {
            return scheduleData;
        }
    });

    public void setIndex(int index) {
        mIndex.setValue(index);
    }

    public void setData(ArrayList<Course> data) { scheduleData = data; }

    public LiveData<ArrayList<Course>> getText() {
        return mText;
    }
}