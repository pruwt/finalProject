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