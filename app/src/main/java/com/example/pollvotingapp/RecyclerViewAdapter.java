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

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private Context context;
    ArrayList<Poll> polls;
    String email; // #
    private FirebaseDatabase database = FirebaseDatabase.getInstance("https://android-poll-database-default-rtdb.europe-west1.firebasedatabase.app");
    private DatabaseReference reference = database.getReference();

    public RecyclerViewAdapter(Context context, ArrayList<Poll> polls, String email){
        this.context = context;
        this.polls = polls;
        this.email = email; // #
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView pollQuestion;
        RadioGroup radioGroup;
        //private RecyclerViewAdapter adapter;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            pollQuestion = itemView.findViewById(R.id.pollQuestion);
            radioGroup = itemView.findViewById(R.id.radioGroup);

        }
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_row, parent, false);

        return new RecyclerViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.MyViewHolder holder, int position) {
        Poll poll = polls.get(position);

        holder.radioGroup.removeAllViews();

        holder.pollQuestion.setText(polls.get(position).getPollQuestion());
        for (int i = 0; i < poll.getOptions().size(); i++) {
            RadioButton rbn = new RadioButton(context);
            rbn.setId(i);
            rbn.setText(poll.getOptions().get(i));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1f);
            rbn.setLayoutParams(params);
            holder.radioGroup.addView(rbn);
        }
        holder.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                //RadioButton rb = (RadioButton) holder.radioGroup.findViewById(R.id.get);

                // TODO => Save user that voted on poll and their vote in database, get location of user
                RadioButton checkedRadioButton = (RadioButton) radioGroup.findViewById(i);
                boolean isChecked = checkedRadioButton.isChecked();
                if (isChecked){
                    //Toast.makeText(context, "Option " + checkedRadioButton.getText() + " selected", Toast.LENGTH_SHORT).show();
                    //reference.child("Votes").child("User").child(poll)
                    //Toast.makeText(context, "Poll Question is " + poll.getPollQuestion(), Toast.LENGTH_SHORT).show();
                    Vote vote = new Vote(email, poll.getPollQuestion(), checkedRadioButton.getText().toString());//child(poll.getPollQuestion()).setValue(checkedRadioButton.getText());
                    reference.child("Votes").child(email + "," + poll.getPollQuestion()).setValue(vote);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return polls.size();
    }

    public void removeItem(int position){

    }

}
