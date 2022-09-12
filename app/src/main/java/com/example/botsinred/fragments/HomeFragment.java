package com.example.botsinred.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.botsinred.R;
import com.example.botsinred.adapters.DateAdapter;
import com.example.botsinred.adapters.DoseAdapter;
import com.example.botsinred.models.DateModel;
import com.example.botsinred.models.DoseModel;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements DoseAdapter.OnViewItemClickListener {

    //for doses
    private ArrayList<DoseModel> arrayListDoses;
    private RecyclerView recyclerViewDoses;
    private DoseAdapter doseAdapter;

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
        addDates();
        dateAdapter = new DateAdapter(arrayListDates, getActivity());
        recyclerViewDates.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewDates.setAdapter(dateAdapter);
    }

    private void addDates() {
        arrayListDates.add(new DateModel("Monday", "1"));
        arrayListDates.add(new DateModel("Tuesday", "2"));
        arrayListDates.add(new DateModel("Wednesday", "3"));
        arrayListDates.add(new DateModel("Thursday", "4"));
        arrayListDates.add(new DateModel("Friday", "5"));
        arrayListDates.add(new DateModel("Saturday", "6"));
        arrayListDates.add(new DateModel("Sunday", "7"));
    }

    private void setUpDoseAdapter() {

        //add data to the dose list
        addDoses();
        doseAdapter = new DoseAdapter(arrayListDoses, getActivity(), this);
        recyclerViewDoses.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewDoses.setAdapter(doseAdapter);
    }

    private void addDoses() {
        arrayListDoses.add(new DoseModel(1, "Paracetamol", 3, "For regular headaches", "Mon", "24", "Nov", "20", "22"));
        arrayListDoses.add(new DoseModel(2, "Dolo 650", 1, "For regular headaches", "Mon", "24", "Nov", "20", "22"));
        arrayListDoses.add(new DoseModel(3, "Pacimol 500", 3, "For regular headaches", "Mon", "24", "Nov", "20", "22"));
        arrayListDoses.add(new DoseModel(4, "Ondem 350", 3, "For regular headaches", "Mon", "24", "Nov", "20", "22"));
        arrayListDoses.add(new DoseModel(5, "Paracetamol", 3, "For regular headaches", "Mon", "24", "Nov", "20", "22"));
        arrayListDoses.add(new DoseModel(1, "Paracetamol", 3, "For regular headaches", "Mon", "24", "Nov", "20", "22"));
        arrayListDoses.add(new DoseModel(2, "Dolo 650", 1, "For regular headaches", "Mon", "24", "Nov", "20", "22"));
        arrayListDoses.add(new DoseModel(3, "Pacimol 500", 3, "For regular headaches", "Mon", "24", "Nov", "20", "22"));
        arrayListDoses.add(new DoseModel(4, "Ondem 350", 3, "For regular headaches", "Mon", "24", "Nov", "20", "22"));
        arrayListDoses.add(new DoseModel(5, "Paracetamol", 3, "For regular headaches", "Mon", "24", "Nov", "20", "22"));
    }

    private void initializer() {
        recyclerViewDoses = getView().findViewById(R.id.recyclerviewDoses);
        arrayListDoses = new ArrayList<>();

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