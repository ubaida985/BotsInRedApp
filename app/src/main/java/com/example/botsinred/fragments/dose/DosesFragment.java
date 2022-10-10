package com.example.botsinred.fragments.dose;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.botsinred.R;
import com.example.botsinred.adapters.ScheduleAdapter;
import com.example.botsinred.database.Data;
import com.example.botsinred.fragments.userdetails.ProfileFragment;
import com.example.botsinred.models.CategoryModel;
import com.example.botsinred.models.ScheduleModel;
import com.example.botsinred.utilities.AlarmReceiver;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.Calendar;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class DosesFragment extends Fragment implements ScheduleAdapter.OnViewItemClickListener {

    //for doses
    private ArrayList<ScheduleModel> schedules;
    private ArrayList<CategoryModel> categories;
    private RecyclerView recyclerViewDoses;
    private ScheduleAdapter scheduleAdapter;

    //UI Components
    private RoundedImageView roundedImageViewAvatar;
    private FloatingActionButton fabAddDose;

    //for the alarm
    Calendar calendar;
    AlarmManager alarmManager;
    PendingIntent pendingIntent;
    ArrayList<PendingIntent> pendingIntents;
    int hours, mins;
    String doseTime;

    public DosesFragment() {
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
        return inflater.inflate(R.layout.fragment_doses, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //initialize all class variables
        initializer();

        //setting up adapters
        setUpDoseAdapter();

        //add listeners
        addListener();
    }

    private void addListener() {
        roundedImageViewAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new ProfileFragment());
            }
        });

        fabAddDose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new DoseDetailFragment());
            }
        });
    }


    private void setUpDoseAdapter() {
        Data data = new Data();
        schedules = data.getSchedule();
        scheduleAdapter = new ScheduleAdapter(schedules, getActivity(), this);
        recyclerViewDoses.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewDoses.setAdapter(scheduleAdapter);
    }


    private void initializer() {
        recyclerViewDoses = getView().findViewById(R.id.recyclerviewDoses);
        schedules = new ArrayList<>();

        roundedImageViewAvatar = getView().findViewById(R.id.roundedImageViewAvatar);
        fabAddDose = getView().findViewById(R.id.fabAddDose);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerViewDoses);
    }


    private void loadFragment(Fragment fragment) {
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frameLayout, fragment)
                .commit();
    }

    @Override
    public void onItemClick(int position) {
        ScheduleModel schedule = schedules.get(position);
        Fragment fragment = new PillCategoryFragment();
        Bundle bundle = new Bundle();
        bundle.putString("doseName", schedule.getName());
        bundle.putString("doseTime", schedule.getTime());
        fragment.setArguments(bundle);
        loadFragment(fragment);
    }


    ScheduleModel schedule;
    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();
            switch (direction){
                case ItemTouchHelper.LEFT:
                    schedule = schedules.get(position);
                    deleteSchedule(schedule.getScheduleID());
                    schedules.remove(position);
                    scheduleAdapter.notifyItemRemoved(position);
                    showMessage("Deleted");
                    Snackbar.make(recyclerViewDoses, schedule.getName(), Snackbar.LENGTH_LONG)
                            .setAction("Undo", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    schedules.add(position, schedule);
                                    addSchedule(schedule);
                                    scheduleAdapter.notifyItemInserted(position);
                                    showMessage("Added Back");
                                }
                            }).show();
                    break;

                case ItemTouchHelper.RIGHT:
                    schedules.get(position).setCompleted(true);
                    schedule = schedules.get(position);
                    schedules.remove(position);
                    scheduleAdapter.notifyItemRemoved(position);
                    showMessage("Marked as completed");
                    Snackbar.make(recyclerViewDoses, schedule.getName(), Snackbar.LENGTH_LONG)
                            .setAction("Undo", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    schedules.add(position, schedule);
                                    scheduleAdapter.notifyItemInserted(position);
                                    showMessage("Marked as incomplete");
                                }
                            }).show();
                    break;
            }
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(getActivity(), R.color.error))
                    .addSwipeLeftActionIcon(R.drawable.ic_baseline_delete_24)
                    .addSwipeRightBackgroundColor(ContextCompat.getColor(getActivity(), R.color.success))
                    .addSwipeRightActionIcon(R.drawable.ic_baseline_check_24)
                    .create()
                    .decorate();
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };

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
    }

    private void setAlarm ( ) {
        alarmManager = (AlarmManager) getActivity().getSystemService ( Context.ALARM_SERVICE ) ;
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


    private void addSchedule(ScheduleModel schedule) {
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        DocumentReference scheduleRef = database.collection("schedules")
                .document();
        schedule.setUserID(FirebaseAuth.getInstance().getCurrentUser().getUid());
        schedule.setScheduleID(scheduleRef.getId());
        schedule.setCompleted(false);

        scheduleRef.set(schedule).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if( task.isSuccessful() ){
                    //showMessage("Schedule Inserted");
                }else{
                    //showMessage("Schedule not Inserted");
                }
            }
        });

    }

}