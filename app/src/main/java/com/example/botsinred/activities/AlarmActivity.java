package com.example.botsinred.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.arch.core.executor.TaskExecutor;

import android.app.KeyguardManager;
import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.botsinred.R;
import com.example.botsinred.database.Data;
import com.example.botsinred.models.CategoryModel;
import com.example.botsinred.models.ScheduleModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;

public class AlarmActivity extends AppCompatActivity {

    private TextView textViewDoseTime, textViewDoseName, textViewDosePills;
    private ArrayList<ScheduleModel> schedules;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            setShowWhenLocked(true);
            setTurnScreenOn(true);
            KeyguardManager keyguardManager = (KeyguardManager) getSystemService(this.KEYGUARD_SERVICE);
            keyguardManager.requestDismissKeyguard(this, null);
        }
        else {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                    WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                    WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON |
                    WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }

        //initialize all class variables
        initialize();

        //setting up the data
        setupData();
    }

    private void setupData() {
        DateFormat dateFormat = new SimpleDateFormat("hh:mm a");
        String format = dateFormat.format(new Date());
        format = format.toUpperCase();
        showMessage(""+format);
        int i = 0, minDif = Integer.MAX_VALUE;
        for( ScheduleModel schedule : schedules ){
            int x = Math.abs(schedule.getTime().compareTo(""+format));
            if( x < minDif ){
                minDif = x;
                ++i;
            }
        }

        ScheduleModel schedule = schedules.get(i-1);
        textViewDoseTime.setText(schedule.getTime());
        textViewDoseName.setText(schedule.getName());
        String pills = "";
        for(CategoryModel category : schedule.getCategories() ){
            pills += category.getPills().toString() + "\n";
        }
        textViewDosePills.setText(pills);
    }

    private void initialize() {
        Data data = new Data();
        schedules = data.getSchedule();

        textViewDoseName = findViewById(R.id.textViewDoseName);
        textViewDoseTime = findViewById(R.id.textViewDoseTime);
        textViewDosePills = findViewById(R.id.textViewDosePills);
    }

    private void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}