package com.example.mind_games_app;

import static com.example.mind_games_app.MenuScreen.CHANNEL_ID;

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

public class SettingsScreen extends AppCompatActivity {

    Button backgroundMusic,soundEffects,difficultyButton,reset,exit;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_settings_screen);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        backgroundMusic = findViewById(R.id.backgroundMusicButton);
        soundEffects = findViewById(R.id.soundEffectsButton);
        difficultyButton = findViewById(R.id.difficultyButton);
        reset = findViewById(R.id.resetDataButton);
        exit = findViewById(R.id.menuButton);

        backgroundMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                createNotificationChannel();

                Intent MusicIntent = new Intent(SettingsScreen.this,AmbientSoundService.class);
                startService(MusicIntent);

            }
        });

        soundEffects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        difficultyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent menuIntent = new Intent(SettingsScreen.this,MenuScreen.class);
                startActivity(menuIntent);
            }
        });

    }

    // notification for music service
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createNotificationChannel() {
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID,"Music Playing", NotificationManager.IMPORTANCE_DEFAULT);
        channel.setDescription("Shows when foreground music service is playing");
        NotificationManager manager = getSystemService(NotificationManager.class);
        manager.createNotificationChannel(channel);
    }

}
