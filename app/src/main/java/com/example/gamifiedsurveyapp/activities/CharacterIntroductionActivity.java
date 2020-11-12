package com.example.gamifiedsurveyapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.gamifiedsurveyapp.R;
import com.example.gamifiedsurveyapp.models.Survey;

import java.util.HashMap;

public class CharacterIntroductionActivity extends AppCompatActivity {

    Survey mSurvey;
    HashMap<Integer, Boolean> mMap;
    //private ImageView avatarBackgroundImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_introduction);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        mSurvey = (Survey) bundle.getSerializable("SURVEY_DATA");
        System.out.println("PRINTING FROM CHARACTER INTO ACTIVITY CLASS");
        mSurvey.printAll();

        String avatarSelected = mSurvey.getAvatar();
        System.out.println(avatarSelected);


        ImageView avatarBackgroundImage = (ImageView) findViewById(R.id.character_background_image);

        if  (avatarSelected.equals("Boy")) {
            avatarBackgroundImage.setImageResource(R.drawable.boy_bkrd);
        } else if (avatarSelected.equals("Robot")){
            avatarBackgroundImage.setImageResource(R.drawable.robot_bkrd);
        } else if (avatarSelected.equals("Monkey")){
            avatarBackgroundImage.setImageResource(R.drawable.monkey_bkrd);
        }

    }

    public void acceptAndContinue(View v) {
        System.out.println("USER HAS BEEN BRIEFED GOING TO NEXT ACTIVITY");
        Bundle extras = new Bundle();
        extras.putSerializable("SURVEY_DATA", mSurvey);

        mMap = new HashMap<Integer, Boolean>();
        mMap.put(1, false);
        mMap.put(2, false);
        mMap.put(3, false);
        mMap.put(4, false);

        extras.putSerializable("PROGRESS", mMap);
        Intent intent = new Intent(CharacterIntroductionActivity.this, LevelSelectActivity.class);

        intent.putExtras(extras);
        startActivity(intent);


    }
}
