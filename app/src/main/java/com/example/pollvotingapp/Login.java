package com.example.pollvotingapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Login extends AppCompatActivity {

    private FirebaseDatabase database = FirebaseDatabase.getInstance("https://android-poll-database-default-rtdb.europe-west1.firebasedatabase.app");
    private DatabaseReference mDatabase = database.getReference();
    private DatabaseReference pollsDatabase = database.getReference();
    private DatabaseReference countdownsDatabase = database.getReference();
    private List<Poll> activePolls = new ArrayList<>();
    private List<Vote> votesList = new ArrayList<>();
    private List<String> usersList = new ArrayList<>();
    private HashMap<String, List> map = new HashMap<>();
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText editTextEmail = findViewById(R.id.email);
        EditText editTextPassword = findViewById(R.id.password);
        Button loginBtn = findViewById(R.id.loginButton);
        Button needAnAccountBtn = findViewById(R.id.needAnAccountBtn);

//        User user = new User();
//        user.checkUsers();

        pollsDatabase.child("Polls").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshotPolls : snapshot.getChildren()){
                    Poll currPoll = snapshotPolls.getValue(Poll.class);
                    System.out.println(currPoll);
                    Poll prom = null;
                    if (currPoll != null) {
                        prom = new Poll(currPoll.getPollQuestion(), currPoll.getOptions(), currPoll.isActive());
                    }
                    if (prom.isActive()){
                        activePolls.add(prom);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


//        countdownsDatabase.child("Countdown").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot snap : snapshot.getChildren()){
//
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        })
//        for (int i = 0; i < activePolls.size(); i++){
//            Poll currPoll = activePolls.get(i);
//            countdownsDatabase.child()
//        }

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email1 = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();

                String email = email1.replace('.', ',');

                mDatabase.child("Users").child(email).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            // Email exists in db, check if password is matching
                            User currUser = snapshot.getValue(User.class);
                            if (currUser.getPassword().equals(password)){
                                Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                if (email.equals("petargjorgjevski@gmail,com")){
                                    Intent adminIntent = new Intent(Login.this, AdminActivity.class);
                                    startActivity(adminIntent);
                                    finish();
                                }
                                else {
                                    Intent mainIntent = new Intent(Login.this, MainActivity.class);
//                                    Bundle args = new Bundle();
//                                    args.putSerializable("ARRAYLIST", (Serializable)activePolls);
//                                    mainIntent.putExtra("BUNDLE", args);
                                    mainIntent.putExtra("Email", email);
                                    startActivity(mainIntent);
                                    finish();
                                }
                            }
                            else {
                                Toast.makeText(Login.this, "Incorrect password", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(Login.this, "Account doesn't exist", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

        needAnAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent makeAnAccountIntent = new Intent(Login.this, Register.class);
                startActivity(makeAnAccountIntent);
            }
        });
    }


//    @Override
//    protected void onStart() {
//        super.onStart();
//
//        FirebaseUser fbuser = FirebaseAuth.getInstance().getCurrentUser();
//
//        if (fbuser != null){
//            startActivity(new Intent(Login.this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
//        }
//    }
    //TODO uncomment this before launching final version, this method remembers the user login, commented due to testing purposes
}