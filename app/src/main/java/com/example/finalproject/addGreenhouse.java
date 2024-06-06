package com.example.finalproject;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class addGreenhouse extends AppCompatActivity {

    private static final String TAG = "AddGreenhouseActivity";
    private FirebaseFirestore db;

    private TextView herbNameValueTextView;
    private TextView soilMoistureValueTextView;
    private TextView locationValueTextView;
    private Button startButton, stopButton, editButton, deleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_greenhouse);

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();

        // Initialize UI elements
        herbNameValueTextView = findViewById(R.id.herbNameValueTextView);
        soilMoistureValueTextView = findViewById(R.id.soilMoistureValueTextView);
        locationValueTextView = findViewById(R.id.locationValueTextView);
        startButton = findViewById(R.id.startButton);
        stopButton = findViewById(R.id.stopButton);
        editButton = findViewById(R.id.editButton);
        deleteButton = findViewById(R.id.deleteButton);

        // Get values from TextViews
        String herbName = herbNameValueTextView.getText().toString();
        String soilMoisture = soilMoistureValueTextView.getText().toString();
        String location = locationValueTextView.getText().toString();

        Map<String, Object> greenhouse = new HashMap<>();
        greenhouse.put("herbName", herbName);
        greenhouse.put("soilMoisture", soilMoisture);
        greenhouse.put("location", location);

        // Add data to Firestore on button click
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addGreenhouseData(greenhouse);
            }
        });
    }

    private void addGreenhouseData(Map<String, Object> greenhouse) {
        db.collection("greenhouses")
                .add(greenhouse)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }
}
