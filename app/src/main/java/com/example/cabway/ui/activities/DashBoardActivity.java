package com.example.cabway.ui.activities;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.cabway.R;
import com.example.cabway.ui.Interfaces.RecyclerViewItemClickListener;
import com.example.cabway.ui.adapter.AvailableRidesListAdapter;
import com.example.cabway.ui.adapter.ProfileMenuAdapter;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DashBoardActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.nav_view)
    NavigationView navigationView;

    @BindView(R.id.profile_menu)
    RecyclerView mProfileMenu;

    @BindView(R.id.availableRidesList)
    RecyclerView availableRidesList;

    ProfileMenuAdapter profileMenuAdapter;
    AvailableRidesListAdapter availableRidesListAdapter;
    private List<String> menu_items;
    private TypedArray menu_items_icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        inItData();
        setNavigationDrawerLayout();
        setRidesList();
    }


    private void setRidesList(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        availableRidesList.setLayoutManager(layoutManager);
        availableRidesListAdapter = new AvailableRidesListAdapter(this, ridesViewItemClickListener);
        availableRidesList.setAdapter(availableRidesListAdapter);
    }

    private void setNavigationDrawerLayout() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mProfileMenu.setLayoutManager(layoutManager);
        profileMenuAdapter = new ProfileMenuAdapter(this, menu_items, menu_items_icon, recyclerViewItemClickListener);
        mProfileMenu.setAdapter(profileMenuAdapter);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void inItData() {
        //Data
        menu_items = Arrays.asList(getResources().getStringArray(R.array.profile_menu));
        menu_items_icon = getResources().obtainTypedArray(R.array.profile_menu_icons);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return true;
    }

    RecyclerViewItemClickListener recyclerViewItemClickListener = new RecyclerViewItemClickListener() {
        @Override
        public void onItemClick(View v, int position) {
            String menuItem = menu_items.get(position);
            startMenuActivities(menuItem);
        }
    };

    RecyclerViewItemClickListener ridesViewItemClickListener = (v, position) -> {
    };

    void startMenuActivities(String menuItem){
        Toast.makeText(this, menuItem,Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, DocumentListActivity.class));
        drawer.closeDrawer(GravityCompat.START);
    }

}
