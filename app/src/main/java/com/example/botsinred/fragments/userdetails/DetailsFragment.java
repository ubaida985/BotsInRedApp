package com.example.botsinred.fragments.userdetails;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.botsinred.R;
import com.example.botsinred.models.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.makeramen.roundedimageview.RoundedImageView;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class DetailsFragment extends Fragment {

    private TextView textViewAddImage, textViewBack;
    private EditText editTextName, editTextEmail, editTextContact, editTextEmergencyContact, editTextWeight, editTextBloodGroup, editTextAddress;
    private RoundedImageView roundedImageViewProfile;
    private MaterialButton buttonSubmit;
    private ProgressBar progressBar;
    private FrameLayout layoutAddImage;
    private String encodedImage;
    UserModel user;

    public DetailsFragment() {
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
        return inflater.inflate(R.layout.fragment_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //initialize class variables
        initialize();

        //adding listeners
        addListeners();

    }

    private void addListeners() {
        layoutAddImage.setOnClickListener(v->{
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            pickImage.launch(intent);
        });
        buttonSubmit.setOnClickListener(v -> {
            addDetails();
            if( validEntries() ){

                loadFragment(new ProfileFragment());
            }
        });
        textViewBack.setOnClickListener(v-> {
            loadFragment( new ProfileFragment() );
        });
    }

    private boolean validEntries() {
        if( encodedImage == null ){
            showMessage("Please select a image");
            return false;
        }else if( editTextName.getText().toString().equals("") ){
            showMessage("Please enter your name");
            return false;
        }else if( editTextEmail.getText().toString().equals("") ){
            showMessage("Please enter your email");
            return false;
        }else if( editTextContact.getText().toString().equals("") ){
            showMessage("Please enter your contact");
            return  false;
        }else if( editTextEmergencyContact.getText().toString().equals("") ){
            showMessage("Please enter an emergency contact");
            return  false;
        }else if( editTextWeight.getText().toString().equals("") ){
            showMessage("Please enter your weight");
            return false;
        }else if( editTextBloodGroup.getText().toString().equals("") ){
            showMessage("Please enter your blood group");
            return false;
        }else if( editTextAddress.getText().toString().equals("") ){
            showMessage("Please enter your address");
            return false;
        }
        return true;
    }

    private void addDetails() {
        showMessage(this.user.getID());
        this.user.setImage(encodedImage);
        this.user.setUsername(editTextEmail.getText().toString());
        this.user.setName(editTextName.getText().toString());
        this.user.setWeight(editTextWeight.getText().toString());
        this.user.setBloodGroup(editTextBloodGroup.getText().toString());
        this.user.setEmail(editTextEmail.getText().toString());
        this.user.setContact(editTextContact.getText().toString());
        this.user.setEmergencyContact(editTextEmergencyContact.getText().toString());
        this.user.setAddress(editTextAddress.getText().toString());

        Map<String, Object> userData = new HashMap<>();
        userData.put("username", this.user.getUsername());
        userData.put("name", this.user.getName());
        userData.put("image", this.user.getImage());
        userData.put("address", this.user.getAddress());
        userData.put("email", this.user.getEmail());
        userData.put("contact", this.user.getContact());
        userData.put("emergencyContact", this.user.getEmergencyContact());
        userData.put("bloodGroup", this.user.getBloodGroup());
        userData.put("weight", this.user.getWeight());

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore database = FirebaseFirestore.getInstance();

        DocumentReference userRef = database.collection("users")
                                            .document(this.user.getID());
        userRef.update(userData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //showMessage("failed to add");
                    }
                });


    }

    private void initialize() {
        textViewAddImage = getView().findViewById(R.id.textViewAddImage);
        textViewBack = getView().findViewById(R.id.textViewBack);

        editTextAddress = getView().findViewById(R.id.editTextAddress);
        editTextName = getView().findViewById(R.id.editTextName);
        editTextEmail = getView().findViewById(R.id.editTextEmail);
        editTextContact = getView().findViewById(R.id.editTextContact);
        editTextEmergencyContact = getView().findViewById(R.id.editTextEmergencyContact);
        editTextWeight = getView().findViewById(R.id.editTextWeight);
        editTextBloodGroup = getView().findViewById(R.id.editTextBloodGroup);

        roundedImageViewProfile = getView().findViewById(R.id.roundedImageViewProfile);

        buttonSubmit = getView().findViewById(R.id.buttonSubmit);

        progressBar = getView().findViewById(R.id.progressBar);

        layoutAddImage = getView().findViewById(R.id.layoutAddImage);

        user = new UserModel();

        editTextAddress.setText(user.getAddress());
        editTextName.setText(user.getName());
        editTextWeight.setText(user.getWeight());
        editTextContact.setText(user.getContact());
        editTextEmergencyContact.setText(user.getEmergencyContact());
        editTextEmail.setText(user.getEmail());
        editTextBloodGroup.setText(user.getBloodGroup());
        if(  user.getImage() != null  ){
            byte[] bytes = Base64.decode(user.getImage(), Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            roundedImageViewProfile.setImageBitmap(bitmap);
            textViewAddImage.setVisibility(View.GONE);
            encodedImage = encodeImage(bitmap);
            user.setImage(encodedImage);
        }
    }

    //helpers
    private String encodeImage(Bitmap bitmap){
        int previewWidth = 150;
        int previewHeight = bitmap.getHeight()*previewWidth/bitmap.getWidth();
        Bitmap previewBitmap = Bitmap.createScaledBitmap(bitmap, previewWidth, previewHeight, false);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        previewBitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }

    private final ActivityResultLauncher<Intent> pickImage = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> {
                if( result.getResultCode() == getActivity().RESULT_OK ){
                    if( result.getData() != null ){
                        Uri imageUri = result.getData().getData();
                        try{
                            InputStream inputStream = getActivity().getContentResolver().openInputStream(imageUri);
                            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                            roundedImageViewProfile.setImageBitmap(bitmap);
                            textViewAddImage.setVisibility(View.GONE);
                            encodedImage = encodeImage(bitmap);
                            user.setImage(encodedImage);
                        }catch (FileNotFoundException e){
                            e.printStackTrace();
                        }
                    }
                }
            }
    );


    private void loading( boolean isLoading ){
        if( isLoading ){
            buttonSubmit.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.VISIBLE);
        }else{
            buttonSubmit.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.INVISIBLE);
        }
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