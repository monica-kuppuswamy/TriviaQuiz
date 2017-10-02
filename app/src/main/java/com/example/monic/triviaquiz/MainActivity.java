package com.example.monic.triviaquiz;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AsyncTriviaTask.IData{
    final static String base_Url=" http://dev.theappsdr.com/apis/trivia_json/index.php";
    Button start,exit;
    TextView loading,ready;
    ImageView logo;
    ProgressBar pb1;
    ArrayList<Trivia> triviaList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        triviaList = new ArrayList<Trivia>();
        start = (Button) findViewById(R.id.btn2);
        exit = (Button) findViewById(R.id.btn1);
        loading = (TextView) findViewById(R.id.tv3);
        logo = (ImageView) findViewById(R.id.img1);
        pb1= (ProgressBar) findViewById(R.id.progressbar1);
        ready = (TextView) findViewById(R.id.tv4);

        logo.setVisibility(View.INVISIBLE);
        loading.setVisibility(View.VISIBLE);
        pb1.setVisibility(ProgressBar.VISIBLE);
        start.setEnabled(false);
        ready.setVisibility(View.INVISIBLE);

        new AsyncTriviaTask(MainActivity.this).execute(base_Url);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,TriviaActivity.class);
                Bundle args = new Bundle();
                args.putSerializable("ARRAYLIST",(Serializable)triviaList);
                i.putExtra("BUNDLE",args);
                startActivity(i);
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void setupData(ArrayList<Trivia> trivias) {
        triviaList=trivias;
        loading.setVisibility(View.INVISIBLE);
        pb1.setVisibility(ProgressBar.GONE);
        logo.setVisibility(View.VISIBLE);
        ready.setVisibility(View.VISIBLE);
        start.setEnabled(true);

    }
}
