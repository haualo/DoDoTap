package com.habloappstudio.dodotap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class MainActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(this, "ca-app-pub-6760992196528607~4331020967");
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        //bannerAD code
        AdView myAdView;
        myAdView = findViewById(R.id.bannerMain);
        AdRequest adRequest = new AdRequest.Builder().build();
        myAdView.loadAd(adRequest);

    }

    public void startGame(View view) {

        startActivity(new Intent(MainActivity.this, testingGame.class));

    }

    public void aboutPopUp(View view) {
        startActivity(new Intent(MainActivity.this, AboutActivityPopUp.class));
        Toast.makeText(this, "Press outside box to leave", Toast.LENGTH_SHORT).show();
    }
}