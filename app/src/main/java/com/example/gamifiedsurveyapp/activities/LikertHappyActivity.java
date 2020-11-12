package com.example.gamifiedsurveyapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gamifiedsurveyapp.R;
import com.example.gamifiedsurveyapp.models.Question;
import com.example.gamifiedsurveyapp.models.Survey;

import java.util.HashMap;
import java.util.Random;

public class LikertHappyActivity extends AppCompatActivity implements View.OnClickListener {

    private Survey mSurvey;
    private HashMap<Integer, Boolean> mMap;

    private int questionsToAnswerThisLevel;
    private int questionsAnsweredInThisLevel;

    private int thisQuestionNum;
    private int nextQuestionType;

    private ImageButton nextQuestionButton;
    private ImageButton exitSurveyButton;
    private ImageView alienAvatar;

    private Question mQuestion;

    private ImageButton choice1, choice2, choice3, choice4,  choice5;

    private String response = "null";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_likert_happy);

        choice1 = (ImageButton) findViewById(R.id.very_unhappy_button);
        choice2 = (ImageButton) findViewById(R.id.unhappy_button);
        choice3 = (ImageButton) findViewById(R.id.neutral_button);
        choice4 = (ImageButton) findViewById(R.id.happy_button);
        choice5 = (ImageButton) findViewById(R.id.very_happy_button);

        choice1.setOnClickListener(this);
        choice2.setOnClickListener(this);
        choice3.setOnClickListener(this);
        choice4.setOnClickListener(this);
        choice5.setOnClickListener(this);

        setUpNextAndExitButtons();

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        mSurvey = (Survey) extras.getSerializable("SURVEY_DATA");
        //nextQuestionType = mSurvey.getNextQuestionType();
        //System.out.println("THE NEXT QUESTION TYPE IS + " + nextQuestionType);

        thisQuestionNum = mSurvey.getNumCompletedQuestions();

        mQuestion = mSurvey.getQuestion(thisQuestionNum);

        mMap = (HashMap<Integer, Boolean>) extras.getSerializable("PROGRESS");

        questionsAnsweredInThisLevel = extras.getInt("NUM_QUESTIONS_ANSWERED");
        questionsToAnswerThisLevel = extras.getInt("NUM_QUESTIONS_TO_ANSWER");

        TextView questionBody = findViewById(R.id.question_body);
        questionBody.setText("Greetings.... \n " + mSurvey.getQuestionBody(thisQuestionNum));
        TextView progressText = findViewById(R.id.progress_text);
        progressText.setText("Question "  + (questionsAnsweredInThisLevel + 1) + " of " + questionsToAnswerThisLevel);



        Button submitButton = (Button) findViewById(R.id.submit_button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (response.equals("null")){
                    Toast.makeText(LikertHappyActivity.this,
                            "Choose a response!", Toast.LENGTH_SHORT).show();
                    return;
                }

                System.out.println("RESPONSE GIVEN IN MULTIPLE CHOICE: " + response);

                mSurvey.setResponse(thisQuestionNum, response);
                mSurvey.increaseQuestionCounter();
                questionsAnsweredInThisLevel += 1;

                // CREATE AN INTENT FOR THE NEXT ACTIVITY DEPENDING ON WHAT THE NEXT QUESTION TYPE IS
                Intent intent;
                if (questionsAnsweredInThisLevel == questionsToAnswerThisLevel) {
                    // intent go back to level screen if enough questions are done
                    intent = new Intent(LikertHappyActivity.this, LevelSelectActivity.class);
                } else if (mSurvey.getNextQuestionType() == 1) {
                    intent = new Intent(LikertHappyActivity.this, StarRatingActivity.class);
                } else if (mSurvey.getNextQuestionType() == 2) {
                    intent = new Intent(LikertHappyActivity.this, LikertAgreeActivity.class);
                } else if (mSurvey.getNextQuestionType() == 3) {
                    intent = new Intent(LikertHappyActivity.this, LikertHappyActivity.class);
                } else if (mSurvey.getNextQuestionType() == 4) {
                    intent = new Intent(LikertHappyActivity.this, TextInputActivity.class);
                } else {
                    intent = new Intent(LikertHappyActivity.this, MultipleChoiceActivity.class);
                }

                Bundle bundle = new Bundle();
                bundle.putSerializable("SURVEY_DATA", mSurvey);
                bundle.putInt("NUM_QUESTIONS_TO_ANSWER", questionsToAnswerThisLevel);
                bundle.putInt("NUM_QUESTIONS_ANSWERED", questionsAnsweredInThisLevel);
                bundle.putSerializable("PROGRESS", mMap);

                intent.putExtras(bundle);
                startActivity(intent);

            }
        });



        setAlienImage();
    }

    public void setAlienImage(){
        // set alien image randomly to one of 20 saved images
        alienAvatar = (ImageView) findViewById(R.id.alien_avatar);
        int resId[]={R.drawable.alien1,R.drawable.alien2,R.drawable.alien3,R.drawable.alien4,R.drawable.alien5,R.drawable.alien6,R.drawable.alien7,R.drawable.alien8,R.drawable.alien9,R.drawable.alien10,R.drawable.alien11,R.drawable.alien12,R.drawable.alien13,R.drawable.alien14,R.drawable.alien15,R.drawable.alien16,R.drawable.alien17,R.drawable.alien18,R.drawable.alien19,R.drawable.alien20};
        Random rand = new Random();
        int index = rand.nextInt((resId.length- 1) - 0 + 1) + 0;
        alienAvatar.setImageResource(resId[index]);
    }


    public void setUpNextAndExitButtons(){
        nextQuestionButton = (ImageButton) findViewById(R.id.next_question_button);
        exitSurveyButton = (ImageButton) findViewById(R.id.exit_survey_button);
        nextQuestionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(LikertHappyActivity.this);

                builder.setCancelable(true);
                builder.setTitle("Skip This Question?");
                builder.setMessage("Press OK to move on to the next question");

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // do something
                        String response = "No Response Given";
                        System.out.println("Response Was " + response);

                        mSurvey.setResponse(thisQuestionNum, response);
                        mSurvey.increaseQuestionCounter();
                        questionsAnsweredInThisLevel += 1;

                        Intent intent;
                        if (questionsAnsweredInThisLevel == questionsToAnswerThisLevel) {
                            // intent go back to level screen if enough questions are done

                            intent = new Intent(LikertHappyActivity.this, LevelSelectActivity.class);
                        } else if (mSurvey.getNextQuestionType() == 1) {
                            intent = new Intent(LikertHappyActivity.this, StarRatingActivity.class);
                        } else {
                            intent = new Intent(LikertHappyActivity.this, TextInputActivity.class);
                        }

                        Bundle bundle = new Bundle();
                        bundle.putSerializable("SURVEY_DATA", mSurvey);
                        bundle.putInt("NUM_QUESTIONS_TO_ANSWER", questionsToAnswerThisLevel);
                        bundle.putInt("NUM_QUESTIONS_ANSWERED", questionsAnsweredInThisLevel);
                        bundle.putSerializable("PROGRESS", mMap);

                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });
                builder.show();
            }
        });

        exitSurveyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(LikertHappyActivity.this);

                builder.setCancelable(true);
                builder.setTitle("Are You Sure You Want To Quit?");
                builder.setMessage("None of your responses will be saved");

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

                builder.setPositiveButton("Yes, Quit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // do something
                        Intent intent = new Intent(LikertHappyActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                });
                builder.show();
            }
        });
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.very_unhappy_button:
                response = "Very Dissatisfied";
                System.out.println(response);
                setButtonColors(1);
                break;
            case R.id.unhappy_button:
                response = "Dissatisfied";
                System.out.println(response);
                setButtonColors(2);
                break;
            case R.id.neutral_button:
                response = "Neutral";
                System.out.println(response);
                setButtonColors(3);
                break;
            case R.id.happy_button:
                response = "Satisfied";
                System.out.println(response);
                setButtonColors(4);
                break;
            case R.id.very_happy_button:
                response = "Very Satisfied";
                System.out.println(response);
                setButtonColors(5);
                break;

        }
    }

    public void setButtonColors(int i) {
        choice1.setBackgroundResource(R.drawable.happy_button_color_states);
        choice2.setBackgroundResource(R.drawable.happy_button_color_states);
        choice3.setBackgroundResource(R.drawable.happy_button_color_states);
        choice4.setBackgroundResource(R.drawable.happy_button_color_states);
        choice5.setBackgroundResource(R.drawable.happy_button_color_states);

        switch (i){
            case 1:
                choice1.setBackgroundResource(R.drawable.happy_button_pressed);
                break;
            case 2:
                choice2.setBackgroundResource(R.drawable.happy_button_pressed);
                break;
            case 3:
                choice3.setBackgroundResource(R.drawable.happy_button_pressed);
                break;
            case 4:
                choice4.setBackgroundResource(R.drawable.happy_button_pressed);
                break;
            case 5:
                choice5.setBackgroundResource(R.drawable.happy_button_pressed);
                break;

        }

    }




}
