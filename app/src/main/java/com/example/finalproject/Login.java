package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        signuptextview = findViewById(R.id.signredirect);
        loginnametxt = findViewById(R.id.loginnametxt);
        loginpasstxt = findViewById(R.id.loginpasstxt);
        loginbtn = findViewById(R.id.loginbtn);

loginbtn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        String email = loginnametxt.getText().toString();
        String password = loginpasstxt.getText().toString();

        if (validateInput(email, password) && validatePass()) {
            loginUser(email, password);
        }
    }
});

        signuptextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, SignUp.class);
                startActivity(intent);
            }
        });
    }


    private boolean validateInput(String email, String password) {
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!isValidEmail(email)) {
            loginnametxt.setError("Invalid email format");
            loginnametxt.requestFocus();
            return false;
        }

        return true;
    }

    //email validation
        private boolean isValidEmail(String email) {
           return Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }


    public Boolean validatePass (){
        //in our case it's name
        String val = loginpasstxt.getText().toString();
        if(val.isEmpty()){
            loginpasstxt.setError("password cannot be empty");
            return true;
        }
        else {
            loginpasstxt.setError(null);
            return true;
        }
    }



//    private void loginUser(String email, String password) {
//        mAuth.signInWithEmailAndPassword(email, password)
//                .addOnCompleteListener(this, task -> {
//                    if (task.isSuccessful()) {
//                        // Sign in success, update UI with the signed-in user's information
////                       check users role
//                        if (task.isSuccessful()) {
//                            FirebaseUser user = mAuth.getCurrentUser();
//                            if (user != null) {
//                                checkUserRole(user.getUid()); //check user role and redirect based on that
//                            } else {
//                                // If sign in fails, display a message to the user.
//                                String errorMessage = task.getException().getMessage();
//
//                                if (errorMessage.contains("The email address is badly formatted")) {
//                                    Toast.makeText(Login.this, "Invalid email format", Toast.LENGTH_SHORT).show();
//                                } else if (errorMessage.contains("There is no user record corresponding to this identifier")) {
//                                    Toast.makeText(Login.this, "No account found with this email", Toast.LENGTH_SHORT).show();
//                                } else if (errorMessage.contains("The password is invalid or the user does not have a password")) {
//                                    Toast.makeText(Login.this, "Incorrect password", Toast.LENGTH_SHORT).show();
//                                } else {
//                                    Toast.makeText(Login.this, "Authentication failed: " + errorMessage, Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        }
////
////                        Intent intent = new Intent(Login.this, Home.class);
////                        startActivity(intent);
//                    }
//                });
//    }

    private void loginUser(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            checkUserRole(user.getUid()); // Check user role and redirect based on that
                        }
                    } else {
                        // If sign in fails, display a message to the user.
                        String errorMessage = task.getException().getMessage();

                        // Check for specific error message related to incorrect password
                        if (errorMessage != null && errorMessage.contains("The password is invalid")) {
                            Toast.makeText(Login.this, "Incorrect password", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(Login.this, "Authentication failed: " + errorMessage, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }



    //check user role function
    private void checkUserRole(String userId) {
        mDatabase.child("users").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String role = snapshot.child("role").getValue(String.class); // Fetch the role
                    if ("admin".equals(role)) {
                        Intent intent = new Intent(Login.this, AdminHome.class); // Redirect to admin screen
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(Login.this, Home.class); // Redirect to user screen
                        startActivity(intent);
                    }
                } else {
                    Toast.makeText(Login.this, "User not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Login.this, "Database error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}




//val pass
