package com.example.pollvotingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class Register extends AppCompatActivity {

    private FirebaseDatabase database = FirebaseDatabase.getInstance("https://android-poll-database-default-rtdb.europe-west1.firebasedatabase.app");
    //"https://android-poll-database-default-rtdb.europe-west1.firebasedatabase.app" -> database
    private DatabaseReference mDatabase = database.getReference();
// ...
    long counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        EditText editTextFullName = findViewById(R.id.fullNameRegister);
        EditText editTextEmail = findViewById(R.id.emailRegister);
        EditText editTextPassword = findViewById(R.id.passwordRegister);
        Button registerBtn = findViewById(R.id.registerBtn);
        Button loginNowBtn = findViewById(R.id.loginNowBtn);

        loginNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fullName = editTextFullName.getText().toString();
                String email1 = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();

                if (fullName.isEmpty() || email1.isEmpty() || password.isEmpty()){
                    // The user hasn't filled one/all forms
                    Toast.makeText(Register.this, "Please fill in all the inputs", Toast.LENGTH_LONG).show();
                }
                else{
                    // The user has filled all inputs, handle data
                    // TODO Check if this user exists, if it does do not allow registration
                    String email = email1.replace('.', ',');
                    User user = new User(fullName, email, password);

                    user.setEmail(user.getEmail());

                    DatabaseReference ref = database.getReference();
                    ref.child("Users").child(user.getEmail()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()){
                                Toast.makeText(Register.this, "Sorry, user already exists", Toast.LENGTH_SHORT).show();
                            }else {
                                mDatabase.child("Users").child(user.getEmail()).setValue(user);
                                Toast.makeText(Register.this, "Registration successful", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });
    }

}



