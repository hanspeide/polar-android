package com.polarb.android.asynctask;

import android.os.AsyncTask;

import com.polarb.android.PolarApplication;
import com.polarb.android.ws.ApiClient;

import javax.inject.Inject;
import java.net.URL;

public class SubmitVoteAsyncTask extends AsyncTask<URL, Integer, Void> {

    @Inject private ApiClient apiClient;

    private final int choiceId;
    private final int pollId;

    public SubmitVoteAsyncTask(PolarApplication application, int pollId, int choiceId){
        application.inject(this);
        this.pollId = pollId;
        this.choiceId = choiceId;
    }

    @Override
    protected Void doInBackground(URL... params) {
        apiClient.submitVote(pollId, choiceId);

        return null;
    }
}