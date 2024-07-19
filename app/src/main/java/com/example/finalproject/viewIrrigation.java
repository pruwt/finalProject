package com.example.finalproject;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class viewIrrigation extends AppCompatActivity {

    private static final String TAG = "viewIrrigation";
    private DatabaseReference db;
    private FirebaseAuth mAuth;
    private ListView readingsListView;
    private ReadingsAdapter adapter;
    private List<Reading> readingsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_irrigation); // Ensure this matches your XML layout file name

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Initialize Realtime Database
        db = FirebaseDatabase.getInstance().getReference("UsersData");

        // Initialize UI elements
        readingsListView = findViewById(R.id.readingsListView); // Ensure this matches the ID in your XML layout
        readingsList = new ArrayList<>();
        adapter = new ReadingsAdapter();
        readingsListView.setAdapter(adapter);

        // Fetch and display data
        fetchReadingsData();

        // Set item click listener if needed
        readingsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Handle item click if necessary
            }
        });
    }

    private void fetchReadingsData() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String uid = currentUser.getUid();
            DatabaseReference readingsRef = db.child(uid).child("readings");

            readingsRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    readingsList.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String moisture = snapshot.child("moisture").getValue(String.class);
                        String timestamp = snapshot.child("timestamp").getValue(String.class);
                        readingsList.add(new Reading(timestamp, moisture));
                    }
                    adapter.notifyDataSetChanged();
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

    private class ReadingsAdapter extends ArrayAdapter<Reading> {

        public ReadingsAdapter() {
            super(viewIrrigation.this, R.layout.list_item_reading, readingsList);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.list_item_reading, parent, false);
            }

            Reading reading = getItem(position);

            TextView timestampTextView = convertView.findViewById(R.id.timestampTextView);
            TextView moistureTextView = convertView.findViewById(R.id.moistureTextView);

            if (reading != null) {
                timestampTextView.setText(reading.getTimestamp());
                moistureTextView.setText("Moisture: " + reading.getMoisture());
            }

            return convertView;
        }
    }

    private static class Reading {
        private String timestamp;
        private String moisture;

        public Reading(String timestamp, String moisture) {
            this.timestamp = timestamp;
            this.moisture = moisture;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public String getMoisture() {
            return moisture;
        }
    }
}
