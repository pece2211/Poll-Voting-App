package com.example.pollvotingapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdminAdapter extends RecyclerView.Adapter<AdminAdapter.MyViewHolder>{

    private Context context;
    private ArrayList<Vote> votes;

    public AdminAdapter(Context context, ArrayList<Vote> votes){
        this.context = context;
        this.votes = votes;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.votes_for_admin_row, parent, false);

        return new AdminAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Vote vote = votes.get(position);
        String pollquest = "Poll Question: " + vote.getQuestion();
        String voterstr = "User: " + vote.getVoter();
        String opt = "Option Chosen: " + vote.getOption();

        holder.pollQuestion.setText(pollquest);
        holder.voter.setText(voterstr);
        holder.optionSelected.setText(opt);

    }

    @Override
    public int getItemCount() {
        return votes.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView pollQuestion;
        TextView voter;
        TextView optionSelected;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            pollQuestion = itemView.findViewById(R.id.votedPollTv);
            voter = itemView.findViewById(R.id.voterTv);
            optionSelected = itemView.findViewById(R.id.chosenOptionTv);

        }
    }


}
