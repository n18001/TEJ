package com.example.ejtabacco;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;


public class Login extends AppCompatActivity {
    private DBOpenHelper helper;
    private SQLiteDatabase db;
    String string = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        System.out.println(">>>>>>> 💚");

        if (helper == null) {
            helper = new DBOpenHelper(getApplicationContext());
        }
        if (db == null) {
            db = helper.getWritableDatabase();
        }

        findViewById(R.id.input_button).setOnClickListener(button());
        findViewById(R.id.inputBirth).setOnClickListener(button());

    }


    View.OnClickListener button() {
        return new View.OnClickListener() {

            TextView birthArea = findViewById(R.id.birth_area);
            TextView hintArea = findViewById(R.id.hint_text_area);
            EditText username = findViewById(R.id.editTextName);
            String birthday = "";

            final Calendar date = Calendar.getInstance();

            public void onClick(View v) {
                if (v != null) {
                    switch (v.getId()) {
                        case R.id.input_button:
                            ContentValues values = new ContentValues();
                            if ((!username.getText().toString().isEmpty()) && !birthArea.getText().toString().isEmpty()) {
                                birthday = birthArea.getText().toString();
                                values.put("nickname", username.getText().toString());
                                values.put("birth", string);
                                db.insert("profile",null, values);
                                finish();
                            } else {
                                if (username.getText().toString().isEmpty()) {
                                    hintArea.setText("名前を入力してください。");
                                } else if (birthArea.getText().toString().isEmpty()) {
                                    hintArea.setText("生年月日を入力してください。");
                                }
                            }
                            break;

                        case R.id.inputBirth:
                            //DatePickerDialogインスタンスを取得
                            DatePickerDialog datePickerDialog = new DatePickerDialog(
                                    Login.this,
                                    new DatePickerDialog.OnDateSetListener() {
                                        @Override
                                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                            //setした日付を取得して表示
                                            string = String.format("%d-%02d-%02d", year, month+1, dayOfMonth);
                                            String text = String.format("%d / %02d / %02d", year, month+1, dayOfMonth);
                                            birthArea.setText(text);
                                        }
                                    },
                                    date.get(Calendar.YEAR) - 20,
                                    date.get(Calendar.MONTH),
                                    date.get(Calendar.DATE)
                            );

                            datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

                            //dialogを表示
                            datePickerDialog.show();

                    }
                }
            }
        };
    }
}
