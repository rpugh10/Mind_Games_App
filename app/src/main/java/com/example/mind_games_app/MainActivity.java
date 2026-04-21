package com.example.mind_games_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tvHighScore = findViewById(R.id.tvHighScore);
        Button btnEasy       = findViewById(R.id.btnEasy);
        Button btnMedium     = findViewById(R.id.btnMedium);
        Button btnHard       = findViewById(R.id.btnHard);

        SharedPreferences prefs = getSharedPreferences("Leaderboard", MODE_PRIVATE);

        Set<String> scores = prefs.getStringSet("word_scramble_high_score", new HashSet<>());

        int overallBest = 0;

        for (String s : scores) {
            int val = Integer.parseInt(s);
            if (val > overallBest) {
                overallBest = val;
            }
        }

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