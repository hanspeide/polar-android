package com.polarb.android.ui.home;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.polarb.android.R;
import com.polarb.android.ui.BaseActivity;
import com.polarb.android.ui.create.CreateActivity;
import com.polarb.android.ui.fragments.PollListFragment;
import com.polarb.android.ws.ApiClient;

import javax.inject.Inject;

public class HomeActivity extends BaseActivity implements ActionBar.TabListener {
    @Inject
    private ApiClient apiClient;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_home);
        setupActionBar();

        if (savedInstanceState == null){
            Fragment pollListFragment = new PollListFragment();
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.add(R.id.home_layout, pollListFragment).commit();
        }
    }

    @Override
    public void onResume(){
        super.onResume();
    }

    private void setupActionBar() {
        ActionBar actionbar = getActionBar();

        // TODO Uncomment if tabs are to be shown in app
        // actionbar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        // setupActionBarTabs(actionbar);
    }

    private void setupActionBarTabs(ActionBar actionbar) {
        ActionBar.Tab popularTab = actionbar.newTab().setText("Popular");
        ActionBar.Tab newTab = actionbar.newTab().setText("New");
        ActionBar.Tab followingTab = actionbar.newTab().setText("Following");

        popularTab.setTabListener(this);
        newTab.setTabListener(this);
        followingTab.setTabListener(this);

        actionbar.addTab(popularTab);
        actionbar.addTab(newTab);
        actionbar.addTab(followingTab);
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        if (item.getItemId() == R.id.menu_create){
            Intent startActivityIntent = new Intent(this, CreateActivity.class);
            startActivity(startActivityIntent);
        }

        if (item.getItemId() == R.id.menu_results){
            /* No-op */
        }

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        /* No-op */
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
        /* No-op */
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
        /* No-op */
    }
}
