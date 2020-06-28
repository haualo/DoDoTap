package com.habloappstudio.dodotap;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.media.MediaPlayer;
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

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.Timer;
import java.util.TimerTask;

public class testingGame extends AppCompatActivity {

    //sound
    MediaPlayer tapScreen;
    MediaPlayer getPoint;
    MediaPlayer hitThree;
    MediaPlayer hitObj;


    private TextView scoreLabel;
    private TextView tapLabel;

    private ImageView thePlayer1;
    private ImageView thePlayer2fly;
    private ImageView thePlayer2fall;
    private ImageView thePlayer3;
    private ImageView spikeSmall;
    private ImageView bigThree;
    private ImageView mediumThree;
    private ImageView smallThree;
    private ImageView crystal;
    private ImageView cloud1;
    private ImageView cloud2;
    private ImageView cloud3;

    //bannerAd
    private AdView myAdView;

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
    private float crystalX, crystalY;
    private float cloud1X, cloud1Y;
    private float cloud2X, cloud2Y;
    private float cloud3X, cloud3Y;

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

        //bannerAD code
        myAdView = findViewById(R.id.banner1);
        AdRequest adRequest = new AdRequest.Builder().build();
        myAdView.loadAd(adRequest);


        //sound
        tapScreen = MediaPlayer.create(getBaseContext(), R.raw.sfx_wing);
        getPoint = MediaPlayer.create(this, R.raw.sfx_point);
        hitThree = MediaPlayer.create(this, R.raw.sfx_die);
        hitObj = MediaPlayer.create(this, R.raw.sfx_hit);


        scoreLabel = (TextView) findViewById(R.id.scoreText);
        tapLabel = (TextView) findViewById(R.id.tapStart);

        bigThree = (ImageView) findViewById(R.id.three1);
        mediumThree = (ImageView) findViewById(R.id.three2);
        smallThree = (ImageView) findViewById(R.id.three3);

        thePlayer1 = (ImageView) findViewById(R.id.player1);
        thePlayer2fly = (ImageView) findViewById(R.id.player2fly);
        thePlayer2fall = (ImageView) findViewById(R.id.player2fall);
        thePlayer3 = (ImageView) findViewById(R.id.player3);

        spikeSmall = (ImageView) findViewById(R.id.smallSpike);
        crystal = (ImageView) findViewById(R.id.crystalP);

        cloud1 = (ImageView) findViewById(R.id.cloud1);
        cloud2 = (ImageView) findViewById(R.id.cloud2);
        cloud3 = (ImageView) findViewById(R.id.cloud3);

        //Screen Size
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        Point size = new Point();

        screenWidth = size.x;
        screenHeight = size.y;

        //the score back if continue is pressed in gameOver screen/pop
        score = getIntent().getIntExtra("SCORE", 0);

        //move out of screen/start pos
        spikeSmallX = -250;
        bigThreeX = -250;
        bigThree.setY(bigThreeY);
        mediumThreeX = -250;
        mediumThree.setY(mediumThreeY);
        smallThreeX = -250;
        smallThree.setY(smallThreeY);
        cloud1.setX(-500);
        cloud2.setX(-500);
        cloud3.setX(-500);

        cloud1.setY(-500);
        cloud2.setY(-500);
        cloud3.setY(-500);

        crystalX = -250;
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
            spikeSmallX += 45;
            crystalX += 15;
            cloud1X += 5;
            cloud2X +=5;
            cloud3X +=5;
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


            crystalX -=15;
            if(crystalX < -200){
                crystalX = 1500;
                crystalY = (int) Math.floor(Math.random() * ((1270) - 300)) + 300;
            }
            crystal.setX(crystalX);
            crystal.setY(crystalY);

            spikeSmallX -= 45;
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
            thePlayer2fly.setY(thePlayerY);
            thePlayer2fall.setY(thePlayerY);
            thePlayer3.setY(thePlayerY);

            //clouds
            cloud1X -= 5;
            cloud2X -=5;
            cloud3X -=5;

