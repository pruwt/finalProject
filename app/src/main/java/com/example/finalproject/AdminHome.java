package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminHome extends AppCompatActivity {
    Button viewUsersbtn,sensorsbtnadmin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        viewUsersbtn = findViewById(R.id.viewUsersbtn);
        sensorsbtnadmin = findViewById(R.id.sensorsbtnadmin);
        viewUsersbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminHome.this, viewFarmers.class);
                startActivity(intent);
            }
        });

        sensorsbtnadmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminHome.this, SensorViewAdmin.class);
                startActivity(intent);
            }
        });
    }
}