package com.example.botsinred.fragments.dose;


import android.app.Activity;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
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
import com.example.botsinred.utilities.AlarmReceiver;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AddDoseFragment extends Fragment {


    private EditText editTextPillName;
    private TextView textViewPillQty;
    private RoundedImageView roundedImageViewDecrease, roundedImageViewIncrease;
    private Button buttonAddPill;

    String doseName, doseTime, doseCategory, pillName, pillQty;
    ArrayList<ScheduleModel> schedules;
    ArrayList<CategoryModel> categories;
    HashMap<String, Integer> pills;
    FirebaseFirestore firestoreDB = FirebaseFirestore.getInstance();


    // for setting alarms
    Calendar calendar;
    AlarmManager alarmManager;
    PendingIntent pendingIntent;
    ArrayList<PendingIntent> pendingIntents;
    int hours, mins;

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
                    schedules = data.getSchedule();
                    categories = null;
                    pills = null;


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
                        data.setSchedule(schedules);
                        // for setting alarm
                        hours = Integer.parseInt(""+doseTime.charAt(0)+doseTime.charAt(1));
                        mins = Integer.parseInt(""+doseTime.charAt(3)+doseTime.charAt(4));
                        if( doseTime.charAt(6) == 'P' ){
                            hours += 12;
                        }
                        addSchedule();
                        Collections.sort(schedules, (o1, o2)
                                -> o1.getTime().compareTo(
                                o2.getTime()));
                        //  setAnAlarm();
                        loadFragment(fragment);
                        return;
                    }
                    deleteSchedule(schedules.get(i).getScheduleID());
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
                        addSchedule();
                        loadFragment(fragment);
                        return;
                    }

                    addSchedule();

                    Collections.sort(schedules, (o1, o2)
                            -> o1.getTime().compareTo(
                            o2.getTime()));
                    //TODO: what to do if the schedule exists, the category exists, the category exists along with the pill
                    data.setSchedule(schedules);
                    loadFragment(fragment);
                }
            }
        });
    }

    private void deleteSchedule(String scheduleID) {
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        DocumentReference scheduleRef = database.collection("schedules")
                .document(scheduleID);
        scheduleRef.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if( task.isSuccessful() ){

                }else{

                }
            }
        });
    }

    private void addSchedule() {
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        DocumentReference scheduleRef = database.collection("schedules")
                .document();
        ScheduleModel schedule = new ScheduleModel();
        schedule.setUserID(FirebaseAuth.getInstance().getCurrentUser().getUid());
        schedule.setScheduleID(scheduleRef.getId());
        schedule.setTime(doseTime);
        schedule.setName(doseName);
        schedule.setCategories(categories);
        schedule.setCompleted(false);

        scheduleRef.set(schedule).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if( task.isSuccessful() ){
                    schedules.add(schedule);
                    //showMessage("Schedule Inserted");
                }else{
                    //showMessage("Schedule not Inserted");
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
        pendingIntents = new ArrayList<>();
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

    //alarm functions
    private void setAnAlarm() {
        //creating the notification channel
        createNotificationChannel();

        calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hours);
        calendar.set(Calendar.MINUTE, mins);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        setAlarm();
        showMessage(doseTime);
    }

    private void setAlarm ( ) {
        alarmManager = ( AlarmManager ) getActivity().getSystemService ( Context.ALARM_SERVICE ) ;
        Intent intent = new Intent ( getActivity() , AlarmReceiver.class );
        pendingIntent = PendingIntent.getBroadcast ( getActivity() , AlarmReceiver.reqCode , intent , PendingIntent.FLAG_IMMUTABLE ) ;
        //alarms won't repeat daily
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        //alarmManager.setRepeating( AlarmManager.RTC_WAKEUP , calendar.getTimeInMillis ( ), AlarmManager.INTERVAL_DAY , pendingIntent );
        pendingIntents.add(pendingIntent);
        ++AlarmReceiver.reqCode;

    }

    private void cancelAlarm ( ) {
        if ( alarmManager == null ) {
            alarmManager = ( AlarmManager ) getActivity().getSystemService ( Context.ALARM_SERVICE ) ;
        }
        for( PendingIntent pendingIntent : pendingIntents ){
            alarmManager.cancel ( pendingIntent ) ;
        }
    }

    private void createNotificationChannel ( ) {
        if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ) {
            CharSequence name = "Dose" ; //this should be according to the application
            String description = "Channel for the application" ; //this should always have the description of the string above
            int importance = NotificationManager.IMPORTANCE_HIGH ;
            NotificationChannel channel = new NotificationChannel ( "RedBots" , name , importance ) ; //id should be same as in the AlarmReceiver class created
            channel.setDescription ( description ) ;

            NotificationManager notificationManager = getActivity().getSystemService(NotificationManager.class) ;
            notificationManager.createNotificationChannel(channel);
        }
    }

}