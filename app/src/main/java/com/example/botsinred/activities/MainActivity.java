package com.example.botsinred.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.botsinred.R;
import com.example.botsinred.database.Data;
import com.example.botsinred.fragments.CallFragment;
import com.example.botsinred.fragments.HomeFragment;
import com.example.botsinred.fragments.ProfileFragment;
import com.example.botsinred.fragments.ShopFragment;
import com.example.botsinred.fragments.dose.DosesFragment;
import com.example.botsinred.models.ScheduleModel;
import com.example.botsinred.utilities.AlarmReceiver;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    //the bottom bar
    MeowBottomNavigation bottomNavigation;

    //for the alarm
    Calendar calendar;
    AlarmManager alarmManager;
    PendingIntent pendingIntent;
    ArrayList<PendingIntent> pendingIntents;
    int hours, mins;
    String doseTime;
    ArrayList<ScheduleModel> schedules;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialize all the class variable
        initializer();

        //bottom bar setUP
        setupBottomBar();

        //get doses
        getAllDoses();
    }

    private void initializer() {
        bottomNavigation = findViewById(R.id.bottomNavigation);
    }

    private void setupBottomBar() {
        bottomNavigation.add(new MeowBottomNavigation.Model(1, R.drawable.icon_phone));
        bottomNavigation.add(new MeowBottomNavigation.Model(2, R.drawable.ic_home));
        bottomNavigation.add(new MeowBottomNavigation.Model(3, R.drawable.icon_add));
        bottomNavigation.add(new MeowBottomNavigation.Model(4, R.drawable.icon_profile));
        bottomNavigation.add(new MeowBottomNavigation.Model(5, R.drawable.icon_shop));

        bottomNavigation.setOnShowListener(item -> {
            //Initializing Fragment
            Fragment fragment = null;
            switch (item.getId()){
                case 1:
                    fragment = new CallFragment();
                    break;
                case 2:
                    fragment = new HomeFragment();
                    break;
                case 3:
                    fragment = new DosesFragment();
                    break;
                case 4:
                    fragment = new ProfileFragment();
                    break;
                case 5:
                    fragment = new ShopFragment();
                    break;
            }
            loadFragment(fragment);
        });

        //set count
        bottomNavigation.setCount(1, "10");

        //setting home fragment initially selected
        bottomNavigation.show(2, true);

        bottomNavigation.setOnClickMenuListener(item -> {
            //display toast
            showMessage("You clicked on " + item.getId());
        });

        bottomNavigation.setOnReselectListener(item -> showMessage("You reselected " + item.getId()));
    }



    //helpers
    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frameLayout, fragment)
                .commit();
    }

    private void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void getAllDoses(){
        Data data = new Data();
        if( data.getSchedule() != null ){
            schedules = data.getSchedule();
            for( ScheduleModel schedule : schedules ){
                doseTime = schedule.getTime();
                hours = Integer.parseInt(""+doseTime.charAt(0)+doseTime.charAt(1));
                mins = Integer.parseInt(""+doseTime.charAt(3)+doseTime.charAt(4));
                setAnAlarm();
            }
        }

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
    }

    private void setAlarm ( ) {
        alarmManager = (AlarmManager) getSystemService ( Context.ALARM_SERVICE ) ;
        Intent intent = new Intent ( this , AlarmReceiver.class );
        pendingIntent = PendingIntent.getBroadcast ( this , AlarmReceiver.reqCode , intent , PendingIntent.FLAG_IMMUTABLE ) ;
        //alarms won't repeat daily
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        //alarmManager.setRepeating( AlarmManager.RTC_WAKEUP , calendar.getTimeInMillis ( ), AlarmManager.INTERVAL_DAY , pendingIntent );
        pendingIntents.add(pendingIntent);
        ++AlarmReceiver.reqCode;

    }

    private void cancelAlarm ( ) {
        if ( alarmManager == null ) {
            alarmManager = ( AlarmManager ) getSystemService ( Context.ALARM_SERVICE ) ;
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

            NotificationManager notificationManager = getSystemService(NotificationManager.class) ;
            notificationManager.createNotificationChannel(channel);
        }
    }

}