package com.example.ejtabacco;


import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class Monster extends AppCompatActivity {
    private DBOpenHelper helper;
    private SQLiteDatabase db;
    private ProgressBar progressBar;
    private Integer val = 0;
    private Integer require_exp = 0;


    @Override
    protected void onRestart() {
        super.onRestart();
        long recodeCountMonster = DatabaseUtils.queryNumEntries(db, "monster", null);
        if (recodeCountMonster == 0) {
            finish();
        } else {
            onLoad();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.monster);
        onLoad();
    }


    protected void onLoad() {
        if (helper == null) {
            helper = new DBOpenHelper(getApplicationContext());
        }
        if (db == null) {
            db = helper.getWritableDatabase();
        }

        progressBar = findViewById(R.id.favo_bar);

        TextView level = findViewById(R.id.level_text);
        TextView rePoint = findViewById(R.id.rePoint);
        ImageButton girl = findViewById(R.id.girl);

        long recodeCountMonster = DatabaseUtils.queryNumEntries(db, "monster", null);
        if (recodeCountMonster == 0) {
            Intent intent;
            intent = new Intent(Monster.this, CharacterSetting.class);
            startActivity(intent);
        } else {
            try {
                int now_level = GetNowLevel();
                int now_level_total_exp = GetTotalExp(now_level);
                int next_level_total_exp = GetTotalExp(now_level + 1);
                require_exp = next_level_total_exp - now_level_total_exp;
                progressBar.setMax(require_exp);
                val = GetMonsterExp() - now_level_total_exp;
                progressBar.setProgress(val);
                String levelText = "Lv." + now_level;
                level.setText(levelText);

                String rePointText = ((require_exp / 4) + 1) + "えん";
                rePoint.setText(rePointText);

                if (loverImageId() == 0) {
                    girl.setImageResource(R.drawable.red_stand);
                }
                if (loverImageId() == 1) {
                    girl.setImageResource(R.drawable.blue_stand);
                }
                if (loverImageId() == 2) {
                    girl.setImageResource(R.drawable.girl1_stand);
                }
                if (loverImageId() == 3) {
                    girl.setImageResource(R.drawable.girl2_stand);
                }

                TextView chat = findViewById(R.id.monster_text);
                chat.setText(loverName());


            } catch (CursorIndexOutOfBoundsException e) {
                Intent intent = new Intent(Monster.this, Leave.class);
                startActivity(intent);
                finish();
            }
        }

        // % text
        TextView favoText = findViewById(R.id.favo_rate);

        float monster_exp = (float) val / require_exp * 100;
        // String rateText = String.format("%.0f％", monster_exp);
        String rateText = val + " / " + require_exp;

        favoText.setText(rateText);

        findViewById(R.id.grow_button).setOnClickListener(monsterScreenButton());
        findViewById(R.id.girl).setOnClickListener(monsterScreenButton());
        findViewById(R.id.tabacco_button1).setOnClickListener(monsterScreenButton());
        findViewById(R.id.tabacco_button2).setOnClickListener(monsterScreenButton());
        findViewById(R.id.tabacco_button3).setOnClickListener(monsterScreenButton());


        TextView point_text = findViewById(R.id.point_text);
        StringBuilder point_builder = new StringBuilder();
        int point = GetPoint();
        point_builder.append(point);
        point_builder.append(" えん");
        point_text.setText(point_builder);


    }


    View.OnClickListener monsterScreenButton() {
        return new View.OnClickListener() {
            public void onClick(View v) {
                TextView chat = findViewById(R.id.monster_text);
                // list setting
                List<String> girlTalkThx = GetTalkText("girl_thx.txt");
                List<String> girlTalkTips = GetTalkText("girl_tips.txt");
                List<String> boyTalkThx = GetTalkText("boy_thx.txt");
                List<String> boyTalkTips = GetTalkText("boy_tips.txt");
                // Brand name setting
                String txt1 = getBrandName("1");
                String txt2 = getBrandName("2");
                String txt3 = getBrandName("3");
                // Dialog setting
                SmokeTabaccoDialogFragment smokeDialog = new SmokeTabaccoDialogFragment();
                smokeDialog.setCallback(new SmokeTabaccoDialogFragment.Callback() {
                    @Override
                    public void run() {
                        onLoad();
                    }
                });
                NotTabaccoSetting notDialog = new NotTabaccoSetting();

                // R.id switch
                if (v != null) {
                    switch (v.getId()) {
                        case R.id.grow_button:
                            Integer require_point = (require_exp / 4) + 1;
                            int point = GetPoint();
                            if (point >= require_point) {
                                ContentValues point_values = new ContentValues();
                                point_values.put("point", point - require_point);
                                db.update("tax_point", point_values, "point_id = 1", null);

                                ContentValues monster_values = new ContentValues();
                                int monster_exp = GetMonsterExp();
                                monster_values.put("exp", monster_exp + require_point);
                                db.update("monster", monster_values, null, null);

                                onLoad();

                                String txt = "";
                                if ((loverImageId() == 0) || (loverImageId() == 1)) {
                                    txt = (loverName() + "\n") + boyTalkThx.get((int) Math.floor(Math.random() * boyTalkThx.size()));
                                } else {
                                    txt = (loverName() + "\n") + girlTalkThx.get((int) Math.floor(Math.random() * girlTalkThx.size()));
                                }
                                chat.setText(txt);

                            } else {
                                String txt = "";
                                if ((loverImageId() == 0) || (loverImageId() == 1)) {
                                    txt = (loverName() + "\n") + "「金が足りてねぇ、\nもっと吸え。」";
                                } else {
                                    txt = (loverName() + "\n") + "「お金が足りないわ、\nもっと吸って。」";
                                }
                                chat.setText(txt);
                            }
                            monsterLevel();
                            break;

                        case R.id.girl:
                            String txt = "";
                            if ((loverImageId() == 0) || (loverImageId() == 1)) {
                                txt = (loverName() + "\n") + boyTalkTips.get((int) Math.floor(Math.random() * boyTalkTips.size()));
                            } else {
                                txt = (loverName() + "\n") + girlTalkTips.get((int) Math.floor(Math.random() * girlTalkTips.size()));
                            }
                            chat.setText(txt);
                            break;

                        case R.id.tabacco_button1:
                            if (txt1 != null) {
                                smokeDialog.setMessage(txt1 + "を吸いますか？", "1");
                                smokeDialog.show(getSupportFragmentManager(), null);
                            } else {
                                notDialog.show(getSupportFragmentManager(), null);
                            }
                            break;

                        case R.id.tabacco_button2:
                            if (txt2 != null) {
                                smokeDialog.setMessage(txt2 + "を吸いますか？", "2");
                                smokeDialog.show(getSupportFragmentManager(), null);
                            } else {
                                notDialog.show(getSupportFragmentManager(), null);
                            }
                            break;

                        case R.id.tabacco_button3:
                            if (txt3 != null) {
                                smokeDialog.setMessage(txt3 + "を吸いますか？", "3");
                                smokeDialog.show(getSupportFragmentManager(), null);
                            } else {
                                notDialog.show(getSupportFragmentManager(), null);
                            }
                            break;

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
            return null;
        }
    }

    public int GetPoint() {
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
        int point = cursor.getInt(0);
        cursor.close();
        return point;
    }


    public int GetMonsterExp() {
        Cursor cursor = db.query(
                "monster",
                new String[]{"exp"},
                "monster_id = 1",
                null,
                null,
                null,
                null
        );

        cursor.moveToFirst();
        int monster_exp = cursor.getInt(0);
        cursor.close();
        return monster_exp;
    }

    public int GetNowLevel() {
        /*
        Cursor cursor = db.query(
                "exp,monster",
                new String[]{"MIN(lv)"},
                "monster.exp >= exp.total_exp",
                null,
                null,
                null,
                null
        );
         */
        Cursor cursor = db.rawQuery("SELECT MAX(lv) FROM exp, monster WHERE monster.exp >= exp.total_exp", null);

        cursor.moveToFirst();
        int now_level = cursor.getInt(0);
        cursor.close();
        return now_level;
    }


    public void monsterLevel() {
        ContentValues values = new ContentValues();
        values.put("done", Boolean.TRUE);
        int nowLevel = GetNowLevel();
        if (nowLevel == 1000) {
            db.update("medal_info", values, "medal_id = 30", null);
        } else if (nowLevel == 700) {
            db.update("medal_info", values, "medal_id = 29", null);
        } else if (nowLevel == 500) {
            db.update("medal_info", values, "medal_id = 28", null);
        } else if (nowLevel == 250) {
            db.update("medal_info", values, "medal_id = 27", null);
        } else if (nowLevel == 100) {
            db.update("medal_info", values, "medal_id = 26", null);
        } else if (nowLevel == 75) {
            db.update("medal_info", values, "medal_id = 25", null);
        } else if (nowLevel == 50) {
            db.update("medal_info", values, "medal_id = 24", null);
        } else if (nowLevel == 25) {
            db.update("medal_info", values, "medal_id = 23", null);
        } else if (nowLevel == 10) {
            db.update("medal_info", values, "medal_id = 22", null);
        } else if (nowLevel == 1) {
            db.update("medal_info", values, "medal_id = 21", null);
        }
    }

    public int GetTotalExp(int level) {
        Cursor cursor = db.query(
                "exp",
                new String[]{"total_exp"},
                "lv = ?",
                new String[]{String.valueOf(level)},
                null,
                null,
                null
        );
        cursor.moveToFirst();
        int total_exp = cursor.getInt(0);
        cursor.close();
        return total_exp;
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

    public int loverImageId() {
        Cursor cursor = db.rawQuery("SELECT lover_id FROM monster WHERE monster_id = 1", null);
        cursor.moveToFirst();
        int loverId = cursor.getInt(0);
        cursor.close();
        return loverId;
    }

    public String loverName() {
        Cursor cursor = db.rawQuery("SELECT name From monster WHERE monster_id = 1", null );
        cursor.moveToFirst();
        String loverName = cursor.getString(0);
        cursor.close();
        return loverName;
    }

}
