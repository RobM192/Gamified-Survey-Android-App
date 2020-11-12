package com.example.gamifiedsurveyapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gamifiedsurveyapp.R;
import com.example.gamifiedsurveyapp.models.Question;
import com.example.gamifiedsurveyapp.models.Survey;
import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class FinalActivity extends AppCompatActivity {

    private Survey mSurvey;
    private HashMap<Integer, String> idResponseMap;
    String server_url_insert = "http://localhost/SurveyAPI/api/response/insert.php";
    Button AddData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);

        //get intents from previous activity and hold in survey object
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        mSurvey = (Survey) extras.getSerializable("SURVEY_DATA");
        String avatarSelected = mSurvey.getAvatar();
        System.out.println(avatarSelected);
        ImageView avatarBackgroundImage = (ImageView) findViewById(R.id.character_background_image);
        if (avatarSelected.equals("Boy")) {
            avatarBackgroundImage.setImageResource(R.drawable.boy_bkrd);
        } else if (avatarSelected.equals("Robot")) {
            avatarBackgroundImage.setImageResource(R.drawable.robot_bkrd);
        } else if (avatarSelected.equals("Monkey")) {
            avatarBackgroundImage.setImageResource(R.drawable.monkey_bkrd);
        }

        idResponseMap = new HashMap<>();
        for (Question q : mSurvey.questionList()) {
            idResponseMap.put(q.getQuestionId(), q.getQuestionResponse());
        }
        System.out.println(idResponseMap.toString());
        AddData = findViewById(R.id.submit_button);
        AddData.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                try {
                    Add();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void Add() throws UnsupportedEncodingException {

        String url=server_url_insert+"?";
        Iterator<Entry<Integer, String>> it = idResponseMap.entrySet().iterator();

        // iterating every set of entry in the HashMap.
        while (it.hasNext()) {
            Map.Entry<Integer, String> set = (Map.Entry<Integer, String>) it.next();
            System.out.println(set.getKey() + " = " + set.getValue());
            Integer questionId = Integer.parseInt(URLEncoder.encode(set.getKey().toString(),"UTF8"));
            String responseBody = (URLEncoder.encode(set.getValue(),"UTF8"));
            url+= questionId+"="+responseBody+"&";
        }

        url = url.substring(0, url.length() - 1);
        System.out.println(url);
        Log.e("URL",url);

        StringRequest stringRequest= new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                try {

                    JSONObject jsonObject=new JSONObject(response);
                    Toast.makeText(FinalActivity.this,jsonObject.getString("message"),Toast.LENGTH_LONG).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(FinalActivity.this,"e"+e.toString(),Toast.LENGTH_LONG).show();
                }
            }
        },

                new Response.ErrorListener() {

                    @Override

                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(FinalActivity.this,"err"+error.toString(),Toast.LENGTH_LONG).show();
                    }
                }
        );

        RequestQueue requestQueue=Volley.newRequestQueue(FinalActivity.this);

        requestQueue.add(stringRequest);

        Intent intent = new Intent(FinalActivity.this, MainActivity.class);
        startActivity(intent);


    }


}




