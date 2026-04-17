package com.example.mind_games_app;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LeaderBoard extends AppCompatActivity {

    TextView multi, mem, word;
    Button exit, reset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_leaderboard);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        multi = findViewById(R.id.multiplicationScore);
        mem = findViewById(R.id.memoryScore);
        word = findViewById(R.id.wordScore);
        exit = findViewById(R.id.button3);
        reset = findViewById(R.id.leaderboardReset);

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(LeaderBoard.this)
                        .setTitle("Reset Scores")
                        .setMessage("Are you sure you want to clear all scores?")
                        .setPositiveButton("Yes", (dialog, which) -> clearScores())
                        .setNegativeButton("Cancel", null)
                        .show();
            }
        });

        SharedPreferences prefs = getSharedPreferences("Leaderboard", MODE_PRIVATE);

        Set<String> memorySet = prefs.getStringSet("memory_scores", new HashSet<>());
        Set<String> multiSet = prefs.getStringSet("multiplication_highscore", new HashSet<>());
        Set<String> wordSet = prefs.getStringSet("word_scramble_highscore", new HashSet<>());


        List<Integer> memoryList = new ArrayList<>();
        for (String s : memorySet) {
            memoryList.add(Integer.parseInt(s));
        }

        List<Integer> multiList = new ArrayList<>();
        for (String s : multiSet) {
            multiList.add(Integer.parseInt(s));
        }

        List<Integer> wordList = new ArrayList<>();
        for (String s : wordSet) {
           wordList.add(Integer.parseInt(s));
        }

        Collections.sort(memoryList, Collections.reverseOrder());
        Collections.sort(multiList, Collections.reverseOrder());
        Collections.sort(wordList, Collections.reverseOrder());

        StringBuilder memText = new StringBuilder("Memory Game Top 5 Scores:\n");

        for (int i = 0; i < memoryList.size(); i++) {
            memText.append((i + 1) + ". " + memoryList.get(i) + "\n");
        }

        mem.setText(memText.toString());

        StringBuilder multiText = new StringBuilder("Multiplication Game Top 5 Scores:\n");

        for (int i = 0; i < multiList.size(); i++) {
            multiText.append((i + 1) + ". " + multiList.get(i) + "\n");
        }

        multi.setText(multiText.toString());

        StringBuilder wordText = new StringBuilder("Word Scramble Top 5 Scores:\n");

        for (int i = 0; i < wordList.size(); i++) {
            wordText.append((i + 1) + ". " + wordList.get(i) + "\n");
        }

        word.setText(wordText.toString());




        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LeaderBoard.this, MenuScreen.class);
                startActivity(i);
            }
        });


    }

    private void clearScores() {
        SharedPreferences prefs = getSharedPreferences("Leaderboard", MODE_PRIVATE);
        prefs.edit().clear().apply();

        Intent i = getIntent();
        finish();
        startActivity(i);
        Toast.makeText(this, "Scores cleared", Toast.LENGTH_SHORT).show();
    }
}