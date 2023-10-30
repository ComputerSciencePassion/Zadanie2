package pl.edu.pb.quizz;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    private Button trueButton;
    private Button falseButton;
    private Button nextButton;
    private TextView questionTextView;
    private Boolean answerWasShown = false;

    public static final String KEY_EXTRA_ANSWER = "pl.edu.pb.wi.quizz.correctAnswer";
    private Question[] questions = new Question[]{
            new Question(R.string.q_activity, true),
            new Question(R.string.q_find_resources, false),
            new Question(R.string.q_listener, true),
            new Question(R.string.q_resources, false),
            new Question(R.string.q_version, false),
    };

    private int currentIndex = 0;
    private static final String KEY_CURRENT_INDEX = "currentIndex";
    private static final int REQUEST_CODE_PROMPT = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("QUIZ_TAG", "Wywołana zostałą metoda cyklu życia: onCreate");
        setContentView(R.layout.activity_main);

        trueButton = findViewById(R.id.true_button);
        falseButton = findViewById(R.id.false_button);
        nextButton = findViewById(R.id.next_button);
        questionTextView = findViewById(R.id.question_text_view);
        Button promptButton = findViewById(R.id.hintButton);

        if (savedInstanceState != null) {
            currentIndex = savedInstanceState.getInt(KEY_CURRENT_INDEX);
        }

        trueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswerCorrectness(true);
            }
        });

        falseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswerCorrectness(false);
            }
        });

        nextButton.setOnClickListener((v) -> {
            currentIndex = (currentIndex + 1) % questions.length;
            setNextQuestion();
            answerWasShown = false;
        });
        setNextQuestion();

        promptButton.setOnClickListener((v) -> {
            boolean correctAnswer = questions[currentIndex].isTrueAnswer();
            Intent intent = new Intent(MainActivity.this, MainActivity2.class);
            intent.putExtra(KEY_EXTRA_ANSWER, correctAnswer);
            startActivityForResult(intent, REQUEST_CODE_PROMPT);
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("MainActicity", "Wywołana została metoda z cyklu życia: onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("MainActicity", "Wywołana została metoda z cyklu życia: onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("MainActicity", "Wywołana została metoda z cyklu życia: onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("MainActicity", "Wywołana została metoda z cyklu życia: onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("Lifecycle", "Wywołana została metoda z cyklu życia: onDestroy");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d("QUIZ_TAG", "Wywołana została metoda: onSaveInstanceState");
        outState.putInt(KEY_CURRENT_INDEX, currentIndex);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_CODE_PROMPT) {
            if (data == null) {
                return;
            }
            answerWasShown = data.getBooleanExtra(MainActivity2.KEY_EXTRA_ANSWER_SHOWN, false);
        }
    }

    private void checkAnswerCorrectness(boolean userAnswer) {
        boolean correctAnswer = questions[currentIndex].isTrueAnswer();
        int resultMessageId = 0;

        if (answerWasShown) {
            resultMessageId = R.string.answer_Was_Shown;
        } else {
            if (userAnswer == correctAnswer) {
                resultMessageId = R.string.correct_answer;
            } else {
                resultMessageId = R.string.incorrect_answer;
            }
        }

        Toast.makeText(this, resultMessageId, Toast.LENGTH_SHORT).show();
    }


    private void setNextQuestion() {
        questionTextView.setText(questions[currentIndex].getQuestionId());
    }
}
