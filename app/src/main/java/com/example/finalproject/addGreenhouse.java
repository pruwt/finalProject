package com.example.finalproject;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class addGreenhouse extends AppCompatActivity {

    private static final String TAG = "addGreenhouse";
    private DatabaseReference db;
    private FirebaseAuth mAuth;

    private EditText herbNameEditText;
    private EditText soilMoistureEditText;
    private EditText locationEditText;
    private Button startButton, stopButton, editButton, deleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_greenhouse);

        // Initialize Realtime Database
        db = FirebaseDatabase.getInstance().getReference("users");

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Initialize UI elements
        herbNameEditText = findViewById(R.id.herbNameEditText);
        soilMoistureEditText = findViewById(R.id.soilMoistureEditText);
        locationEditText = findViewById(R.id.locationEditText);
        startButton = findViewById(R.id.startButton);
        stopButton = findViewById(R.id.stopButton);
        editButton = findViewById(R.id.editButton);
        deleteButton = findViewById(R.id.deleteButton);

        // Add data to Realtime Database on button click
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String herbName = herbNameEditText.getText().toString();
                String soilMoisture = soilMoistureEditText.getText().toString();
                String location = locationEditText.getText().toString();

                if (!herbName.isEmpty() && !soilMoisture.isEmpty() && !location.isEmpty()) {
                    addGreenhouseData(herbName, soilMoisture, location);
                } else {
                    Toast.makeText(addGreenhouse.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void addGreenhouseData(String herbName, String soilMoisture, String location) {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String uid = currentUser.getUid();

            Map<String, Object> greenhouse = new HashMap<>();
            greenhouse.put("herbName", herbName);
            greenhouse.put("soilMoisture", soilMoisture);
            greenhouse.put("location", location);

            // Store the greenhouse data under the user's UID
            db.child(uid).child("greenhouses").push().setValue(greenhouse)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(TAG, "Data added successfully");
                            Toast.makeText(addGreenhouse.this, "Greenhouse added", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error adding data", e);
                            Toast.makeText(addGreenhouse.this, "Failed to add greenhouse", Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(this, "User not authenticated", Toast.LENGTH_SHORT).show();
        }
    }
}
