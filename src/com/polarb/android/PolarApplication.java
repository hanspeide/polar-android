package com.polarb.android;

import android.app.Application;
import android.content.Context;
import com.polarb.android.asynctask.FetchPollsAsyncTask;
import com.polarb.android.asynctask.SubmitVoteAsyncTask;
import com.polarb.android.parser.PollParser;
import com.polarb.android.ui.create.CreateActivity;
import com.polarb.android.ui.fragments.CreateFragment;

import com.polarb.android.ui.fragments.PollListFragment;
import com.polarb.android.ui.home.HomeActivity;
import com.polarb.android.ws.ApiClient;
import com.polarb.android.ws.ApiClientImpl;
import com.squareup.otto.Bus;
import dagger.Module;
import dagger.ObjectGraph;
import dagger.Provides;

import javax.inject.Singleton;

public class PolarApplication extends Application {
    private ObjectGraph objectGraph;

    @Override
    public void onCreate() {
        super.onCreate();

        objectGraph = ObjectGraph.create(new PolarModule(this));
    }

    public void inject(Object object) {
         objectGraph.inject(object);
    }

    @Module(
            entryPoints = {
                    /* Activities */
                    HomeActivity.class,
                    CreateActivity.class,

                    /* Fragments */
                    PollListFragment.class,
                    CreateFragment.class,

                    /* AsyncTask */
                    FetchPollsAsyncTask.class,
                    SubmitVoteAsyncTask.class
            }
    )
    static class PolarModule {
        private final Context appContext;

        PolarModule(Context appContext) {
            this.appContext = appContext;
        }

        @Provides @Singleton
        PollParser providePollParser() {
            return new PollParser();
        }


        @Provides
        @Singleton
        ApiClient provideApiClient(PollParser pollParser) {
            return new ApiClientImpl(pollParser);
        }

        @Provides @Singleton Bus provideBus() {
            return new Bus();
        }
    }
}
