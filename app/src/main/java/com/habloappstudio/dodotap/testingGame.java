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
    MediaPlayer flapSound;
    MediaPlayer pointSound;
    MediaPlayer hitSound;
    MediaPlayer crystalSound;
    MediaPlayer swooshSound;




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
    private ImageView threeUpAndDown;
    private ImageView threeUpAndDown2;
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
    private float threeUpAndDownX, threeUpAndDownY = -300;
    private float threeUpAndDownX2, threeUpAndDownY2 = -300;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing_game);

        //bannerAD code
        myAdView = findViewById(R.id.banner1);
        AdRequest adRequest = new AdRequest.Builder().build();
        myAdView.loadAd(adRequest);



        //sound
        flapSound = MediaPlayer.create(getBaseContext(), R.raw.sfx_wing);
        pointSound = MediaPlayer.create(this, R.raw.sfx_point);
        hitSound = MediaPlayer.create(this, R.raw.sfx_hit);
        crystalSound = MediaPlayer.create(this, R.raw.sfx_crystal);
        swooshSound = MediaPlayer.create(this, R.raw.sfx_swooshing);

        scoreLabel = (TextView) findViewById(R.id.scoreText);
        tapLabel = (TextView) findViewById(R.id.tapStart);

        bigThree = (ImageView) findViewById(R.id.three1);
        mediumThree = (ImageView) findViewById(R.id.three2);
        smallThree = (ImageView) findViewById(R.id.three3);
        threeUpAndDown = (ImageView) findViewById(R.id.threeupanddown);
        threeUpAndDown2 = (ImageView) findViewById(R.id.threeupanddown2);

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
        cloud1X = -500;
        cloud2X = -700;
        cloud3X = -800;

        cloud1.setY(-500);
        cloud2.setY(-500);
        cloud3.setY(-500);

        crystalX = -600;
        thePlayer1.setX(thePlayerX);
        thePlayer3.setX(thePlayerX);

        threeUpAndDownX = -600;
        threeUpAndDown.setY(threeUpAndDownY);

        threeUpAndDownX2 = -600;
        threeUpAndDown2.setY(threeUpAndDownY2);

        scoreLabel.setText("Score: " + score);
    }


    public void changePos(){

        hitCheck();
        hitThreeBig();



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
            threeUpAndDownX +=10;
            threeUpAndDownX2 +=10;
        }else{

            //the upNdown threes
            threeUpAndDownX -=threeSpeed;
            if(threeUpAndDownX < -200){
                threeUpAndDownX = 1000;
                threeUpAndDownY = (int) Math.floor(Math.random() * (-150 + 450)) - 450;
            }
            threeUpAndDown.setX(threeUpAndDownX);
            threeUpAndDown.setY(threeUpAndDownY);

            threeUpAndDownX2 -=threeSpeed;
            if(threeUpAndDownX2 < -200){
                threeUpAndDownX2 = threeUpAndDownX + 500;
                threeUpAndDownY2 = (int) Math.floor(Math.random() * (-150 + 450)) - 450;
            }
            threeUpAndDown2.setX(threeUpAndDownX2);
            threeUpAndDown2.setY(threeUpAndDownY2);


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
            hitSound.start();
            gameOver();

        }else if(0 <= threeUpDownCenterX && threeUpDownCenterX+80 <= playerSize &&thePlayerY <= threeUpDownCenterY+250 && thePlayerY >= threeUpDownCenterY-250 ){
            swooshSound.start();
            score += 10;
            pointSound.start();
        }

        //three 2
        float threeUpDownCenterX2 = threeUpAndDownX2 + threeUpAndDown2.getWidth() / 2.0f;
        float threeUpDownCenterY2 = threeUpAndDownY2 + threeUpAndDown2.getHeight() / 2.0f;
        if( (0 <= threeUpDownCenterX2 && threeUpDownCenterX2 <= playerSize+100 && (thePlayerY-50) > (threeUpDownCenterY2))
                || (0 <= threeUpDownCenterX2 && threeUpDownCenterX2 <= playerSize+100 && (thePlayerY+50) < (threeUpDownCenterY2-150)) ){
            hitSound.start();
            gameOver();

        }else if(0 <= threeUpDownCenterX2 && threeUpDownCenterX2+80 <= playerSize &&thePlayerY <= threeUpDownCenterY2+250 && thePlayerY >= threeUpDownCenterY2-250 ){
            swooshSound.start();
            score += 10;
            pointSound.start();

        }


    }

    public void hitCheck(){


        //spike
        float spikeCenterX = spikeSmallX + spikeSmall.getWidth() / 2.0f;
        float spikeCenterY = spikeSmallY + spikeSmall.getHeight() / 2.0f;

        //spike
        if( 0 <= spikeCenterX && spikeCenterX <= playerSize && thePlayerY <= spikeCenterY && spikeCenterY <= thePlayerY+playerSize){
            score -= 25;
            hitSound.start();
            if(score <= 0){
                score=0;
            }
        }

        //crystal
        float crystalCenterX = crystalX + crystal.getWidth() / 2.0f;
        float crystalCenterY = crystalY + crystal.getHeight() / 2.0f;

        if( 0 <= crystalCenterX && crystalCenterX <= playerSize && thePlayerY <= crystalCenterY && crystalCenterY <= thePlayerY+playerSize){
            crystalX = 1500;
            score +=50;
            crystalSound.start();

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
            bigThree.setVisibility(View.GONE);
            mediumThree.setVisibility(View.GONE);
            smallThree.setVisibility(View.GONE);

            crystal.setVisibility(View.VISIBLE);
            threeUpAndDown.setVisibility(View.VISIBLE);
            threeUpAndDown2.setVisibility(View.VISIBLE);

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
            flapSound.start();
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

    @Override
    public void onBackPressed() {
    }
}