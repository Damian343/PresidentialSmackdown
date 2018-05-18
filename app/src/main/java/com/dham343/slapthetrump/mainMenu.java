package com.dham343.slapthetrump;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by damian on 4/2/2017.
 */

public class mainMenu extends Activity{
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainmenu);

        button = (Button) findViewById(R.id.menubtn);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), characterSelection.class).putExtra("highscore", 0.0);
                startActivity(intent);
            }
        });
    }
}
