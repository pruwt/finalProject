package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {
    //
        EditText nametxtsign,farmlocationtxtsign,passtxtsign,emailtxtsign;
        Button signupbtnsign;
        FirebaseDatabase db;
        DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
//
        nametxtsign = findViewById(R.id.nametxt);
        farmlocationtxtsign = findViewById(R.id.farmlocationtxt);
        passtxtsign = findViewById(R.id.passtxt);
        signupbtnsign = findViewById(R.id.signupbtn);
        emailtxtsign = findViewById(R.id.emailtxt);
//
            signupbtnsign.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //init db
                    db = FirebaseDatabase.getInstance();
                    reference = db.getReference("users"); // parent name in realtime db

                    //get input vals from fields

                    String name = nametxtsign.getText().toString();
                    String email = emailtxtsign.getText().toString();
                    String password = passtxtsign.getText().toString();
                    String farmlocation = farmlocationtxtsign.getText().toString();

                    HelperClass helperClass = new HelperClass(name, email, password, farmlocation);
                    reference.child(name).setValue(helperClass); //child

                    Toast.makeText(SignUp.this,"You have signed up successfully",Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(SignUp.this, Login.class);//finish intent to signup/login
                    startActivity(intent);
                }
               //login redirect here
            });
    }
    }
