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

public class LikertAgreeActivity extends AppCompatActivity implements View.OnClickListener {

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

    private TextView choice1, choice2, choice3, choice4,  choice5;

    private String response = "null";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_likert_agree);

        choice1 = (TextView) findViewById(R.id.choice_1);
        choice2 = (TextView) findViewById(R.id.choice_2);
        choice3 = (TextView) findViewById(R.id.choice_3);
        choice4 = (TextView) findViewById(R.id.choice_4);
        choice5 = (TextView) findViewById(R.id.choice_5);

        setUpNextAndExitButtons();

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        mSurvey = (Survey) extras.getSerializable("SURVEY_DATA");
        //nextQuestionType = mSurvey.getNextQuestionType();
        //System.out.println("THE NEXT QUESTION TYPE IS + " + nextQuestionType);

        thisQuestionNum = mSurvey.getNumCompletedQuestions();

        mQuestion = mSurvey.getQuestion(thisQuestionNum);
        choice1.setText("Strongly Disagree");
        choice2.setText("Disagree");
        choice3.setText("Neutral");
        choice4.setText("Agree");
        choice5.setText("Strongly Agree");

        choice1.setOnClickListener(this);
        choice2.setOnClickListener(this);
        choice3.setOnClickListener(this);
        choice4.setOnClickListener(this);
        choice5.setOnClickListener(this);


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
                    Toast.makeText(LikertAgreeActivity.this,
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
                    intent = new Intent(LikertAgreeActivity.this, LevelSelectActivity.class);
                } else if (mSurvey.getNextQuestionType() == 1) {
                    intent = new Intent(LikertAgreeActivity.this, StarRatingActivity.class);
                } else if (mSurvey.getNextQuestionType() == 2) {
                    intent = new Intent(LikertAgreeActivity.this, LikertAgreeActivity.class);
                } else if (mSurvey.getNextQuestionType() == 3) {
                    intent = new Intent(LikertAgreeActivity.this, LikertHappyActivity.class);
                } else if (mSurvey.getNextQuestionType() == 4) {
                    intent = new Intent(LikertAgreeActivity.this, TextInputActivity.class);
                } else {
                    intent = new Intent(LikertAgreeActivity.this, MultipleChoiceActivity.class);
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
                AlertDialog.Builder builder = new AlertDialog.Builder(LikertAgreeActivity.this);

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

                            intent = new Intent(LikertAgreeActivity.this, LevelSelectActivity.class);
                        } else if (mSurvey.getNextQuestionType() == 1) {
                            intent = new Intent(LikertAgreeActivity.this, StarRatingActivity.class);
                        } else {
                            intent = new Intent(LikertAgreeActivity.this, TextInputActivity.class);
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
                AlertDialog.Builder builder = new AlertDialog.Builder(LikertAgreeActivity.this);

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
                        Intent intent = new Intent(LikertAgreeActivity.this, MainActivity.class);
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
            case R.id.choice_1:
                response = (String) choice1.getText();
                System.out.println(choice1.getText());
                setButtonColors(1);
                break;
            case R.id.choice_2:
                response = (String) choice2.getText();
                setButtonColors(2);
                break;
            case R.id.choice_3:
                response = (String) choice3.getText();
                setButtonColors(3);
                break;
            case R.id.choice_4:
                response = (String) choice4.getText();
                setButtonColors(4);
                break;
            case R.id.choice_5:
                response = (String) choice5.getText();
                setButtonColors(5);
                break;

        }
    }

    public void setButtonColors(int i) {
        choice1.setBackgroundResource(R.drawable.text_box_multiple_choice);
        choice2.setBackgroundResource(R.drawable.text_box_multiple_choice);
        choice3.setBackgroundResource(R.drawable.text_box_multiple_choice);
        choice4.setBackgroundResource(R.drawable.text_box_multiple_choice);
        choice5.setBackgroundResource(R.drawable.text_box_multiple_choice);

        switch (i){
            case 1:
                choice1.setBackgroundResource(R.drawable.text_box_multiple_choice_pressed);
                break;
            case 2:
                choice2.setBackgroundResource(R.drawable.text_box_multiple_choice_pressed);
                break;
            case 3:
                choice3.setBackgroundResource(R.drawable.text_box_multiple_choice_pressed);
                break;
            case 4:
                choice4.setBackgroundResource(R.drawable.text_box_multiple_choice_pressed);
                break;
            case 5:
                choice5.setBackgroundResource(R.drawable.text_box_multiple_choice_pressed);
                break;

        }

    }




}
