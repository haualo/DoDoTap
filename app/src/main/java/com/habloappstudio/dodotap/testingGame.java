package com.habloappstudio.dodotap;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
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
    private TextView tapLabel;

    private ImageView thePlayer1;
    private ImageView thePlayer2;
    private ImageView spikeSmall;
    private ImageView bigThree;
    private ImageView mediumThree;
    private ImageView smallThree;

    //Size
    private int screenHeight, screenWidth;
    private int frameHeight;
    private int playerSize;

    //position
    private float thePlayerX=10,thePlayerY;
    private float spikeSmallX, spikeSmallY;
    private float bigThreeX,bigThreeY=650;
    private float mediumThreeX,mediumThreeY=800;
    private float smallThreeX,smallThreeY=900;

    //score
    private int score;

    //Timer
    private Timer timer = new Timer();
    private Handler handler = new Handler();

    //status
    private boolean action_flg = false;
    private boolean start_flg = false;

    private boolean stopMovements = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing_game);

        scoreLabel = (TextView) findViewById(R.id.scoreText);
        tapLabel = (TextView) findViewById(R.id.tapStart);

        thePlayer1 = (ImageView) findViewById(R.id.player1);
        thePlayer2 = (ImageView) findViewById(R.id.player2);

        spikeSmall = (ImageView) findViewById(R.id.smallSpike);
        bigThree = (ImageView) findViewById(R.id.three1);
        mediumThree = (ImageView) findViewById(R.id.three2);
        smallThree = (ImageView) findViewById(R.id.three3);

        //Screen Size
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        Point size = new Point();

        screenWidth = size.x;
        screenHeight = size.y;


        //move out of screen/start pos
        spikeSmallX = -250;
        bigThreeX = -250;
        bigThree.setY(bigThreeY);
        mediumThreeX = -250;
        mediumThree.setY(mediumThreeY);
        smallThreeX = -250;
        smallThree.setY(smallThreeY);

        thePlayer1.setX(thePlayerX);
        scoreLabel.setText("Score: " + score);
    }


    public void changePos(){

        hitCheck();

        if(stopMovements){
            thePlayerY -= 11;
            smallThreeX += 12;
            mediumThreeX += 12;
            bigThreeX += 12;
            spikeSmallX += 30;
        }else{
            bigThreeX -= 12;
            if(bigThreeX < -300 ){
                bigThreeX = 1400;
                bigThreeY = (int) Math.floor(Math.random() * (1000 - 650)) + 650;
            }
            bigThree.setX(bigThreeX);
            bigThree.setY(bigThreeY);

            mediumThreeX -= 12;
            if(mediumThreeX < -300 ){
                mediumThreeX = 1900;
                mediumThreeY = (int) Math.floor(Math.random() * (950 - 800)) + 800;
            }
            mediumThree.setX(mediumThreeX);
            mediumThree.setY(mediumThreeY);

            smallThreeX -= 12;
            if(smallThreeX < -300 ){
                smallThreeX = 2400;
                smallThreeY = (int) Math.floor(Math.random() * (900 - 1000)) + 1000;
            }
            smallThree.setX(smallThreeX);
            smallThree.setY(smallThreeY);



            spikeSmallX -= 30;
            if(spikeSmallX < -200){
                spikeSmallX = 1200;
                spikeSmallY = (int) Math.floor(Math.random() * ((1270) - 300)) + 300;
            }
            spikeSmall.setX(spikeSmallX);
            spikeSmall.setY(spikeSmallY);

            thePlayerY +=11;

            if(thePlayerY < 300){
                thePlayerY=300;
                thePlayerY +=30;
            }
            if(thePlayerY > 1270){
                thePlayerY = 1270;
            }
            thePlayer1.setY(thePlayerY);
            thePlayer2.setY(thePlayerY);

        }


        scoreLabel.setText("Score: " + score);

        //Log.d("bigThreeY", " bigThreeY:  " +  bigThreeY);
        //Log.d("bigThreeY", " thePlayerY:  " +  thePlayerY);
    }

    public void hitCheck(){

        //SpikeCenter
        float spikeCenterX = spikeSmallX + spikeSmall.getWidth() / 2.0f;
        float spikeCenterY = spikeSmallY + spikeSmall.getHeight() / 2.0f;

        //spike
        if( 0 <= spikeCenterX && spikeCenterX <= playerSize && thePlayerY <= spikeCenterY && spikeCenterY <= thePlayerY+playerSize){
            stopMovements = true;
            gameOver();
        }

        //big three
        if(thePlayerY > bigThreeY && bigThreeX <= thePlayerX+100 && thePlayerX < bigThreeX+100){
            stopMovements = true;
            gameOver();
        }

        //medium three
        if(thePlayerY > mediumThreeY && mediumThreeX <= thePlayerX+100 && thePlayerX < mediumThreeX+100){
            stopMovements = true;
            gameOver();
        }

        //small three
        if(thePlayerY > smallThreeY && smallThreeX <= thePlayerX+100 && thePlayerX < smallThreeX+100){
            stopMovements = true;
            gameOver();
        }

    }

    public void gameOver(){
        startActivity(new Intent(testingGame.this, testingGamePopUp.class));
    }


    public boolean onTouchEvent(MotionEvent event) {

        if(!start_flg){
            start_flg = true;
            tapLabel.setVisibility(View.GONE);
            bigThree.setVisibility(View.VISIBLE);
            mediumThree.setVisibility(View.VISIBLE);
            smallThree.setVisibility(View.VISIBLE);

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