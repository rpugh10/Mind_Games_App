package com.example.mind_games_app;

import static com.example.mind_games_app.MenuScreen.CHANNEL_ID;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;

public class AmbientSoundService extends Service {
    MediaPlayer myPlayer;
    public static boolean isRunning = false;
    public AmbientSoundService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (isRunning == false) {
            isRunning = true;

            Intent notifIntent = new Intent(this, SplashScreen.class);

            PendingIntent pendingIntent = PendingIntent.getActivity(this,10,notifIntent, PendingIntent.FLAG_IMMUTABLE);

            Notification notif = new NotificationCompat.Builder(this,CHANNEL_ID)
                    .setContentTitle("Background Music")
                    .setContentText("Playing background music")
                    .setSmallIcon(android.R.drawable.ic_media_play)
                    .setContentIntent(pendingIntent)
                    .setOngoing(true)
                    .build();

            startForeground(1,notif);

            myPlayer = MediaPlayer.create(this,R.raw.background_music);
            myPlayer.start();

            return START_STICKY;
        }
        else {
            stopSelf();
            return START_NOT_STICKY;
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        myPlayer.stop();
        myPlayer.release();

        isRunning = false;
    }

}
