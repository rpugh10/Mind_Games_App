package com.example.mind_games_app;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;

public class Memory_Match extends AppCompatActivity implements CardAdapter.OnMatchListener {

    RecyclerView recyclerView;
    String difficulty;
    int matchedPair = 0;
    int columns;
    int totalCards;
    int pairs;
    int moves;

    TextView movesDisplay;
    TextView timerDisplay;
    Handler timerHandle = new Handler(); //Searched up a Youtube video about implementing a timer
    int seconds = 0;
    boolean running = false;

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

        difficulty = getIntent().getStringExtra("difficulty");

        recyclerView = findViewById(R.id.recyclerView);
        movesDisplay = findViewById(R.id.moves);
        timerDisplay = findViewById(R.id.timer);
        startTimer();


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

    @Override
    public void onMove(){
        moves++;
        movesDisplay.setText("Moves: " + moves);
    }

    public void gameWon(){
        running = false;
        Toast.makeText(this, "YOU WIN!", Toast.LENGTH_SHORT).show();
    }
}