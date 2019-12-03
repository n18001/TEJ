package com.example.ejtabacco;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBOpenHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Tabacco.db";

    //profile
    private static final String SQL_CREATE_PROFILE =
            "CREATE TABLE IF NOT EXISTS profile (" +
                    "user_id INTEGER PRIMARY KEY," +
                    "birth DATE," +
                    "nickname TEXT," +
                    "uuid INTEGER)";

    private static final String SQL_DELETE_PROFILE =
            "DROP TABLE IF EXISTS profile";

    //tabacco_info
    private static final String SQL_CREATE_TABACCO_INFO =
            "CREATE TABLE IF NOT EXISTS tabacco_info (" +
                    "tabacco_id INTEGER PRIMARY KEY," +
                    "brand TEXT," +
                    "box_num INTEGER," +
                    "price INTEGER," +
                    "one_price INTEGER," +
                    "tax INTEGER," +
                    "button_num INTEGER DEFAULT 0)";

    private static final String SQL_DELETE_TABACCO_INFO =
            "DROP TABLE IF EXISTS tabacco_info";

    //smoke_log
    private static final String SQL_CREATE_SMOKE_LOG =
            "CREATE TABLE IF NOT EXISTS smoke_log (" +
                    "log_id INTEGER PRIMARY KEY," +
                    "time DEFAULT (datetime('now','localtime'))," +
                    "user_id INTEGER," +
                    "tabacco_id INTEGER)";

    private static final String SQL_DELETE_SMOKE_LOG =
            "DROP TABLE IF EXISTS smoke_log";

    //memory
    private static final String SQL_CREATE_MEMORY =
            "CREATE TABLE IF NOT EXISTS memory (" +
                    "memo_id INTEGER PRIMARY KEY," +
                    "total_price INTEGER," +
                    "total_tabacco INTEGER," +
                    "total_tax INTEGER)";

    private static final String SQL_DELETE_MEMORY =
            "DROP TABLE IF EXISTS memory";

    //medal_info
    private static final String SQL_CREATE_MEDAL_INFO =
            "CREATE TABLE IF NOT EXISTS medal_info (" +
                    "medal_id INTEGER PRIMARY KEY," +
                    "title TEXT," +
                    "content TEXT," +
                    "done BOOLEAN DEFAULT 'false')";

    private static final String SQL_DELETE_MEDAL_INFO =
            "DROP TABLE IF EXISTS medal_info";

    //tax_point
    private static final String SQL_CREATE_TAX_POINT =
            "CREATE TABLE IF NOT EXISTS tax_point (" +
                    "point_id INTEGER PRIMARY KEY," +
                    "point INTEGER)";

    private static final String SQL_DELETE_TAX_POINT =
            "DROP TABLE IF EXISTS tax_point";

    //monster
    private static final String SQL_CREATE_MONSTER =
            "CREATE TABLE IF NOT EXISTS monster (" +
                    "monster_id INTEGER PRIMARY KEY," +
                    "exp INTEGER DEFAULT 0," +
                    "lover_id INTEGER," +
                    "name TEXT)";

    private static final String SQL_DELETE_MONSTER =
            "DROP TABLE IF EXISTS monster";

    //exp
    private static final String SQL_CREATE_EXP =
            "CREATE TABLE IF NOT EXISTS exp (" +
                    "lv INTEGER PRIMARY KEY," +
                    "total_exp INTEGER)";

    private static final String SQL_DELETE_EXP =
            "DROP TABLE IF EXISTS exp";



    DBOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void  onCreate(SQLiteDatabase db) {
        // テーブル作成
        db.execSQL(SQL_CREATE_PROFILE);
        db.execSQL(SQL_CREATE_TABACCO_INFO);
        db.execSQL(SQL_CREATE_SMOKE_LOG);
        db.execSQL(SQL_CREATE_MEMORY);

        db.execSQL(SQL_CREATE_MEDAL_INFO);

        db.execSQL(SQL_CREATE_TAX_POINT);
        db.execSQL(SQL_CREATE_MONSTER);
        db.execSQL(SQL_CREATE_EXP);

        db.execSQL("INSERT INTO tax_point(point_id, point) values(1, 0);");
//        db.execSQL("INSERT INTO monster(monster_id, exp, name) values(1, 0, null);");

//        db.execSQL("INSERT INTO exp(lv, total_exp) values(1, 0);");
//        db.execSQL("INSERT INTO exp(lv, total_exp) values(2, 10);");
//        db.execSQL("INSERT INTO exp(lv, total_exp) values(3, 20);");
//        db.execSQL("INSERT INTO exp(lv, total_exp) values(4, 40);");
//        db.execSQL("INSERT INTO exp(lv, total_exp) values(5, 80);");
//        db.execSQL("INSERT INTO exp(lv, total_exp) values(6, 160);");
//        db.execSQL("INSERT INTO exp(lv, total_exp) values(7, 320);");
//        db.execSQL("INSERT INTO exp(lv, total_exp) values(8, 640);");
//        db.execSQL("INSERT INTO exp(lv, total_exp) values(9, 1280);");
//        db.execSQL("INSERT INTO exp(lv, total_exp) values(10, 2560);");

        db.execSQL("INSERT INTO medal_info(medal_id, title, content) values(1, 'はじめの一歩', '総本数が1本以上で達成');");
        db.execSQL("INSERT INTO medal_info(medal_id, title, content) values(2, '煙草道', '総本数が5本以上で達成');");
        db.execSQL("INSERT INTO medal_info(medal_id, title, content) values(3, '壱箱', '総本数が20本以上で達成');");
        db.execSQL("INSERT INTO medal_info(medal_id, title, content) values(4, '接吻の心得', '総本数が50本以上で達成');");
        db.execSQL("INSERT INTO medal_info(medal_id, title, content) values(5, 'もう戻れない', '総本数が100本以上で達成');");
        db.execSQL("INSERT INTO medal_info(medal_id, title, content) values(6, '煙草愛', '総本数が150本以上で達成');");
        db.execSQL("INSERT INTO medal_info(medal_id, title, content) values(7, '体大事', '総本数が250本以上で達成');");
        db.execSQL("INSERT INTO medal_info(medal_id, title, content) values(8, '煙草覇者', '総本数が500本以上で達成');");
        db.execSQL("INSERT INTO medal_info(medal_id, title, content) values(9, '七七七', '総本数が777本以上で達成');");
        db.execSQL("INSERT INTO medal_info(medal_id, title, content) values(10, '安らかに眠れ', '総本数が1000本以上で達成');");
        db.execSQL("INSERT INTO medal_info(medal_id, title, content) values(11, '輝かしい未来', '総額が500円以上で達成');");
        db.execSQL("INSERT INTO medal_info(medal_id, title, content) values(12, '煙草小僧', '総額が2,000円以上で達成');");
        db.execSQL("INSERT INTO medal_info(medal_id, title, content) values(13, '煙草大人', '総額が5,000円以上で達成');");
        db.execSQL("INSERT INTO medal_info(medal_id, title, content) values(14, '最高の嗜好品', '総額が10,000円以上で達成');");
        db.execSQL("INSERT INTO medal_info(medal_id, title, content) values(15, '煙草社員', '総額が15,000円以上で達成');");
        db.execSQL("INSERT INTO medal_info(medal_id, title, content) values(16, '煙草社長', '総額が25,000円以上で達成');");
        db.execSQL("INSERT INTO medal_info(medal_id, title, content) values(17, '煙草大臣', '総額が50,000円以上で達成');");
        db.execSQL("INSERT INTO medal_info(medal_id, title, content) values(18, '幸運', '総額が77,777円以上で達成');");
        db.execSQL("INSERT INTO medal_info(medal_id, title, content) values(19, '煙草王', '総額が100,000円以上で達成');");
        db.execSQL("INSERT INTO medal_info(medal_id, title, content) values(20, '煙草神', '総額が500,000円以上で達成');");
        db.execSQL("INSERT INTO medal_info(medal_id, title, content) values(21, '初対面', '好感度1で達成');");
        db.execSQL("INSERT INTO medal_info(medal_id, title, content) values(22, '貢ぎ癖', '好感度10で達成');");
        db.execSQL("INSERT INTO medal_info(medal_id, title, content) values(23, '貢ぎ依存症', '好感度25で達成');");
        db.execSQL("INSERT INTO medal_info(medal_id, title, content) values(24, '好印象', '好感度50で達成');");
        db.execSQL("INSERT INTO medal_info(medal_id, title, content) values(25, '愛人', '好感度75で達成');");
        db.execSQL("INSERT INTO medal_info(medal_id, title, content) values(26, '心模様', '好感度100で達成');");
        db.execSQL("INSERT INTO medal_info(medal_id, title, content) values(27, '恋心', '好感度250で達成');");
        db.execSQL("INSERT INTO medal_info(medal_id, title, content) values(28, '恋人', '好感度500で達成');");
        db.execSQL("INSERT INTO medal_info(medal_id, title, content) values(29, '嫁', '好感度700で達成');");
        db.execSQL("INSERT INTO medal_info(medal_id, title, content) values(30, '別れ', '好感度1000で達成');");



        initialize(db);

        Log.d("debug", "onCreate(SQLiteDatabase db)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // アップデートの判別
        db.execSQL(SQL_DELETE_PROFILE);
        db.execSQL(SQL_DELETE_TABACCO_INFO);
        db.execSQL(SQL_DELETE_SMOKE_LOG);
        db.execSQL(SQL_DELETE_MEMORY);
        db.execSQL(SQL_DELETE_MEDAL_INFO);

        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    private void initialize(SQLiteDatabase db) {
        int preExp = 0;
        for (int i = 1; i <= 1000; i++) {
            ContentValues values = new ContentValues();
            values.put("lv", i);
            if (i > 1) {
                values.put("total_exp", (i - 2) * 2 + 5 + preExp);
                preExp = (i - 2) * 2 + 5 + preExp;
            } else {
                values.put("total_exp", 0);
            }
            db.insert("exp", null, values);
        }
    }

}