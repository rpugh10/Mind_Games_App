package com.example.mind_games_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Memory_Match extends AppCompatActivity implements CardAdapter.OnMatchListener {

    RecyclerView recyclerView;
    String difficulty;
    int matchedPair = 0;
    int columns;
    int totalCards;
    int pairs;
    int moves;
    TextView score;

    TextView movesDisplay;
    TextView timerDisplay;
    Handler timerHandle = new Handler(); //Searched up a Youtube video about implementing a timer
    int seconds = 0;
    boolean running = false;
    Button exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_memory_match);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        SharedPreferences prefs = getSharedPreferences("Settings", MODE_PRIVATE);
        difficulty = prefs.getString("difficulty", "easy");

        recyclerView = findViewById(R.id.recyclerView);
        movesDisplay = findViewById(R.id.moves);
        timerDisplay = findViewById(R.id.timer);
        exit = findViewById(R.id.button);
        score = findViewById(R.id.scores);
        startTimer();


        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Memory_Match.this, MenuScreen.class);
                startActivity(i);
            }
        });
        if(difficulty == null){
            difficulty = "easy";
        }
        if(difficulty.equalsIgnoreCase("easy")){
            columns = 3;
            totalCards = 12;
            pairs = 6;
        }else if(difficulty.equalsIgnoreCase("medium")){
            columns = 4;
            totalCards = 16;
            pairs = 8;
        }else{
            columns = 5;
            totalCards = 20;
            pairs = 10;
        }
        GridLayoutManager layoutManager = new GridLayoutManager(this, columns);
        recyclerView.setLayoutManager(layoutManager);

        ArrayList<Card> cards = new ArrayList<>();
        for (int i = 1; i <= pairs; i++){
            cards.add(new Card(i));
            cards.add(new Card(i));
        }

        Log.d("DEBUG", "Cards size: " + cards.size());

        Collections.shuffle(cards); //Found this method online

        CardAdapter adapter = new CardAdapter(cards, this);
        recyclerView.setAdapter(adapter);

        SharedPreferences sharedPreferences = getSharedPreferences("LeaderBoard", MODE_PRIVATE);
        int currentHighScore = sharedPreferences.getInt("memory_highscore", 0);


    }

    private void startTimer() {
        running = true;

        timerHandle.post(new Runnable() {
            @Override
            public void run() {
                if(running){
                    seconds++;

                    int min = seconds / 60;
                    int sec = seconds % 60;
                    timerDisplay.setText(String.format("Time: %02d:%02d", min, sec));

                    timerHandle.postDelayed(this, 1000);
                }
            }
        });
    }

    @Override
    public void onMatch(){
        matchedPair++;

        if(matchedPair == pairs){
            gameWon();
        }
    }

    private int calculateScore(){
        int base = pairs * 100;
        int movePenalty = moves * 5;
        int timePenalty = seconds * 2;

        int finalScore = base - movePenalty - timePenalty;

        return Math.max(finalScore, 0);
    }


    @Override
    public void onMove(){
        moves++;
        movesDisplay.setText("Moves: " + moves);
    }

    public void gameWon(){
        running = false;
        int finalScore = calculateScore();

        score.setText("Score: " + finalScore);
        SharedPreferences prefs = getSharedPreferences("Leaderboard", MODE_PRIVATE);

        Set<String> scoreSet = prefs.getStringSet("memory_scores", new HashSet<>());

        Set<String> newSet = new HashSet<>(scoreSet);

        newSet.add(String.valueOf(finalScore));

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

        prefs.edit().putStringSet("memory_scores", topSet).apply();
        Toast.makeText(this, "YOU WIN!", Toast.LENGTH_SHORT).show();
    }
}