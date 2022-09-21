package com.example.botsinred.fragments.userdetails;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.botsinred.R;
import com.example.botsinred.activities.LoginActivity;
import com.example.botsinred.database.Data;
import com.example.botsinred.fragments.HomeFragment;
import com.example.botsinred.models.UserModel;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GetTokenResult;
import com.makeramen.roundedimageview.RoundedImageView;

public class ProfileFragment extends Fragment {

    private LinearLayout linearLayoutAddress, linearLayoutContact, linearLayoutEmergencyContact, linearLayoutEmail, linearLayoutBloodGroup, linearLayoutWeight;
    private TextView textViewLogout, textViewName, textViewDeviceID, textViewAddress, textViewContact, textViewEmergencyContact, textViewEmail, textViewBloodGroup, textViewWeight, textViewEdit;
    private ImageView imageViewBack;
    private RoundedImageView roundedImageViewAvatar;

    UserModel user;
    public ProfileFragment() {
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
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //initializing class variables
        initialize();

        //getting values of user
        getValues();

        //add listeners
        addListeners();
    }

    private void getValues() {
        if( !user.getImage().equals("") ){
            byte[] bytes = Base64.decode(user.getImage(), Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            roundedImageViewAvatar.setImageBitmap(bitmap);
        }
        textViewName.setText(user.getName());
        textViewContact.setText(user.getContact());
        textViewEmergencyContact.setText(user.getEmergencyContact());
        textViewWeight.setText(user.getWeight());
        textViewEmail.setText(user.getEmail());
        textViewAddress.setText(user.getAddress());
        textViewBloodGroup.setText(user.getBloodGroup());
    }

    private void addListeners() {
        textViewEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new DetailsFragment());
            }
        });
        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new HomeFragment());
            }
        });
        textViewLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AuthUI.getInstance().signOut(getActivity())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if( task.isSuccessful() ){
                                    startLoginActivity();
                                }else{
                                    showMessage(task.getException().toString());
                                }
                            }
                        });
            }

        });
    }

    private void initialize() {
        linearLayoutAddress = getView().findViewById(R.id.linearLayoutAddress);
        linearLayoutContact = getView().findViewById(R.id.linearLayoutContact);
        linearLayoutEmergencyContact = getView().findViewById(R.id.linearLayoutEmergencyContact);
        linearLayoutEmail = getView().findViewById(R.id.linearLayoutEmail);
        linearLayoutBloodGroup = getView().findViewById(R.id.linearLayoutBloodGroup);
        linearLayoutWeight = getView().findViewById(R.id.linearLayoutWeight);

        textViewName = getView().findViewById(R.id.textViewName);
        textViewDeviceID = getView().findViewById(R.id.textViewDeviceID);
        textViewAddress = getView().findViewById(R.id.textViewAddress);
        textViewContact = getView().findViewById(R.id.textViewContact);
        textViewEmergencyContact = getView().findViewById(R.id.textViewEmergencyContact);
        textViewEmail = getView().findViewById(R.id.textViewEmail);
        textViewBloodGroup = getView().findViewById(R.id.textViewBloodGroup);
        textViewWeight = getView().findViewById(R.id.textViewWeight);
        textViewEdit = getView().findViewById(R.id.textViewEdit);
        textViewLogout = getView().findViewById(R.id.textViewLogout);

        imageViewBack = getView().findViewById(R.id.imageViewBack);
        roundedImageViewAvatar = getView().findViewById(R.id.roundedImageViewAvatar);

        user = new UserModel();
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


    public void startLoginActivity(){
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
        getActivity().finish();
    }
}