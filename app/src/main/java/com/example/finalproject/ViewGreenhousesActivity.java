package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import android.view.ViewGroup;


public class ViewGreenhousesActivity extends AppCompatActivity {

    private static final String TAG = "ViewGreenhousesActivity";
    private DatabaseReference db;
    private FirebaseAuth mAuth;
    private ListView greenhousesListView;
    private GreenhouseAdapter adapter;
    private List<Greenhouse> greenhouseList;
    private List<String> greenhouseIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_greenhouses);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Initialize Realtime Database
        db = FirebaseDatabase.getInstance().getReference("users");

        // Initialize UI elements
        greenhousesListView = findViewById(R.id.greenhousesListView);
        greenhouseList = new ArrayList<>();
        greenhouseIds = new ArrayList<>();
        adapter = new GreenhouseAdapter();
        greenhousesListView.setAdapter(adapter);

        // Fetch and display data
        fetchGreenhousesData();

        // Set item click listener to pass the ID back
        greenhousesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedId = greenhouseIds.get(position);
                Intent intent = new Intent(ViewGreenhousesActivity.this, addGreenhouse.class);
                intent.putExtra("selectedGreenhouseId", selectedId);
                startActivity(intent);
            }
        });
    }

    private void fetchGreenhousesData() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String uid = currentUser.getUid();
            DatabaseReference greenhousesRef = db.child(uid).child("greenhouses");

            greenhousesRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    greenhouseList.clear();
                    greenhouseIds.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String herbName = snapshot.child("herbName").getValue(String.class);
                        String soilMoisture = snapshot.child("soilMoisture").getValue(String.class);
                        String location = snapshot.child("location").getValue(String.class);
                        String id = snapshot.getKey();
                        greenhouseList.add(new Greenhouse(herbName, soilMoisture, location, id));
                        greenhouseIds.add(id);
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

    private class GreenhouseAdapter extends ArrayAdapter<Greenhouse> {

        public GreenhouseAdapter() {
            super(ViewGreenhousesActivity.this, R.layout.list_item, greenhouseList);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.list_item, parent, false);
            }

            Greenhouse greenhouse = getItem(position);

            TextView herbTextView = convertView.findViewById(R.id.herbTextView);
            TextView soilMoistureTextView = convertView.findViewById(R.id.soilMoistureTextView);
            TextView locationTextView = convertView.findViewById(R.id.locationTextView);

            if (greenhouse != null) {
                herbTextView.setText(greenhouse.getHerbName());
                soilMoistureTextView.setText("Soil Moisture: " + greenhouse.getSoilMoisture());
                locationTextView.setText("Location: " + greenhouse.getLocation());
            }

            return convertView;
        }
    }

    private static class Greenhouse {
        private String herbName;
        private String soilMoisture;
        private String location;
        private String id;

        public Greenhouse(String herbName, String soilMoisture, String location, String id) {
            this.herbName = herbName;
            this.soilMoisture = soilMoisture;
            this.location = location;
            this.id = id;
        }

        public String getHerbName() {
            return herbName;
        }

        public String getSoilMoisture() {
            return soilMoisture;
        }

        public String getLocation() {
            return location;
        }

        public String getId() {
            return id;
        }
    }
}
