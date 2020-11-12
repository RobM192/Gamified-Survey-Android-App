package com.example.gamifiedsurveyapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gamifiedsurveyapp.R;
import com.example.gamifiedsurveyapp.models.Survey;

import java.util.HashMap;
import java.util.Random;

public class TextInputActivity extends AppCompatActivity {

    private Survey mSurvey;

    private HashMap<Integer, Boolean> mMap;

    private int questionsToAnswerThisLevel;
    private int questionsAnsweredInThisLevel;

    private int thisQuestionNum;
    private View view;

    private ImageButton nextQuestionButton;
    private TextView nextQuestionAlert;

    private ImageButton exitSurveyButton;
    private TextView exitSurveyAlert;
    private ImageView alienAvatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_input);

        setUpNextAndExitButtons();

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        mSurvey = (Survey) extras.getSerializable("SURVEY_DATA");
        //nextQuestionType = mSurvey.getNextQuestionType();
        //System.out.println("THE NEXT QUESTION TYPE IS + " + nextQuestionType);

        thisQuestionNum = mSurvey.getNumCompletedQuestions();

        mMap = (HashMap<Integer, Boolean>) extras.getSerializable("PROGRESS");

        questionsAnsweredInThisLevel = extras.getInt("NUM_QUESTIONS_ANSWERED");
        questionsToAnswerThisLevel = extras.getInt("NUM_QUESTIONS_TO_ANSWER");

        TextView questionBody = findViewById(R.id.question_body);
        questionBody.setText("Greetings.... \n " + mSurvey.getQuestionBody(thisQuestionNum));
        TextView progressText = findViewById(R.id.progress_text);
        progressText.setText("Question "  + (questionsAnsweredInThisLevel + 1) + " of " + questionsToAnswerThisLevel);

        final EditText textBox = (EditText) findViewById(R.id.text_box);

        Button submitButton = (Button) findViewById(R.id.submit_button);

        textBox.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String response = textBox.getText().toString();
                System.out.println("TYPED RESPONSE WAS " + response);

                if (response.length() < 1) {
                    Toast.makeText(TextInputActivity.this,
                            "Please type an answer into the box", Toast.LENGTH_LONG).show();
                    return;
                }

                mSurvey.setResponse(thisQuestionNum, response);
                mSurvey.increaseQuestionCounter();
                questionsAnsweredInThisLevel += 1;

                // CREATE AN INTENT FOR THE NEXT ACTIVITY DEPENDING ON WHAT THE NEXT QUESTION TYPE IS
                Intent intent;
                if (questionsAnsweredInThisLevel == questionsToAnswerThisLevel) {
                    // intent go back to level screen if enough questions are done
                    intent = new Intent(TextInputActivity.this, LevelSelectActivity.class);
                } else if (mSurvey.getNextQuestionType() == 1) {
                    intent = new Intent(TextInputActivity.this, StarRatingActivity.class);
                } else if (mSurvey.getNextQuestionType() == 2) {
                    intent = new Intent(TextInputActivity.this, LikertAgreeActivity.class);
                } else if (mSurvey.getNextQuestionType() == 3) {
                    intent = new Intent(TextInputActivity.this, LikertHappyActivity.class);
                } else if (mSurvey.getNextQuestionType() == 4) {
                    intent = new Intent(TextInputActivity.this, TextInputActivity.class);
                } else {
                    intent = new Intent(TextInputActivity.this, MultipleChoiceActivity.class);
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

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void setUpNextAndExitButtons(){
        nextQuestionButton = (ImageButton) findViewById(R.id.next_question_button);

        exitSurveyButton = (ImageButton) findViewById(R.id.exit_survey_button);
        nextQuestionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(TextInputActivity.this);

                builder.setCancelable(true);
                builder.setTitle("Skip this question?");
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

                            intent = new Intent(TextInputActivity.this, LevelSelectActivity.class);
                        } else if (mSurvey.getNextQuestionType() == 1) {
                            intent = new Intent(TextInputActivity.this, StarRatingActivity.class);
                        } else {
                            intent = new Intent(TextInputActivity.this, TextInputActivity.class);
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
                AlertDialog.Builder builder = new AlertDialog.Builder(TextInputActivity.this);

                builder.setCancelable(true);
                builder.setTitle("Are you sure you want to quit?");
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
                        Intent intent = new Intent(TextInputActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                });
                builder.show();
            }
        });
    }
}

