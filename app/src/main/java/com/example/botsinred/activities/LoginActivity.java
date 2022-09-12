package com.example.botsinred.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.widget.Toast;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.botsinred.R;
import com.example.botsinred.fragments.tabs.LogInFragment;
import com.example.botsinred.fragments.tabs.SignUpFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class LoginActivity extends AppCompatActivity {

    private FloatingActionButton fabGoogle, fabTwitter, fabFacebook;
    private MeowBottomNavigation logInNavigation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //initialize
        initialize();

        //setup top menu bar
        setupTopBar();

    }


    private void setupTopBar() {
        logInNavigation.add(new MeowBottomNavigation.Model(1, R.drawable.icon_profile));
        logInNavigation.add(new MeowBottomNavigation.Model(2, R.drawable.icon_signup));

        logInNavigation.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
                //Initializing Fragment
                Fragment fragment = null;
                switch (item.getId()) {
                    case 1:
                        fragment = new LogInFragment();
                        break;
                    case 2:
                        fragment = new SignUpFragment();
                        break;
                }
                loadFragment(fragment);
            }
        });
        //set count
        logInNavigation.setCount(1, "10");

        //setting home fragment initially selected
        logInNavigation.show(2, true);

        logInNavigation.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {
                //display toast
                showMessage("You clicked on " + item.getId());
            }
        });

        logInNavigation.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) {
                showMessage("You reselected " + item.getId());
            }
        });
    }

    private void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frameLayout, fragment)
                .commit();
    }

    private void initialize() {
        fabGoogle = findViewById(R.id.fabGoogle);
        logInNavigation = findViewById(R.id.logInNavigation);
    }
}