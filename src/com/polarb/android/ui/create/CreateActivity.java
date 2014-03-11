package com.polarb.android.ui.create;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.polarb.android.R;
import com.polarb.android.ui.BaseActivity;
import com.polarb.android.ws.ApiClient;

import javax.inject.Inject;

public class CreateActivity extends BaseActivity {
    @Inject
    private ApiClient apiClient;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_create);
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
}
