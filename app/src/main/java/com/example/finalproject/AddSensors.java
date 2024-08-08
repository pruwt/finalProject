package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.hardware.Sensor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

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

public class AddSensors extends AppCompatActivity {
    private DatabaseReference db;
    private FirebaseAuth mAuth;
    private EditText sensorEditText, numofsensorsEditText, conditionEditText;
    private ImageButton homeButtonSensors,greenhousesButtonSensors,irrigationButtonSensors,logoutButtonSensors;
    private Button addSensorBtn, viewSensorsBtn, deleteSensorBtn,updateSensorBtn;
    private String selectedSensorId = null; // This will store the ID of the selected sensor


    private static final String TAG = "AddSensors";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sensors);

        //init the rtdb

        db = FirebaseDatabase.getInstance().getReference("users");

        //init auth
        mAuth = FirebaseAuth.getInstance();

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("selectedSensorId")) {
            selectedSensorId = intent.getStringExtra("selectedSensorId");
            fetchSensorDetails(selectedSensorId);
        }


        sensorEditText = findViewById(R.id.sensorEditText);
        numofsensorsEditText = findViewById(R.id.numofsensorsEditText);
        conditionEditText = findViewById(R.id.conditionEditText);
        addSensorBtn = findViewById(R.id.addSensorBtn);
        updateSensorBtn = findViewById(R.id.updateSensorBtn);
        deleteSensorBtn = findViewById(R.id.deleteSensorBtn);
        viewSensorsBtn = findViewById(R.id.viewSensorsBtn);
        homeButtonSensors= findViewById(R.id.homeButtonSensors);
        greenhousesButtonSensors= findViewById(R.id.greenhousesButtonSensors);
        irrigationButtonSensors= findViewById(R.id.irrigationButtonSensors);
        logoutButtonSensors= findViewById(R.id.logoutButtonSensors);

        addSensorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sensorName = sensorEditText.getText().toString();
                String NumOfSensors = numofsensorsEditText.getText().toString();
                String sensorCondition = conditionEditText.getText().toString();

                if (!sensorName.isEmpty() && !NumOfSensors.isEmpty() && !sensorCondition.isEmpty()) {
                    addSensorData(sensorName, NumOfSensors, sensorCondition);
                } else {
                    Toast.makeText(AddSensors.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });

        updateSensorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedSensorId != null) {
                    String sensorName = sensorEditText.getText().toString();
                    String numOfSensors = numofsensorsEditText.getText().toString();
                    String sensorCondition = conditionEditText.getText().toString();

                    if (!sensorName.isEmpty() && !numOfSensors.isEmpty() && !sensorCondition.isEmpty()) {
                        updateSensorData(selectedSensorId, sensorName, numOfSensors, sensorCondition);
                    } else {
                        Toast.makeText(AddSensors.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(AddSensors.this, "No sensor selected for update", Toast.LENGTH_SHORT).show();
                }
            }
        });

        deleteSensorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedSensorId != null) {
                    deleteSensorData(selectedSensorId);
                } else {
                    Toast.makeText(AddSensors.this, "No sensor selected for deletion", Toast.LENGTH_SHORT).show();
                }
            }

        });

        viewSensorsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent = new Intent(AddSensors.this, ViewSensorsUsers.class);
            startActivity(intent);
            }
        });

        //navbar
        homeButtonSensors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddSensors.this,Home.class);
                startActivity(intent);
            }
        });

        greenhousesButtonSensors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddSensors.this,addGreenhouse.class);
                startActivity(intent);
            }
        });

        irrigationButtonSensors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddSensors.this,Dashboard.class);
                startActivity(intent);
            }
        });

        logoutButtonSensors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddSensors.this,Login.class);
                startActivity(intent);
            }
        });
    }
    //fetch sensor data

    private void fetchSensorDetails(String sensorId) {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String uid = currentUser.getUid();
            DatabaseReference sensorRef = db.child(uid).child("sensors").child(sensorId);

            sensorRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String sensorType = dataSnapshot.child("sensorType").getValue(String.class);
                        String numOfSensors = dataSnapshot.child("numOfSensors").getValue(String.class);
                        String sensorCondition = dataSnapshot.child("sensorCondition").getValue(String.class);

                        // Populate the UI fields
                        sensorEditText.setText(sensorType);
                        numofsensorsEditText.setText(numOfSensors);
                        conditionEditText.setText(sensorCondition);
                    } else {
                        Toast.makeText(AddSensors.this, "Sensor data not found", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.w(TAG, "Failed to read sensor details", databaseError.toException());
                    Toast.makeText(AddSensors.this, "Failed to load sensor details", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this, "User not authenticated", Toast.LENGTH_SHORT).show();
        }
    }


        //add sensor function
        private void addSensorData (String sensorName, String NumOfSensors, String sensorCondition){
            FirebaseUser currentUser = mAuth.getCurrentUser();
            if(currentUser !=null){
                String uid = currentUser.getUid();

                //hashmap that will stroe the data
                Map<String,Object> sensor = new HashMap<>();
                sensor.put("sensorType",sensorName);
                sensor.put("numOfSensors",NumOfSensors);
                sensor.put("sensorCondition",sensorCondition);

                //store sensor data under the users UID
                db.child(uid).child("sensors").push().setValue(sensor).
                        addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "Data added successfully");
                                Toast.makeText(AddSensors.this,"Sensor added!",Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Errod adding data");
                                Toast.makeText(AddSensors.this,"Failed to add sensor!",Toast.LENGTH_SHORT).show();

                            }
                        });
            } else{
                Toast.makeText(this, "User not authenticated", Toast.LENGTH_SHORT).show();
            }
    }

    private void deleteSensorData(String sensorId){
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser !=null){
            String uid = currentUser.getUid();
            DatabaseReference sensorRef = db.child(uid).child("sensors").child(sensorId);

            //successful listener, when id is matched

            sensorRef.removeValue()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(AddSensors.this, "Sensor deleted successfully",Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AddSensors.this, "Failed to delete sensor",Toast.LENGTH_SHORT).show();

                        }
                    });
        } else {
            Toast.makeText(this, "User not authenticated", Toast.LENGTH_SHORT).show();
        }
    }

    //update
    private void updateSensorData(String sensorId, String sensorName, String numOfSensors, String sensorCondition) {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String uid = currentUser.getUid();

            // HashMap to store the updated data
            Map<String, Object> sensor = new HashMap<>();
            sensor.put("sensorType", sensorName);
            sensor.put("numOfSensors", numOfSensors);
            sensor.put("sensorCondition", sensorCondition);

            // Update sensor data
            db.child(uid).child("sensors").child(sensorId).updateChildren(sensor)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(TAG, "Data updated successfully");
                            Toast.makeText(AddSensors.this, "Sensor updated!", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error updating data");
                            Toast.makeText(AddSensors.this, "Failed to update sensor!", Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(this, "User not authenticated", Toast.LENGTH_SHORT).show();
        }
    }
}
