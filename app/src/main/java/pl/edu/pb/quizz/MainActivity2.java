package pl.edu.pb.quizz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {

    private boolean correctAnswer;
    private Button ShowCorrectAnswerButton;
    private TextView answerTextView;

    public static final String KEY_EXTRA_ANSWER_SHOWN = "pl.edu.pb.quizz.answerShown";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        correctAnswer = getIntent().getBooleanExtra(MainActivity.KEY_EXTRA_ANSWER, true);
        ShowCorrectAnswerButton = findViewById(R.id.AnswerButton);
        answerTextView = findViewById(R.id.answerTextView);

        ShowCorrectAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int answer = correctAnswer ? R.string.button_true : R.string.button_false;
                answerTextView.setText(answer);
                setAnswerShownResult(true);
            }
        });
    }

    private void setAnswerShownResult(boolean answerWasShown) {
        Intent resultIntent = new Intent();
        resultIntent.putExtra(KEY_EXTRA_ANSWER_SHOWN, answerWasShown);
        setResult(RESULT_OK, resultIntent);
    }
}
