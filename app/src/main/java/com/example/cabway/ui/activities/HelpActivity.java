package com.example.cabway.ui.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.widget.TextView;

import com.example.cabway.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HelpActivity extends BaseActivity {

    @BindView(R.id.tv_help_number)
    TextView helpNumberText;
    @BindView(R.id.tv_write_us)
    TextView writeUsEmailText;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        setUpActionBar();
        ButterKnife.bind(this);
    }

    @OnClick(R.id.bt_call_us)
    void callUs() {
        String phone_no = helpNumberText.getText().toString().replaceAll("-", "");
        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse("tel:" + phone_no));
        startActivity(callIntent);
    }

    @OnClick(R.id.bt_write_us)
    void writeUs() {
        String to = writeUsEmailText.getText().toString();
        Intent email = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + to));
        startActivity(Intent.createChooser(email, "Choose an Email client :"));
    }
}
