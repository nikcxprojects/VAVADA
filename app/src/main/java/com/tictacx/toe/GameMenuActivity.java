package com.tictacx.toe;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.tictacx.toe.R;

import java.sql.Time;

public class GameMenuActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_menu);

        Button start = findViewById(R.id.startgame);
        Button settings = findViewById(R.id.settings);
        Button about = findViewById(R.id.about);
        Button quit = findViewById(R.id.quit);

    }


    public void onClickStartGame(View view) {
        Intent intent = new Intent(this, ActivityGameStart.class);
        startActivity(intent);


    }

    public void onClickSettings(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
        super.finish();
    }

    public void onClickAbout(View view) {
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
        super.finish();
    }

    public void onClickQuit(View view){
        finish();

    }
}
