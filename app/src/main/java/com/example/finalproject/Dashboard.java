package com.example.finalproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Dashboard extends Activity {

    private TextView MoistureVal, TemperatureVal, HumidityVal;
    private Switch actuatorSwitch;
    private ImageButton homeButtonIrrigation,greenhousesButtonIrrigation,irrigationButtonIrrigation,logoutButtonIrrigation;

    private DatabaseReference databaseReference;
    private DatabaseReference db;
    private FirebaseAuth mAuth;
    private static final String TAG = "Dashboard";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        FirebaseUser currentUser = mAuth.getCurrentUser();

        // Initialize Firebase Database
        String userId = currentUser.getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference("UsersData").child(userId).child("actuations");
        db = FirebaseDatabase.getInstance().getReference("UsersData");

        actuatorSwitch = findViewById(R.id.actuatorSwitch);
        MoistureVal = findViewById(R.id.MoistureVal);
        TemperatureVal = findViewById(R.id.TemperatureVal);
        HumidityVal = findViewById(R.id.HumidityVal);
        homeButtonIrrigation = findViewById(R.id.homeButtonIrrigation);
        greenhousesButtonIrrigation = findViewById(R.id.greenhousesButtonIrrigation);
        irrigationButtonIrrigation = findViewById(R.id.irrigationButtonIrrigation);
        logoutButtonIrrigation = findViewById(R.id.logoutButtonIrrigation);

        fetchReadingsData();
        fetchPumpState();


        homeButtonIrrigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this, Home.class);
                startActivity(intent);
            }
        });

        greenhousesButtonIrrigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this, addGreenhouse.class);
                startActivity(intent);
            }
        });

        irrigationButtonIrrigation .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this, Dashboard.class);
                startActivity(intent);
            }
        });

        logoutButtonIrrigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this, Login.class);
                startActivity(intent);
            }
        });



        actuatorSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Log.d(TAG, "Pump turned ON");
                    sendIrrigationStateToFirebase("on");
                } else {
                    Log.d(TAG, "Pump turned OFF");
                    sendIrrigationStateToFirebase("off");
                }
            }
        });
    }

    private void sendIrrigationStateToFirebase(String state) {
        databaseReference.child("pumpstate").setValue(state);
    }

    private void fetchReadingsData() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String uid = currentUser.getUid();
            DatabaseReference readingsRef = db.child(uid).child("readings");

            readingsRef.limitToLast(1).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String moisture = snapshot.child("moisture").getValue(String.class);
                        String temperature = snapshot.child("temperature").getValue(String.class);
                        String humidity = snapshot.child("humidity").getValue(String.class);

                        MoistureVal.setText(moisture + "%");
                        TemperatureVal.setText(temperature + "°C");
                        HumidityVal.setText(humidity + "%");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.w(TAG, "Failed to read data", databaseError.toException());
                }
            });
        } else {
            Log.w(TAG, "User not authenticated");
        }
    }

    private void fetchPumpState() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String uid = currentUser.getUid();
            DatabaseReference pumpStateRef = db.child(uid).child("actuations").child("pumpstate");

            pumpStateRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String pumpState = dataSnapshot.getValue(String.class);
                    if (pumpState != null) {
                        actuatorSwitch.setChecked(pumpState.equals("on"));
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.w(TAG, "Failed to read pump state", databaseError.toException());
                }
            });
        }
    }
}