package com.polarb.android.ws;

import com.polarb.android.domain.Poll;

import java.util.List;

public interface ApiClient {
    List<Poll> fetchPolls();
    void submitVote(int pollId, int choiceId);
}
