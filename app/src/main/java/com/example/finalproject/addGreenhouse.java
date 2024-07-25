package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class addGreenhouse extends AppCompatActivity {

    private static final String TAG = "addGreenhouse";

    private DatabaseReference db;
    private FirebaseAuth mAuth;

    private EditText herbNameEditText;
    private EditText soilMoistureEditText;
    private EditText locationEditText;

    ImageButton homeButton,greenhousesButton,irrigationButton,logoutButton; //navbar
    private Button insertButton, updateButton, viewButton, deleteButton;
    private String selectedGreenhouseId = null; // This will store the ID of the selected greenhouse

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
        insertButton = findViewById(R.id.insertButton);
        updateButton = findViewById(R.id.updateButton);
        viewButton = findViewById(R.id.viewButton);
        deleteButton = findViewById(R.id.deleteButton);
        homeButton = findViewById(R.id.homeButton);
        greenhousesButton = findViewById(R.id.greenhousesButton);
        irrigationButton = findViewById(R.id.irrigationButton);
        logoutButton = findViewById(R.id.logoutButton);

        // Check if there is an intent extra for the selected greenhouse ID
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("selectedGreenhouseId")) {
            selectedGreenhouseId = intent.getStringExtra("selectedGreenhouseId");
            Log.d(TAG, "Selected Greenhouse ID: " + selectedGreenhouseId);
            fetchGreenhouseDetails(selectedGreenhouseId);
        }

        // Add data to Realtime Database on button click
        insertButton.setOnClickListener(new View.OnClickListener() {
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

        // Update data in Realtime Database on button click
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String herbName = herbNameEditText.getText().toString();
                String soilMoisture = soilMoistureEditText.getText().toString();
                String location = locationEditText.getText().toString();

                if (!herbName.isEmpty() && !soilMoisture.isEmpty() && !location.isEmpty()) {
                    if (selectedGreenhouseId != null) {
                        updateGreenhouseData(selectedGreenhouseId, herbName, soilMoisture, location);
                    } else {
                        Toast.makeText(addGreenhouse.this, "No greenhouse selected for update", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(addGreenhouse.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // View data from Realtime Database on button click
        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(addGreenhouse.this, ViewGreenhousesActivity.class);
                startActivity(intent);
            }
        });

        // Delete data from Realtime Database on button click
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedGreenhouseId != null) {
                    deleteGreenhouseData(selectedGreenhouseId);
                } else {
                    Toast.makeText(addGreenhouse.this, "No greenhouse selected for deletion", Toast.LENGTH_SHORT).show();
                }
            }
        });

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(addGreenhouse.this, Home.class);
                startActivity(intent);
            }
        });
        irrigationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(addGreenhouse.this, Dashboard.class);
                startActivity(intent);
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(addGreenhouse.this, Login.class);
                startActivity(intent);
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

    private void updateGreenhouseData(String greenhouseId, String herbName, String soilMoisture, String location) {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String uid = currentUser.getUid();
            DatabaseReference greenhouseRef = db.child(uid).child("greenhouses").child(greenhouseId);

            Map<String, Object> updates = new HashMap<>();
            updates.put("herbName", herbName);
            updates.put("soilMoisture", soilMoisture);
            updates.put("location", location);

            greenhouseRef.updateChildren(updates)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(TAG, "Data updated successfully");
                            Toast.makeText(addGreenhouse.this, "Greenhouse updated", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error updating data", e);
                            Toast.makeText(addGreenhouse.this, "Failed to update greenhouse", Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(this, "User not authenticated", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteGreenhouseData(String greenhouseId) {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String uid = currentUser.getUid();
            DatabaseReference greenhouseRef = db.child(uid).child("greenhouses").child(greenhouseId);

            greenhouseRef.removeValue()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(TAG, "Data deleted successfully");
                            Toast.makeText(addGreenhouse.this, "Greenhouse deleted", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error deleting data", e);
                            Toast.makeText(addGreenhouse.this, "Failed to delete greenhouse", Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(this, "User not authenticated", Toast.LENGTH_SHORT).show();
        }
    }

    private void fetchGreenhouseDetails(String greenhouseId) {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String uid = currentUser.getUid();
            DatabaseReference greenhouseRef = db.child(uid).child("greenhouses").child(greenhouseId);

            greenhouseRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String herbName = dataSnapshot.child("herbName").getValue(String.class);
                    String soilMoisture = dataSnapshot.child("soilMoisture").getValue(String.class);
                    String location = dataSnapshot.child("location").getValue(String.class);

                    herbNameEditText.setText(herbName);
                    soilMoistureEditText.setText(soilMoisture);
                    locationEditText.setText(location);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.w(TAG, "Failed to read greenhouse details", databaseError.toException());
                }
            });
        }
    }
}
