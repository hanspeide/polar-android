package com.polarb.android.asynctask;

import android.os.AsyncTask;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.polarb.android.PolarApplication;
import com.polarb.android.domain.Poll;
import com.polarb.android.event.FetchPollsSuccess;
import com.polarb.android.ui.fragments.PollListFragment;
import com.polarb.android.ws.ApiClient;
import com.squareup.otto.Bus;

import javax.inject.Inject;
import java.net.URL;
import java.util.List;

public class FetchPollsAsyncTask extends AsyncTask<URL, Integer, List<Poll>> {

    @Inject private Bus bus;
    @Inject private ApiClient apiClient;
    private PollListFragment pollListFragment;
    private PullToRefreshListView pullToRefreshView;

    public FetchPollsAsyncTask(PolarApplication application, PollListFragment pollListFragment){
        application.inject(this);
        this.pollListFragment = pollListFragment;
    }

    public FetchPollsAsyncTask(PolarApplication application, PollListFragment pollListFragment, PullToRefreshListView pullToRefreshView){
        application.inject(this);
        this.pollListFragment = pollListFragment;
        this.pullToRefreshView = pullToRefreshView;
    }

    @Override
    protected List<Poll> doInBackground(URL... params) {
        return apiClient.fetchPolls();
    }

    @Override
    protected void onPostExecute(List<Poll> polls){
        super.onPostExecute(polls);
        bus.post(new FetchPollsSuccess(polls));
    }
}