package com.example.gamifiedsurveyapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.gamifiedsurveyapp.R;
import com.example.gamifiedsurveyapp.models.Survey;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ConsentActivity extends AppCompatActivity implements Serializable {

    Survey mSurvey;
    HashMap<Integer, Boolean> mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consent);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        mSurvey = (Survey) bundle.getSerializable("SURVEY_DATA");
    }

    public void acceptAndContinue(View v) {
        Bundle extras = new Bundle();
        extras.putSerializable("SURVEY_DATA", mSurvey);
        Intent intent = new Intent(ConsentActivity.this, AvatarSelectActivity.class);
        intent.putExtras(extras);
        startActivity(intent);
    }
}
