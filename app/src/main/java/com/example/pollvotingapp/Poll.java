package com.example.pollvotingapp;

import android.os.CountDownTimer;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Optional;

public class Poll implements Serializable {


    private String pollQuestion;
    ArrayList<String> options = new ArrayList<>();
    private boolean isActive;
    // TODO Have a timer which closes poll when it runs out
    //        USE THIS FOR TIMERS OF POLLS, TO BE IMPLEMENTED IN ADMIN ACTIVITY
//        new CountDownTimer(30000, 1000) {
//
//            public void onTick(long millisUntilFinished) {
//                testZaTimer.setValue("seconds remaining: " + millisUntilFinished / 1000);
//            }
//
//            public void onFinish() {
//                testZaTimer.setValue("done!");
//            }
//        }.start();


    public Poll(){

    }


    public Poll(String question, ArrayList<String> ops, boolean active){
        this.pollQuestion = question;
        this.options = ops;
        this.isActive = active;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getPollQuestion() {
        return pollQuestion;
    }

    public void setPollQuestion(String pollQuestion) {
        this.pollQuestion = pollQuestion;
    }

    public ArrayList<String> getOptions() {
        return options;
    }

    public void setOptions(ArrayList<String> options) {
        this.options = options;
    }

    @Override
    public String toString() {
        return "Poll{" +
                "pollQuestion='" + pollQuestion + '\'' +
                ", options=" + options +
                '}';
    }

}
