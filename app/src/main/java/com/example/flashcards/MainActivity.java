package com.example.flashcards;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView flashcardQuestion;
    TextView flashcardAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        flashcardQuestion = findViewById(R.id.flashcards_questions);
        flashcardAnswer = findViewById(R.id.flashcards_answers);
        flashcardQuestion.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                flashcardQuestion.setVisibility(View.INVISIBLE);
                flashcardAnswer.setVisibility(View.VISIBLE);
            }
        });

        findViewById(R.id.flashcards_add_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddCardActivity.class);
                startActivityForResult(intent, 100);
            }
        });

    }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
            super.onActivityResult(requestCode, resultCode, data);
            Log.d("onActivityResult", "work");
            if(requestCode == 100 && resultCode == RESULT_OK) {//get data
                Log.d("onActivityResult", "resultCode == 100");
               if(data !=null) {// check if there's an Intent
                   String questionString = data.getExtras().getString("QUESTION_KEY");
                   String answerString = data.getExtras().getString("ANSWER_KEY");
                   flashcardQuestion.setText(questionString);
                   flashcardAnswer.setText(answerString);
               }
            }
        }

}