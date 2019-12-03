package com.example.ejtabacco;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.sip.SipSession;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;

import org.w3c.dom.Text;

import java.util.zip.Inflater;

public class SmokeTabaccoDialogFragment extends DialogFragment {
    private DBOpenHelper helper;
    private SQLiteDatabase db;
    private Callback callback;
    private String message = "";
    private String number = "";


    interface Callback {
        public void run();
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.MyAlertDialogStyle);


//        TextView tvtitle = new TextView(getContext());
//        tvtitle.setText("確認");
//        tvtitle.setGravity(Gravity.TOP);
//        tvtitle.setPadding(25, 25,0,0);
//        tvtitle.setTextSize(35);
//        tvtitle.setTextColor(Color.RED);


        TextView tvMessage = new TextView(getContext());
        tvMessage.setText(message);
        tvMessage.setGravity(Gravity.TOP);
        tvMessage.setPadding(25,35,25,50);
        tvMessage.setTextSize(35);
        tvMessage.setTextColor(Color.BLACK);


//        builder.setCustomTitle(tvtitle);
        builder.setView(tvMessage)
                .setPositiveButton(R.string.fire, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ContentValues values = new ContentValues();
                        ContentValues point_values = new ContentValues();

                        if (helper == null) {
                            helper = new DBOpenHelper(getContext());
                        }
                        if (db == null) {
                            db = helper.getWritableDatabase();
                        }
                        // FIRE ZE MISSILES!
                        long recodeCount = DatabaseUtils.queryNumEntries(db, "tabacco_info", "button_num = " + number);
                        if (recodeCount != 0) {
                            int tabacco_id = GetTabaccoID(number);
                            values.put("user_id", 1);
                            values.put("tabacco_id", tabacco_id);
                            db.insert("smoke_log", null, values);
                            float point = GetTaxPoint();
                            float tax = GetTabaccoTax(number);
                            point_values.put("point", point + tax);
                            db.update("tax_point", point_values, "point_id =1", null);
                            MedalJudge();
                        }
                    }
                })
                .setNeutralButton("やめる", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });


        return builder.create();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (callback != null) {
            System.out.println("SmokeDialogOnDestroy and execute callback.run");
            callback.run();
        }
    }


    public void setMessage(String message, String number) {
        this.message = message;
        this.number = number;
    }


    public void setCallback(Callback callback) {
        this.callback = callback;
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
}