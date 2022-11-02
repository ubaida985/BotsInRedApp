package com.example.botsinred.fragments;

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
import com.example.botsinred.adapters.UserAdapter;
import com.example.botsinred.database.Data;
import com.example.botsinred.fragments.dose.DoseDetailFragment;
import com.example.botsinred.fragments.dose.DosesFragment;
import com.example.botsinred.fragments.dose.PillCategoryFragment;
import com.example.botsinred.fragments.userdetails.ProfileFragment;
import com.example.botsinred.models.ScheduleModel;
import com.example.botsinred.models.UserModel;
import com.example.botsinred.utilities.AlarmReceiver;
import com.firebase.ui.auth.data.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class UserFragment extends Fragment implements UserAdapter.OnViewItemClickListener {

    
    ArrayList<UserModel> users;
    RecyclerView recyclerViewUsers;
    private UserAdapter userAdapter;

    public UserFragment() {
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
        return inflater.inflate(R.layout.fragment_user, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //initialize all class variables
        initializer();

        //setUpAdapter
        setUserAdapter();
    }

    private void setUserAdapter() {
        Data data = new Data();
        users = data.getUsers();
        userAdapter = new UserAdapter(users, getActivity(), this);
        recyclerViewUsers.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewUsers.setAdapter(userAdapter);
    }

    private void initializer() {
        recyclerViewUsers = getView().findViewById(R.id.recyclerViewUsers);
        users = new ArrayList<>();

    }

    private void loadFragment(Fragment fragment) {
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frameLayout, fragment)
                .commit();
    }

    @Override
    public void onItemClick(int position) {
        UserModel user = users.get(position);
        Fragment fragment = new DosesFragment();
        Data data = new Data();
        data.setUser(user);
        fetchDosesOfUser(data.getUser().getUserID());
        loadFragment(fragment);
    }

    private void fetchDosesOfUser(String userID) {
        //showMessage(userID);
        ArrayList<ScheduleModel> schedules = new ArrayList<>();
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        CollectionReference scheduleCollectionReference = database.collection("schedules");
        Query schedulesQuery = scheduleCollectionReference
                .whereEqualTo("userID", userID);
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
        Data data = new Data();
        data.setSchedule(schedules);
    }

    private void showMessage(String message) {
       // Toast.makeText(getActivity().getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}