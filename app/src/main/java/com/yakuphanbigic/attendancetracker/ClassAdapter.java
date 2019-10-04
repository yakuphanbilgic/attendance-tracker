package com.yakuphanbigic.attendancetracker;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yakuphanbigic.attendancetracker.ui.main.Course;

import java.io.IOException;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class ClassAdapter extends ArrayAdapter<Course>{
    private List<Course> list;
    private Context context;

    TextView currentFoodName,
            currentCost,
            quantityText,
            addMeal,
            subtractMeal,
            removeMeal;

    public ClassAdapter(Context context, List<Course> myOrders) {
        super(context, 0, myOrders);
        this.list = myOrders;
        this.context = context;
    }


    public View getView(final int position, final View convertView, ViewGroup parent){
        View listItemView = convertView;
        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_schedule,parent,false
            );
        }

        final Course currentFood = getItem(position);

        currentFoodName = listItemView.findViewById(R.id.selected_food_name);
        subtractMeal = listItemView.findViewById(R.id.minus_meal);
        quantityText = listItemView.findViewById(R.id.quantity);
        addMeal = listItemView.findViewById(R.id.plus_meal);
        removeMeal = listItemView.findViewById(R.id.delete_item);

        //Set the text of the meal, amount and quantity
        currentFoodName.setText(currentFood.getmName());
        quantityText.setText("x "+ currentFood.getmQuantity());

        //OnClick listeners for all the buttons on the ListView Item
        addMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences mPrefs = context.getSharedPreferences("myPrefs", MODE_PRIVATE);
                SharedPreferences.Editor prefsEditor = mPrefs.edit();
                String id = mPrefs.getString("id", "");
                String json = "";

                for(int i = 1; i < Integer.valueOf(id); i++){
                    Course currentCourse = new Course();

                    final ObjectMapper mapper2 = new ObjectMapper();
                    try {
                        json = mPrefs.getString("course" + i , "");
                        currentCourse = mapper2.readValue(json, Course.class);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    if(json != "" && currentCourse.getmName().equals(list.get(position).getmName())) {
                        currentFood.addToQuantity();

                        if(currentFood.getmQuantity() == 1){
                            Toast.makeText(context, "Everyone misses classes once. Hope you are okay.", Toast.LENGTH_LONG).show();
                        }

                        if(currentFood.getmQuantity() == 2){
                            Toast.makeText(context, "Don't let this become a habit okay?", Toast.LENGTH_LONG).show();
                        }

                        if(currentFood.getmQuantity() == 3){
                            Toast.makeText(context, "You are getting closer to the danger zone, careful!", Toast.LENGTH_LONG).show();
                        }

                        if(currentFood.getmQuantity() == 4){
                            Toast.makeText(context, "You are about to fail this course.", Toast.LENGTH_LONG).show();
                        }

                        if(currentFood.getmQuantity() == 5){
                            Toast.makeText(context, "You just failed the course. What are you doing with your life?", Toast.LENGTH_LONG).show();
                        }

                        if(currentFood.getmQuantity() > 5){
                            Toast.makeText(context, "You might as well don't use this app anymore.", Toast.LENGTH_LONG).show();
                        }

                        String courseJson = new String();
                        ObjectMapper mapper = new ObjectMapper();
                        try {
                            courseJson = mapper.writeValueAsString(currentFood);
                        } catch (JsonProcessingException e) {
                            e.printStackTrace();
                        }
                        prefsEditor.putString("course" + i, courseJson);

                        quantityText.setText("x "+ currentFood.getmQuantity() + " absences so far.");
                        notifyDataSetChanged();
                        prefsEditor.commit();
                        break;
                    }
                }
            }
        });

        subtractMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences mPrefs = context.getSharedPreferences("myPrefs", MODE_PRIVATE);
                SharedPreferences.Editor prefsEditor = mPrefs.edit();
                String id = mPrefs.getString("id", "");
                String json = "";

                for(int i = 1; i < Integer.valueOf(id); i++){
                    Course currentCourse = new Course();

                    final ObjectMapper mapper2 = new ObjectMapper();
                    try {
                        json = mPrefs.getString("course" + i , "");
                        currentCourse = mapper2.readValue(json, Course.class);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    if(json != "" && currentCourse.getmName().equals(list.get(position).getmName())) {
                        currentFood.removeFromQuantity();

                        String courseJson = new String();
                        ObjectMapper mapper = new ObjectMapper();
                        try {
                            courseJson = mapper.writeValueAsString(currentFood);
                        } catch (JsonProcessingException e) {
                            e.printStackTrace();
                        }
                        prefsEditor.putString("course" + i, courseJson);
                        quantityText.setText("x "+currentFood.getmQuantity() + " absences so far.");
                        notifyDataSetChanged();
                        prefsEditor.commit();
                        break;
                    }
                }
            }
        });

        removeMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                new AlertDialog.Builder(view.getRootView().getContext())
                        .setTitle("Remove Course")
                        .setMessage("Do you really want to delete this course?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(R.string.dialog_yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                SharedPreferences mPrefs = context.getSharedPreferences("myPrefs", MODE_PRIVATE);
                                SharedPreferences.Editor prefsEditor = mPrefs.edit();
                                String id = mPrefs.getString("id", "");
                                String json = "";

                                for(int i = 1; i < Integer.valueOf(id); i++){
                                    Course currentCourse = new Course();

                                    final ObjectMapper mapper2 = new ObjectMapper();
                                    try {
                                        json = mPrefs.getString("course" + i , "");
                                        currentCourse = mapper2.readValue(json, Course.class);

                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }

                                    if(json != "" && currentCourse.getmName().equals(list.get(position).getmName())) {
                                        Toast.makeText(context, "removed " + list.get(position).getmName(), Toast.LENGTH_SHORT).show();
                                        list.remove(position);
                                        prefsEditor.remove("course" + i);
                                        prefsEditor.commit();
                                        break;
                                    }
                                }

                                notifyDataSetChanged();
                            }})
                        .setNegativeButton(android.R.string.no, null).show();
            }
        });


        return listItemView;
    }
}