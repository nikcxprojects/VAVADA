package com.tictacx.toe;

import android.annotation.SuppressLint;
import android.app.assist.AssistStructure;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tictacx.toe.R;

public class GameActivity extends AppCompatActivity
{
    boolean isGameActive = true;

    int playCounter = 0;
    int currentPlayer = 0;

    int[] gamePositions = {-1, -1, -1, -1, -1, -1, -1, -1, -1};
    int[][] winningPatterns = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};

    //фон доски
    int[] imagesBG = {R.drawable.bg,R.drawable.bge,R.drawable.bgg,R.drawable.bgr,R.drawable.bgv};

    int[] imagesPl1 = {R.drawable.o,R.drawable.o1,R.drawable.o2,R.drawable.o3};
    int[] imagesPl2 = {R.drawable.x,R.drawable.x1,R.drawable.x2,R.drawable.x3};

    private int currentImagesBack;
    private int currentImagesPlayer;

    public void playerTurn(View view)
    {
        ImageView currentTapped = (ImageView) view;

        int tapped = Integer.parseInt(currentTapped.getTag().toString());

        if(isGameActive && gamePositions[tapped] == -1 && currentPlayer == 0)
        {
            gamePositions[tapped] = currentPlayer;

            currentTapped.setImageResource(R.drawable.o);

            checkForWinner();
        }
    }

    public void botTurn()
    {
        new CountDownTimer(1000, 1000)
        {
            public void onFinish()
            {
                int tapped = 0;
                boolean found = false;

                while(!found)
                {
                    int random = (int) Math.floor(Math.random() * 9);

                    if(gamePositions[random] == -1)
                    {
                        tapped = random;
                        found = true;
                    }
                }

                int id = getResources().getIdentifier("imageView" + (tapped + 1), "id", getPackageName());

                ImageView currentTapped = (ImageView) findViewById(id);

                gamePositions[tapped] = currentPlayer;

                currentTapped.setImageResource(R.drawable.x);

                checkForWinner();
            }

            public void onTick(long millisUntilFinished)
            {
            }
        }.start();
    }

    public void checkForWinner()
    {
        TextView playerTurn = (TextView) findViewById(R.id.playerTurn);

        for(int[] i : winningPatterns)
        {
            if((gamePositions[i[0]] == gamePositions[i[1]] && gamePositions[i[1]] == gamePositions[i[2]]) && gamePositions[i[0]] != -1)
            {
                //TextView winnerText = (TextView) findViewById(R.id.winnerText);
                //ConstraintLayout winLayout = (ConstraintLayout) findViewById(R.id.winLayout);

                playerTurn.setText("Game ended");

                isGameActive = false;

                Button again = findViewById(R.id.playAgainButton);
                again.setVisibility(View.VISIBLE);

//                if(gamePositions[i[0]] == 0)
//                {
//                    winnerText.setText("Победа!!");
//                }
//                else
//                {
//                    winnerText.setText("Тебя слили...");
//                }
//
//                winLayout.setVisibility(View.VISIBLE);
//                winLayout.setAlpha(0f);
//                winLayout.animate().setDuration(800).alpha(1f);
            }
        }

        if(isGameActive)
        {
            if(++playCounter == 9)
            {
                //TextView winnerText = (TextView) findViewById(R.id.winnerText);
                //ConstraintLayout winLayout = (ConstraintLayout) findViewById(R.id.winLayout);

                playerTurn.setText("Game ended");

                isGameActive = false;

                Button again = findViewById(R.id.playAgainButton);
                again.setVisibility(View.VISIBLE);

                //winnerText.setText("Ничья");

//                winLayout.setVisibility(View.VISIBLE);
//                winLayout.setAlpha(0f);
//                winLayout.animate().setDuration(500).alpha(1f);
            }
            else
            {
                if(currentPlayer == 0)
                {
                    currentPlayer = 1;


                    playerTurn.setText("Bot move");

                    botTurn();
                }
                else
                {
                    currentPlayer = 0;

                    playerTurn.setText("Your turn");
                }
            }
        }
    }

    public void playAgain(View view)
    {
        //ConstraintLayout winLayout = (ConstraintLayout) findViewById(R.id.winLayout);
        GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayout);

        //winLayout.setVisibility(View.INVISIBLE);
        TextView playerTurn = (TextView) findViewById(R.id.playerTurn);

        playerTurn.setText("Your turn");

        isGameActive = true;
        currentPlayer = 0;
        playCounter = 0;

        for(int i = 0; i < gamePositions.length; i++)
        {
            gamePositions[i] = -1;
        }

        for(int i = 0; i < gridLayout.getChildCount(); i++)
        {
            ((ImageView) gridLayout.getChildAt(i)).setImageResource(0);
        }


        Button again = findViewById(R.id.playAgainButton);
        again.setVisibility(View.GONE);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        TextView timers = findViewById(R.id.timer_visible);
        GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayout);
        SharedPreferences sp = getSharedPreferences("game.ttt.bots", MODE_PRIVATE);
        // проверяем, первый ли раз открывается программа
        boolean timer = sp.getBoolean("service_status", false);

        Button backgroundChange = findViewById(R.id.background_change);
        backgroundChange.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        currentImagesBack++;
                        currentImagesBack=currentImagesBack % imagesBG.length;
                        gridLayout.setBackgroundResource(imagesBG[currentImagesBack]);
                    }
                }
        );


