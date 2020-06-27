package com.habloappstudio.dodotap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;

public class testingGamePopUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing_game_pop_up);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*0.8), (int)(height*0.3));


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void mainMenu(View view) {
        startActivity(new Intent(testingGamePopUp.this, MainActivity.class));
        finish();
    }

    public void viewAd(View view) {
    }
}


