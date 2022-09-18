package com.example.botsinred.fragments.dose;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.botsinred.R;
import com.example.botsinred.database.Data;
import com.example.botsinred.models.CategoryModel;
import com.example.botsinred.models.ScheduleModel;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AddDoseFragment extends Fragment {


    private EditText editTextPillName;
    private TextView textViewPillQty;
    private RoundedImageView roundedImageViewDecrease, roundedImageViewIncrease;
    private Button buttonAddPill;

    String doseName, doseTime, doseCategory, pillName, pillQty;
    public AddDoseFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        doseName = getArguments().getString("doseName");
        doseTime = getArguments().getString("doseTime");
        doseCategory = getArguments().getString("doseCategory");

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_dose, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //initialize class variables
        initialize();

        //add listeners
        addListeners();
    }

    private void addListeners() {
        roundedImageViewIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pills = getPills();
                setPills( pills + 1 );
            }
        });
        roundedImageViewDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pills = getPills();
                setPills( pills - 1 );
            }
        });
        buttonAddPill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( validateEntries() ){
                    Fragment fragment = new DosesFragment();
                    Data data = new Data();
                    ArrayList<ScheduleModel> schedules = data.getSchedule();
                    ArrayList<CategoryModel> categories = null;
                    HashMap<String, Integer> pills = null;


                    //handles if another dose is being added to the same time
                    int i = 0;
                    for( ScheduleModel schedule : schedules ){
                        if( schedule.getTime().equals(doseTime) ){
                            categories = schedule.getCategories();
                            break;
                        }
                        ++ i;
                    }
                    //no schedule at given time
                    if( i == schedules.size() ){
                        categories = new ArrayList<>();
                        pills = new HashMap<>();
                        pills.put( pillName, Integer.parseInt(pillQty) );
                        categories.add(new CategoryModel(doseCategory, pills));
                        schedules.add(new ScheduleModel(doseTime, doseName, categories, new Date()));
                        data.setSchedule(schedules);
                        loadFragment(fragment);
                        return;
                    }
                    schedules.remove(i);

                    //there's a schedule and hence a category exists at the given time, so we fetch the category list
                    boolean catExist = false;
                    for( CategoryModel category : categories ){
                        if( category.getCategoryName().equals(doseCategory) ){
                            pills = category.getPills();
                            catExist = true;
                            break;
                        }
                    }
                    if( !catExist ){
                        pills = new HashMap<>();
                        pills.put( pillName, Integer.parseInt(pillQty) );
                        categories.add(new CategoryModel(doseCategory, pills));
                        schedules.add(new ScheduleModel(doseTime, doseName, categories, new Date()));
                        data.setSchedule(schedules);
                        loadFragment(fragment);
                        return;
                    }

                    schedules.add(new ScheduleModel(doseTime, doseName, categories, new Date()));

                    //TODO: what to do if the schedule exists, the category exists, the category exists along with the pill
                    data.setSchedule(schedules);
                    loadFragment(fragment);
                }
            }
        });
    }

    private boolean validateEntries() {
        pillName = editTextPillName.getText().toString();
        pillQty = textViewPillQty.getText().toString();
        if( pillName.length() == 0 ){
            showMessage("Please enter a valid pill name");
            return false;
        }
        if( pillQty.equals("0") ){
            showMessage("Pill quantity must be greater than 0");
            return false;
        }
        return true;
    }

    private void initialize() {
        editTextPillName = getView().findViewById(R.id.editTextPillName);
        textViewPillQty = getView().findViewById(R.id.textViewPillQty);
        roundedImageViewDecrease = getView().findViewById(R.id.roundedImageViewDecrease);
        roundedImageViewIncrease = getView().findViewById(R.id.roundedImageViewIncrease);
        buttonAddPill = getView().findViewById(R.id.buttonAddPill);
    }

    //helpers
    private void setPills(int i) {
        if( i <= 0 ){
            i = 1;
        }
        textViewPillQty.setText(Integer.toString(i));
    }

    private int getPills() {
        return Integer.parseInt( textViewPillQty.getText().toString() );
    }
    private void loadFragment(Fragment fragment) {
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frameLayout, fragment)
                .commit();
    }

    private void showMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

}