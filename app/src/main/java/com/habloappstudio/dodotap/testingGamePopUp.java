package com.habloappstudio.dodotap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdCallback;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;


public class testingGamePopUp extends AppCompatActivity {

    //reward ad
    private RewardedAd rewardedAd;

    //score
    int score;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing_game_pop_up);

        ///Ad to continue
        rewardedAd = new RewardedAd(this, "ca-app-pub-3940256099942544/5224354917");
        RewardedAdLoadCallback callback = new RewardedAdLoadCallback(){
            @Override
            public void onRewardedAdFailedToLoad(int i) {
                super.onRewardedAdFailedToLoad(i);
                Log.d("AD FAILED", "AD FAILED");
            }

            @Override
            public void onRewardedAdLoaded() {
                super.onRewardedAdLoaded();
                Log.d("AD LOADED", "AD Loaded");
            }
        };
        this.rewardedAd.loadAd(new AdRequest.Builder().build(), callback);


        //Set size for screen
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int)(width*0.8), (int)(height*0.35));
        //////

        TextView scoreLabel = findViewById(R.id.scoreLabel);
        TextView highScoreLabel = findViewById(R.id.highScore);

        //get Score
        score = getIntent().getIntExtra("SCORE", 0);
        scoreLabel.setText(score + "");

        //high score/save
        SharedPreferences sharedPreferences = getSharedPreferences("GAME_DATA", Context.MODE_PRIVATE);
        int highScore = sharedPreferences.getInt("HIGH_SCORE", 0);

        if(score > highScore){
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("HIGH_SCORE", score);
            editor.apply();

            highScoreLabel.setText("BEST: " + score);
        }else{
            highScoreLabel.setText("BEST: " + highScore);
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void mainMenu(View view) {
        startActivity(new Intent(testingGamePopUp.this, MainActivity.class));
    }

    public void viewAd(View view) {

        if (rewardedAd.isLoaded()) {
            Activity activityContext = testingGamePopUp.this;
            RewardedAdCallback adCallback = new RewardedAdCallback() {

                @Override
                public void onUserEarnedReward(@NonNull RewardItem reward) {
                    Intent intent = new Intent(getApplicationContext(), testingGame.class);
                    intent.putExtra("SCORE", score);
                    startActivity(intent);
                }
                @Override
                public void onRewardedAdClosed() {

                }

            };
            rewardedAd.show(activityContext, adCallback);
        } else {
            Log.d("TAG", "The Ad ad wasn't loaded yet.");
            Toast.makeText(this, "Ad Loading", Toast.LENGTH_SHORT).show();
        }


    }
}


