package com.example.botsinred.activities;

import android.content.Intent;
import android.hardware.usb.UsbRequest;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.botsinred.R;
import com.example.botsinred.database.Data;
import com.example.botsinred.models.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.HashMap;
import java.util.Map;

public class ScanningActivity extends AppCompatActivity {

    private UserModel user;
    private Data data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanning);

        data = new Data();

        launchScanner();
    }

    private void launchScanner() {
        IntentIntegrator intentIntegrator = new IntentIntegrator(this);
        intentIntegrator.setPrompt("Scan a barcode or QR Code");
        intentIntegrator.setOrientationLocked(true);
        intentIntegrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        // if the intentResult is null then
        // toast a message as "cancelled"
        if (intentResult != null) {
            if (intentResult.getContents() == null) {
                Toast.makeText(getBaseContext(), "Cancelled", Toast.LENGTH_SHORT).show();
            } else {
                // if the intentResult is not null we'll set
                // the content and format of scan message
                String deviceID = intentResult.getContents();
                user = new Data().getUser();
                user.setDeviceID(deviceID);
                new Data().setUser(user);

                //writing to database
                addUser();
                if( !user.getDeviceID().equals("") ) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void addUser() {
        this.user = new Data().getUser();
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
        userData.put("deviceID", this.user.getDeviceID());

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
}