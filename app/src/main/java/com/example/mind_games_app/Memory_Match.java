package com.example.mind_games_app;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;

public class Memory_Match extends AppCompatActivity {

    RecyclerView recyclerView;
    String difficulty;

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
        int columns;
        int totalCards;
        int pairs;

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

        ArrayList<Integer> cards = new ArrayList<>();
        for (int i = 1; i <= pairs; i++){
            cards.add(i);
            cards.add(i);
        }

        Log.d("DEBUG", "Cards size: " + cards.size());

        Collections.shuffle(cards); //Found this method online

        CardAdapter adapter = new CardAdapter(cards);
        recyclerView.setAdapter(adapter);
    }
}