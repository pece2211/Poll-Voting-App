package com.example.pollvotingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class CreatePollActivity extends AppCompatActivity {

    private FirebaseDatabase database = FirebaseDatabase.getInstance("https://android-poll-database-default-rtdb.europe-west1.firebasedatabase.app");
    private DatabaseReference mDatabase = database.getReference();
    private DatabaseReference countdowns = database.getReference();
    private Button sumbitPollBtn;
    private ArrayList<Vote> votes = new ArrayList<>();
    private EditText pollQ;
    private EditText pollOpt;
    private EditText minutes;
    private Poll poll;
    private String pollQuestion;
    private String pollOptionsString;
    private String mns;
    private ArrayList<String> myList;
    private ArrayList<Poll> polls;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_poll);


        pollQ = findViewById(R.id.pollQuestionId);
        pollOpt = findViewById(R.id.pollOptionsId);
        minutes = findViewById(R.id.minutesOfPoll);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("Notification", "Notification", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        sumbitPollBtn = findViewById(R.id.submitPollBtn);
        sumbitPollBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pollQuestion = pollQ.getText().toString();
                pollOptionsString = pollOpt.getText().toString();
                mns = minutes.getText().toString();
                long minutesInLong = Long.parseLong(mns);
                long rmilis = minutesInLong * 60 * 1000;
                myList = new ArrayList<String>(Arrays.asList(pollOptionsString.split(",")));
                poll = new Poll(pollQuestion, myList, true);
                System.out.println(poll);

                mDatabase.child("Polls").child(poll.getPollQuestion()).setValue(poll);
                //polls.add(poll);
                DatabaseReference testZaTimer = database.getReference().child("Countdown").child(poll.getPollQuestion());
                // Send Notification
                NotificationCompat.Builder builder = new NotificationCompat.Builder(CreatePollActivity.this, "Notification");
                builder.setContentTitle("New poll has just been posted.");
                builder.setContentText("Poll Question: " + poll.getPollQuestion());
                builder.setSmallIcon(R.drawable.ic_launcher_background);
                builder.setAutoCancel(true);

                NotificationManagerCompat managerCompat = NotificationManagerCompat.from(CreatePollActivity.this);
                managerCompat.notify(1, builder.build());

        //USE THIS FOR TIMERS OF POLLS, TO BE IMPLEMENTED IN ADMIN ACTIVITY
        new CountDownTimer(rmilis, 1000) {

            public void onTick(long millisUntilFinished) {
                String calc = ("seconds remaining: " + millisUntilFinished / 1000);
                testZaTimer.setValue(calc);
                // Give admin votes in realtime


            }

            public void onFinish() {
                testZaTimer.setValue("done");
                //poll.notActive(); public void setActive(boolean active) {
                //        isActive = active;
                //    }
                Poll newp = new Poll(poll.getPollQuestion(), poll.getOptions(), false);
                DatabaseReference dbr = database.getReference();
                dbr.child("Polls").child(poll.getPollQuestion()).setValue(newp); // poll.getPollQuestion();


            }
        }.start();
                //countdowns.child("Countdown").child(poll.getPollQuestion()).setValue();
            }
        });


    }
}