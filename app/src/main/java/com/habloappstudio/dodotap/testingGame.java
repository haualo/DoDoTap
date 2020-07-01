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
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.Timer;
import java.util.TimerTask;

public class testingGame extends AppCompatActivity {

    //sound
    MediaPlayer flapSound;
    MediaPlayer pointSound;
    MediaPlayer hitSound;
    MediaPlayer crystalSound;
    MediaPlayer swooshSound;




    private TextView scoreLabel;
    private TextView tapLabel;

    private ImageView theSun;
    private ImageView theMoon;
    private ImageView soundOff;
    private ImageView soundOn;
    private ImageView thePlayer1;
    private ImageView thePlayer2fly;
    private ImageView thePlayer2fall;
    private ImageView thePlayer3;
    private ImageView spikeSmall;
    private ImageView crystal;
    private ImageView cloud1;
    private ImageView cloud2;
    private ImageView cloud3;
    private ImageView threeUpAndDown;
    private ImageView threeUpAndDown2;
    private ImageView threeUpAndDown3;
    private ImageView threeUpAndDown4;


    //bannerAd
    private AdView myAdView;

    //Size
    private int screenHeight, screenWidth;
    private int frameHeight;
    private int playerSize;

    //position
    private float thePlayerX=10,thePlayerY;
    private float spikeSmallX, spikeSmallY;
    private float crystalX, crystalY;
    private float cloud1X, cloud1Y;
    private float cloud2X, cloud2Y;
    private float cloud3X, cloud3Y;
    private float threeUpAndDownX, threeUpAndDownY = -700;
    private float threeUpAndDownX2, threeUpAndDownY2 = -700;
    private float threeUpAndDownX3, threeUpAndDownY3 = -700;
    private float threeUpAndDownX4, threeUpAndDownY4 = -700;
    private float theSunMoonX, theSunMoonY;
    private float myTimer;

    //score
    private int score;

    //Speed
    private int playerSpeedUp, playerSpeedDown, threeSpeed, spikeSpeed, crystalSpeed;


    //Timer
    private Timer timer = new Timer();
    private Handler handler = new Handler();

