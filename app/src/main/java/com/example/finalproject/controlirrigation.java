package com.example.finalproject;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class controlirrigation extends AppCompatActivity {

    private Button irrigationOnButton;
    private Button irrigationOffButton;
    private DatabaseReference databaseReference;
    private static final String TAG = "ControlIrrigation";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controlirrigation);

        // Initialize Firebase Database
        String userId = "Kn5DiqEWICQf5mWHoHIAn5MNPO72"; // Replace with actual user ID
        databaseReference = FirebaseDatabase.getInstance().getReference("UsersData").child(userId).child("readings");

        irrigationOnButton = findViewById(R.id.irrigationOnButton);
        irrigationOffButton = findViewById(R.id.irrigationOffButton);

        irrigationOnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Irrigation ON button clicked");
                sendIrrigationStateToFirebase("on");
            }
        });

        irrigationOffButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Irrigation OFF button clicked");
                sendIrrigationStateToFirebase("off");
            }
        });
    }

    private void sendIrrigationStateToFirebase(String state) {
        String timestamp = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
        String dayOfWeek = new SimpleDateFormat("EEEE", Locale.getDefault()).format(new Date());
        DatabaseReference readingRef = databaseReference.child(dayOfWeek + ": " + timestamp);

        readingRef.child("moisture").setValue("1024");
        readingRef.child("timestamp").setValue(timestamp);
        readingRef.child("irrigationState").setValue(state);
    }

}
