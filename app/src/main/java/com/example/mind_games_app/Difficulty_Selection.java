package com.example.mind_games_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Difficulty_Selection extends AppCompatActivity {

    Button easyButton;
    Button medButton;
    Button hardButton;

    String difficulty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_difficulty_selection);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        easyButton = findViewById(R.id.easyButton);
        medButton = findViewById(R.id.medButton);
        hardButton = findViewById(R.id.hardButton);

        easyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                difficulty = "easy";
                Intent i = new Intent(Difficulty_Selection.this, Memory_Match.class);
                i.putExtra("difficulty", difficulty);
                startActivity(i);
            }
        });

        medButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                difficulty = "medium";
                Intent i = new Intent(Difficulty_Selection.this, Memory_Match.class);
                i.putExtra("difficulty", difficulty);
                startActivity(i);
            }
        });

        hardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                difficulty = "hard";
                Intent i = new Intent(Difficulty_Selection.this, Memory_Match.class);
                i.putExtra("difficulty", difficulty);
                startActivity(i);
            }
        });
    }
}