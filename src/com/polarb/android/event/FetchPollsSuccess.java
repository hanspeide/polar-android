package com.polarb.android.event;

import com.polarb.android.domain.Poll;

import java.util.List;

public class FetchPollsSuccess {

    private List<Poll> polls;

    public FetchPollsSuccess(List<Poll> polls){
        this.polls = polls;
    }

    public List<Poll> getPolls(){
        return polls;
    }
}
