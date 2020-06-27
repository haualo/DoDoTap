package com.habloappstudio.dodotap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.View;

import com.habloappstudio.dodotap.R;

public class GameView extends View {

    //canvas
    private int canvasWidth;
    private int canvasHeight;


    //bird
    private Bitmap thePlayer [] = new Bitmap[2];
    private int thePlayerX = 10, thePlayerY, thePlayerSpeed;

    //background
    private Bitmap bgImage;

    //spike
    private Bitmap spikeSmall, spikeMedium, spikeBig;

    private int spikeSmallX, spikeSmallY, spikeSmallSpeed = 10;
    private int spikeMediumX, spikeMediumY, spikeMediumSpeed = 10;


    //score
    private Paint scorePaint = new Paint();
    private int score;

    //status check
    private boolean touch_flg = false;

    public GameView(Context context) {
        super(context);

        thePlayer[0] = BitmapFactory.decodeResource(getResources(), R.drawable.dodobird1small);
        thePlayer[1] = BitmapFactory.decodeResource(getResources(), R.drawable.dodobird2small);

        bgImage = BitmapFactory.decodeResource(getResources(), R.drawable.backgroundnew);

        spikeSmall = BitmapFactory.decodeResource(getResources(), R.drawable.spikesmall);

        scorePaint.setColor(Color.BLACK);
        scorePaint.setTextSize(75);
        scorePaint.setTypeface(Typeface.DEFAULT_BOLD);
        scorePaint.setAntiAlias(true);

        //First position
        thePlayerY = 500;
        spikeSmallX = 1000;
        spikeMediumX = 1500;
        score=0;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvasWidth = canvas.getWidth();
        canvasHeight = canvas.getHeight();


        canvas.drawBitmap(bgImage,0,0,null);



        //Player
        int minPlayerY = thePlayer[0].getHeight() + 20;
        int maxPlayerY = canvasHeight - thePlayer[0].getHeight() * 4 ;
        
        thePlayerY += thePlayerSpeed;
        if(thePlayerY < minPlayerY){ thePlayerY = minPlayerY;}
        if(thePlayerY > maxPlayerY){ thePlayerY = maxPlayerY;}
        thePlayerSpeed +=2;

        if(touch_flg){
            canvas.drawBitmap(thePlayer[1], thePlayerX, thePlayerY, null);
            touch_flg = false;
        }else{
            canvas.drawBitmap(thePlayer[0], thePlayerX, thePlayerY, null);
        }


        //small spike
        spikeSmallX -= spikeSmallSpeed;

        if(hitCheck(spikeSmallX, spikeSmallY)){
            score+=10;
            spikeSmallX = -700;

        }

        if(spikeSmallX < -600){
            spikeSmallX = canvasHeight + 30;
            spikeSmallY = (int) Math.floor(Math.random() * ((maxPlayerY-300) - minPlayerY)) + minPlayerY;
        }
        canvas.drawBitmap(spikeSmall,spikeSmallX,spikeSmallY,null);


        //big spike
        spikeMediumY = maxPlayerY-545;
        spikeMediumX -= spikeMediumSpeed;
        if(spikeMediumX < -600){
            spikeMediumX = canvasWidth + 20;
        }
        canvas.drawBitmap(spikeMedium,spikeMediumX,spikeMediumY,null);

        canvas.drawText("Score: " + score,20, 60, scorePaint );

    }

    public  boolean hitCheck(int x, int y){

        if(thePlayerX < x && x+150 < (thePlayerX + thePlayer[0].getWidth()) && thePlayerY < y+50 && y+50 < (thePlayerY + thePlayer[0].getHeight())){
            return true;
        }
        return false;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            touch_flg = true;
            thePlayerSpeed = -20;
        }
        return true;
    }
}
