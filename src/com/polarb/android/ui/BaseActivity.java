package com.polarb.android.ui;

import android.app.Activity;
import android.os.Bundle;
import com.polarb.android.PolarApplication;
import com.squareup.otto.Bus;

import javax.inject.Inject;

public abstract class BaseActivity extends Activity {

    @Inject
    protected Bus bus;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((PolarApplication) getApplication()).inject(this);

        bus.register(this);
    }
}