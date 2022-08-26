package com.tictacx.toe;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import com.tictacx.toe.R;

public class SettingsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Button backMenu2 = findViewById(R.id.backMenu2);

        SharedPreferences sp = getSharedPreferences("game.ttt.bots", MODE_PRIVATE);

        SwitchCompat switchCompat = findViewById(R.id.switch_compat_timer);
        switchCompat.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = getSharedPreferences("game.ttt.bots", MODE_PRIVATE).edit();
                editor.putBoolean("service_status", switchCompat.isChecked());
                editor.apply();
            }
        });

    }

    public void onClickBack2(View view) {
        Intent intent;
        intent = new Intent(this, GameMenuActivity.class);
        startActivity(intent);
        super.finish();
    }

    public void onClickSound(View view) {
       Toast.makeText(this, "Sound on", Toast.LENGTH_SHORT).show();
    }

    public void onClickTimer(View view) {

        Toast.makeText(this, "Timer activated", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        Intent intent;
        intent = new Intent(this, GameMenuActivity.class);
        startActivity(intent);
        super.finish();
        // your code.
    }
}
