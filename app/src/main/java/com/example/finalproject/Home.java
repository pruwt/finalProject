package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Home extends AppCompatActivity {
    Button addGreenhouseButton, viewIrrigationButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        addGreenhouseButton = findViewById(R.id.addGreenhouseButton);
        viewIrrigationButton = findViewById(R.id.viewIrrigationButton);

        addGreenhouseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, addGreenhouse.class);
                startActivity(intent);
            }
        });

        viewIrrigationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, viewIrrigation.class);
                startActivity(intent);
            }
        });
    }
}