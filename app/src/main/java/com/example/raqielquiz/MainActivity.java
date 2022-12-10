package com.example.raqielquiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView totalQuestionsTextView;
    TextView questionTextView;
    Button ansA, ansB, ansC, ansD;
    Button submitBtn;

    int score = 0;
    int totalQuestion = QuestionAnswer.question.length;
    int currentQuestionIndex = 0 ;
    String selectedAnswer= "";

    @Override
    protected void onCreate(Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        totalQuestionsTextView = findViewById(R.id.total_questions);
        questionTextView = findViewById(R.id.questions);
        ansA = findViewById(R.id.ans_A);
        ansB = findViewById(R.id.ans_B);
        ansC = findViewById(R.id.ans_C);
        ansD = findViewById(R.id.ans_D);
        submitBtn = findViewById(R.id.submit);

        ansA.setOnClickListener(this);
        ansB.setOnClickListener(this);
        ansC.setOnClickListener(this);
        ansD.setOnClickListener(this);
        submitBtn.setOnClickListener(this);

        totalQuestionsTextView.setText("Total questions : "+totalQuestion);

        loadNewQuestion();


    }

    private void loadNewQuestion() {

        if(currentQuestionIndex == totalQuestion){
            finishQuiz();
            return;
        }

        questionTextView.setText(QuestionAnswer.question[currentQuestionIndex]);
        ansA.setText(QuestionAnswer.choices[currentQuestionIndex][0]);
        ansB.setText(QuestionAnswer.choices[currentQuestionIndex][1]);
        ansC.setText(QuestionAnswer.choices[currentQuestionIndex][2]);
        ansD.setText(QuestionAnswer.choices[currentQuestionIndex][3]);
    }

    void finishQuiz(){
        String passStatus = "";
        if (score > totalQuestion*0.60){
            passStatus = "Você passou";
        }else {
            passStatus = "Você falhou";
        }

        new AlertDialog.Builder(this)
                .setTitle(passStatus)
                .setMessage("Sua pontuação é " + score+ " de " +totalQuestion)
                .setPositiveButton("Refazer",((dialogInterface, i) -> restartQuiz()))
                .setCancelable(false)
                .show();

    }

    private void restartQuiz() {
        score = 0;
        currentQuestionIndex = 0;
        loadNewQuestion();

    }


    @Override
    public void onClick(View view) {

        ansA.setBackgroundColor(ContextCompat.getColor(this, R.color.purple_500));
        ansB.setBackgroundColor(ContextCompat.getColor(this, R.color.purple_500));
        ansC.setBackgroundColor(ContextCompat.getColor(this, R.color.purple_500));
        ansD.setBackgroundColor(ContextCompat.getColor(this, R.color.purple_500));


        Button clickedButton = (Button) view;
        if (clickedButton.getId() == R.id.submit){
            if(selectedAnswer.equals(QuestionAnswer.correctAnswers[currentQuestionIndex])){
                score++;
            }
            currentQuestionIndex++;
            loadNewQuestion();


        }else{
            selectedAnswer = clickedButton.getText().toString();
            clickedButton.setBackgroundColor(ContextCompat.getColor(this, R.color.teal_700));
        }

    }
}