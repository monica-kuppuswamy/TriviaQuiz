package com.example.monic.triviaquiz;

import android.support.v4.os.ParcelableCompatCreatorCallbacks;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by sai shanmukhi on 9/28/2017.
 */

public class Trivia implements Serializable {
    int id;
    String question,imgpath;
    ArrayList<String>choice;
    int answer,useranswer;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getImgpath() {
        return imgpath;
    }

    public void setImgpath(String imgpath) {
        this.imgpath = imgpath;
    }

    public ArrayList<String> getChoice() {
        return choice;
    }

    public void setChoice(ArrayList<String> choice) {
        this.choice = choice;
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }

    public int getUseranswer() {
        return useranswer;
    }

    public void setUseranswer(int useranswer) {
        this.useranswer = useranswer;
    }


    @Override
    public String toString() {
        return "Trivia{" +
                "id=" + id +
                ", question='" + question + '\'' +
                ", imgpath='" + imgpath + '\'' +
                ", choice=" + choice +
                ", answer=" + answer +
                ", useranswer=" + useranswer +
                '}';
    }
}
