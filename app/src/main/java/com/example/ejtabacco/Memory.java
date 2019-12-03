package com.example.ejtabacco;

import androidx.appcompat.app.AppCompatActivity;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class Memory extends AppCompatActivity {
    private DBOpenHelper helper;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory);
        if(helper == null){
            helper = new DBOpenHelper(getApplicationContext());
        }
        if(db == null){
            db = helper.getReadableDatabase();
        }

        // total price
        TextView totalprice = findViewById(R.id.total_price_text);
        StringBuilder totalbuilder  = new StringBuilder();
        Cursor cursor1 = db.rawQuery("SELECT sum(one_price) FROM smoke_log NATURAL INNER JOIN tabacco_info",null);
        cursor1.moveToFirst();
        totalbuilder.append("約 ");
        totalbuilder.append(cursor1.getInt(0));
        totalbuilder.append(" 円");
        cursor1.close();
        totalprice.setText(totalbuilder.toString());


        // total tax
        TextView totaltax = findViewById(R.id.total_tax_price);
        StringBuilder taxbuilder  = new StringBuilder();
        Cursor cursor2 = db.rawQuery("SELECT sum(tax) FROM smoke_log NATURAL INNER JOIN tabacco_info",null);
        cursor2.moveToFirst();
        taxbuilder.append("約 ");
        taxbuilder.append(cursor2.getInt(0));
        taxbuilder.append(" 円");
        cursor2.close();
        totaltax.setText(taxbuilder.toString());


        // today price
        TextView todayprice = findViewById(R.id.today_price_text);
        StringBuilder todaybuilder  = new StringBuilder();
        Cursor cursor3 = db.rawQuery("SELECT sum(one_price) FROM smoke_log NATURAL INNER JOIN tabacco_info WHERE date('now','localtime') == date(time)",null);
        cursor3.moveToFirst();
        todaybuilder.append("約 ");
        todaybuilder.append(cursor3.getInt(0));
        todaybuilder.append(" 円");
        cursor3.close();
        todayprice.setText(todaybuilder.toString());


        // total count
        TextView totalcount = findViewById(R.id.total_count_text);
        StringBuilder totalcountbuilder  = new StringBuilder();
        Cursor cursor4 = db.rawQuery("SELECT count(*) FROM smoke_log NATURAL INNER JOIN tabacco_info",null);
        cursor4.moveToFirst();
        totalcountbuilder.append(cursor4.getInt(0));
        totalcountbuilder.append(" 本");
        cursor4.close();
        totalcount.setText(totalcountbuilder.toString());


        // today count
        TextView todaycount = findViewById(R.id.today_count_text);
        StringBuilder todaycountbuilder  = new StringBuilder();
        Cursor cursor5 = db.rawQuery("SELECT count(*) FROM smoke_log NATURAL INNER JOIN tabacco_info WHERE date('now','localtime') == date(time)",null);
        cursor5.moveToFirst();
        todaycountbuilder.append(cursor5.getInt(0));
        todaycountbuilder.append(" 本");
        cursor5.close();
        todaycount.setText(todaycountbuilder.toString());


        // life span
        TextView lifespan = findViewById(R.id.lifespan_text);
        StringBuilder lifespanbuilder  = new StringBuilder();
        Cursor cursor6 = db.rawQuery("SELECT 5 * count(*) FROM smoke_log NATURAL INNER JOIN tabacco_info",null);
        cursor6.moveToFirst();
        lifespanbuilder.append("約 ");
        lifespanbuilder.append(cursor6.getInt(0));
        lifespanbuilder.append(" 分");
        cursor6.close();
        lifespan.setText(lifespanbuilder.toString());


        // lost time
        TextView losttime= findViewById(R.id.lost_time_text);
        StringBuilder losttimebuilder  = new StringBuilder();
        Cursor cursor7 = db.rawQuery("SELECT 3 * count(*) FROM smoke_log NATURAL INNER JOIN tabacco_info",null);
        cursor7.moveToFirst();
        losttimebuilder.append("約 ");
        losttimebuilder.append(cursor7.getInt(0));
        losttimebuilder.append(" 分");
        cursor7.close();
        losttime.setText(losttimebuilder.toString());

    }
}