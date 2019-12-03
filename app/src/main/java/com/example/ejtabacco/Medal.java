package com.example.ejtabacco;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class Medal extends AppCompatActivity {
    private DBOpenHelper helper;
    private SQLiteDatabase db;
    private List<MedalData> medalDatas = new ArrayList<MedalData>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medal);

        if (helper == null) {
            helper = new DBOpenHelper(getApplicationContext());
        }
        if (db == null) {
            db = helper.getReadableDatabase();
        }

        // 獲得した数
        long getMedalCount = DatabaseUtils.queryNumEntries(db, "medal_info", "done = 1");

        // 全ての数
        long totalMedalCount = DatabaseUtils.queryNumEntries(db, "medal_info");

        String txt = "";
        TextView textView = findViewById(R.id.medalCountArea);

        if (getMedalCount == totalMedalCount) {
            textView.setText("完全取得!!");
        } else {
            txt = "獲得数 " + getMedalCount + "/" + totalMedalCount;
            textView.setText(txt);
        }




        // debug
        Log.d("debug", "**********Cursor");

        Cursor cursor = db.query(
                "medal_info",
                new String[]{"title", "content", "done"},
                null,
                null,
                null,
                null,
                null
        );

        cursor.moveToFirst();

        for (int i = 0; i < cursor.getCount(); i++) {

            MedalData medalData = new MedalData(cursor.getString(0), cursor.getString(1), cursor.getInt(2));
            medalDatas.add(medalData);
            cursor.moveToNext();
        }
        // 忘れずに！
        cursor.close();

        RecyclerView recyclerView = findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager rLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(rLayoutManager);

        RecyclerView.Adapter rAdapter = new MyAdapter(medalDatas);
        recyclerView.setAdapter(rAdapter);

    }
}