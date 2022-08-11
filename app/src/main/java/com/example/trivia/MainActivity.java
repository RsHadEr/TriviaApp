package com.example.trivia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.trivia.controller.AppController;
import com.example.trivia.data.AnswerListAsyncResponse;
import com.example.trivia.data.QuestionBank;
import com.example.trivia.model.Question;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView question;
    private TextView questionCounter;
    private ImageButton nextButton;
    private ImageButton prevButton;
    private Button trueButton;
    private Button falseButton;
    private int currquestion=0;
    private List<Question> mQuestions;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        question=findViewById(R.id.question);
        questionCounter=findViewById(R.id.questioncounter);
        nextButton=findViewById(R.id.next_button);
        prevButton=findViewById(R.id.prev_button);
        trueButton=findViewById(R.id.True_button);
        falseButton=findViewById(R.id.false_button);

        nextButton.setOnClickListener( this);
        trueButton.setOnClickListener(this);
        falseButton.setOnClickListener(this);
        prevButton.setOnClickListener(this);


       mQuestions = new QuestionBank().getQuestions(new AnswerListAsyncResponse() {
            @Override
            public void processFinished(ArrayList<Question> questionArrayList) {
                question.setText(questionArrayList.get(currquestion).getAnswer());
                questionCounter.setText(currquestion+"/"+mQuestions.size());

            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.false_button:
                checkAnswer(false);
                updateQuestion();
                break;
            case R.id.True_button:
                checkAnswer(true);
                updateQuestion();
                break;
            case R.id.next_button:
                currquestion=(currquestion+1)%mQuestions.size();
                updateQuestion();
                break;
            case R.id.prev_button:
                    if(currquestion>0) {
                        currquestion = (currquestion - 1) % mQuestions.size();
                        updateQuestion();
                    }
                break;
        }


    }

    private void checkAnswer(boolean b) {
        boolean val=mQuestions.get(currquestion).getAnswerTrue();
        int toastMessageId=0;
        if(val==b)
        {fadeView();
        }
        else{
            shakeAnimation();
        }


    }

    private void updateQuestion() {
        String newQuestion=mQuestions.get(currquestion).getAnswer();
        question.setText(newQuestion);
        questionCounter.setText(currquestion+"/"+mQuestions.size());
    }
    private  void shakeAnimation() {
        Animation shake = AnimationUtils.loadAnimation(MainActivity.this,R.anim.shake_animation);
        CardView card=findViewById(R.id.cardView);
        card.setAnimation(shake);
        shake.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                card.setCardBackgroundColor(Color.RED);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                card.setCardBackgroundColor(Color.WHITE);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


    }
    private void fadeView(){
        CardView cardView=findViewById(R.id.cardView);
        AlphaAnimation animation= new AlphaAnimation(1.0f,0.0f);
        animation.setDuration(350);
        animation.setRepeatCount(1);
        animation.setRepeatMode(Animation.REVERSE);
        cardView.setAnimation(animation);

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                cardView.setCardBackgroundColor(Color.GREEN);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                cardView.setCardBackgroundColor(Color.WHITE);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }
}




//        List<Question>questionList= new QuestionBank().getQuestions(new AnswerListAsyncResponse() {
//            @Override
//            public void processFinished(ArrayList<Question> questionArrayList) {
//                Log.d("inside","value"+questionArrayList );
//
//            }
//



