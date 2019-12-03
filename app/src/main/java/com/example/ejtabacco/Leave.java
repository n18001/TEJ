package com.example.ejtabacco;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Leave extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leave);

        findViewById(R.id.frog_man).setOnClickListener(frogTalk());
    }


    View.OnClickListener frogTalk() {
        final TextView frog_talk = findViewById(R.id.frog_talk);

        return new View.OnClickListener() {
            public void onClick(View v) {
                List<String> frog_talk_text = GetTalkText("frog_comfort.txt");

                // R.id switch
                if (v != null) {
                    switch (v.getId()) {
                        case R.id.frog_man:
                            frog_talk.setText(frog_talk_text.get((int) Math.floor(Math.random() * frog_talk_text.size())));
                    }
                }
            }
        };
    }




    public List<String> GetTalkText(String filename) {
        InputStream is = null;
        BufferedReader br = null;
        String text = "";
        final List<String> sample_values = new ArrayList<String>();

        try {
            try {
                is = this.getAssets().open(filename);
                br = new BufferedReader(new InputStreamReader(is));

                String str;
                while ((str = br.readLine()) != null) {
                    text = str;
                    sample_values.add(text);
                }
            } finally {
                if (is != null) is.close();
                if (br != null) br.close();
            }
        } catch (Exception e) {
            System.out.println(">>> EXCEPTION ERROR");
        }

        return sample_values;
    }

}
