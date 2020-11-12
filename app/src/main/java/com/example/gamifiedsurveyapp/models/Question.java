package com.example.gamifiedsurveyapp.models;

import java.io.Serializable;

public class Question implements Serializable {

    private int questionId;
    private String questionBody;
    private int questionType;
    private String questionResponse;
    private String choice1, choice2, choice3, choice4, choice5;

    public Question(int questionId, String questionBody, int questionType) {
        this.questionId = questionId;
        this.questionBody = questionBody;
        this.questionType = questionType;
        this.questionResponse = "default";
        this.choice1 = null;
        this.choice2 = null;
        this.choice3 = null;
        this.choice4 = null;
        this.choice5 = null;
    }

    public Question(int questionId, String questionBody, int questionType, String c1, String c2, String c3, String c4, String c5) {
        this.questionId = questionId;
        this.questionBody = questionBody;
        this.questionType = questionType;

        this.choice1 = c1;
        this.choice2 = c2;
        this.choice3 = c3;
        this.choice4 = c4;
        this.choice5 = c5;

        this.questionResponse = "default";
    }

    public String getChoice1(){
        return this.choice1;
    }

    public String getChoice2(){
        return this.choice2;
    }

    public String getChoice3(){
        return this.choice3;
    }

    public String getChoice4(){
        return this.choice4;
    }

    public String getChoice5(){
        return this.choice5;
    }

    public int getQuestionId() {
        return questionId;
    }

    public String getQuestionBody() {
        return questionBody;
    }

    public void setQuestionBody(String questionBody) {
        this.questionBody = questionBody;
    }

    public int getQuestionType() {
        return questionType;
    }

    public void setQuestionType(int questionType) {
        this.questionType = questionType;
    }

    public String getQuestionResponse() {
        return questionResponse;
    }

    public void setQuestionResponse(String questionResponse) {
        this.questionResponse = questionResponse;
    }

    @Override
    public String toString() {
        return "Question{" +
                "questionBody='" + questionBody + '\'' +
                ", questionType=" + questionType +
                ", questionResponse='" + questionResponse + '\'' +
                '}';
    }
}
