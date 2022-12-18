package com.example.pollvotingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;

public class MyVotesActivity extends AppCompatActivity {

    String email;
//    User user;
//    Poll poll;
    FirebaseDatabase database = FirebaseDatabase.getInstance("https://android-poll-database-default-rtdb.europe-west1.firebasedatabase.app");
    DatabaseReference mDatabase = database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_votes);

        ArrayList<Vote> votes = new ArrayList<>();

        Intent intent = getIntent();
//        Bundle args = intent.getBundleExtra("BUNDLE");
        email = intent.getStringExtra("Email");
//        ArrayList<Vote> votes = (ArrayList<Vote>) args.getSerializable("VOTES");

        RecyclerView recyclerView = findViewById(R.id.recyclerView2);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        MyVotesAdapter adapter = new MyVotesAdapter(this, votes, email);
        recyclerView.setAdapter(adapter);

        ArrayList<String> closedP = new ArrayList<>();

        DatabaseReference closedPolls = database.getReference();
        closedPolls.child("Polls").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                closedP.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()){
                    Poll currP = snapshot1.getValue(Poll.class);
                    if (!currP.isActive()){
                        closedP.add(currP.getPollQuestion());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference dbr = database.getReference();
        dbr.child("Votes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                votes.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()){
                    Vote currVote = snapshot1.getValue(Vote.class);
                    if (email.equals(currVote.getVoter())){
                        for (int i = 0 ; i < closedP.size(); i++){
                            String pollName = closedP.get(i);
                            if (pollName.equals(currVote.getQuestion())){
                                votes.add(currVote);
                            }
                        }

                    }
                    System.out.println("Current votes are " + votes);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}