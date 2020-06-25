package com.habloappstudio.dodotap;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class testingGame extends AppCompatActivity {

    private TextView scoreLabel;

    private ImageView thePlayer1;
    private ImageView thePlayer2;
    private ImageView spikeSmall;
    private ImageView spikeMedium;

    //Size
    private int screenHeight, screenWidth;
    private int frameHeight;
    private int playerSize;

    //position
    private float thePlayerY;
    private float spikeSmallX, spikeSmallY;

    //Timer
    private Timer timer = new Timer();
    private Handler handler = new Handler();

    //status
    private boolean action_flg = false;
    private boolean start_flg = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing_game);

        scoreLabel = (TextView) findViewById(R.id.scoreText);

        thePlayer1 = (ImageView) findViewById(R.id.player1);
        thePlayer2 = (ImageView) findViewById(R.id.player2);

        spikeSmall = (ImageView) findViewById(R.id.smallSpike);
        spikeMedium = (ImageView) findViewById(R.id.mediumSpike);

        //Screen Size
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        Point size = new Point();

        screenWidth = size.x;
        screenHeight = size.y;


        //move out of screen
        spikeSmall.setX(-100.0f);
        spikeSmall.setY(-500.0f);
        spikeMedium.setX(-300.0f);
        spikeMedium.setY(-300.0f);

    }


    public void changePos(){

        spikeSmallX -= 12;
        if(spikeSmallX < -200){
            spikeSmallX = 1200;
            spikeSmallY = (int) Math.floor(Math.random() * ((1270) - 300)) + 300;
        }
        spikeSmall.setX(spikeSmallX);
        spikeSmall.setY(spikeSmallY);

        thePlayerY +=11;

        if(thePlayerY < 300){
            thePlayerY=300;
        }
        if(thePlayerY > 1270){
            thePlayerY = 1270;
        }
        thePlayer1.setY(thePlayerY);
        thePlayer2.setY(thePlayerY);
        Log.d("playerY", " thePlayer.getHeight():  " +  spikeSmallX);
    }

    public boolean onTouchEvent(MotionEvent event) {

        if(!start_flg){
            start_flg = true;

            //FrameHeight
            FrameLayout frameLayout = findViewById(R.id.frame);
            frameHeight = frameLayout.getHeight();

            //player
            thePlayerY = thePlayer1.getY();
            playerSize = thePlayer1.getHeight();

            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            changePos();
                        }
                    });
                }
            },0 , 30);
        }

        if(event.getAction() == MotionEvent.ACTION_DOWN){
            thePlayerY -= 100;
            action_flg = true;
            thePlayer1.setVisibility(View.INVISIBLE);
            thePlayer2.setVisibility(View.VISIBLE);
        }else{

            action_flg = false;
            thePlayer1.setVisibility(View.VISIBLE);
            thePlayer2.setVisibility(View.INVISIBLE);
        }

        return true;
    }
}