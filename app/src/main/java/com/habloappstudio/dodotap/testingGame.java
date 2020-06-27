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

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.Timer;
import java.util.TimerTask;

public class testingGame extends AppCompatActivity {

    private TextView scoreLabel;
    private TextView tapLabel;

    private ImageView thePlayer1;
    private ImageView thePlayer2;
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



        scoreLabel = (TextView) findViewById(R.id.scoreText);
        tapLabel = (TextView) findViewById(R.id.tapStart);

        bigThree = (ImageView) findViewById(R.id.three1);
        mediumThree = (ImageView) findViewById(R.id.three2);
        smallThree = (ImageView) findViewById(R.id.three3);

        thePlayer1 = (ImageView) findViewById(R.id.player1);
        thePlayer2 = (ImageView) findViewById(R.id.player2);
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
            spikeSmallX += 30;
            crystalX += 15;
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
        Log.d("plauerY", " PlayerY:  " +  thePlayerY);
    }

    public void hitCheck(){

        //SpikeCenter
        float spikeCenterX = spikeSmallX + spikeSmall.getWidth() / 2.0f;
        float spikeCenterY = spikeSmallY + spikeSmall.getHeight() / 2.0f;

        //spike
        if( 0 <= spikeCenterX && spikeCenterX <= playerSize && thePlayerY <= spikeCenterY && spikeCenterY <= thePlayerY+playerSize){
            //gameOver();
        }

        //crystal
        float crystalCenterX = crystalX + crystal.getWidth() / 2.0f;
        float crystalCenterY = crystalY + crystal.getHeight() / 2.0f;

        //spike
        if( 0 <= crystalCenterX && crystalCenterX <= playerSize && thePlayerY <= crystalCenterY && crystalCenterY <= thePlayerY+playerSize){
            crystalX = 1500;
            score +=10;
        }
        //big three
        if(thePlayerY > bigThreeY && bigThreeX <= thePlayerX+100 && thePlayerX < bigThreeX+100){
           // gameOver();
        }

        //medium three
        if(thePlayerY > mediumThreeY && mediumThreeX <= thePlayerX+100 && thePlayerX < mediumThreeX+100){
            //gameOver();
        }

        //small three
        if(thePlayerY > smallThreeY && smallThreeX <= thePlayerX+100 && thePlayerX < smallThreeX+100){
            //gameOver();
        }

    }

    public void gameOver(){
        if(timer != null){
            timer.cancel();
            timer = null;
        }
        thePlayer1.setVisibility(View.GONE);
        thePlayer2.setVisibility(View.GONE);
        thePlayer3.setVisibility(View.VISIBLE);

        stopMovements = true;
        //startActivity(new Intent(testingGame.this, testingGamePopUp.class));
        Intent intent = new Intent(getApplicationContext(), testingGamePopUp.class);
        intent.putExtra("SCORE", score);
        startActivity(intent);
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