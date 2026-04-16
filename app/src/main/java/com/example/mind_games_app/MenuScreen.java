package com.example.mind_games_app;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MenuScreen extends AppCompatActivity {

    Button multiplication,memory,word,leaderboard,settings;

    // string for music service
    public static final String CHANNEL_ID = "MusicServiceChannel";

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu_screen);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // ATTRIBUTION FOR MUSIC (put somewhere later):
        // Music by <a href="https://pixabay.com/users/soulfuljamtracks-46363515/?utm_source=link-attribution&utm_medium=referral&utm_campaign=music&utm_content=512494">SOULFULJAMTRACKS</a> from <a href="https://pixabay.com/music//?utm_source=link-attribution&utm_medium=referral&utm_campaign=music&utm_content=512494">Pixabay</a>

        multiplication = findViewById(R.id.multiplicationPuzzleButton);
        memory = findViewById(R.id.memoryMatchButton);
        word = findViewById(R.id.wordScrambleButton);
        leaderboard = findViewById(R.id.leaderboardButton);
        settings = findViewById(R.id.settingsButton);

        multiplication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent multIntent = new Intent(MenuScreen.this, MultiplicationPuzzle.class);
                startActivity(multIntent);
            }
        });

        memory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        word.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        leaderboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent settingsIntent = new Intent(MenuScreen.this,SettingsScreen.class);
                startActivity(settingsIntent);

            }
        });

    }


}