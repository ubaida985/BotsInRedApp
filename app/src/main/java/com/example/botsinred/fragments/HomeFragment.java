package com.example.botsinred.fragments;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.botsinred.R;
import com.example.botsinred.adapters.DateAdapter;
import com.example.botsinred.adapters.ScheduleAdapter;
import com.example.botsinred.database.Data;
import com.example.botsinred.fragments.userdetails.ProfileFragment;
import com.example.botsinred.models.CategoryModel;
import com.example.botsinred.models.DateModel;
import com.example.botsinred.models.ScheduleModel;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

import java.util.Calendar;
import java.util.Collections;

public class HomeFragment extends Fragment implements ScheduleAdapter.OnViewItemClickListener {

    //for doses
    private ArrayList<ScheduleModel> schedules;
    ArrayList<CategoryModel> categories;
    private RecyclerView recyclerViewDoses;
    private ScheduleAdapter scheduleAdapter;

    //for dates
    private ArrayList<DateModel> arrayListDates;
    private RecyclerView recyclerViewDates;
    private DateAdapter dateAdapter;

    //UI Components
    private RoundedImageView roundedImageViewAvatar;


    public HomeFragment() {
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
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //initialize all class variables
        initializer();

        //setting up adapters
        setUpDoseAdapter();
        setUpDatesAdapter();

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
    }

    private void setUpDatesAdapter() {
        //add data to the dates list
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            addDates();
        }
        dateAdapter = new DateAdapter(arrayListDates, getActivity());
        recyclerViewDates.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewDates.setAdapter(dateAdapter);
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void addDates() {

        Calendar cal = Calendar.getInstance();
        arrayListDates.add(new DateModel(cal.getTime()));
        cal.add(Calendar.DATE, 1);
        arrayListDates.add(new DateModel(cal.getTime()));
        cal.add(Calendar.DATE, 1);
        arrayListDates.add(new DateModel(cal.getTime()));
        cal.add(Calendar.DATE, 1);
        arrayListDates.add(new DateModel(cal.getTime()));
        cal.add(Calendar.DATE, 1);
        arrayListDates.add(new DateModel(cal.getTime()));
        cal.add(Calendar.DATE, 1);
        arrayListDates.add(new DateModel(cal.getTime()));
        cal.add(Calendar.DATE, 1);
        arrayListDates.add(new DateModel(cal.getTime()));

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setUpDoseAdapter() {

        //add data to the dose list
        setData();
        ArrayList<ScheduleModel> schedules = new ArrayList<>();
        if( this.schedules.size() >= 2 ){
            schedules.add(this.schedules.get(0));
            schedules.add(this.schedules.get(1));
        }else{
            schedules = this.schedules;
        }
        scheduleAdapter = new ScheduleAdapter(schedules, getActivity(), this);
        recyclerViewDoses.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewDoses.setAdapter(scheduleAdapter);
    }

    private void addDoses() {
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setData() {
        Data data = new Data();
        schedules = data.getSchedule();
        if( schedules == null ){
            schedules = new ArrayList<>();
            //addDoses();
        }else{
            Collections.sort(schedules, (o1, o2)
                    -> o1.getTime().compareTo(
                    o2.getTime()));
        }
        data.setSchedule(schedules);
    }

    private void initializer() {
        recyclerViewDoses = getView().findViewById(R.id.recyclerviewDoses);
        schedules = new ArrayList<>();
        categories = new ArrayList<>();

        arrayListDates = new ArrayList<>();
        recyclerViewDates = getView().findViewById(R.id.recyclerViewDates);

        roundedImageViewAvatar = getView().findViewById(R.id.roundedImageViewAvatar);
    }


    private void loadFragment(Fragment fragment) {
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frameLayout, fragment)
                .commit();
    }

    @Override
    public void onItemClick(int position) {

    }


    private void showMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }
}