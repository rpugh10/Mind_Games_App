package com.example.mind_games_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WordScramble extends AppCompatActivity {

    private TextView tvCategory, tvTimer, tvProgress, tvScore;
    private TextView tvScrambled, tvAttempts, tvFeedback, tvCorrectAnswer;
    private EditText etAnswer;
    private Button btnSubmit, btnHint;

    private List<WordData.WordItem> roundWords;
    private int currentIndex = 0;
    private int score = 0;
    private int attemptsLeft = 3;
    private boolean hintUsed = false;
    private boolean answered = false;
    int timeLeft;


    private CountDownTimer countDownTimer;
    private int timerSeconds = 30;

    Button exit;
    private String difficulty = "Easy";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_scramble);


        if (difficulty.equals("Medium")) timerSeconds = 20;
        else if (difficulty.equals("Hard")) timerSeconds = 12;
        else timerSeconds = 30;

        tvCategory      = findViewById(R.id.tvCategory);
        tvTimer         = findViewById(R.id.tvTimer);
        tvProgress      = findViewById(R.id.tvProgress);
        tvScore         = findViewById(R.id.tvScore);
        tvScrambled     = findViewById(R.id.tvScrambled);
        tvAttempts      = findViewById(R.id.tvAttempts);
        tvFeedback      = findViewById(R.id.tvFeedback);
        tvCorrectAnswer = findViewById(R.id.tvCorrectAnswer);
        etAnswer        = findViewById(R.id.etAnswer);
        btnSubmit       = findViewById(R.id.btnSubmit);
        btnHint         = findViewById(R.id.btnHint);
        exit = findViewById(R.id.button2);


        roundWords = WordData.getRoundWords();

        SharedPreferences prefs = getSharedPreferences("Settings", MODE_PRIVATE);
        difficulty = prefs.getString("difficulty", "easy");

        if (difficulty.equals("easy")) {
            timerSeconds = 30;
        } else if (difficulty.equals("medium")) {
            timerSeconds = 20;
        } else {
            timerSeconds = 12;
        }

        btnSubmit.setOnClickListener(v -> handleSubmit());
        btnHint.setOnClickListener(v -> handleHint());

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(WordScramble.this, MenuScreen.class);
                startActivity(i);
            }
        });

        loadQuestion();
    }

    private void loadQuestion() {
        if (currentIndex >= roundWords.size()) {
            endGame();
            return;
        }

        attemptsLeft = 3;
        hintUsed = false;
        answered = false;
        etAnswer.setText("");
        etAnswer.setEnabled(true);
        btnSubmit.setEnabled(true);
        btnHint.setEnabled(true);
        tvFeedback.setText("");
        tvCorrectAnswer.setText("");

        WordData.WordItem current = roundWords.get(currentIndex);

        tvCategory.setText("Category: " + current.category);
        tvProgress.setText("Question " + (currentIndex + 1) + " / " + roundWords.size());
        tvScore.setText("Score: " + score);
        tvAttempts.setText("Attempts left: " + attemptsLeft);
        tvScrambled.setText(WordScrambleHelper.scramble(current.word));

        startTimer();
    }

    private void startTimer() {
        if (countDownTimer != null) countDownTimer.cancel();

        countDownTimer = new CountDownTimer(timerSeconds * 1000L, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                tvTimer.setText(millisUntilFinished / 1000 + "s");
            }

            @Override
            public void onFinish() {
                if (!answered) {
                    tvTimer.setText("0s");
                    tvFeedback.setText("Time's up!");
                    tvFeedback.setTextColor(getColor(android.R.color.holo_red_dark));
                    tvCorrectAnswer.setText("Answer: " + roundWords.get(currentIndex).word);
                    lockQuestion();
                    nextQuestionDelayed();
                }
            }
        }.start();
    }

    private void handleSubmit() {
        if (answered) return;

        String userAnswer = etAnswer.getText().toString();
        if (userAnswer.isEmpty()) return;

        WordData.WordItem current = roundWords.get(currentIndex);

        if (WordScrambleHelper.isCorrect(userAnswer, current.word)) {
            int pointsEarned;
            if (attemptsLeft == 3) pointsEarned = 15;
            else if (attemptsLeft == 2) pointsEarned = 8;
            else pointsEarned = 3;

            if (pointsEarned < 0) pointsEarned = 0;

            score += pointsEarned;
            tvFeedback.setText("Correct! +" + pointsEarned + " pts");
            tvFeedback.setTextColor(getColor(android.R.color.holo_green_dark));
            lockQuestion();
            nextQuestionDelayed();

        } else {
            attemptsLeft--;
            tvAttempts.setText("Attempts left: " + attemptsLeft);

            if (attemptsLeft <= 0) {
                tvFeedback.setText("Out of attempts!");
                tvFeedback.setTextColor(getColor(android.R.color.holo_red_dark));
                tvCorrectAnswer.setText("Answer: " + current.word);
                lockQuestion();
                nextQuestionDelayed();
            } else {
                tvFeedback.setText("Wrong, try again!");
                tvFeedback.setTextColor(getColor(android.R.color.holo_orange_dark));
                etAnswer.setText("");
            }
        }
    }

    private void handleHint() {
        if (hintUsed || answered) return;

        hintUsed = true;
        btnHint.setEnabled(false);

        String word = roundWords.get(currentIndex).word;
        etAnswer.setText(String.valueOf(word.charAt(0)));
        etAnswer.setSelection(etAnswer.getText().length());

        score -= 5;
        if (score < 0) score = 0;

        tvScore.setText("Score: " + score);

        tvFeedback.setText("Hint used! -5 points");
        tvFeedback.setTextColor(getColor(android.R.color.holo_orange_dark));
    }

    private void lockQuestion() {
        answered = true;
        if (countDownTimer != null) countDownTimer.cancel();
        etAnswer.setEnabled(false);
        btnSubmit.setEnabled(false);
        btnHint.setEnabled(false);
        tvScore.setText("Score: " + score);
    }

    private void nextQuestionDelayed() {
        tvScrambled.postDelayed(() -> {
            currentIndex++;
            loadQuestion();
        }, 1500);
    }

    private void endGame() {
        Log.d("LEADERBOARD_DEBUG", "END GAME TRIGGERED");

        saveHighScore();
        Log.d("SCORE_DEBUG", "Final score: " + score);

        Intent broadcastIntent = new Intent(GameOverReceiver.ACTION_GAME_OVER);
        broadcastIntent.putExtra("SCORE", score);
        broadcastIntent.putExtra("GAME_NAME", "Word Scramble");
        broadcastIntent.putExtra("DIFFICULTY", difficulty);
        sendBroadcast(broadcastIntent);


        Intent intent = new Intent(this, Game_result.class);
        intent.putExtra("SCORE", score);
        intent.putExtra("GAME_NAME", "Word Scramble");
        intent.putExtra("DIFFICULTY", difficulty);
        startActivity(intent);
        finish();
    }



    private void saveHighScore() {

        SharedPreferences prefs = getSharedPreferences("Leaderboard", MODE_PRIVATE);

        Set<String> existingSet = prefs.getStringSet("word_scramble_high_score", new HashSet<>());

        // IMPORTANT: make a REAL copy
        Set<String> newSet = new HashSet<>();
        newSet.addAll(existingSet);

        newSet.add(String.valueOf(score));

        List<Integer> scoreList = new ArrayList<>();
        for (String s : newSet) {
            scoreList.add(Integer.parseInt(s));
        }

        Collections.sort(scoreList, Collections.reverseOrder());

        if (scoreList.size() > 5) {
            scoreList = scoreList.subList(0, 5);
        }

        Set<String> topSet = new HashSet<>();
        for (int s : scoreList) {
            topSet.add(String.valueOf(s));
        }
        Log.d("LEADERBOARD_DEBUG", "Saving score: " + score);

        Log.d("LEADERBOARD_DEBUG", "Before save: " +
                prefs.getStringSet("word_scramble_high_score", new HashSet<>()).toString());

        prefs.edit().putStringSet("word_scramble_high_score", topSet).apply();

        Log.d("LEADERBOARD_DEBUG", "After save: " +
                prefs.getStringSet("word_scramble_high_score", new HashSet<>()).toString());
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (countDownTimer != null) countDownTimer.cancel();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!answered) startTimer();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) countDownTimer.cancel();
    }
}
