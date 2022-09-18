package com.example.botsinred.fragments.tabs;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.botsinred.R;
import com.example.botsinred.activities.MainActivity;

public class SignUpFragment extends Fragment {

    private EditText editTextPassword;
    private TextView textViewMistake, textViewNumber;
    private Button buttonLogin;

    private String phoneNumber;
    public SignUpFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //getting the phone number passed
        phoneNumber = getArguments().getString("phoneNumber");

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //initialize
        initialize();

        //setting some animations
        //animations();

        //set values
        setValues();

        //set listeners
        setListeners();
    }

    private void setValues() {
        textViewNumber.setText("+91 "+phoneNumber);
    }

    private void setListeners() {
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( validateLogin() ){
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                }
            }
        });

        textViewMistake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new LogInFragment());
            }
        });
    }



    private void initialize() {
        textViewNumber = getView().findViewById(R.id.textViewNumber);
        editTextPassword = getView().findViewById(R.id.editTextPassword);
        buttonLogin = getView().findViewById(R.id.buttonLogin);
        textViewMistake = getView().findViewById(R.id.textViewMistake);
    }

    //helpers
    private boolean validateLogin() {
        return true;
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

}