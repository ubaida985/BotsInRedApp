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

public class LogInFragment extends Fragment {
    private EditText editTextPhone;
    private Button buttonLogin;

    private String phoneNumber;
    public LogInFragment() {
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
        return inflater.inflate(R.layout.fragment_log_in, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //initialize
        initialize();

        //add listeners
        addListeners();

        //setting some animations
        //animations();

    }

    private void addListeners() {
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( validateLogin() ){
                    Fragment fragment = new SignUpFragment();
                    Bundle data = new Bundle();
                    data.putString("phoneNumber", phoneNumber);
                    fragment.setArguments(data);
                    loadFragment(fragment);
                }else{
                    showMessage("Please enter a valid number");
                }
            }
        });
    }

    private void initialize() {
        editTextPhone = getView().findViewById(R.id.editTextPhone);
        buttonLogin = getView().findViewById(R.id.buttonLogin);
    }


    //helpers
    private boolean validateLogin() {
        phoneNumber = editTextPhone.getText().toString();
        if( phoneNumber.length() == 10 ){
            return true;
        }
        return false;
    }

    //helpers
    private void loadFragment(Fragment fragment) {
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frameLayout, fragment)
                .commit();
    }

    private void showMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    /**
    private void animations() {
        editTextPhone.setTranslationY(300);
        editTextPassword.setTranslationY(300);
        buttonLogin.setTranslationY(300);
        textViewForgotPassword.setTranslationY(300);

        editTextPhone.setAlpha(opacity);
        editTextPassword.setAlpha(opacity);
        buttonLogin.setAlpha(opacity);
        textViewForgotPassword.setAlpha(opacity);

        editTextPhone.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(400).start();
        editTextPassword.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(600).start();
        buttonLogin.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(800).start();
        textViewForgotPassword.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(100).start();
    }
    */
}