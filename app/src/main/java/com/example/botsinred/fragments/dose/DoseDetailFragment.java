package com.example.botsinred.fragments.dose;

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
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class DoseDetailFragment extends Fragment {

    private EditText editTextDoseName;
    private Button buttonTime, buttonSubmit;

    String doseName, doseTime;
    MaterialTimePicker picker;

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

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( validateEntries() ){
                    Fragment fragment = new PillCategoryFragment();
                    Bundle data = new Bundle();
                    data.putString("doseName", doseName);
                    data.putString("doseTime", doseTime);
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