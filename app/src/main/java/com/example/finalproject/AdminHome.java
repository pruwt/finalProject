package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class AdminHome extends AppCompatActivity {
    Button viewFarmersButton,viewSensorsButton;

    ImageButton homeButton,greenhousesButton,irrigationButton,logoutButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        viewFarmersButton = findViewById(R.id.viewFarmersButton);
        viewSensorsButton = findViewById(R.id.viewSensorsButton);
//        deleteFarmersButton = findViewById(R.id.deleteFarmersButton);
        homeButton = findViewById(R.id.homeButton);
        greenhousesButton = findViewById(R.id.greenhousesButton);
        irrigationButton = findViewById(R.id.irrigationButton);
        logoutButton = findViewById(R.id.logoutButton);
        viewFarmersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminHome.this, viewFarmers.class);
                startActivity(intent);
            }
        });

        viewSensorsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminHome.this, SensorViewAdmin.class);
                startActivity(intent);
            }
        });

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminHome.this, AdminHome.class);
                startActivity(intent);
            }
        });

        irrigationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminHome.this, viewFarmers.class);
                startActivity(intent);
            }
        });

        greenhousesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminHome.this, ViewSensorsUsers.class);
                startActivity(intent);
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminHome.this, Login.class);
                startActivity(intent);
            }
        });
    }
}

//package com.example.finalproject;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ImageButton;
//import android.widget.Toast;
//
//import com.google.android.gms.tasks.OnFailureListener;
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//
//public class AdminHome extends AppCompatActivity {
//    private static final String TAG = "AdminHome";
//
//    Button viewFarmersButton, viewSensorsButton, deleteFarmersButton;
//    ImageButton homeButton, greenhousesButton, irrigationButton, logoutButton;
//    private DatabaseReference db;
//    private FirebaseAuth mAuth;
//    private String selectedFarmerId;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_admin_home);
//
//        // Initialize Firebase Auth
//        mAuth = FirebaseAuth.getInstance();
//
//        // Initialize Realtime Database
//        db = FirebaseDatabase.getInstance().getReference("users");
//
//        // Initialize UI elements
//        viewFarmersButton = findViewById(R.id.viewFarmersButton);
//        viewSensorsButton = findViewById(R.id.viewSensorsButton);
////        deleteFarmersButton = findViewById(R.id.deleteFarmersButton);
//        homeButton = findViewById(R.id.homeButton);
//        greenhousesButton = findViewById(R.id.greenhousesButton);
//        irrigationButton = findViewById(R.id.irrigationButton);
//        logoutButton = findViewById(R.id.logoutButton);
//
//        // Check if there is an intent extra for the selected farmer ID
//        Intent intent = getIntent();
//        if (intent != null && intent.hasExtra("selectedFarmerId")) {
//            selectedFarmerId = intent.getStringExtra("selectedFarmerId");
//            Log.d(TAG, "Selected Farmer ID: " + selectedFarmerId);
//        }
//
//        viewFarmersButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(AdminHome.this, viewFarmers.class);
//                startActivity(intent);
//            }
//        });
//
//        viewSensorsButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(AdminHome.this, SensorViewAdmin.class);
//                startActivity(intent);
//            }
//        });
//
//        deleteFarmersButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (selectedFarmerId != null) {
//                    deleteFarmerData(selectedFarmerId);
//                } else {
//                    Toast.makeText(AdminHome.this, "No farmer selected for deletion", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//
//        homeButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(AdminHome.this, AdminHome.class);
//                startActivity(intent);
//            }
//        });
//
//        irrigationButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(AdminHome.this, viewFarmers.class);
//                startActivity(intent);
//            }
//        });
//
//        greenhousesButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(AdminHome.this, ViewSensorsUsers.class);
//                startActivity(intent);
//            }
//        });
//
//        logoutButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(AdminHome.this, Login.class);
//                startActivity(intent);
//            }
//        });
//    }
//
//    private void deleteFarmerData(String farmerId) {
//        db.child(farmerId).removeValue()
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        Log.d(TAG, "Farmer deleted successfully");
//                        Toast.makeText(AdminHome.this, "Farmer deleted", Toast.LENGTH_SHORT).show();
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.w(TAG, "Error deleting farmer", e);
//                        Toast.makeText(AdminHome.this, "Failed to delete farmer", Toast.LENGTH_SHORT).show();
//                    }
//                });
//    }
//}
