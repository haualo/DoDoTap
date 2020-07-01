package com.habloappstudio.dodotap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startGame(View view) {

        startActivity(new Intent(MainActivity.this, testingGame.class));

    }

    public void aboutPopUp(View view) {
        startActivity(new Intent(MainActivity.this, AboutActivityPopUp.class));
        Toast.makeText(this, "Press outside box to leave", Toast.LENGTH_SHORT).show();
    }
}