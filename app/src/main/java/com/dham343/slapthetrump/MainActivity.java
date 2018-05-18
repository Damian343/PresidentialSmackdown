package com.dham343.slapthetrump;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.VelocityTrackerCompat;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

import static java.lang.Thread.sleep;

//if user presses screen keep getting x and y of finger
//until crosses or touches image, image will fly off screen
//new screen blinking background with text of velocity
//if above certain score then song plays text flashes

public class MainActivity extends Activity {
    private ImageView       img;
    private Button          button;
    private TextView        txtScore, newScore;

    private int[]           anims = { R.anim.slide, R.anim.slide2, R.anim.slide3 };

    private double          highScore;
    private VelocityTracker mTracker = null;
    private Random          random = new Random();
    private MediaPlayer     mp, mp2;
    private Animation       animSlide, blinker;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boolean selectedPresident = getIntent().getExtras().getBoolean("president");
        highScore = getIntent().getExtras().getDouble("highscore");
        System.out.println(highScore);

        txtScore  = (TextView) findViewById(R.id.txtScore);
        txtScore.setText("Highscore: " + highScore);
        newScore  = (TextView) findViewById(R.id.txtHighscore);
        button    = (Button)   findViewById(R.id.btntitle);
        img       = (ImageView)findViewById(R.id.face);

        mp        =  MediaPlayer.create(this, R.raw.slap);
        mp2       =  MediaPlayer.create(this, R.raw.post);
        blinker   = new AlphaAnimation(0.0f, 1.0f);
        blinker.setDuration(160);
        blinker.setStartOffset(20);
        blinker.setRepeatMode(Animation.REVERSE);
        blinker.setRepeatCount(7);

        Random random = new Random();
        int slide = anims[random.nextInt(2)];
        animSlide =  AnimationUtils.loadAnimation(getApplicationContext()
                                                    , slide);

        if(selectedPresident){ img.setImageDrawable(getResources().getDrawable(R.drawable.trumphdpi  ));}
        else {                 img.setImageDrawable(getResources().getDrawable(R.drawable.hillaryhdpi));}

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), characterSelection.class).putExtra("highscore", highScore);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int index     = event.getActionIndex();
        int action    = event.getActionMasked();
        int pointerId     = event.getPointerId(index);

        switch(action){
            case MotionEvent.ACTION_DOWN:
                if (mTracker == null) { mTracker = VelocityTracker.obtain(); }
                else { mTracker.clear(); }

                mTracker.addMovement(event);
                break;

            case MotionEvent.ACTION_MOVE:
                mp.create(this, R.raw.slap);
                mTracker.addMovement(event);
                mTracker.computeCurrentVelocity(1000);
                animSlide.setFillAfter(false);

                if(checkHit(Math.floor(event.getX())) ) {
                    double velocity = Math.abs(Math.floor(VelocityTrackerCompat.getXVelocity(mTracker, pointerId)));
                    mp.start();
                    postSlap(velocity);
                }
                break;

            case MotionEvent.ACTION_CANCEL:
                mTracker.recycle();
                img.clearAnimation();
                break;
        }
        return true;
    }

    public boolean checkHit(double x) {

        double maximumX = img.getRight();
        double minimumX = img.getLeft();

        if(x >= minimumX && x <= maximumX) { return true; }
        else return false;

    }

    public void postSlap(double velocity) {
        int num = random.nextInt(3);
        System.out.println(num);
        animSlide =  AnimationUtils.loadAnimation(getApplicationContext()
                , anims[num]);

        img.startAnimation(animSlide);

        if(velocity > highScore){
            mp2.start();

            highScore = velocity;
            newScore.setText("NEW SLAP RECORD!");
            txtScore.setText("Highscore: " + highScore);
            newScore.setTextColor(Color.RED);
            newScore.startAnimation(blinker);
            newScore.setVisibility(View.INVISIBLE);
        }
        else {
            newScore.setText("NICE SLAP! " + (int)velocity + " mps");
            newScore.setTextColor(Color.WHITE);
            newScore.setVisibility(View.VISIBLE);
        }
    }


}
