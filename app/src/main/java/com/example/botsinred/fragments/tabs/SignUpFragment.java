package com.example.botsinred.fragments.tabs;

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

import com.example.botsinred.R;

public class SignUpFragment extends Fragment {

    private EditText editTextName, editTextPhone, editTextPassword;
    private Button buttonSignUp;
    private TextView textViewSwitchLogin;

    final float opacity = 0;
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
    }


    private void initialize() {
        editTextPhone = getView().findViewById(R.id.editTextPhone);
        editTextPassword = getView().findViewById(R.id.editTextPassword);
        editTextName = getView().findViewById(R.id.editTextName);
        buttonSignUp = getView().findViewById(R.id.buttonSignUp);
        textViewSwitchLogin = getView().findViewById(R.id.textViewSwitchLogin);
    }
}