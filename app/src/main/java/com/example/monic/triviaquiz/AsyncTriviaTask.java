package com.example.monic.triviaquiz;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by sai shanmukhi on 9/28/2017.
 */

public class AsyncTriviaTask extends AsyncTask<String,Integer,ArrayList<Trivia>>{
    IData activity;
    public AsyncTriviaTask(IData activity) {
        this.activity = activity;
    }


    @Override
    protected ArrayList<Trivia> doInBackground(String... params) {
        int count = 0;
        try {

            URL url = new URL(params[0]);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.connect();
            int statusCode = con.getResponseCode();
            if (statusCode == HttpURLConnection.HTTP_OK) {

                BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = reader.readLine();

                while (line != null) {
                    sb.append(line);
                    line = reader.readLine();
                }
                return ParseTrivia(sb.toString());
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute (ArrayList < Trivia > trivias) {
        activity.setupData(trivias);
        super.onPostExecute(trivias);
    }
    public interface IData {
        void setupData(ArrayList<Trivia> trivias);
    }
    public ArrayList<Trivia> ParseTrivia(String in) throws JSONException
    {
        JSONObject root,childObject=null;
        JSONArray triviaJSONArray=null,choiceJSONArray=null;
        ArrayList<String> choices;

        ArrayList<Trivia> triviaresult = new ArrayList<Trivia>();

        root = new JSONObject(in);

        if(root.has("questions"))
            triviaJSONArray = root.getJSONArray("questions");

        for(int i=0;i<triviaJSONArray.length();i++)
        {
            JSONObject triviajsonobject = triviaJSONArray.getJSONObject(i);
            Trivia t = new Trivia();
            if(triviajsonobject.has("id"))
                t.setId(triviajsonobject.getInt("id"));
            if(triviajsonobject.has("text"))
                t.setQuestion(triviajsonobject.getString("text"));
            if(triviajsonobject.has("image"))
                t.setImgpath(triviajsonobject.getString("image"));
            if(triviajsonobject.has("choices")) {
                childObject = triviajsonobject.getJSONObject("choices");
            }
            if(childObject.has("choice"))
                choiceJSONArray = childObject.getJSONArray("choice");
            choices = new ArrayList<>();
            for(int j = 0;j<choiceJSONArray.length();j++)
            {
                choices.add(choiceJSONArray.getString(j));
            }
            t.setChoice(choices);
            if(childObject.has("answer"))
                t.setAnswer(childObject.getInt("answer"));

            triviaresult.add(t);
        }
        return triviaresult;
    }

}
