package com.example.mind_games_app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class GameOverReceiver extends BroadcastReceiver {

    public static final String ACTION_GAME_OVER = "com.brainzone.app.GAME_OVER";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (ACTION_GAME_OVER.equals(intent.getAction())) {
            String gameName  = intent.getStringExtra("GAME_NAME");
            int    score     = intent.getIntExtra("SCORE", 0);
            String difficulty = intent.getStringExtra("DIFFICULTY");

            Log.d("GameOverReceiver", gameName + " ended | Score: " + score + " | " + difficulty);

            // Teammates' receivers will also fire from this same broadcast
            // No extra logic needed on your end
        }
    }
}
