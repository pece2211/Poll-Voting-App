package com.example.pollvotingapp;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ActivePolls {
    private ArrayList<Poll> polls = new ArrayList<>();
    private FirebaseDatabase database = FirebaseDatabase.getInstance("https://android-poll-database-default-rtdb.europe-west1.firebasedatabase.app");
    private DatabaseReference reference = database.getReference();

    public ActivePolls(){
        loopInPolls();
    }

    public ArrayList<Poll> getActivePolls(){
        return this.polls;
    }

    public void setActivePolls(Poll poll){
        polls.add(poll);
    }

    public void loopInPolls(){
        reference.child("Polls").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()){
                    Poll currPoll = snapshot1.getValue(Poll.class);
                    setActivePolls(currPoll);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
