package com.example.cabway.ui.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.cabway.R;
import com.example.cabway.Utils.IntentConstants;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UpgradeActivity extends BaseActivity {

    @BindView(R.id.skip)
    Button skip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upgrade);
        ButterKnife.bind(this);
        if (getIntent().getBooleanExtra(IntentConstants.IS_FORCE_UPGRADE, false))
            skip.setVisibility(View.GONE);
        else
            skip.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.update)
    public void onUpdateClick() {
        final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }

    @OnClick(R.id.skip)
    public void startNextActivity() {
        Intent nextActivity;
        if (appPreferences.isLogin()) {
            if (appPreferences.getUserDetails().documentCompleted)
                nextActivity = new Intent(this, DashBoardActivity.class);
            else {
                nextActivity = new Intent(this, DocumentListActivity.class);
                nextActivity.putExtra(IntentConstants.IS_FROM_LOGIN, true);
            }
        } else
            nextActivity = new Intent(this, LoginActivity.class);
        startActivity(nextActivity);
        finish();
    }
}