//        ImageView image_View_1 = findViewById(R.id.imageView1);
//        ImageView image_View_2 = findViewById(R.id.imageView2);
//        ImageView image_View_3 = findViewById(R.id.imageView3);
//        ImageView image_View_4 = findViewById(R.id.imageView4);
//        ImageView image_View_5 = findViewById(R.id.imageView5);
//        ImageView image_View_6 = findViewById(R.id.imageView6);
//        ImageView image_View_7 = findViewById(R.id.imageView7);
//        ImageView image_View_8 = findViewById(R.id.imageView8);
//        ImageView image_View_9 = findViewById(R.id.imageView9);
//
//        Button playerChange = findViewById(R.id.players_change);
//        playerChange.setOnClickListener(
//                new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        currentImagesPlayer++;
//                        currentImagesPlayer=currentImagesPlayer % imagesPl1.length;
//                        image_View_1.setImageResource(imagesPl1[currentImagesPlayer]);
//                        image_View_2.setImageResource(imagesPl1[currentImagesPlayer]);
//                        image_View_3.setImageResource(imagesPl1[currentImagesPlayer]);
//                        image_View_4.setImageResource(imagesPl1[currentImagesPlayer]);
//                        image_View_5.setImageResource(imagesPl1[currentImagesPlayer]);
//                        image_View_6.setImageResource(imagesPl1[currentImagesPlayer]);
//                        image_View_7.setImageResource(imagesPl1[currentImagesPlayer]);
//                        image_View_8.setImageResource(imagesPl1[currentImagesPlayer]);
//                        image_View_9.setImageResource(imagesPl1[currentImagesPlayer]);
//
//                        currentImagesPlayer=currentImagesPlayer % imagesPl2.length;
//                        image_View_1.setImageResource(imagesPl2[currentImagesPlayer]);
//                        image_View_2.setImageResource(imagesPl2[currentImagesPlayer]);
//                        image_View_3.setImageResource(imagesPl2[currentImagesPlayer]);
//                        image_View_4.setImageResource(imagesPl2[currentImagesPlayer]);
//                        image_View_5.setImageResource(imagesPl2[currentImagesPlayer]);
//                        image_View_6.setImageResource(imagesPl2[currentImagesPlayer]);
//                        image_View_7.setImageResource(imagesPl2[currentImagesPlayer]);
//                        image_View_8.setImageResource(imagesPl2[currentImagesPlayer]);
//                        image_View_9.setImageResource(imagesPl2[currentImagesPlayer]);
//                    }
//                }
//        );

        if (timer){
            Toast.makeText(this, "Timer activated", Toast.LENGTH_SHORT).show();
            timers.setVisibility(View.VISIBLE);
            long seconds = Long.parseLong(timers.getText().toString());
            CountDownTimer my_timer = new CountDownTimer(seconds*1000,1000) {
                @SuppressLint("SetTextI18n")
                @Override
                public void onTick(long millisUntilFinished) {
                    timers.setText(Long.toString(millisUntilFinished/1000));
                }

                @Override
                public void onFinish() {
                    timers.setText("Time is over!");
                }
            };
            my_timer.start();
        } else {
            Toast.makeText(this, "Timer NOT activated", Toast.LENGTH_SHORT).show();
            timers.setVisibility(View.GONE);
        }


//        SharedPreferences.Editor editor = getSharedPreferences("game.ttt.bots", MODE_PRIVATE).edit();
//        editor.putBoolean("service_status", false);
//        editor.apply();

    }

}
