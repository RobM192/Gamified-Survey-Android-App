package com.example.gamifiedsurveyapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.gamifiedsurveyapp.R;
import com.example.gamifiedsurveyapp.models.Survey;

import java.util.HashMap;

public class LevelSelectActivity extends AppCompatActivity implements View.OnClickListener{

    private Survey mSurvey;

    private int nextQuestionNum;
    private int nextQuestionType;
    private int totalQuestions;

    private int questionsPerPlanet;
    private int questionsPerLastPlanet;

    private int planetsCompleted = 0;

    private LinearLayout planet1Button, planet2Button, planet3Button, planet4Button;
    private ImageView planet1Medal, planet2Medal, planet3Medal, planet4Medal;
    private TextView planet1Text, planet2Text,  planet3Text, planet4Text;
    HashMap<Integer, Boolean> mMap;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_select);

        // get the survey object data and hashmap of progress of which levels are done
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        mSurvey = (Survey) extras.getSerializable("SURVEY_DATA");
        mMap = (HashMap<Integer, Boolean>) extras.getSerializable("PROGRESS");

        // find the next survey type and divide the total number of questions into 4
        totalQuestions = mSurvey.numOfQuestions();
        nextQuestionNum = mSurvey.getNumCompletedQuestions();
        System.out.println("NEXT QUESTION NUM IS " + nextQuestionNum);

        if (totalQuestions != mSurvey.getNumCompletedQuestions()) {
            nextQuestionType = mSurvey.getNextQuestionType();
            System.out.println("PRINTING NEXT QUESTION TYPE " + nextQuestionType);
            splitQuestionsIntoFourPlanets();
        }

        //set the graphics  for which levels complete and medals
        setPlanetsGraphics();

        //create onClick listeners for each layout/planet button
        if (mMap.get(1) == false) {
            final LinearLayout mPlanet1 = (LinearLayout) findViewById(R.id.planet1LinLayout);
            mPlanet1.setOnClickListener(this);
        }

        if (mMap.get(2) == false) {
            final LinearLayout mPlanet2 = (LinearLayout) findViewById(R.id.planet2LinLayout);
            mPlanet2.setOnClickListener(this);
        }

        if (mMap.get(3) == false) {
            final LinearLayout mPlanet3 = (LinearLayout) findViewById(R.id.planet3LinLayout);
            mPlanet3.setOnClickListener(this);
        }

        if (mMap.get(4) == false) {
            final LinearLayout mPlanet4 = (LinearLayout) findViewById(R.id.planet4LinLayout);
            mPlanet4.setOnClickListener(this);
        }

        isSurveyComplete();


    }

    public void isSurveyComplete(){

        if(mMap.get(1) == true && mMap.get(2) == true && mMap.get(3)  ==  true && mMap.get(4) == true){
            System.out.println("SURVEY HAS BEEN COMPLETED BY USER");

            Intent intent = new Intent(LevelSelectActivity.this, FinalActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("SURVEY_DATA", mSurvey);
            intent.putExtras(bundle);
            startActivity(intent);

        }
    }

    @Override
    public void onClick(View v) {

        Intent intent;
        Bundle bundle = new Bundle();

        switch (v.getId()) {

            case R.id.planet1LinLayout:
                // check the next question type and prepare intent for that activity
                System.out.println("PRINTING NEXT QUESTION TYPE IN SURVEY HASHMAP: " + mSurvey.getNextQuestionType());

                if (mSurvey.getNextQuestionType() == 1) {
                    intent = new Intent(LevelSelectActivity.this, StarRatingActivity.class);
                } else if (mSurvey.getNextQuestionType() == 2) {
                    intent = new Intent(LevelSelectActivity.this, LikertAgreeActivity.class);
                } else if (mSurvey.getNextQuestionType() == 3) {
                    intent = new Intent(LevelSelectActivity.this, LikertHappyActivity.class);
                } else if (mSurvey.getNextQuestionType() == 4) {
                    intent = new Intent(LevelSelectActivity.this, TextInputActivity.class);
                } else {
                    intent = new Intent(LevelSelectActivity.this, MultipleChoiceActivity.class);
                }

                bundle.putSerializable("SURVEY_DATA", mSurvey);
                // if this is the last level there might be more questions to answer in the next activity
                if (planetsCompleted < 3) {
                    bundle.putInt("NUM_QUESTIONS_TO_ANSWER", questionsPerPlanet);
                } else {
                    bundle.putInt("NUM_QUESTIONS_TO_ANSWER", questionsPerLastPlanet);
                }

                bundle.putInt("NUM_QUESTIONS_ANSWERED", 0);

                mMap.put(1, true);
                bundle.putSerializable("PROGRESS", mMap);

                intent.putExtras(bundle);
                startActivity(intent);
                break;

            case R.id.planet2LinLayout:
                // do your code
                System.out.println("BUTTON 2 PRESSED");
                // check the next question type and prepare intent for that activity
                if (mSurvey.getNextQuestionType() == 1) {
                    intent = new Intent(LevelSelectActivity.this, StarRatingActivity.class);
                } else if (mSurvey.getNextQuestionType() == 2) {
                    intent = new Intent(LevelSelectActivity.this, LikertAgreeActivity.class);
                } else if (mSurvey.getNextQuestionType() == 3) {
                    intent = new Intent(LevelSelectActivity.this, LikertHappyActivity.class);
                } else if (mSurvey.getNextQuestionType() == 4) {
                    intent = new Intent(LevelSelectActivity.this, TextInputActivity.class);
                } else {
                    intent = new Intent(LevelSelectActivity.this, MultipleChoiceActivity.class);
                }

                bundle.putSerializable("SURVEY_DATA", mSurvey);
                // if this is the last level there might be more questions to answer in the next activity
                if (planetsCompleted < 3) {
                    bundle.putInt("NUM_QUESTIONS_TO_ANSWER", questionsPerPlanet);
                } else {
                    bundle.putInt("NUM_QUESTIONS_TO_ANSWER", questionsPerLastPlanet);
                }

                bundle.putInt("NUM_QUESTIONS_ANSWERED", 0);

                mMap.put(2, true);
                bundle.putSerializable("PROGRESS", mMap);

                intent.putExtras(bundle);
                startActivity(intent);
                break;

            case R.id.planet3LinLayout:
                // do your code
                System.out.println("BUTTON 3 PRESSED");
                if (mSurvey.getNextQuestionType() == 1) {
                    intent = new Intent(LevelSelectActivity.this, StarRatingActivity.class);
                } else if (mSurvey.getNextQuestionType() == 2) {
                    intent = new Intent(LevelSelectActivity.this, LikertAgreeActivity.class);
                } else if (mSurvey.getNextQuestionType() == 3) {
                    intent = new Intent(LevelSelectActivity.this, LikertHappyActivity.class);
                } else if (mSurvey.getNextQuestionType() == 4) {
                    intent = new Intent(LevelSelectActivity.this, TextInputActivity.class);
                } else {
                    intent = new Intent(LevelSelectActivity.this, MultipleChoiceActivity.class);
                }

                bundle.putSerializable("SURVEY_DATA", mSurvey);
                // if this is the last level there might be more questions to answer in the next activity
                if (planetsCompleted < 3) {
                    bundle.putInt("NUM_QUESTIONS_TO_ANSWER", questionsPerPlanet);
                } else {
                    bundle.putInt("NUM_QUESTIONS_TO_ANSWER", questionsPerLastPlanet);
                }

                bundle.putInt("NUM_QUESTIONS_ANSWERED", 0);

                mMap.put(3, true);
                bundle.putSerializable("PROGRESS", mMap);

                intent.putExtras(bundle);
                startActivity(intent);
                break;

            case R.id.planet4LinLayout:
                // do your code
                System.out.println("BUTTON 4 PRESSED");
                if (mSurvey.getNextQuestionType() == 1) {
                    intent = new Intent(LevelSelectActivity.this, StarRatingActivity.class);
                } else if (mSurvey.getNextQuestionType() == 2) {
                    intent = new Intent(LevelSelectActivity.this, LikertAgreeActivity.class);
                } else if (mSurvey.getNextQuestionType() == 3) {
                    intent = new Intent(LevelSelectActivity.this, LikertHappyActivity.class);
                } else if (mSurvey.getNextQuestionType() == 4) {
                    intent = new Intent(LevelSelectActivity.this, TextInputActivity.class);
                } else {
                    intent = new Intent(LevelSelectActivity.this, MultipleChoiceActivity.class);
                }

                bundle.putSerializable("SURVEY_DATA", mSurvey);
                // if this is the last level there might be more questions to answer in the next activity
                if (planetsCompleted < 3) {
                    bundle.putInt("NUM_QUESTIONS_TO_ANSWER", questionsPerPlanet);
                } else {
                    bundle.putInt("NUM_QUESTIONS_TO_ANSWER", questionsPerLastPlanet);
                }

                bundle.putInt("NUM_QUESTIONS_ANSWERED", 0);

                mMap.put(4, true);
                bundle.putSerializable("PROGRESS", mMap);

                intent.putExtras(bundle);
                startActivity(intent);
                break;

            default:
                break;
        }
    }

    public  void setPlanetsGraphics(){
        // set how many levels are done so far
        for (int i  = 1;  i <=4;i++) {
            if (mMap.get(i) == true) {
                planetsCompleted +=1;
            }
        }

        planet1Button = findViewById(R.id.planet1LinLayout);
        planet1Medal = findViewById(R.id.planet1medal);
        planet1Text = findViewById(R.id.planet1text);
        if  (mMap.get(1) == true)  {
            planet1Button.setBackgroundResource(R.drawable.gradient_background);
            planet1Medal.setImageResource(R.drawable.medal_awarded_small);
            planet1Text.setText("Complete!");
        }
        planet2Button = findViewById(R.id.planet2LinLayout);
        planet2Medal = findViewById(R.id.planet2medal);
        planet2Text = findViewById(R.id.planet2text);
        if  (mMap.get(2) == true)  {
            planet2Button.setBackgroundResource(R.drawable.gradient_background);
            planet2Medal.setImageResource(R.drawable.medal_awarded_small);
            planet2Text.setText("Complete!");
        }
        planet3Button = findViewById(R.id.planet3LinLayout);
        planet3Medal = findViewById(R.id.planet3medal);
        planet3Text = findViewById(R.id.planet3text);
        if  (mMap.get(3) == true)  {
            planet3Button.setBackgroundResource(R.drawable.gradient_background);
            planet3Medal.setImageResource(R.drawable.medal_awarded_small);
            planet3Text.setText("Complete!");
        }
        planet4Button = findViewById(R.id.planet4LinLayout);
        planet4Medal = findViewById(R.id.planet4medal);
        planet4Text = findViewById(R.id.planet4text);
        if  (mMap.get(4) == true)  {
            planet4Button.setBackgroundResource(R.drawable.gradient_background);
            planet4Medal.setImageResource(R.drawable.medal_awarded_small);
            planet4Text.setText("Complete!");
        }

    }




        // return the type of question as a string representation: 1 STAR_RATING, 2 LIKERT_AGREE, 3 LIKERT_HAPPY or 4 TEXT_BOX
    /**
    public int getNextQuestionType(){
        Question q = mSurvey.getQuestion(nextQuestionNum);
        return q.getQuestionType();

    }
    **/

    public void splitQuestionsIntoFourPlanets() {
        // if the number of questions is divisible by 4 split them between the 4 planets
        // if not divisible by 4, add the remaining questions to the last one

        int perPlanet;

        if (totalQuestions<4){
            perPlanet = totalQuestions;
            questionsPerPlanet = perPlanet; questionsPerLastPlanet = perPlanet;
            return;
        }

        //  if the number of total number of questions is divisible by 4 divide them evenly, otherwise add  the remaining to the last level
        if (totalQuestions % 4 == 0) {
            perPlanet = totalQuestions/4;
            questionsPerPlanet = perPlanet;
            questionsPerLastPlanet = perPlanet;
        } else {
            int remainder = totalQuestions%4;
            perPlanet = (totalQuestions - remainder) / 4;
            questionsPerPlanet = perPlanet;
            questionsPerLastPlanet = perPlanet + remainder;
        }

        System.out.println("NUMBER OF QUESTIONS IS " + totalQuestions);
        System.out.println("QUESTIONS PER LEVEL IS " + questionsPerPlanet);
        System.out.println("QUESTIONS PER LAST LEVEL IS " + questionsPerLastPlanet);
    }


}
