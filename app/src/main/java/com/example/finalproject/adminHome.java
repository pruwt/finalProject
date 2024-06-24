package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class adminHome extends AppCompatActivity {

    private Button addFarmerButton;
    private Button deleteFarmerButton;
    private Button viewFarmersButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);


        deleteFarmerButton = findViewById(R.id.deleteFarmerButton);
        viewFarmersButton = findViewById(R.id.viewFarmersButton);


        deleteFarmerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to Delete Farmer Activity
//                Intent intent = new Intent(AdminHomeActivity.this, DeleteFarmerActivity.class);
//                startActivity(intent);
            }
        });

        viewFarmersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to View Farmers Activity
//                Intent intent = new Intent(AdminHomeActivity.this, ViewFarmersActivity.class);
//                startActivity(intent);
            }
        });
    }
}
