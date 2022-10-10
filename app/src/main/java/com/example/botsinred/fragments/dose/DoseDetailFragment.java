package com.example.botsinred.fragments.dose;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.botsinred.R;
import com.example.botsinred.models.ScheduleModel;
import com.example.botsinred.utilities.AlarmReceiver;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class DoseDetailFragment extends Fragment {

    private EditText editTextDoseName;
    private Button buttonTime, buttonSubmit, buttonDate;

    String doseName, doseTime, doseDate;
    MaterialTimePicker picker;
    MaterialDatePicker materialDatePicker;
    public DoseDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dose_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //initializing all class variables
        initializer();

        //add listeners
        addListeners();

    }

    private void addListeners() {
        buttonTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickTime();
            }
        });
        buttonDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickDate();
            }
        });

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( validateEntries() ){
                    Fragment fragment = new PillCategoryFragment();
                    Bundle data = new Bundle();
                    data.putString("doseName", doseName);
                    data.putString("doseTime", doseTime);
                    data.putString("doseDate", doseDate);
                    fragment.setArguments(data);
                    loadFragment(fragment);
                }
            }
        });
    }



    private boolean validateEntries() {
        doseName = editTextDoseName.getText().toString();
        doseTime = buttonTime.getText().toString();
        if( doseName.equals("") ){
            showMessage("Enter a valid dose name");
            return false;
        }
        if( doseTime.length() <= 5 ){
            showMessage("Select time");
            return false;
        }
        return true;
    }

    private void pickDate() {
        MaterialDatePicker.Builder materialDateBuilder = MaterialDatePicker.Builder.datePicker()
                .setTheme(R.style.MaterialCalendarTheme);

        // now define the properties of the
        // materialDateBuilder that is title text as SELECT A DATE
        materialDateBuilder.setTitleText("SELECT A DATE");

        // now create the instance of the material date
        // picker
        materialDatePicker = materialDateBuilder.build();

        // handle select date button which opens the
        // material design date picker
        materialDatePicker.show(getActivity().getSupportFragmentManager(), "MATERIAL_DATE_PICKER");
        // now handle the positive button click from the
        // material design date picker
        materialDatePicker.addOnPositiveButtonClickListener(
                new MaterialPickerOnPositiveButtonClickListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onPositiveButtonClick(Object selection) {

                        // if the user clicks on the positive
                        // button that is ok button update the
                        // selected date
                        doseDate = materialDatePicker.getHeaderText();
                        buttonDate.setText(doseDate);
                        // in the above statement, getHeaderText
                        // is the selected date preview from the
                        // dialog
                    }
                });
    }

    private void pickTime() {
        picker = new MaterialTimePicker.Builder ( )
                .setTimeFormat ( TimeFormat.CLOCK_12H )
                .setHour( 12 )
                .setMinute( 0 )
                .setTitleText ("Select Alarm Time")
                .setTheme(R.style.AppTheme_TimePickerTheme)
                .build();

        picker.show( getActivity().getSupportFragmentManager ( ), "RedBots" ) ;
        picker.addOnPositiveButtonClickListener( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                if (picker.getHour() > 12) {
                    doseTime = String.format("%02d", (picker.getHour()-12)) + ":" + String.format("%02d", picker.getMinute());
                    doseTime += " PM";
                } else {
                    doseTime = String.format("%02d", picker.getHour()) + ":" + String.format("%02d", picker.getMinute());
                    doseTime += " AM";
                }
                buttonTime.setText(doseTime);
            }
        }) ;
    }



    private void initializer() {
        editTextDoseName = getView().findViewById(R.id.editTextDoseName);
        buttonTime = getView().findViewById(R.id.buttonTime);
        buttonDate = getView().findViewById(R.id.buttonDate);
        buttonSubmit = getView().findViewById(R.id.buttonSubmit);

    }


    //helpers
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