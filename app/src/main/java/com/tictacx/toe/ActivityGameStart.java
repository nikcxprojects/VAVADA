package com.tictacx.toe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


import androidx.appcompat.app.AppCompatActivity;

import com.tictacx.toe.R;

public class ActivityGameStart extends AppCompatActivity {

    public boolean flag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_start);

    }

    public void onClickGameBot1(View view) {
        Intent intent;
        intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }

    public void onClickGameBot2(View view) {
        flag = true;
        Intent intent;
        intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }
}