package com.example.ejtabacco;


import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class CharacterSetting extends AppCompatActivity {
    private DBOpenHelper helper;
    private SQLiteDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chara_setting);

        if (helper == null) {
            helper = new DBOpenHelper(getApplicationContext());
        }
        if (db == null) {
            db = helper.getWritableDatabase();
        }


        findViewById(R.id.input_button).setOnClickListener(switchButton());
        findViewById(R.id.hint_button).setOnClickListener(switchButton());
    }

    // switch
    private View.OnClickListener switchButton() {
        return new View.OnClickListener() {

            HintDialog hint = new HintDialog();
            ContentValues values = new ContentValues();
            EditText name = findViewById(R.id.nameInput);
            RadioGroup rg = findViewById(R.id.lover_group);
            RadioButton btn1 = findViewById(R.id.radio1);
            TextView textView = findViewById(R.id.warning);

            public void onClick(View v) {
                if (v != null) {
                    Intent intent;
                    switch (v.getId()) {
                        // input button
                        case R.id.input_button:

                            if ((rg.getCheckedRadioButtonId() != -1) && (!name.getText().toString().isEmpty())) {
                                int radioButtoinId = rg.getCheckedRadioButtonId();
                                View radioButton = rg.findViewById(radioButtoinId);
                                int idx = rg.indexOfChild(radioButton);

                                values.put("name", name.getText().toString());
                                values.put("lover_id", idx);
                                db.insert("monster", null, values);

                                ContentValues values = new ContentValues();
                                values.put("done", Boolean.TRUE);
                                db.update("medal_info", values, "medal_id = 21", null);

                                finish();
                            } else {
                                if (rg.getCheckedRadioButtonId() == -1 && name.getText().toString().isEmpty()) {
                                    textView.setText("愛人を選択してください。\n名前をつけてあげてください。");
                                } else if (name.getText().toString().isEmpty()) {
                                    textView.setText("名前をつけてあげてください。");
                                } else {
                                    textView.setText("愛人を選択してください。");
                                }
                            }

                            break;

                        case R.id.hint_button:
                            hint.show(getSupportFragmentManager(), null);
                            break;

                    }
                }
            }
        };
    }
}
