package com.polarb.android.ui.fragments;

import android.app.ListFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.polarb.android.PolarApplication;
import com.polarb.android.R;
import com.polarb.android.adapter.PollListAdapter;
import com.polarb.android.asynctask.FetchPollsAsyncTask;
import com.polarb.android.domain.Poll;
import com.polarb.android.event.FetchPollsFailure;
import com.polarb.android.event.FetchPollsSuccess;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import javax.inject.Inject;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import static com.polarb.android.Config.BASE_API_URL;

public class PollListFragment extends ListFragment {
    @Inject private Bus bus;

    View fragmentPollList;
    private List<Poll> polls;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        ((PolarApplication) getActivity().getApplication()).inject(this);
    }

    @Override
    public void onResume(){
        super.onResume();
        bus.register(this);
    }

    @Override
    public void onPause(){
        super.onPause();
        bus.unregister(this);
        PullToRefreshListView listView = (PullToRefreshListView)fragmentPollList.findViewById(R.id.poll_list);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        fragmentPollList = inflater.inflate(R.layout.fragment_poll_list, container, false);
        if (polls == null || polls.isEmpty()){
            try {
                new FetchPollsAsyncTask((PolarApplication)getActivity().getApplication(), this).execute(new URL(BASE_API_URL));
            } catch (MalformedURLException e) {
                Log.d("Polar", "Something went awry when trying to fetch polls");
            }

            final PullToRefreshListView pullToRefreshView = (PullToRefreshListView) fragmentPollList.findViewById(R.id.poll_list);
            pullToRefreshView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
                @Override
                public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                    try {
                        new FetchPollsAsyncTask((PolarApplication)getActivity().getApplication(), PollListFragment.this, pullToRefreshView).execute(new URL(BASE_API_URL));
                    } catch (MalformedURLException e) {
                        Log.d("Polar", "Something went awry when trying to fetch polls using pull to refresh");
                    }
                }
            });
        } else {

        }
        return fragmentPollList;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
    }

    @Subscribe
    public void fetchPollsSuccess(FetchPollsSuccess fetchPollsSuccess){
        this.polls = fetchPollsSuccess.getPolls();
        PollListAdapter adapter = new PollListAdapter(getActivity(), polls);
        setListAdapter(adapter);

//        if (pullToRefreshView != null ){
//            pullToRefreshView.onRefreshComplete();
//        }
    }

    @Subscribe
    public void fetchPollsError(FetchPollsFailure fetchPollsFailure){
        Log.d("Polar", "Unable to fetch polls");
    }
}
