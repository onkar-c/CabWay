package com.example.cabway.ui.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.database.entities.Hero;
import com.example.cabway.R;
import com.example.cabway.viewModels.MainActivityViewModel;
import com.facebook.stetho.Stetho;

import java.util.Objects;

public class MainActivity extends BaseActivity{

    MainActivityViewModel mainActivityViewModel;
    TextView textView;
    Button hit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Stetho.initializeWithDefaults(this);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);
        hit = findViewById(R.id.hitButton);

        hit.setOnClickListener(view -> {
            showProgressDialog(this, "Please Wait", false);
            mainActivityViewModel.makeCall(MainActivity.this);
        });
        mainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        mainActivityViewModel.init();

        mainActivityViewModel.getResponse().observe(this, s -> {
            textView.setText("");
            removeProgressDialog();
            for (Hero hero : Objects.requireNonNull(s)){
                textView.append("\n" +hero.getData());
            }
        });

        mainActivityViewModel.getResponseRecived().observe(this, aBoolean -> {
            if (aBoolean != null && aBoolean)
                mainActivityViewModel.getHeroes(MainActivity.this);
        });
    }
}
