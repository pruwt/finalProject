package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    EditText loginnametxt, loginpasstxt;
    Button loginbtn;
    TextView signuptextview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        signuptextview = findViewById(R.id.signredirect);
        loginnametxt = findViewById(R.id.loginnametxt);
        loginpasstxt = findViewById(R.id.loginpasstxt);
        loginbtn = findViewById(R.id.loginbtn);

//loginbtn.setOnClickListener(new View.OnClickListener() {
//    @Override
//    public void onClick(View v) {
//        if(!validatePass() | !validateUsername()){
//
//        }else{
//            checkUser(); //if they are true then user is checked
//        }
//    }
//});

        signuptextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, SignUp.class);
                startActivity(intent);
            }
        });
    }
}

//    public Boolean validateUsername (){
//        //in our case it's name
//        String val = loginnametxt.getText().toString();
//        if(val.isEmpty()){
//            loginnametxt.setError("Name cannot be empty");
//            return true;
//        }
//        else {
//            loginnametxt.setError(null);
//            return true;
//        }
//    }
//
//    //pass validation
//    public Boolean validatePass (){
//        //in our case it's name
//        String val = loginpasstxt.getText().toString();
//        if(val.isEmpty()){
//            loginpasstxt.setError("Name cannot be empty");
//            return true;
//        }
//        else {
//            loginpasstxt.setError(null);
//            return true;
//        }
//    }
//
//    //check on db if exist
//    public void checkUser(){
//        String userdbname = loginnametxt.getText().toString();
//        String userdbpass = loginpasstxt.getText().toString();
//
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
//
//        Query checkUserDB = reference.orderByChild("name").equalTo(userdbname);
//
//        checkUserDB.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                if (snapshot.exists()) {
//                    //user exists{
//                    loginnametxt.setError(null);
//                    String passfromdb = snapshot.child(userdbname).child("password").getValue(String.class); //compare value in db
//
//                    if (passfromdb.equals(userdbpass)) {
//                        loginnametxt.setError(null);
//                        Intent intent = new Intent(Login.this, Home.class);
//                        startActivity(intent);
//                    } else {
//                        loginpasstxt.setError("Invalid login details");
//                        loginpasstxt.requestFocus(); //highlight
//                    }
//
//                } else {
//                    //user does not exisiy
//                    loginnametxt.setError("User does not exist");
//                    loginnametxt.requestFocus();
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }
//}

//val pass
