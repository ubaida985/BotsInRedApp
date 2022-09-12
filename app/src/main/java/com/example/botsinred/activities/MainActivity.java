package com.example.botsinred.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.widget.Toast;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.botsinred.R;
import com.example.botsinred.fragments.AddDoseFragment;
import com.example.botsinred.fragments.CallFragment;
import com.example.botsinred.fragments.HomeFragment;
import com.example.botsinred.fragments.ProfileFragment;
import com.example.botsinred.fragments.ShopFragment;

public class MainActivity extends AppCompatActivity {

    MeowBottomNavigation bottomNavigation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialize all the class variable
        initializer();

        //bottom bar setUP
        setupBottomBar();
    }

    private void initializer() {
        bottomNavigation = findViewById(R.id.bottomNavigation);
    }

    private void setupBottomBar() {
        bottomNavigation.add(new MeowBottomNavigation.Model(1, R.drawable.icon_phone));
        bottomNavigation.add(new MeowBottomNavigation.Model(2, R.drawable.ic_home));
        bottomNavigation.add(new MeowBottomNavigation.Model(3, R.drawable.icon_add));
        bottomNavigation.add(new MeowBottomNavigation.Model(4, R.drawable.icon_profile));
        bottomNavigation.add(new MeowBottomNavigation.Model(5, R.drawable.icon_shop));

        bottomNavigation.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
                //Initializing Fragment
                Fragment fragment = null;
                switch (item.getId()){
                    case 1:
                        fragment = new CallFragment();
                        break;
                    case 2:
                        fragment = new HomeFragment();
                        break;
                    case 3:
                        fragment = new AddDoseFragment();
                        break;
                    case 4:
                        fragment = new ProfileFragment();
                        break;
                    case 5:
                        fragment = new ShopFragment();
                        break;
                }
                loadFragment(fragment);
            }
        });

        //set count
        bottomNavigation.setCount(1, "10");

        //setting home fragment initially selected
        bottomNavigation.show(2, true);

        bottomNavigation.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {
                //display toast
                showMessage("You clicked on " + item.getId());
            }
        });

        bottomNavigation.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) {
                showMessage("You reselected " + item.getId());
            }
        });
    }



    //helpers
    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frameLayout, fragment)
                .commit();
    }

    private void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}