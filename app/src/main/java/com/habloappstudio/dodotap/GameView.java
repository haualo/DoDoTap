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
    private Bitmap spike1, spike2;

    //score
    private Paint scorePaint = new Paint();

    //status check
    private boolean touch_flg = false;

    public GameView(Context context) {
        super(context);

        thePlayer[0] = BitmapFactory.decodeResource(getResources(), R.drawable.dodobird1small);
        thePlayer[1] = BitmapFactory.decodeResource(getResources(), R.drawable.dodobird2small);

        bgImage = BitmapFactory.decodeResource(getResources(), R.drawable.map_startmain);

        spike1 = BitmapFactory.decodeResource(getResources(), R.drawable.spike1);
        spike2 = BitmapFactory.decodeResource(getResources(), R.drawable.spike2);

        scorePaint.setColor(Color.BLACK);
        scorePaint.setTextSize(75);
        scorePaint.setTypeface(Typeface.DEFAULT_BOLD);
        scorePaint.setAntiAlias(true);

        //First position
        thePlayerY = 500;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvasWidth = canvas.getWidth();
        canvasHeight = canvas.getHeight();


        canvas.drawBitmap(bgImage,0,50,null);



        //Player
        int minPlayerY = thePlayer[0].getHeight() - 100;
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


        canvas.drawText("Score: 0",20, 60, scorePaint );

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
