package com.example.cabway.ui.activities;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProviders;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.example.cabway.R;
import com.example.cabway.viewModels.MainActivityViewModel;
import com.example.cabway.workers.UserWorker;
import com.example.database.entities.Hero;
import com.facebook.stetho.Stetho;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    MainActivityViewModel mainActivityViewModel;
    @BindView(R.id.text_insert)
    EditText text;
    @BindView(R.id.display_textView)
    TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Stetho.initializeWithDefaults(this);
        mainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
    }

    private void setData(List<Hero> heroes) {
        for (Hero hero : heroes) {
            textView.append("\n" + hero.getData());
        }
    }


    @OnClick(R.id.hitButton)
    public void selectData() {
        mainActivityViewModel.getHeroes().observe(this, this::setData);
    }

    @OnClick(R.id.insert)
    public void insertData() {
        OneTimeWorkRequest insertWork = new OneTimeWorkRequest.Builder(UserWorker.class)
                .build();
        WorkManager.getInstance(this).enqueue(insertWork);
    }
}
