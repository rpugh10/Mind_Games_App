package com.example.mind_games_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
public class Game_result extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_result);


        int score        = getIntent().getIntExtra("SCORE", 0);
        String gameName  = getIntent().getStringExtra("GAME_NAME");
        String difficulty = getIntent().getStringExtra("DIFFICULTY");
        if (gameName == null)  gameName  = "Word Scramble";
        if (difficulty == null) difficulty = "Easy";


        SharedPreferences prefs = getSharedPreferences("brainzone_prefs", MODE_PRIVATE);
        String key = "word_scramble_high_score_" + difficulty.toLowerCase();
        int highScore = prefs.getInt(key, 0);


        TextView tvGameName    = findViewById(R.id.tvGameName);
        TextView tvFinalScore  = findViewById(R.id.tvFinalScore);
        TextView tvHighScore   = findViewById(R.id.tvHighScore);
        TextView tvDifficulty  = findViewById(R.id.tvDifficulty);
        Button btnPlayAgain    = findViewById(R.id.btnPlayAgain);
        Button btnShare        = findViewById(R.id.btnShare);
        Button btnHome         = findViewById(R.id.btnHome);


        tvGameName.setText(gameName);
        tvFinalScore.setText("Your Score: " + score);
        tvHighScore.setText("Best: " + highScore);
        tvDifficulty.setText("Difficulty: " + difficulty);


        String finalDifficulty = difficulty;
        String finalGameName = gameName;


        btnPlayAgain.setOnClickListener(v -> {
            Intent intent = new Intent(this, WordScramble.class);
            intent.putExtra("DIFFICULTY", finalDifficulty);
            startActivity(intent);
            finish();
        });


        btnShare.setOnClickListener(v -> {
            String shareText = "I scored " + score + " points in " + finalGameName
                    + " on " + finalDifficulty + " difficulty! Can you beat me? #BrainZone";
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);
            startActivity(Intent.createChooser(shareIntent, "Share your score"));
        });


        btnHome.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        });
    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}