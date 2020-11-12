package com.example.gamifiedsurveyapp.models;

import com.example.gamifiedsurveyapp.models.Question;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;


public class Survey implements Serializable {

    private ArrayList<Question> mQuestionList;
    private String surveyName;
    private int completedQuestions = 0;
    private String avatar;


    public Survey(){
        mQuestionList = new ArrayList<>();
    }

    public ArrayList<Question> questionList(){
        return this.mQuestionList;
    }

    // return the type of question as a string representation: 1 STAR_RATING, 2 LIKERT_AGREE, 3 LIKERT_HAPPY, 4 TEXT_BOX, 5 MULTIPLE CHOICE

    public int getNextQuestionType(){
        Question q = mQuestionList.get(completedQuestions);
        return q.getQuestionType();
    }

    public void printIdandResponse(){
        for (Question q: mQuestionList){
            System.out.println("PRINTING QUESTION ID AND RESPONSE");
            System.out.println(q.getQuestionId());
            System.out.println(q.getQuestionResponse());
        }
    }

    public int numOfQuestions() {
        return this.mQuestionList.size();
    }

    public void add(Question q) {
        this.mQuestionList.add(q);
    }

    public void clearAll(){
        this.mQuestionList.clear();
    }

    public void increaseQuestionCounter(){
        completedQuestions += 1;
    }

    public int getNumCompletedQuestions(){
        return this.completedQuestions;
    }

    public void setSurveyName(String surveyName){
        this.surveyName = surveyName;
    }

    public String getSurveyName(){
        return this.surveyName;
    }

    // set the response to the question object in the list given the question number and the response...
    public void addResponse(int num, String response){
        Question q = mQuestionList.get(num);
        q.setQuestionResponse(response);
    }

    public String getQuestionBody(int i) {
        Question q = mQuestionList.get(i);
        return q.getQuestionBody();
    }

    public Question getQuestion(int i){
        return mQuestionList.get(i);
    }

    // print all to console for testing
    public void printAll() {
        for (Question q: mQuestionList) {
            System.out.println(q.getQuestionId() + " " + q.getQuestionBody());
        }
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String a) {
        this.avatar = a;
    }

    public void setResponse(int i, String response){
        Question q = mQuestionList.get(i);
        q.setQuestionResponse(response);
    }


}
