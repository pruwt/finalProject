//package com.example.finalproject;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.ListView;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class viewFarmers extends AppCompatActivity {
//
//    private static final String TAG = "viewFarmers";
//    private DatabaseReference db;
//    private FirebaseAuth mAuth;
//    private ListView farmersListView;
//    private farmerAdapter adapter;
//    private List<Farmer> farmerList;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_view_farmers);
//
//        // Initialize Firebase Auth
//        mAuth = FirebaseAuth.getInstance();
//
//        // Initialize Realtime Database
//        db = FirebaseDatabase.getInstance().getReference("users");
//
//        // Initialize UI elements
//        farmersListView = findViewById(R.id.farmersListView);
//        farmerList = new ArrayList<>();
//        adapter = new farmerAdapter(this, farmerList);
//        farmersListView.setAdapter(adapter);
//
//        // Fetch and display data
//        fetchFarmersData();
//
//        // Set item click listener to pass the ID back
//        farmersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Farmer selectedFarmer = adapter.getItem(position);
//                Intent intent = new Intent(viewFarmers.this, AdminHome.class);
//                intent.putExtra("selectedFarmerId", selectedFarmer.getId());
//                startActivity(intent);
//            }
//        });
//    }
//
//    private void fetchFarmersData() {
//        db.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                farmerList.clear();
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    String role = snapshot.child("role").getValue(String.class);
//                    if ("user".equals(role)) {
//                        String id = snapshot.getKey();
//                        String name = snapshot.child("name").getValue(String.class);
//                        String email = snapshot.child("email").getValue(String.class);
//                        String location = snapshot.child("farmlocation").getValue(String.class);
//                        Farmer farmer = new Farmer(id, name, email, location, role);
//                        farmerList.add(farmer);
//                    }
//                }
//                adapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Log.w(TAG, "Failed to read data", databaseError.toException());
//            }
//        });
//    }
//}

//package com.example.finalproject;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.ListView;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class viewFarmers extends AppCompatActivity {
//
//    private static final String TAG = "viewFarmers";
//    private DatabaseReference db;
//    private FirebaseAuth mAuth;
//    private ListView farmersListView;
//    private farmerAdapter adapter;
//    private List<Farmer> farmerList;
//    private String selectedFarmerId = null;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_view_farmers);
//
//        // Initialize Firebase Auth
//        mAuth = FirebaseAuth.getInstance();
//
//        // Initialize Realtime Database
//        db = FirebaseDatabase.getInstance().getReference("users");
//
//        // Initialize UI elements
//        farmersListView = findViewById(R.id.farmersListView);
//        farmerList = new ArrayList<>();
//        adapter = new farmerAdapter(this, farmerList);
//        farmersListView.setAdapter(adapter);
//
//        // Fetch and display data
//        fetchFarmersData();
//
//        // Set item click listener to pass the ID back
//        farmersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Farmer selectedFarmer = adapter.getItem(position);
//                selectedFarmerId = selectedFarmer.getId();
//                Intent intent = new Intent(viewFarmers.this, AdminHome.class);
//                intent.putExtra("selectedFarmerId", selectedFarmerId);
//                startActivity(intent);
//            }
//        });
//    }
//
//    private void fetchFarmersData() {
//        db.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                farmerList.clear();
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    String role = snapshot.child("role").getValue(String.class);
//                    if ("user".equals(role)) {
//                        String id = snapshot.getKey();
//                        String name = snapshot.child("name").getValue(String.class);
//                        String email = snapshot.child("email").getValue(String.class);
//                        String location = snapshot.child("farmlocation").getValue(String.class);
//                        Farmer farmer = new Farmer(id, name, email, location, role);
//                        farmerList.add(farmer);
//                    }
//                }
//                adapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Log.w(TAG, "Failed to read data", databaseError.toException());
//            }
//        });
//    }
//}

package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
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
        private farmerAdapter adapter;
        private List<Farmer> farmerList;
        private String selectedFarmerId = null;

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
            Button deleteFarmersButton = findViewById(R.id.deleteFarmersButton);
            farmerList = new ArrayList<>();
            adapter = new farmerAdapter(this, farmerList);
            farmersListView.setAdapter(adapter);

            // Fetch and display data
            fetchFarmersData();

            // Set item click listener to pass the ID back
            farmersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Farmer selectedFarmer = adapter.getItem(position);
                    selectedFarmerId = selectedFarmer.getId();
                    // Show toast to indicate selection
                    Toast.makeText(viewFarmers.this, "Selected Farmer: " + selectedFarmer.getName(), Toast.LENGTH_SHORT).show();
                }
            });

            // Set click listener for delete button
            deleteFarmersButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (selectedFarmerId != null) {
                        checkAdminAndDeleteFarmer(selectedFarmerId);
                    } else {
                        Toast.makeText(viewFarmers.this, "No farmer selected for deletion", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        private void fetchFarmersData() {
            db.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    farmerList.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String role = snapshot.child("role").getValue(String.class);
                        if ("user".equals(role)) {
                            String id = snapshot.getKey();
                            String name = snapshot.child("name").getValue(String.class);
                            String email = snapshot.child("email").getValue(String.class);
                            String location = snapshot.child("farmlocation").getValue(String.class);
                            Farmer farmer = new Farmer(id, name, email, location, role);
                            farmerList.add(farmer);
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

        private void checkAdminAndDeleteFarmer(String farmerId) {
            String currentUserId = mAuth.getCurrentUser().getUid();
            db.child(currentUserId).child("role").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String role = snapshot.getValue(String.class);
                    if ("admin".equals(role)) {
                        deleteFarmerData(farmerId);
                    } else {
                        Toast.makeText(viewFarmers.this, "You do not have permission to delete farmers", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.w(TAG, "Error checking admin role", error.toException());
                }
            });
        }

        private void deleteFarmerData(String farmerId) {
            DatabaseReference farmerRef = db.child(farmerId);

            farmerRef.removeValue()
                    .addOnSuccessListener(aVoid -> {
                        Log.d(TAG, "Farmer data deleted successfully");
                        Toast.makeText(viewFarmers.this, "Farmer deleted", Toast.LENGTH_SHORT).show();
                        // Clear the selection
                        selectedFarmerId = null;
                        // Refresh the farmer list
                        fetchFarmersData();
                    })
                    .addOnFailureListener(e -> {
                        Log.w(TAG, "Error deleting farmer data", e);
                        Toast.makeText(viewFarmers.this, "Failed to delete farmer", Toast.LENGTH_SHORT).show();
                    });
        }
    }



