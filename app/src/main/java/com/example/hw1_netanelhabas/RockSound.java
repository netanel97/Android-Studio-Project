package com.example.hw1_netanelhabas;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.AsyncTask;

public class RockSound extends AsyncTask<Void, Void, Void> {
    private Context context;
    public RockSound(Context context) {this.context=context;}
    @Override
    protected Void doInBackground(Void... params) {
        MediaPlayer player = MediaPlayer.create(context, R.raw.stone_sound);
        player.setVolume(1.0f, 1.0f);
        player.start();
        return null;
    }
}
