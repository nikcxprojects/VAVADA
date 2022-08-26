package com.tictacx.toe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.tictacx.toe.R;


public class AboutActivity extends AppCompatActivity  {

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        Button backMenu = findViewById(R.id.backMenu);


    }

    public void onClickBack(View view) {
        Intent intent;
        intent = new Intent(this, GameMenuActivity.class);
        startActivity(intent);
        super.finish();
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
