package com.example.ejtabacco;

import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class Tabacco extends AppCompatActivity {
    private DBOpenHelper helper;
    private SQLiteDatabase db;
    private TextView no1, no2, no3;


    @Override
    protected void onRestart() {
        super.onRestart();
        long recodeCount = DatabaseUtils.queryNumEntries(db, "profile");
        if ((recodeCount != 0) && (adultDay() < 0)) {
            onLoad();
        } else {
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabacco);

        onLoad();

    }


    protected void onLoad() {

        if (helper == null) {
            helper = new DBOpenHelper(getApplicationContext());
        }
        if (db == null) {
            db = helper.getWritableDatabase();
        }

        long recodeCount = DatabaseUtils.queryNumEntries(db, "profile");

        if (recodeCount == 0) {
            Intent intent = new Intent(Tabacco.this, Login.class);
            startActivity(intent);
            System.out.println(">>>>>> ❤️");
        } else if (adultDay() > 0) {
            Intent intent = new Intent(Tabacco.this, Limit.class);
            startActivity(intent);
            System.out.println(">>>>>> 💙");
        }


        no1 = findViewById(R.id.no1_tabacco);
        no2 = findViewById(R.id.no2_tabacco);
        no3 = findViewById(R.id.no3_tabacco);


        no1.setText(getBrandName("1"));
        no2.setText(getBrandName("2"));
        no3.setText(getBrandName("3"));

        findViewById(R.id.no1_tabacco).setOnClickListener(tabaccoButton());
        findViewById(R.id.no2_tabacco).setOnClickListener(tabaccoButton());
        findViewById(R.id.no3_tabacco).setOnClickListener(tabaccoButton());
        findViewById(R.id.setting_button).setOnClickListener(tabaccoButton());
        findViewById(R.id.menu_button).setOnClickListener(tabaccoButton());
        findViewById(R.id.frog_attack).setOnClickListener(tabaccoButton());
        findViewById(R.id.rabbit_attack).setOnClickListener(tabaccoButton());

    }


    View.OnClickListener tabaccoButton() {
        return new View.OnClickListener() {
            public void onClick(View v) {
                ContentValues values = new ContentValues();
                ContentValues point_values = new ContentValues();

                // original toast
                String txt1 = getBrandName("1");
                String txt2 = getBrandName("2");
                String txt3 = getBrandName("3");

                // R.id switch
                if (v != null) {
                    switch (v.getId()) {
                        case R.id.no1_tabacco:
                            long recordCount1 = DatabaseUtils.queryNumEntries(db, "tabacco_info", "button_num = 1");
                            if (recordCount1 != 0) {
                                int tabacco_id = GetTabaccoID("1");
                                values.put("user_id", 1);
                                values.put("tabacco_id", tabacco_id);
                                db.insert("smoke_log", null, values);
                                float point = GetTaxPoint();
                                float tax = GetTabaccoTax("1");
                                point_values.put("point", point + tax);
                                db.update("tax_point", point_values, "point_id = 1", null);
                                // toast
                                OriginalToast.makeText(Tabacco.this, txt1 + "\nうめえぇ", 1000).show(R.layout.original_toast_view);
                                MedalJudge();
//                                dateCheck();
                                break;
                            } else {
                                OriginalToast.makeText(Tabacco.this, "銘柄設定で\n煙草を登録して", 1500).show(R.layout.original_toast_view2);
                                break;
                            }
                        case R.id.no2_tabacco:
                            long recordCount2 = DatabaseUtils.queryNumEntries(db, "tabacco_info", "button_num = 2");
                            if (recordCount2 != 0) {
                                int tabacco_id = GetTabaccoID("2");
                                values.put("user_id", 1);
                                values.put("tabacco_id", tabacco_id);
                                db.insert("smoke_log", null, values);
                                float point = GetTaxPoint();
                                float tax = GetTabaccoTax("2");
                                point_values.put("point", point + tax);
                                db.update("tax_point", point_values, "point_id = 1", null);
                                //toast
                                OriginalToast.makeText(Tabacco.this, txt2 + "\nうめえぇ", 1000).show(R.layout.original_toast_view);
                                MedalJudge();
                                break;
                            } else {
                                OriginalToast.makeText(Tabacco.this, "銘柄設定で\n煙草を登録して", 1500).show(R.layout.original_toast_view2);
                                break;
                            }
                        case R.id.no3_tabacco:
                            long recordCount3 = DatabaseUtils.queryNumEntries(db, "tabacco_info", "button_num = 3");
                            if (recordCount3 != 0) {
                                int tabacco_id = GetTabaccoID("3");
                                values.put("user_id", 1);
                                values.put("tabacco_id", tabacco_id);
                                db.insert("smoke_log", null, values);
                                float point = GetTaxPoint();
                                float tax = GetTabaccoTax("3");
                                point_values.put("point", point + tax);
                                db.update("tax_point", point_values, "point_id = 1", null);
                                // toast
                                OriginalToast.makeText(Tabacco.this, txt3 + "\nうめえぇ", 1000).show(R.layout.original_toast_view);
                                MedalJudge();
                                break;
                            } else {
                                OriginalToast.makeText(Tabacco.this, "銘柄設定で\n煙草を登録して", 1500).show(R.layout.original_toast_view2);
                                break;
                            }
                        case R.id.setting_button:
                            Intent intent1 = new Intent(getApplication(), TabaccoSetting.class);
                            Intent me = getIntent();
                            startActivity(intent1);
                            overridePendingTransition(0, 0);
                            intent1.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            overridePendingTransition(0, 0);
                            startActivity(me);
                            break;
                        case R.id.menu_button:
                            Intent intent2 = new Intent(getApplication(), MainActivity.class);
                            startActivity(intent2);
                            break;
                        case R.id.frog_attack:
                            OriginalToast.makeText(Tabacco.this, "痛ぇ!!\n殴るな!!", 200).show(R.layout.original_toast_view);
                            break;
                        case R.id.rabbit_attack:
                            Cursor cursor = db.rawQuery("SELECT nickname FROM profile", null);
                            cursor.moveToFirst();
                            OriginalToast.makeText(Tabacco.this, cursor.getString(0) + "さん\n吸ってるぅ?", 200).show(R.layout.original_toast_view2);
                            cursor.close();
                    }
                }
            }
        };
    }

    public String getBrandName(String num) {
        long recodeCount = DatabaseUtils.queryNumEntries(db, "tabacco_info", "button_num = ?", new String[]{num});
        Log.d("select", "recodeCount : " + recodeCount);
        if (recodeCount != 0) {
            Cursor cursor = db.query(
                    "tabacco_info",
                    new String[]{"brand"},
                    "button_num = ?",
                    new String[]{num},
                    null,
                    null,
                    null
            );
            cursor.moveToFirst();
            String name = cursor.getString(0);
            cursor.close();
            return name;
        } else {
            return "設定されて\nいません";
        }
    }

    public void MedalJudge() {

        ContentValues values = new ContentValues();
        values.put("done", Boolean.TRUE);
        Cursor cursor1 = db.rawQuery("SELECT sum(one_price) FROM smoke_log NATURAL INNER JOIN tabacco_info", null);
        cursor1.moveToFirst();
        int totalprice = cursor1.getInt(0);
        cursor1.close();

        Cursor cursor2 = db.rawQuery("SELECT count(*) FROM smoke_log NATURAL INNER JOIN tabacco_info", null);
        cursor2.moveToFirst();
        int totalcount = cursor2.getInt(0);
        cursor2.close();


        if (totalcount == 1000) {
            db.update("medal_info", values, "medal_id = 10", null);
        } else if (totalcount == 777) {
            db.update("medal_info", values, "medal_id = 9", null);
        } else if (totalcount == 500) {
            db.update("medal_info", values, "medal_id = 8", null);
        } else if (totalcount == 250) {
            db.update("medal_info", values, "medal_id = 7", null);
        } else if (totalcount == 150) {
            db.update("medal_info", values, "medal_id = 6", null);
        } else if (totalcount == 100) {
            db.update("medal_info", values, "medal_id = 5", null);
        } else if (totalcount == 50) {
            db.update("medal_info", values, "medal_id = 4", null);
        } else if (totalcount == 20) {
            db.update("medal_info", values, "medal_id = 3", null);
        } else if (totalcount == 5) {
            db.update("medal_info", values, "medal_id = 2", null);
        } else if (totalcount == 1) {
            db.update("medal_info", values, "medal_id = 1", null);
        }

        if (totalprice >= 500) {
            db.update("medal_info", values, "medal_id = 11", null);
        }

        if (totalprice >= 2000) {
            db.update("medal_info", values, "medal_id = 12", null);
        }

        if (totalprice >= 5000) {
            db.update("medal_info", values, "medal_id = 13", null);
        }

        if (totalprice >= 10000) {
            db.update("medal_info", values, "medal_id = 14", null);
        }

        if (totalprice >= 15000) {
            db.update("medal_info", values, "medal_id = 15", null);
        }

        if (totalprice >= 25000) {
            db.update("medal_info", values, "medal_id = 16", null);
        }

        if (totalprice >= 50000) {
            db.update("medal_info", values, "medal_id = 17", null);
        }

        if (totalprice >= 77777) {
            db.update("medal_info", values, "medal_id = 18", null);
        }

        if (totalprice >= 100000) {
            db.update("medal_info", values, "medal_id = 19", null);
        }

        if (totalprice >= 500000) {
            db.update("medal_info", values, "medal_id = 20", null);
        }

    }

    public float GetTabaccoTax(String num) {
        long recodeCount = DatabaseUtils.queryNumEntries(db, "tabacco_info", "button_num = ?", new String[]{num});
        Log.d("select", "recodeCount : " + recodeCount);
        if (recodeCount != 0) {
            Cursor cursor = db.query(
                    "tabacco_info",
                    new String[]{"tax"},
                    "button_num = ?",
                    new String[]{num},
                    null,
                    null,
                    null
            );
            cursor.moveToFirst();
            float tax = cursor.getFloat(0);
            cursor.close();
            return tax;
        } else {
            return 0;
        }
    }

    public int GetTabaccoID(String num) {
        Cursor cursor = db.query(
                "tabacco_info",
                new String[]{"tabacco_id"},
                "button_num = ?",
                new String[]{num},
                null,
                null,
                null
        );
        cursor.moveToFirst();
        int tabacco_id = cursor.getInt(0);
        cursor.close();
        return tabacco_id;
    }

    public float GetTaxPoint() {
        Cursor cursor = db.query(
                "tax_point",
                new String[]{"point"},
                "point_id = 1",
                null,
                null,
                null,
                null
        );
        cursor.moveToFirst();
        float point = cursor.getFloat(0);
        cursor.close();
        return point;
    }

    public int adultDay() {
        try {
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
            String str = cursor.getString(0);
            SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date birth = sdFormat.parse(str);
            System.out.println("生年月日 : " + birth);
            long dateTimeTo = birth.getTime();
            long dateTimeFrom = d1.getTime();
            long dayDiff = ( dateTimeTo - dateTimeFrom ) / (1000 * 60 * 60 * 24) + 1;
            System.out.println("差分日数 : " + dayDiff);
            int day = (int) dayDiff;
            cursor.close();
            return day;
        } catch (ParseException e) {
            System.out.println(">>>>>>>>>>>ERROR>>>>>>>>>>");
        }

        return 0;
    }
}