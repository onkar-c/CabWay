package com.example.cabway.ui.activities;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cabway.R;
import com.example.cabway.Utils.DialogUtils;
import com.example.cabway.Utils.ImageUtils;
import com.example.cabway.ui.Interfaces.RecyclerViewItemClickListener;
import com.example.cabway.ui.adapter.ProfileMenuAdapter;
import com.example.core.CommonModels.UserModel;
import com.example.database.Utills.AppConstants;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

    @BindView(R.id.tv_user_name)
    TextView tvUserName;

    @BindView(R.id.tv_role)
    TextView tvRole;

    @BindView(R.id.profile_image)
    ImageView ivProfile;

    @BindView(R.id.main_action)
    ImageButton mainAction;
    @BindView(R.id.top_description)
    TextView top_description;

    ProfileMenuAdapter profileMenuAdapter;
    private List<String> menu_items;

    RecyclerViewItemClickListener recyclerViewItemClickListener = new RecyclerViewItemClickListener() {
        @Override
        public void onItemClick(View v, int position) {
            String menuItem = menu_items.get(position);
            startMenuActivities(menuItem);
        }
    };
    private TypedArray menu_items_icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        ButterKnife.bind(this);
        inItData();
        setSupportActionBar(toolbar);
        setNavigationDrawerLayout();
        setUpFragments();

    }

    private void setUpFragments() {
        BottomNavigationView navView = findViewById(R.id.bottom_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.available_rides, R.id.requested_rides, R.id.ongoing_rides)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }

    private void setData() {
        UserModel user = appPreferences.getUserDetails();
        tvUserName.setText(String.format("%s %s", user.firstName, user.lastName));
        tvRole.setText(user.role);
        String textToDisplay;
        if(user.role.equals(AppConstants.AGENCY))
            textToDisplay = getString(R.string.create_new_ride);
        else
            textToDisplay = getString(R.string.preferred_city) + " : " + appPreferences.getUserDetails().cityPreferences.getName();

        top_description.setText(textToDisplay);
        ImageUtils.setImageFromUrl(this, user.profileImage, ivProfile);
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
    protected void onResume() {
        super.onResume();
        setData();
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

    void startMenuActivities(String menuItem) {
        drawer.closeDrawer(GravityCompat.START);
        Intent nextActivity = null;
        if (menuItem.equals(getString(R.string.help)))
            nextActivity = new Intent(this, HelpActivity.class);
        else if (menuItem.equals(getString(R.string.documents)))
            nextActivity = new Intent(this, DocumentListActivity.class);
        else if (menuItem.equals(getString(R.string.history)))
            nextActivity = new Intent(this, HistoryListActivity.class);
        else if (menuItem.equals(getString(R.string.about)))
            nextActivity = new Intent(this, AboutUs.class);
        else if (menuItem.equals(getString(R.string.profile)))
            nextActivity = new Intent(this, ProfileActivity.class);
        else if (menuItem.equals(getString(R.string.logout))) {
            DialogUtils.showMessageDialog(this, getString(R.string.logout_message), onLogoutListener);
        }
        if (nextActivity != null) {
            startActivity(nextActivity);
        }
    }


    @OnClick(R.id.top_view)
    public void performAction() {
        Intent nextActivity;
        if (appPreferences.getUserDetails().role.equals(AppConstants.AGENCY)) {
            nextActivity = new Intent(this, CreateRideActivity.class);
        } else {
            nextActivity = new Intent(this, PreferredCityActivity.class);
        }
        startActivity(nextActivity);
    }


    public List<String> getMenu_items() {
        return menu_items;
    }
}
