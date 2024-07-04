package com.example.finalproject;

import android.content.Intent;
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

public class viewFarmers extends AppCompatActivity {

    private static final String TAG = "viewFarmers";
    private DatabaseReference db;
    private FirebaseAuth mAuth;
    private ListView farmersListView;
    private ArrayAdapter<String> adapter;
    private List<String> farmerList;
    private List<String> farmerIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_farmers);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Initialize Realtime Database
        db = FirebaseDatabase.getInstance().getReference("users");

        // Initialize UI elements
        farmersListView = findViewById(R.id.farmersListView);
        farmerList = new ArrayList<>();
        farmerIds = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, farmerList);
        farmersListView.setAdapter(adapter);

        // Fetch and display data
        fetchFarmersData();

        // Set item click listener to pass the ID back
        farmersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedId = farmerIds.get(position);
                Intent intent = new Intent(viewFarmers.this, AdminHome.class);
                intent.putExtra("selectedFarmerId", selectedId);
                startActivity(intent);
            }
        });
    }

    private void fetchFarmersData() {
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                farmerList.clear();
                farmerIds.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String role = snapshot.child("role").getValue(String.class);
                    if ("user".equals(role)) {
                        String name = snapshot.child("name").getValue(String.class);
                        String email = snapshot.child("email").getValue(String.class);
                        String location = snapshot.child("farmlocation").getValue(String.class);
                        String id = snapshot.getKey();
                        farmerList.add(name + "\nEmail: " + email + "\nLocation: " + location);
                        farmerIds.add(id);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG, "Failed to read data", databaseError.toException());
            }
        });
    }
}