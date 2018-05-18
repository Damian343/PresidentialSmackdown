package com.dham343.slapthetrump;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Created by damian on 3/30/2017.
 */

public class characterSelection extends Activity {
    private ImageView trump, hillary;
    private boolean president = true;//defaults to trump
    private double highscore;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.title);

        highscore = getIntent().getExtras().getDouble("highscore");

        trump   = (ImageView) findViewById(R.id.trump);
        hillary = (ImageView) findViewById(R.id.hillary);

        trump.setImageDrawable(getResources().getDrawable(R.drawable.trumphdpi));
        hillary.setImageDrawable(getResources().getDrawable(R.drawable.hillaryhdpi));

        trump.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                createIntent(true);
                System.out.println("selected trump");
                return false;
            }
        });
        hillary.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                createIntent(false);
                System.out.println("selected Hillary");
                return false;
            }
        });
    }

    public void createIntent(boolean selectedPres){
        president = selectedPres;
        Intent intent = new Intent(getApplicationContext(), MainActivity.class).putExtra("president", president);
        intent.putExtra("highscore", highscore);
        startActivity(intent);
    }
}
