package com.example.ejtabacco;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.text.ParseException;



public class Limit extends AppCompatActivity {
    private DBOpenHelper helper;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.limitation);

        TextView textView = findViewById(R.id.limitDay);

        if (helper == null) {
            helper = new DBOpenHelper(getApplicationContext());
        }
        if (db == null) {
            db = helper.getWritableDatabase();
        }

        Cursor cursor = db.rawQuery("SELECT date(birth) FROM profile", null);
        cursor.moveToFirst();

        // 現在時刻
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        // 現在時刻 - 20年
        calendar.add(Calendar.YEAR, -20);
        Date d1 = calendar.getTime();
        System.out.println("今日 : " + d1);


        try {
            String str = cursor.getString(0);
            SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date birth = sdFormat.parse(str);
            System.out.println("生年月日 : " + birth);
            long dateTimeTo = birth.getTime();
            long dateTimeFrom = d1.getTime();
            long dayDiff = ( dateTimeTo - dateTimeFrom ) / (1000 * 60 * 60 * 24) + 1;
            System.out.println("差分日数 : " + dayDiff);
            String text = dayDiff + "日";
            textView.setText(text);

        } catch (ParseException e) {
            System.out.println(">>>>>>>>>>>ERROR>>>>>>>>>>");
        }

        cursor.close();

    }
}
