package com.example.mygame;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MyMainActivity extends AppCompatActivity {

    MyGameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameView = new MyGameView(this);
        setContentView(gameView);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    // This method executes when the player quits the game
    @Override
    protected void onPause() {
        super.onPause();

        // Tell the gameView pause method to execute
        gameView.pause();
    }
}
