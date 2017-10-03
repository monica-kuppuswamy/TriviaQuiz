package com.example.monic.triviaquiz;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class TriviaActivity extends AppCompatActivity implements GetImageAsyncTask.IImage{

    ScrollView scrollView;
    LinearLayout container;
    int currentPosition;
    RadioGroup rg;
    int totalScore = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trivia);

        currentPosition = 0;
        final TextView questionNumber = (TextView) findViewById(R.id.tv1);
        final TextView timeLeft = (TextView) findViewById(R.id.tv2);
        final TextView question = (TextView) findViewById(R.id.question);
        final Button nextButton = (Button) findViewById(R.id.nextButton);
        final Button quitButton = (Button) findViewById(R.id.quitButton);
        scrollView = (ScrollView) findViewById(R.id.optionsList);

        Intent i = getIntent();
        final ArrayList<Trivia> triviaList;
        if (i.getSerializableExtra("tryAgain") != null) {
            triviaList = (ArrayList<Trivia>) i.getSerializableExtra("tryAgain");
        } else {
            Bundle args = i.getBundleExtra("BUNDLE");
            triviaList = (ArrayList<Trivia>) args.getSerializable("ARRAYLIST");
        }

        new CountDownTimer(60000, 1000) {
            public void onTick(long millisUntilFinished) {
                timeLeft.setText("Time Left: " + millisUntilFinished / 1000 + "seconds");
            }

            public void onFinish() {
                Intent intent = new Intent(TriviaActivity.this, TriviaStats.class);
                intent.putExtra("totalCorrect", Integer.toString(totalScore));
                intent.putExtra("totalQuestions", Integer.toString(triviaList.size()));
                intent.putExtra("questions", triviaList);
                startActivity(intent);
            }
        }.start();

        container = new LinearLayout(this);
        container.setOrientation(LinearLayout.VERTICAL);

        questionNumber.setText("Q"+ (triviaList.get(0).getId() + 1));
        question.setText(triviaList.get(0).getQuestion());
        String imgUrl = triviaList.get(0).getImgpath();
        rg = new RadioGroup(this);
        for (int j = 0; j < triviaList.get(0).getChoice().size(); j++) {
            RadioButton button = new RadioButton(this);
            button.setId(j + 1);
            button.setText(triviaList.get(0).getChoice().get(j));
            button.setWidth(920);
            button.setHeight(80);
            button.setBackground(getResources().getDrawable(R.drawable.options_border));
            rg.addView(button);
        }
        container.addView(rg);
        scrollView.removeAllViews();
        scrollView.addView(container);

        if(imgUrl != null) {
            new GetImageAsyncTask(TriviaActivity.this).execute(imgUrl);
        } else {
            ImageView img = (ImageView) findViewById(R.id.imgquestion);
            img.setVisibility(View.INVISIBLE);
            ProgressBar progressBar = (ProgressBar)findViewById(R.id.pgimageloading);
            TextView tv3 = (TextView) findViewById(R.id.tv3);
            progressBar.setVisibility(View.VISIBLE);
            tv3.setVisibility(View.VISIBLE);
        }

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selected = rg.getCheckedRadioButtonId();
                if (selected == triviaList.get(currentPosition).getAnswer()) {
                    totalScore++;
                }
                currentPosition++;
                if (currentPosition < triviaList.size()) {
                    container.removeAllViews();
                    questionNumber.setText("Q"+ (triviaList.get(currentPosition).getId() + 1));
                    question.setText(triviaList.get(currentPosition).getQuestion());
                    String imgUrl = triviaList.get(currentPosition).getImgpath();
                    if(imgUrl != null) {
                        new GetImageAsyncTask(TriviaActivity.this).execute(imgUrl);
                    } else {
                        ImageView img = (ImageView) findViewById(R.id.imgquestion);
                        img.setVisibility(View.INVISIBLE);
                        ProgressBar progressBar = (ProgressBar)findViewById(R.id.pgimageloading);
                        TextView tv3 = (TextView) findViewById(R.id.tv3);
                        progressBar.setVisibility(View.VISIBLE);
                        tv3.setVisibility(View.VISIBLE);
                    }
                    rg = new RadioGroup(TriviaActivity.this);
                    for (int j = 0; j < triviaList.get(currentPosition).getChoice().size(); j++) {
                        RadioButton button = new RadioButton(TriviaActivity.this);
                        button.setId(j + 1);
                        button.setText(triviaList.get(currentPosition).getChoice().get(j));
                        button.setWidth(920);
                        button.setHeight(80);
                        button.setBackground(getResources().getDrawable(R.drawable.options_border));
                        rg.addView(button);
                    }
                    container.addView(rg);
                    scrollView.removeAllViews();
                    scrollView.addView(container);
                } else {
                    Intent intent = new Intent(TriviaActivity.this, TriviaStats.class);
                    intent.putExtra("totalCorrect", Integer.toString(totalScore));
                    intent.putExtra("totalQuestions", Integer.toString(triviaList.size()));
                    intent.putExtra("questions", triviaList);
                    startActivity(intent);
                }
            }
        });

        quitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TriviaActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    public void setupImage(Bitmap result) {
        ProgressBar progressBar = (ProgressBar)findViewById(R.id.pgimageloading);
        TextView tv3 = (TextView) findViewById(R.id.tv3);
        progressBar.setVisibility(View.INVISIBLE);
        tv3.setVisibility(View.INVISIBLE);
        ImageView img = (ImageView) findViewById(R.id.imgquestion);
        img.setImageBitmap(result);
        img.setVisibility(View.VISIBLE);
    }
}
