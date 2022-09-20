package com.example.botsinred.fragments.dose;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

public class DosesFragment extends Fragment implements ScheduleAdapter.OnViewItemClickListener {

    //for doses
    private ArrayList<ScheduleModel> schedules;
    private ArrayList<CategoryModel> categories;
    private RecyclerView recyclerViewDoses;
    private ScheduleAdapter scheduleAdapter;

    //UI Components
    private RoundedImageView roundedImageViewAvatar;
    private FloatingActionButton fabAddDose;

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

    private void addDoses() {
    }

    private void initializer() {
        recyclerViewDoses = getView().findViewById(R.id.recyclerviewDoses);
        schedules = new ArrayList<>();

        roundedImageViewAvatar = getView().findViewById(R.id.roundedImageViewAvatar);
        fabAddDose = getView().findViewById(R.id.fabAddDose);
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


    private void showMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }
}