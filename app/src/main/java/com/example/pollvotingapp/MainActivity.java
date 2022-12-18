package com.example.pollvotingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import org.checkerframework.checker.units.qual.A;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    String email;
    private User user;
    //private ArrayList<Vote> votes;
    //private Poll poll;
    private FirebaseDatabase database = FirebaseDatabase.getInstance("https://android-poll-database-default-rtdb.europe-west1.firebasedatabase.app");
    private DatabaseReference reference = database.getReference();
    private DatabaseReference votesDb = database.getReference();
    //"https://android-poll-database-default-rtdb.europe-west1.firebasedatabase.app" -> database


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Intent intent = getIntent();
//        Bundle args = intent.getBundleExtra("BUNDLE");
//        ArrayList<Poll> polls = (ArrayList<Poll>) args.getSerializable("ARRAYLIST");
        Button myVotes = findViewById(R.id.myVotesBtn);


        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        email = getIntent().getStringExtra("Email");

        ArrayList<Poll> polls = new ArrayList<>();

        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, polls, email);
        recyclerView.setAdapter(adapter);


        DatabaseReference pollsDatabase = database.getReference();
        pollsDatabase.child("Polls").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                polls.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Poll currPoll = dataSnapshot.getValue(Poll.class);
                    if (currPoll.isActive()){
                        polls.add(currPoll);
                    }else{
                        // finished polls
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        // TODO When clicked on myVotes, pass votes in activity


        myVotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myVotesIntent = new Intent(MainActivity.this, MyVotesActivity.class);
                myVotesIntent.putExtra("Email", email);
                startActivity(myVotesIntent);
            }
        });
    }
}