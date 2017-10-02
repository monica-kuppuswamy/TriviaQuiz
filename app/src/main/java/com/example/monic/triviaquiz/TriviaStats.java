package com.example.monic.triviaquiz;

import android.content.Intent;
import android.renderscript.Double2;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class TriviaStats extends AppCompatActivity {

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trivia_stats);

        TextView scorePercentage = (TextView) findViewById(R.id.scorePercentage);
        final Button quitButton = (Button) findViewById(R.id.exitButton);
        final Button tryAgain = (Button) findViewById(R.id.tryAgain);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setMax(100);

        int totalCorrect = Integer.parseInt(getIntent().getExtras().getString("totalCorrect"));
        int totalQuestions = Integer.parseInt(getIntent().getExtras().getString("totalQuestions"));
        scorePercentage.setText(Integer.toString((int)(totalCorrect * 100 / totalQuestions)) + "%");
        progressBar.setProgress((int)(totalCorrect * 100 / totalQuestions));

        quitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TriviaStats.this, MainActivity.class);
                startActivity(i);
            }
        });

        tryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TriviaStats.this, TriviaActivity.class);
                i.putExtra("tryAgain", getIntent().getSerializableExtra("questions"));
                startActivity(i);
            }
        });
    }
}
