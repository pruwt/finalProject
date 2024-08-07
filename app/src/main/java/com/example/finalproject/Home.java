package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Home extends AppCompatActivity {
    Button addGreenhouseButton,sensorsHomeButton, viewIrrigationButton,articlesHomeButton, DashboardButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        addGreenhouseButton = findViewById(R.id.addGreenhouseButton);
        sensorsHomeButton = findViewById(R.id.sensorsHomeButton);
        viewIrrigationButton = findViewById(R.id.viewIrrigationButton);
        articlesHomeButton = findViewById(R.id.articlesHomeButton);
        DashboardButton = findViewById(R.id.DashboardButton);

        addGreenhouseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, addGreenhouse.class);
                startActivity(intent);
            }
        });

        DashboardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, Dashboard.class);
                startActivity(intent);
            }
        });

        sensorsHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this,AddSensors.class);
                startActivity(intent);
            }
        });

        viewIrrigationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this,viewIrrigation.class);
                startActivity(intent);
            }
        });
        articlesHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this,ArticleResource.class);
                startActivity(intent);
            }
        });
    }
}