package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminHome extends AppCompatActivity {
Button viewUsersbtn,articlesbtn,sensorsbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        viewUsersbtn = findViewById(R.id.viewUsersbtn);
        articlesbtn = findViewById(R.id.articlesbtn);
        sensorsbtn = findViewById(R.id.sensorsbtn);

        //sensor mgt section

//        sensorsbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //go to add sensors
//                Intent intent = new Intent(AdminHome.this,AddSensors.class);
//                startActivity(intent);
//            }
//        });
    }
}