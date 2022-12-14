package com.example.botsinred.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.botsinred.R;
import com.example.botsinred.database.Data;
import com.example.botsinred.models.ScheduleModel;
import com.example.botsinred.models.UserModel;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.data.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private int AUTHUI_REQUEST_CODE = 10001;
    private TextView textViewLogin;
    private Data data;
    private static UserModel user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        //initialize
        initialize();


        //deconstruct all models
        //deconstruct();

        //add Listeners
        addListeners();

        //setup top menu bar
        //setupTopBar();
        checkForLogin();

    }

    private void checkForLogin() {
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            user.setUserID(FirebaseAuth.getInstance().getCurrentUser().getUid());
            data.setUser(user);
            System.out.println(user.toString());
            // showMessage("UserID: " + user.getUserID());
            if( user.getUserID().equals("pr02PY2fuxTpMNxg9G0FbpzA4oD3") ){
                startActivity(new Intent(this, AdminActivity.class));
                this.finish();
            }else if( user.getUserID().equals("Cs4uubPXYTcPpAK6qBYap0o8dX12") ){
                startActivity(new Intent(this, DoctorActivity.class));
                this.finish();
            }else{
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                this.finish();
            }
        }else{
            // showMessage("Not signed In");
        }
    }

    private void deconstruct() {
        Data data = new Data();
        data.setSchedule(null);
        UserModel user = new UserModel();
        user.setUsername("");
        user.setWeight("");
        user.setContact("");
        user.setBloodGroup("");
        user.setEmail("");
        user.setAddress("");
        user.setEmergencyContact("");
        user.setImage("");
        user.setUserID("");
        user.setID("");
    }

    private void addListeners() {
        textViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                authenticate();
            }
        });
    }

    private void authenticate() {
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build(),
                new AuthUI.IdpConfig.PhoneBuilder().build()
        );

        Intent intent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setTosAndPrivacyPolicyUrls("https://example.com", "https://example.com")
                .setLogo(R.drawable.logo)
                .setAlwaysShowSignInMethodScreen(true)
                .setIsSmartLockEnabled(false)
                .setTheme(R.style.AppTheme_Auth)
                .build();

        startActivityForResult(intent, AUTHUI_REQUEST_CODE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == AUTHUI_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // We have signed in the user or we have a new user
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                this.user.setUserID(user.getUid());
                this.data.setUser(this.user);
                if( FirebaseAuth.getInstance().getCurrentUser().getUid().equals("pr02PY2fuxTpMNxg9G0FbpzA4oD3") ){
                    Intent intent = new Intent(this, AdminActivity.class);
                    startActivity(intent);
                    this.finish();
                }else if( FirebaseAuth.getInstance().getCurrentUser().getUid().equals("Cs4uubPXYTcPpAK6qBYap0o8dX12") ){
                    Intent intent = new Intent(this, DoctorActivity.class);
                    startActivity(intent);
                    this.finish();
                }else{
                    //Checking for User (New/Old)
                    if (user.getMetadata().getCreationTimestamp() == user.getMetadata().getLastSignInTimestamp()) {
                      //  showMessage("NEW USER");
                        addUser();
                    } else {
                      //  showMessage("OLD USER");
                    }
                    Intent intent;
                    intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                    this.finish();
                }
            } else {
                // Signing in failed
                IdpResponse response = IdpResponse.fromResultIntent(data);
                if (response == null) {
                  //  showMessage("onActivityResult: the user has cancelled the sign in request");
                } else {
                  //  showMessage("onActivityResult: " + response.getError());
                }
            }

        }

    }

    private void addUser() {
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        DocumentReference userRef = database.collection("users")
                .document();
        user.setUserID(FirebaseAuth.getInstance().getCurrentUser().getUid());
        user.setID(userRef.getId());
        System.out.println(user.getID());
        user.setImage("");
        user.setUsername("");
        user.setName("");
        user.setAddress("");
        user.setEmail("");
        user.setEmergencyContact("");
        user.setContact("");
        user.setWeight("");
        user.setBloodGroup("");
      //  showMessage("useradded!");
        userRef.set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if( task.isSuccessful() ){
                    data.setUser(user);
                    //showMessage("Schedule Inserted");
                }else{
                    //showMessage("Schedule not Inserted");
                }
            }
        });
    }

    /**
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
**/

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
        textViewLogin = findViewById(R.id.textViewLogin);
        data = new Data();
        data.setUser(new UserModel());
        user = data.getUser();
    }
}