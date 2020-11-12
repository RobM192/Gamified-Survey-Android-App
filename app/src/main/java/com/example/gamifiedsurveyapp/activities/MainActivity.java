package com.example.gamifiedsurveyapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.gamifiedsurveyapp.R;
import com.example.gamifiedsurveyapp.models.Question;
import com.example.gamifiedsurveyapp.models.Survey;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.gamifiedsurveyapp.activities.MainActivity";
    private static final String TAG = "MainActivity";
    private TextView surveySelected;
    private TextView surveyName;
    private EditText inputSurvey;
    private Button buttonEnter;
    private Button buttonTest;
    private Button buttonLaunch;
    private Question mQuestion;
    private Survey mSurvey;
    private RequestQueue mQueue;

    // initialize buttons and survey object
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addListenerOnEnterButton();
        addListenerOnLaunchButton();

        mSurvey = new Survey();
        mQueue = Volley.newRequestQueue(this);
    }

    // access the API using GET request to retrieve all questions from a survey given survey id
    private void getSurveyJson(int id) {
        mSurvey.clearAll();
        String url = "http://localhost/SurveyAPI/api/question/read_where.php?survey_id=" + id;

        final Question[] mQuestion = new Question[1];
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("questions");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject question = jsonArray.getJSONObject(i);
                                int questionId = question.getInt("question_id");
                                String questionBody = question.getString("question_body");
                                int questionType = question.getInt("question_type");

                                // if question type requires custom multiple choices to be set
                                if (questionType == 5) {
                                    String qc1 = question.getString("choice_1"); System.out.println(qc1);
                                    String qc2 = question.getString("choice_2"); System.out.println(qc2);
                                    String qc3 = question.getString("choice_3"); System.out.println(qc3);
                                    String qc4 = question.getString("choice_4"); System.out.println(qc4);
                                    String qc5 = question.getString("choice_5"); System.out.println(qc5);
                                    mQuestion[0] = new Question(questionId, questionBody, questionType, qc1, qc2, qc3, qc4, qc5); System.out.println("Adding Multiple choice question");
                                } else {
                                    mQuestion[0] = new Question(questionId, questionBody, questionType); System.out.println("Adding regular question");
                                }
                                mSurvey.add(mQuestion[0]);
                            }
                        } catch (
                                JSONException e) {
                            e.printStackTrace();
                            }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mQueue.add(request);
    }

    // access the API using GET request to retrieve title of the survey given survey id
    private void getSurveyNameJson(int id) {
        String url = "http://localhost/SurveyAPI/api/survey/read_where.php?survey_id=" + id;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("surveys");
                            JSONObject singleSurvey = jsonArray.getJSONObject(0);
                            //surveyName.setText(singleSurvey.getString("survey_name"));
                            mSurvey.setSurveyName(singleSurvey.getString("survey_name"));
                            surveyName.setText(mSurvey.getSurveyName());
                        } catch (
                                JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mQueue.add(request);
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume starts ");
        super.onResume();
        Log.d(TAG, "onResume ends");
    }

    // listen for button press, get text from text field, check if survey id is of valid length, if valid clear the text view, parse json, close keyboard
    private void addListenerOnEnterButton() {
        inputSurvey = (EditText) findViewById(R.id.editText);
        surveySelected = (TextView) findViewById(R.id.surveyEnteredText);
        surveyName = (TextView) findViewById(R.id.survey_name);
        buttonEnter = (Button) findViewById(R.id.enter_button);
        buttonEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //mTextViewResult.setText("");
                String surveyIdText = inputSurvey.getText().toString();

                if (surveyIdText.length() != 3) {
                    Toast.makeText(MainActivity.this,
                            "Invalid Survey ID", Toast.LENGTH_LONG).show();
                } else {
                    surveySelected.setText("Survey: #" + surveyIdText );
                    //mTextViewResult.setText("");
                    getSurveyNameJson(Integer.parseInt(surveyIdText));
                    getSurveyJson(Integer.parseInt(surveyIdText));
                    inputSurvey.setText("");
                    closeKeyboard();
                }
            }
        });
    }

    private void addListenerOnLaunchButton() {
        // check if a valid survey with at least 1 question has been chosen

        buttonLaunch = (Button) findViewById(R.id.launch_button);
        buttonLaunch.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (mSurvey.numOfQuestions() < 1) {
                    Toast.makeText(MainActivity.this,
                            "Invalid Survey", Toast.LENGTH_LONG).show();
                    return;
                }

                System.out.println("LAUNCH NEW ACTIVITY");

                Intent intent = new Intent(MainActivity.this, ConsentActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("SURVEY_DATA", mSurvey);
                intent.putExtras(bundle);
                startActivity(intent);
                }
            });
        }

    // close the onscreen keyboard when user taps anywhere on screen
    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
