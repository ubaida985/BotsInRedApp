package com.example.botsinred.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.botsinred.R;
import com.example.botsinred.adapters.DateAdapter;
import com.example.botsinred.adapters.PhoneAdapter;
import com.example.botsinred.adapters.ScheduleAdapter;
import com.example.botsinred.database.Data;
import com.example.botsinred.fragments.userdetails.ProfileFragment;
import com.example.botsinred.models.CategoryModel;
import com.example.botsinred.models.DateModel;
import com.example.botsinred.models.PhoneModel;
import com.example.botsinred.models.ScheduleModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

public class CallFragment extends Fragment implements PhoneAdapter.OnViewItemClickListener {

    //for doses
    private ArrayList<PhoneModel> numbers;
    private RecyclerView recyclerViewNumbers;
    private PhoneAdapter phoneAdapter;



    public CallFragment() {
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
        return inflater.inflate(R.layout.fragment_call, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //initialize all class variables
        initializer();

        //setting up adapters
        setUpPhoneAdapter();

        //add listeners
        addListener();
    }


    private void addListener() {
    }

    private void setUpPhoneAdapter() {

        addNumbers();

        phoneAdapter = new PhoneAdapter(numbers, getActivity(), this);
        recyclerViewNumbers.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewNumbers.setAdapter(phoneAdapter);
    }

    private void addNumbers() {
        numbers.add(new PhoneModel("Kunal", "8860642624"));
    }

    private void initializer() {
        recyclerViewNumbers = getView().findViewById(R.id.recyclerviewNumbers);
        numbers = new ArrayList<>();
    }


    private void loadFragment(Fragment fragment) {
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frameLayout, fragment)
                .commit();
    }

    @Override
    public void onItemClick(int position) {
        String phone = numbers.get(position).getPhone();
        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse("tel: "+phone));
        startActivity(callIntent);
    }


    private void showMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }
}