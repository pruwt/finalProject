package com.example.finalproject;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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
    private ArrayAdapter<String> adapter;
    private List<String> readingsList;

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
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, readingsList);
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
                        readingsList.add("Timestamp: " + timestamp + "\nMoisture: " + moisture);
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
}
