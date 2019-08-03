package com.example.cabway.ui.activities;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import com.example.cabway.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AboutUs extends BaseActivity {

    @BindView(R.id.aboutUsText)
    TextView tvAboutUs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        setUpActionBar();
        ButterKnife.bind(this);
        tvAboutUs.setText(getTextFromAssets());
        tvAboutUs.setMovementMethod(new ScrollingMovementMethod());
    }

    private String getTextFromAssets() {
        StringBuilder returnString = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(getAssets().open("aboutUs.txt")))) {

            String mLine;
            while ((mLine = reader.readLine()) != null) {
                returnString.append(mLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return returnString.toString();
    }
}
