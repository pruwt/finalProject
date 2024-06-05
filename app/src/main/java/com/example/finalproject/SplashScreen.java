package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {
    Button startbtn;
    TextView descr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

    startbtn = findViewById(R.id.btnStart);
    descr = findViewById(R.id.logodescription);

    startbtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(SplashScreen.this, Login.class);//finish intent to signup/login
            startActivity(intent);
        }
    });

    }
}