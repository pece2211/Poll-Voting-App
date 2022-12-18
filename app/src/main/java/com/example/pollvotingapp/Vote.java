package com.example.pollvotingapp;

import java.io.Serializable;

public class Vote implements Serializable {
    //TODO Implement
    private String voter;
    private String question;
    private String option;

    public Vote(){

    }

    @Override
    public String toString() {
        return "Vote{" +
                "voter='" + voter + '\'' +
                ", question='" + question + '\'' +
                ", option='" + option + '\'' +
                '}';
    }

    public String getVoter() {
        return voter;
    }

    public void setVoter(String voter) {
        this.voter = voter;
    }

    public Vote(String voter, String question, String option){
        this.voter = voter;
        this.question = question;
        this.option = option;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }
}
