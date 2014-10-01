package com.example.serviceexample;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class PlayerService extends Service{
    public static final String EXTRA_PLAYLIST="EXTRA_PLAYLIST";
    public static final String EXTRA_SHUFFLE="EXTRA_SHUFFLE";
    private boolean isPlaying=false;
    String TAG="PlayerService" ;
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String playlist=intent.getStringExtra(EXTRA_PLAYLIST);
        boolean useShuffle=intent.getBooleanExtra(EXTRA_SHUFFLE, false);

        play(playlist, useShuffle);     

        return(START_NOT_STICKY);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stop();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return(null);
    }
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG,"onCreate");
    }

    private void play(String playlist, boolean useShuffle) {
        if (!isPlaying) {
            Log.w(getClass().getName(), "Got to play()!");
            isPlaying=true;

            Notification note=new Notification(R.drawable.ic_launcher,
                    "Can you hear the music?",
                    System.currentTimeMillis());
            Intent i=new Intent(this, Main.class);

            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|
                    Intent.FLAG_ACTIVITY_SINGLE_TOP);

            PendingIntent pi=PendingIntent.getActivity(this, 0,
                    i, 0);

            note.setLatestEventInfo(this, "Fake Player",
                    "Now Playing: \"Ummmm, Nothing\"",
                    pi);
            note.flags|=Notification.FLAG_NO_CLEAR;

            startForeground(1337, note);
        }
    }

    private void stop() {
        if (isPlaying) {
            Log.w(getClass().getName(), "Got to stop()!");
            isPlaying=false;
            stopForeground(true);
        }
    }

}
