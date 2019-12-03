package com.example.ejtabacco;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        findViewById(R.id.memory).setOnClickListener(changeScene());
        findViewById(R.id.tabacco).setOnClickListener(changeScene());
        findViewById(R.id.medal).setOnClickListener(changeScene());
        findViewById(R.id.monster).setOnClickListener(changeScene());
        findViewById(R.id.heven).setOnClickListener(changeScene());

    }

    // switch
    private View.OnClickListener changeScene() {
        return new View.OnClickListener() {
            public void onClick(View v) {
                if (v != null) {
                    Intent intent;
                    switch (v.getId()) {
                        // memory
                        case R.id.memory:
                            intent = new Intent(MainActivity.this, Memory.class);
                            startActivity(intent);
                            break;

                        // tabacco
                        case R.id.tabacco:
                            finish();
                            break;

                        // medal
                        case R.id.medal:
                            intent = new Intent(MainActivity.this, Medal.class);
                            startActivity(intent);
                            break;

                        // monster
                        case R.id.monster:
                            intent = new Intent(MainActivity.this, Monster.class);
                            startActivity(intent);
                            break;

                        // heven
                        case R.id.heven:
                            intent = new Intent(MainActivity.this, Heven.class);
                            startActivity(intent);
                            break;
                    }
                }
            }
        };
    }
}