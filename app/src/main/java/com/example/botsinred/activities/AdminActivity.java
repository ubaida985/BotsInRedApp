package com.example.botsinred.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.botsinred.R;
import com.example.botsinred.database.Data;
import com.example.botsinred.fragments.CallFragment;
import com.example.botsinred.fragments.HomeFragment;
import com.example.botsinred.fragments.ShopFragment;
import com.example.botsinred.fragments.UserFragment;
import com.example.botsinred.fragments.dose.DosesFragment;
import com.example.botsinred.fragments.userdetails.ProfileFragment;
import com.example.botsinred.models.ScheduleModel;
import com.example.botsinred.models.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;
import java.util.Collections;

public class AdminActivity extends AppCompatActivity implements FirebaseAuth.AuthStateListener{

    UserModel user;
    ArrayList<UserModel> users;

    ScheduleModel schedule;
    ArrayList<ScheduleModel> schedules;

    MeowBottomNavigation bottomNavigation;

    Data data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        //initialize all the class variable
        initializer();


        //fetching user data
        fetchAllUsers();

        //fetching all the does
        fetchAllDoses();

        //bottom bar setUP
        setupBottomBar();



    }

    private void fetchAllDoses() {
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        CollectionReference scheduleCollectionReference = database.collection("schedules");
        Query schedulesQuery = scheduleCollectionReference;
        schedulesQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if( task.isSuccessful() ){
                    for(QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()){
                        ScheduleModel schedule = queryDocumentSnapshot.toObject(ScheduleModel.class);
                        schedules.add(schedule);
                    }
                }else{
                }
            }
        });
        Collections.sort(schedules, (o1, o2)
                -> o1.getTime().compareTo(
                o2.getTime()));
        //showMessage(schedules.toString());
        data.setSchedule(schedules);

    }

    private void fetchAllUsers() {
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        CollectionReference userCollectionReference = database.collection("users");
        Query usersQuery = userCollectionReference;
        Data data = new Data();
        usersQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if( task.isSuccessful() ){
                    for(QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()){
                        UserModel user = queryDocumentSnapshot.toObject(UserModel.class);
                        users.add(user);
                    }
                }else{
                }
            }
        });
        data.setUsers(users);

    }

    private void setupBottomBar() {
        bottomNavigation.add(new MeowBottomNavigation.Model(2, R.drawable.ic_home));
        bottomNavigation.add(new MeowBottomNavigation.Model(1, R.drawable.icon_profile));

        bottomNavigation.setOnShowListener(item -> {
            //Initializing Fragment
            Fragment fragment = null;
            switch (item.getId()){
                case 1:
                    fragment = new UserFragment();
                    break;
                case 2:
                    fragment = new DosesFragment();
                    break;
            }
            loadFragment(fragment);
        });

        //set count
        //bottomNavigation.setCount(1, "10");

        //setting home fragment initially selected
        bottomNavigation.show(2, true);

        bottomNavigation.setOnClickMenuListener(item -> {
            //display toast
         //   showMessage("You clicked on " + item.getId());
        });

       // bottomNavigation.setOnReselectListener(item -> showMessage("You reselected " + item.getId()));
    }

    private void initializer() {
        bottomNavigation = findViewById(R.id.bottomNavigation);
        schedule = new ScheduleModel();

        users = new ArrayList<>();
        schedules = new ArrayList<>();
        data = new Data();
        data.setSchedule(schedules);
        data.setUsers(users);
        user = data.getUser();

    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frameLayout, fragment)
                .commit();
    }

    private void showMessage(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }


    //logging in and out
    public void startLoginActivity(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseAuth.getInstance().addAuthStateListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        FirebaseAuth.getInstance().removeAuthStateListener(this);
    }

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        if (firebaseAuth.getCurrentUser() == null) {
            //showMessage("going out!");
            startLoginActivity();
            return;
        }
    }


}