    //status
    private boolean action_flg = false;
    private boolean start_flg = false;
    private boolean stopMovements = false;
    private boolean soundOffPressed = false;
    private boolean soundOnPressed = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing_game);

        //bannerAD code
        myAdView = findViewById(R.id.banner1);
        AdRequest adRequest = new AdRequest.Builder().build();
        myAdView.loadAd(adRequest);



        //sound
        flapSound = MediaPlayer.create(this, R.raw.sfx_wing);
        pointSound = MediaPlayer.create(this, R.raw.sfx_point);
        hitSound = MediaPlayer.create(this, R.raw.sfx_hit);
        crystalSound = MediaPlayer.create(this, R.raw.sfx_crystal);
        swooshSound = MediaPlayer.create(this, R.raw.sfx_swooshing);

        scoreLabel = (TextView) findViewById(R.id.scoreText);
        tapLabel = (TextView) findViewById(R.id.tapStart);


        theSun = (ImageView) findViewById(R.id.theSun);
        theMoon = (ImageView) findViewById(R.id.theMoon);

        soundOff = (ImageView) findViewById(R.id.soundOffButton);
        soundOn = (ImageView) findViewById(R.id.soundOnButton);

        threeUpAndDown = (ImageView) findViewById(R.id.threeupanddown);
        threeUpAndDown2 = (ImageView) findViewById(R.id.threeupanddown2);
        threeUpAndDown3 = (ImageView) findViewById(R.id.threeupanddown3);
        threeUpAndDown4 = (ImageView) findViewById(R.id.threeupanddown4);


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
        display.getSize(size);

        screenWidth = size.x;
        screenHeight = size.y;

        //Speed

        playerSpeedUp = Math.round(screenHeight / 20.4f);
        playerSpeedDown = Math.round(screenHeight / 170.0f);

        crystalSpeed = Math.round(screenWidth  / 54.0f);
        spikeSpeed = Math.round(screenWidth  / 54.0f);
        threeSpeed = Math.round(screenWidth / 108.0f);


        //the score back if continue is pressed in gameOver screen/pop
        score = getIntent().getIntExtra("SCORE", 0);

        //move out of screen/start pos
        theSunMoonX=screenWidth;
        theSunMoonY = screenHeight / 5.0f;


        cloud1X = -500;
        cloud2X = -700;
        cloud3X = -800;

        cloud1.setY(-500);
        cloud2.setY(-500);
        cloud3.setY(-500);

        crystalX = -600;
        thePlayer1.setX(thePlayerX);
        thePlayer3.setX(thePlayerX);

        threeUpAndDownX = screenWidth - 3000;
        threeUpAndDown.setY(threeUpAndDownY);

        threeUpAndDownX2 = threeUpAndDownX;
        threeUpAndDown2.setY(threeUpAndDownY2);

        threeUpAndDownX3 = threeUpAndDownX;
        threeUpAndDown3.setY(threeUpAndDownY3);

        threeUpAndDownX4 = threeUpAndDownX;
        threeUpAndDown4.setY(threeUpAndDownY4);

        scoreLabel.setText("Score: " + score);

    }

    public void nighAndDay(){


        FrameLayout nightMode = findViewById(R.id.nightMode);
        FrameLayout dayMode = findViewById(R.id.dayMode);

        boolean timeDay = false;
        boolean timeNight = false;

        theSunMoonX -=0.3f;

        if(theSunMoonX > (screenWidth/2.0f)){
            theSunMoonY -=0.9f;
            if(theSunMoonY<=0){
                theSunMoonY =0;
            }
        }

        if(theSunMoonX < (screenWidth/2.0f)-400){
            theSunMoonY +=0.9f;
        }

        if(theSunMoonX < -200){
            theSunMoonX = screenWidth;
        }

        myTimer++;
        if(myTimer > 4500){
            theMoon.setVisibility(View.VISIBLE);
            theSun.setVisibility(View.GONE);
            dayMode.setVisibility(View.GONE);
            nightMode.setVisibility(View.VISIBLE);
        }
        if(myTimer > 9000){
            theMoon.setVisibility(View.GONE);
            theSun.setVisibility(View.VISIBLE);
            dayMode.setVisibility(View.VISIBLE);
            nightMode.setVisibility(View.GONE);
            myTimer =0;
        }



        theSun.setX(theSunMoonX);
        theSun.setY(theSunMoonY);
        theMoon.setX(theSunMoonX);
        theMoon.setY(theSunMoonY);


        Log.d("myTimer", "myTimer: " + myTimer);

    }

    public void soundEffects(int x){

        if(!soundOffPressed || soundOnPressed){
            switch (x){
                case 1: flapSound.start();  break;
                case 2: pointSound.start(); break;
                case 3: hitSound.start(); break;
                case 4: swooshSound.start(); break;
                case 5: crystalSound.start(); break;
            }
        }

        if(soundOffPressed ){
            if(flapSound.isPlaying()){
                flapSound.stop(); pointSound.stop(); hitSound.stop(); swooshSound.stop(); crystalSound.stop();
                flapSound.reset(); pointSound.reset(); hitSound.reset(); swooshSound.reset(); crystalSound.reset();
                flapSound.release(); pointSound.release(); hitSound.release(); swooshSound.release(); crystalSound.release();
            }

        }


    }

    public void changePos(){

        hitCheck();
        hitThreeBig();
        nighAndDay();

        if(stopMovements){

            thePlayerY -= 11;
            spikeSmallX += 45;
            crystalX += 15;
            cloud1X += 5;
            cloud2X +=5;
            cloud3X +=5;
            threeUpAndDownX +=10;
            threeUpAndDownX2 +=10;

        }else{

            //the upNdown threes
            threeUpAndDownX -=threeSpeed;
            if(threeUpAndDownX < -500){
                threeUpAndDownX = screenWidth + 500;
                threeUpAndDownY = (int) Math.floor(Math.random() * (-150 + 450)) - 450;
            }
            threeUpAndDown.setX(threeUpAndDownX);
            threeUpAndDown.setY(threeUpAndDownY);

            threeUpAndDownX2 -=threeSpeed;
            if(threeUpAndDownX2 < -500){
                threeUpAndDownX2 = threeUpAndDownX + 500;
                threeUpAndDownY2 = (int) Math.floor(Math.random() * (-150 + 450)) - 450;
            }
            threeUpAndDown2.setX(threeUpAndDownX2);
            threeUpAndDown2.setY(threeUpAndDownY2);


            threeUpAndDownX3 -=threeSpeed;
            if(threeUpAndDownX3 < -500){
                threeUpAndDownX3 = threeUpAndDownX2 + 500;
                threeUpAndDownY3 = (int) Math.floor(Math.random() * (-150 + 450)) - 450;
            }
            threeUpAndDown3.setX(threeUpAndDownX3);
            threeUpAndDown3.setY(threeUpAndDownY3);

            threeUpAndDownX4 -=threeSpeed;
            if(threeUpAndDownX4 < -500){
                threeUpAndDownX4 = threeUpAndDownX3 + 500;
                threeUpAndDownY4 = (int) Math.floor(Math.random() * (-150 + 450)) - 450;
            }
            threeUpAndDown4.setX(threeUpAndDownX4);
            threeUpAndDown4.setY(threeUpAndDownY4);


            crystalX -=crystalSpeed;
            if(crystalX < -200){
                crystalX = 1500;
                crystalY = (int) Math.floor(Math.random() * ((1270) - 300)) + 300;
            }
            crystal.setX(crystalX);
            crystal.setY(crystalY);

            spikeSmallX -= spikeSpeed;
            if(spikeSmallX < -200){
                spikeSmallX = 1200;
                spikeSmallY = (int) Math.floor(Math.random() * ((1270) - 300)) + 300;
            }
            spikeSmall.setX(spikeSmallX);
            spikeSmall.setY(spikeSmallY);

            if(action_flg){
                thePlayerY -=playerSpeedUp;
                action_flg = false;
            }
            thePlayerY +=playerSpeedDown;


            if(thePlayerY <= 300){
                thePlayerY=300;
            }
            if(thePlayerY >= 1270){
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
                cloud2X = cloud1X + 500;
                cloud2Y = (int) Math.floor(Math.random() * ((250) - 1)) + 1;
            }
            if(cloud3X < -420){
                cloud3X = cloud2X + 500;
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

    public void hitThreeBig(){

        //three upp and down
        float threeUpDownCenterX = threeUpAndDownX + threeUpAndDown.getWidth() / 2.0f;
        float threeUpDownCenterY = threeUpAndDownY + threeUpAndDown.getHeight() / 2.0f;
        if( (0 <= threeUpDownCenterX && threeUpDownCenterX <= playerSize+100 && (thePlayerY-50) > (threeUpDownCenterY))
                || (0 <= threeUpDownCenterX && threeUpDownCenterX <= playerSize+100 && (thePlayerY+50) < (threeUpDownCenterY-150)) ){
            soundEffects(3);
            gameOver();


        }else if(0 <= threeUpDownCenterX && threeUpDownCenterX+80 <= playerSize &&thePlayerY <= threeUpDownCenterY+250 && thePlayerY >= threeUpDownCenterY-250 ){
            soundEffects(2);
            score +=10;

        }

        //three 2
        float threeUpDownCenterX2 = threeUpAndDownX2 + threeUpAndDown2.getWidth() / 2.0f;
        float threeUpDownCenterY2 = threeUpAndDownY2 + threeUpAndDown2.getHeight() / 2.0f;
        if( (0 <= threeUpDownCenterX2 && threeUpDownCenterX2 <= playerSize+100 && (thePlayerY-50) > (threeUpDownCenterY2))
                || (0 <= threeUpDownCenterX2 && threeUpDownCenterX2 <= playerSize+100 && (thePlayerY+50) < (threeUpDownCenterY2-150)) ){
            soundEffects(3);
            gameOver();


        }else if(0 <= threeUpDownCenterX2 && threeUpDownCenterX2+80 <= playerSize &&thePlayerY <= threeUpDownCenterY2+250 && thePlayerY >= threeUpDownCenterY2-250 ){
            soundEffects(2);
            score +=10;

        }

        //three3
        float threeUpDownCenterX3 = threeUpAndDownX3 + threeUpAndDown3.getWidth() / 2.0f;
        float threeUpDownCenterY3 = threeUpAndDownY3 + threeUpAndDown3.getHeight() / 2.0f;
        if( (0 <= threeUpDownCenterX3 && threeUpDownCenterX3 <= playerSize+100 && (thePlayerY-50) > (threeUpDownCenterY3))
                || (0 <= threeUpDownCenterX3 && threeUpDownCenterX3 <= playerSize+100 && (thePlayerY+50) < (threeUpDownCenterY3-150)) ){
            soundEffects(3);
            gameOver();

        }else if(0 <= threeUpDownCenterX3 && threeUpDownCenterX3+80 <= playerSize &&thePlayerY <= threeUpDownCenterY3+250 && thePlayerY >= threeUpDownCenterY3-250 ){
            soundEffects(2);
            score +=10;

        }

        //three4
        float threeUpDownCenterX4 = threeUpAndDownX4 + threeUpAndDown4.getWidth() / 2.0f;
        float threeUpDownCenterY4 = threeUpAndDownY4 + threeUpAndDown4.getHeight() / 2.0f;
        if( (0 <= threeUpDownCenterX4 && threeUpDownCenterX4 <= playerSize+100 && (thePlayerY-50) > (threeUpDownCenterY4))
                || (0 <= threeUpDownCenterX4 && threeUpDownCenterX4 <= playerSize+100 && (thePlayerY+50) < (threeUpDownCenterY4-150)) ){
            soundEffects(3);
            gameOver();

        }else if(0 <= threeUpDownCenterX4 && threeUpDownCenterX4+80 <= playerSize &&thePlayerY <= threeUpDownCenterY4+250 && thePlayerY >= threeUpDownCenterY4-250 ){
            soundEffects(2);
            score +=10;

        }

        //play swoosh
        if((0 <= threeUpDownCenterX && threeUpDownCenterX <= playerSize) || (0 <= threeUpDownCenterX2 && threeUpDownCenterX2 <= playerSize)
                || (0 <= threeUpDownCenterX3 && threeUpDownCenterX3 <= playerSize) || (0 <= threeUpDownCenterX4 && threeUpDownCenterX4 <= playerSize) ){
            soundEffects(4);
        }

    }

    public void hitCheck(){


        //spike
        float spikeCenterX = spikeSmallX + spikeSmall.getWidth() / 2.0f;
        float spikeCenterY = spikeSmallY + spikeSmall.getHeight() / 2.0f;

        //spike
        if( 0 <= spikeCenterX && spikeCenterX <= playerSize && thePlayerY <= spikeCenterY && spikeCenterY <= thePlayerY+playerSize){
            spikeSmallX = -300;
            score -= 25;
            soundEffects(3);
            if(score <= 0){
                score=0;
            }
        }

        //crystal
        float crystalCenterX = crystalX + crystal.getWidth() / 2.0f;
        float crystalCenterY = crystalY + crystal.getHeight() / 2.0f;

        if( 0 <= crystalCenterX && crystalCenterX <= playerSize && thePlayerY <= crystalCenterY && crystalCenterY <= thePlayerY+playerSize){
            crystalX = -300;
            score +=50;
            soundEffects(5);

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



    public boolean onTouchEvent(MotionEvent event) {

        if(!start_flg){
            start_flg = true;
            thePlayer1.setVisibility(View.GONE);
            tapLabel.setVisibility(View.GONE);

            crystal.setVisibility(View.VISIBLE);
            threeUpAndDown.setVisibility(View.VISIBLE);
            threeUpAndDown2.setVisibility(View.VISIBLE);
            threeUpAndDown3.setVisibility(View.VISIBLE);
            threeUpAndDown4.setVisibility(View.VISIBLE);
            theSun.setVisibility(View.VISIBLE);

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
            soundEffects(1);
            action_flg = true;
            thePlayer2fly.setVisibility(View.VISIBLE);
            thePlayer1.setVisibility(View.GONE);


        }else if(event.getAction() == MotionEvent.ACTION_UP){
            action_flg = false;
            thePlayer2fly.setVisibility(View.GONE);
            thePlayer1.setVisibility(View.VISIBLE);
        }


        return true;
    }




    public void soundOffButton(View view) {
        soundOff.setVisibility(View.INVISIBLE);
        soundOn.setVisibility(View.VISIBLE);
        soundOffPressed = true;
        soundOnPressed = false;

    }
    public void soundOnButton(View view) {
        soundOn.setVisibility(View.INVISIBLE);
        soundOff.setVisibility(View.VISIBLE);
        soundOnPressed = true;
        soundOffPressed = false;

    }


    @Override
    public void onBackPressed() {
    }



}