            if(cloud1X < -380){
                cloud1X = 1200;
                cloud1Y = (int) Math.floor(Math.random() * ((250) - 1)) + 1;
            }
            if(cloud2X < -400){
                cloud2X = 1500;
                cloud2Y = (int) Math.floor(Math.random() * ((250) - 1)) + 1;
            }
            if(cloud3X < -420){
                cloud3X = 1800;
                cloud3Y = (int) Math.floor(Math.random() * ((250) - 1)) + 1;
            }
            cloud1.setX(cloud1X);
            cloud2.setX(cloud2X);
            cloud3.setX(cloud3X);

            cloud1.setY(cloud1Y);
            cloud2.setY(cloud2Y);
            cloud3.setY(cloud3Y);

        }


        scoreLabel.setText("Score: " + score);

        //Log.d("bigThreeY", " bigThreeY:  " +  bigThreeY);
        //Log.d("plauerY", " PlayerY:  " +  thePlayerY);
    }

    public void hitCheck(){

        //spike
        float spikeCenterX = spikeSmallX + spikeSmall.getWidth() / 2.0f;
        float spikeCenterY = spikeSmallY + spikeSmall.getHeight() / 2.0f;

        //spike
        if( 0 <= spikeCenterX && spikeCenterX <= playerSize && thePlayerY <= spikeCenterY && spikeCenterY <= thePlayerY+playerSize){
            soundEffects(3);
            gameOver();
        }

        //crystal
        float crystalCenterX = crystalX + crystal.getWidth() / 2.0f;
        float crystalCenterY = crystalY + crystal.getHeight() / 2.0f;

        if( 0 <= crystalCenterX && crystalCenterX <= playerSize && thePlayerY <= crystalCenterY && crystalCenterY <= thePlayerY+playerSize){
            crystalX = 1500;
            score +=10;
            soundEffects(2);
        }
        //big three
        if(thePlayerY > bigThreeY && bigThreeX <= thePlayerX+120 && thePlayerX < bigThreeX+100){
            soundEffects(3);
            gameOver();
        }

        //medium three
        if(thePlayerY > mediumThreeY && mediumThreeX <= thePlayerX+120 && thePlayerX < mediumThreeX+100){
            soundEffects(3);
            gameOver();
        }

        //small three
        if(thePlayerY > smallThreeY && smallThreeX <= thePlayerX+120 && thePlayerX < smallThreeX+100){
            soundEffects(3);
            gameOver();
        }

    }

    public void gameOver(){

        if(timer != null){
            timer.cancel();
            timer = null;
        }
        thePlayer1.setVisibility(View.GONE);
        thePlayer2fly.setVisibility(View.GONE);
        thePlayer2fall.setVisibility(View.GONE);
        thePlayer3.setVisibility(View.VISIBLE);
        stopMovements = true;
        Intent intent = new Intent(getApplicationContext(), testingGamePopUp.class);
        intent.putExtra("SCORE", score);
        startActivity(intent);
    }


    public void soundEffects(int x){


        switch (x){
            case 1: tapScreen.start();  break;
            case 2: getPoint.start(); break;
            case 3: hitObj.start(); break;
            default: tapScreen.release(); hitObj.release(); getPoint.release();
        }

    }

    public boolean onTouchEvent(MotionEvent event) {

        if(!start_flg){
            start_flg = true;
            thePlayer1.setVisibility(View.GONE);
            tapLabel.setVisibility(View.GONE);
            bigThree.setVisibility(View.VISIBLE);
            mediumThree.setVisibility(View.VISIBLE);
            smallThree.setVisibility(View.VISIBLE);
            crystal.setVisibility(View.VISIBLE);

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
            thePlayer2fly.setVisibility(View.VISIBLE);
            thePlayer1.setVisibility(View.GONE);
            soundEffects(1);

        }else if(event.getAction() == MotionEvent.ACTION_UP){
            action_flg = false;
            thePlayer2fly.setVisibility(View.GONE);
            thePlayer1.setVisibility(View.VISIBLE);
        }


        return true;
    }
}