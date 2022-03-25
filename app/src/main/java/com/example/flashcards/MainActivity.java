package com.example.flashcards;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView flashcardQuestion;
    TextView flashcardAnswer;

    FlashcardDatabase flashcardDatabase;
    List<Flashcard> allFlashcards;

    int currentCardDisplayedIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        flashcardDatabase = new FlashcardDatabase(getApplicationContext());
        allFlashcards = flashcardDatabase.getAllCards();

        if (allFlashcards != null && allFlashcards.size() > 0) {
            ((TextView) findViewById(R.id.flashcards_questions)).setText(allFlashcards.get(0).getQuestion());
            ((TextView) findViewById(R.id.flashcards_answers)).setText(allFlashcards.get(0).getAnswer());
        }

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

        flashcardAnswer.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                flashcardAnswer.setVisibility(View.INVISIBLE);
                flashcardQuestion.setVisibility(View.VISIBLE);

            }
        }
        );

        findViewById(R.id.flashcards_add_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddCardActivity.class);
                startActivityForResult(intent, 100);
            }
        });

        flashcardDatabase = new FlashcardDatabase(getApplicationContext());
        allFlashcards = flashcardDatabase.getAllCards();

        if(allFlashcards !=null && allFlashcards.size() > 0)
        {
            Flashcard firstCard = allFlashcards.get(0);
            flashcardQuestion.setText(firstCard.getQuestion());
            flashcardAnswer.setText(firstCard.getAnswer());
        }

        findViewById(R.id.next_button).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                currentCardDisplayedIndex +=1;

                flashcardAnswer.setVisibility(View.INVISIBLE);
                flashcardQuestion.setVisibility(View.VISIBLE);

                if(currentCardDisplayedIndex >= allFlashcards.size()){
                    Snackbar.make(view,
                            "You have reached the end of the cards, going back to the start",
                            Snackbar.LENGTH_SHORT)
                            .show();
                    currentCardDisplayedIndex = 0;
                }
                Flashcard currentCard = allFlashcards.get(currentCardDisplayedIndex);
                flashcardQuestion.setText(currentCard.getQuestion());
                flashcardAnswer.setText(currentCard.getAnswer());
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

                   Flashcard flashcard = new Flashcard(questionString, answerString);
                   flashcardDatabase.insertCard(flashcard);

                   allFlashcards = flashcardDatabase.getAllCards();

               }
            }
        }

}