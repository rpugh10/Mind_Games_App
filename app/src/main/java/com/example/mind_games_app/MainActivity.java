package com.example.mind_games_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tvHighScore = findViewById(R.id.tvHighScore);
        Button btnEasy       = findViewById(R.id.btnEasy);
        Button btnMedium     = findViewById(R.id.btnMedium);
        Button btnHard       = findViewById(R.id.btnHard);

        SharedPreferences prefs = getSharedPreferences("brainzone_prefs", MODE_PRIVATE);
        int bestEasy   = prefs.getInt("word_scramble_high_score_easy", 0);
        int bestMedium = prefs.getInt("word_scramble_high_score_medium", 0);
        int bestHard   = prefs.getInt("word_scramble_high_score_hard", 0);
        int overallBest = Math.max(bestEasy, Math.max(bestMedium, bestHard));
        tvHighScore.setText("All-Time Best: " + overallBest);

        btnEasy.setOnClickListener(v -> launchGame("Easy"));
        btnMedium.setOnClickListener(v -> launchGame("Medium"));
        btnHard.setOnClickListener(v -> launchGame("Hard"));
    }

    private void launchGame(String difficulty) {
        Intent intent = new Intent(this, WordScramble.class);
        intent.putExtra("DIFFICULTY", difficulty);
        startActivity(intent);
    }
}