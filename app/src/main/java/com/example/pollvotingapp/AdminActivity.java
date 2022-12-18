package com.example.pollvotingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminActivity extends AppCompatActivity {

    private FloatingActionButton createPollBtn;
    private User user;
    private String fullName;
    private String email;
    private FirebaseDatabase database = FirebaseDatabase.getInstance("https://android-poll-database-default-rtdb.europe-west1.firebasedatabase.app");

    // only login with email petargjorgjevski@gmail.com will allow user to access admin activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        ArrayList<Vote> votes = new ArrayList<>();

        fullName = getIntent().getStringExtra("Full Name");
        email = getIntent().getStringExtra("Email");

        createPollBtn = findViewById(R.id.actionBtn);
        RecyclerView recyclerView = findViewById(R.id.adminRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        AdminAdapter adapter = new AdminAdapter(this, votes);
        recyclerView.setAdapter(adapter);

        DatabaseReference votedb = database.getReference();
        votedb.child("Votes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                votes.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Vote currVote = dataSnapshot.getValue(Vote.class);
                    votes.add(currVote);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        createPollBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminActivity.this, CreatePollActivity.class));
            }
        });

    }
}