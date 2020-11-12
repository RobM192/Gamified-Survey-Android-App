package com.example.gamifiedsurveyapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.gamifiedsurveyapp.R;
import com.example.gamifiedsurveyapp.models.Survey;

import java.util.HashMap;

public class AvatarSelectActivity extends AppCompatActivity {

    private Survey mSurvey;
    private String chosenAvatar = "blank";

    ImageButton buttonBoy, buttonGirl, buttonRobot, buttonMonkey;
    private HashMap<Integer, Boolean> mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avatar);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        mSurvey = (Survey) bundle.getSerializable("SURVEY_DATA");

        System.out.println("PRINTING FROM CONSENT AVATAR SELECTION CLASS");
        mSurvey.printAll();

        final ImageButton buttonBoy = (ImageButton) findViewById(R.id.boy_button);
        final ImageButton buttonGirl = (ImageButton) findViewById(R.id.girl_button);
        final ImageButton buttonRobot = (ImageButton) findViewById(R.id.robot_button);
        final ImageButton buttonMonkey = (ImageButton) findViewById(R.id.monkey_button);

        buttonBoy.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // if this avatar is already chosen, deselect the button and change avatar to blank
                if (chosenAvatar.equals("Boy")) {
                    buttonBoy.setBackgroundResource(R.drawable.button);
                    chosenAvatar = "blank";
                    return;
                }

                // if others are currently selected, deselect them
                buttonBoy.setBackgroundResource(R.drawable.button);
                buttonGirl.setBackgroundResource(R.drawable.button);
                buttonRobot.setBackgroundResource(R.drawable.button);
                buttonMonkey.setBackgroundResource(R.drawable.button);

                // set the character to the string and change color of selected avatar
                chosenAvatar = "Boy";
                buttonBoy.setBackgroundResource(R.drawable.button_selector);

            }
        });

        buttonGirl.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // if this avatar is already chosen, deselect the button and change avatar to blank
                if (chosenAvatar.equals("Girl")) {
                    buttonGirl.setBackgroundResource(R.drawable.button);
                    chosenAvatar = "blank";
                    return;
                }

                // if others are currently selected, deselect them
                buttonBoy.setBackgroundResource(R.drawable.button);
                buttonGirl.setBackgroundResource(R.drawable.button);
                buttonRobot.setBackgroundResource(R.drawable.button);
                buttonMonkey.setBackgroundResource(R.drawable.button);

                // set the character to the string and change color of selected avatar
                chosenAvatar = "Girl";
                buttonGirl.setBackgroundResource(R.drawable.button_selector);

            }
        });

        buttonRobot.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // if this avatar is already chosen, deselect the button and change avatar to blank
                if (chosenAvatar.equals("Robot")) {
                    buttonRobot.setBackgroundResource(R.drawable.button);
                    chosenAvatar = "blank";
                    return;
                }

                // if others are currently selected, deselect them
                buttonBoy.setBackgroundResource(R.drawable.button);
                buttonGirl.setBackgroundResource(R.drawable.button);
                buttonRobot.setBackgroundResource(R.drawable.button);
                buttonMonkey.setBackgroundResource(R.drawable.button);

                // set the character to the string and change color of selected avatar
                chosenAvatar = "Robot";
                buttonRobot.setBackgroundResource(R.drawable.button_selector);

            }
        });

        buttonMonkey.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // if this avatar is already chosen, deselect the button and change avatar to blank
                if (chosenAvatar.equals("Monkey")) {
                    buttonMonkey.setBackgroundResource(R.drawable.button);
                    chosenAvatar = "blank";
                    return;
                }

                // if others are currently selected, deselect them
                buttonBoy.setBackgroundResource(R.drawable.button);
                buttonGirl.setBackgroundResource(R.drawable.button);
                buttonRobot.setBackgroundResource(R.drawable.button);
                buttonMonkey.setBackgroundResource(R.drawable.button);

                // set the character to the string and change color of selected avatar
                chosenAvatar = "Monkey";
                buttonMonkey.setBackgroundResource(R.drawable.button_selector);

            }
        });




    }

    public void acceptAvatarAndContinue(View v) {

        if (chosenAvatar.equals("blank")) {
            Toast.makeText(AvatarSelectActivity.this,
                    "Choose your player!", Toast.LENGTH_LONG).show();
            return;
        }

        mSurvey.setAvatar(chosenAvatar);

        System.out.println("AVATAR ACCEPTED GOING TO NEXT ACTIVITY");
        Intent intent = new Intent(AvatarSelectActivity.this, CharacterIntroductionActivity.class);

        Bundle bundle = new Bundle();
        bundle.putSerializable("SURVEY_DATA", mSurvey);
        //bundle.putSerializable("PROGRESS", mMap);
        intent.putExtras(bundle);
        startActivity(intent);
    }





}
