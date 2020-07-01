package com.habloappstudio.dodotap;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.util.DisplayMetrics;


public class AboutActivityPopUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_pop_up);

        //Set size for screen
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int)(width*0.8), (int)(height*0.6));
        //////


    }

}