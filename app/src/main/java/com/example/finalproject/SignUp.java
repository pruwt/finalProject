package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {
    //
        EditText nametxtsign,farmlocationtxtsign,passtxtsign,emailtxtsign;
        Button signupbtnsign;
        CheckBox adminRoleCheckBox;
        FirebaseDatabase db;
        DatabaseReference reference;

        private FirebaseAuth mAuth;
        private DatabaseReference mDatabase; //changed

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        //auth initialization

//
        nametxtsign = findViewById(R.id.nametxt);
        farmlocationtxtsign = findViewById(R.id.farmlocationtxt);
        passtxtsign = findViewById(R.id.passtxt);
        signupbtnsign = findViewById(R.id.signupbtn);
        emailtxtsign = findViewById(R.id.emailtxt);
        adminRoleCheckBox = findViewById(R.id.adminRoleCheckBox);

        mAuth = FirebaseAuth.getInstance();
       mDatabase = FirebaseDatabase.getInstance().getReference();


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

                    //HelperClass helperClass = new HelperClass(name, email, password, farmlocation);
                   // reference.child(name).setValue(helperClass); //child


                    if (validateInput(name, email, password, farmlocation)) {
                        registerUser(name, email, password, farmlocation);
                    }



                    //Toast.makeText(SignUp.this,"You have signed up successfully",Toast.LENGTH_SHORT).show();

                    //Intent intent = new Intent(SignUp.this, Login.class);//finish intent to signup/login
                    //startActivity(intent);
                }
               //login redirect here
            });
    }
    private boolean validateInput(String name, String email, String password, String farmlocation) {
        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || farmlocation.isEmpty()) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!isValidEmail(email)) {
            emailtxtsign.setError("Invalid email format");
            emailtxtsign.requestFocus();
            return false;
        }

        return true;
    }

    private boolean isValidEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    //reg user
    private void registerUser(String name, String email, String password, String farmlocation) {

        boolean isAdmin = adminRoleCheckBox.isChecked(); // Check the state of the checkbox
        String role = isAdmin ? "admin" : "user"; // Set role based on checkbox state

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // User registered successfully
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            // Save additional user details in the database
                            String userId = user.getUid();
                            HelperClass helperClass = new HelperClass(name, email, farmlocation,role);
                            mDatabase.child("users").child(userId).setValue(helperClass)
                                    .addOnCompleteListener(dbTask -> {
                                        if (dbTask.isSuccessful()) {
                                            Toast.makeText(SignUp.this, "You have signed up successfully", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(SignUp.this, Login.class);
                                            startActivity(intent);
                                        } else {
                                            Toast.makeText(SignUp.this, "Failed to save user details", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                    } else {
                        // If registration fails, display a message to the user.
                        Toast.makeText(SignUp.this, "Authentication failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
    public static class HelperClass {
        public String name;
        public String email;
        public String farmlocation;
        public String role;

        public HelperClass() {
            // Default constructor required for calls to DataSnapshot.getValue(HelperClass.class)
        }

        public HelperClass(String name, String email, String farmlocation, String role) {
            this.name = name;
            this.email = email;
            this.farmlocation = farmlocation;
            this.role = role;
        }
    }

}
