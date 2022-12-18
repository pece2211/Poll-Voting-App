package com.example.pollvotingapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MyVotesAdapter extends RecyclerView.Adapter<MyVotesAdapter.MyViewHolder>{

    private Context context;
    private ArrayList<Vote> votes;
    private String email;
    private FirebaseDatabase database = FirebaseDatabase.getInstance("https://android-poll-database-default-rtdb.europe-west1.firebasedatabase.app");
    private DatabaseReference mDatabase = database.getReference();


    public MyVotesAdapter(Context context, ArrayList<Vote> votes, String email){
        this.context = context;
        this.votes = votes;
        this.email = email;
    }


    @NonNull
    @Override
    public MyVotesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_votes_row, parent, false);

        return new MyVotesAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Vote vote = votes.get(position);

        String pollq = "Poll Question: " + vote.getQuestion();
        String pollopt = "Chosen Option: " + vote.getOption();
        holder.pollQ.setText(pollq);
        holder.chosenOption.setText(pollopt);

    }

    @Override
    public int getItemCount() {
        return votes.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView pollQ;
        TextView chosenOption;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            pollQ = itemView.findViewById(R.id.pollQ);
            chosenOption = itemView.findViewById(R.id.chosenOption);
        }
    }


}
