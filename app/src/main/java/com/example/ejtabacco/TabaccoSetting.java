package com.example.ejtabacco;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;


public class TabaccoSetting extends AppCompatActivity {
    private DBOpenHelper helper;
    private SQLiteDatabase db;
    private EditText editNo1Name, editNo1Price, editNo1Num,
            editNo2Name, editNo2Price, editNo2Num,
            editNo3Name, editNo3Price, editNo3Num;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tabacco_setting);
        // no1 tabacco
        editNo1Name = findViewById(R.id.no1_name);
        editNo1Price = findViewById(R.id.no1_price);
        editNo1Num = findViewById(R.id.no1_num);
        // no2 tabacco
        editNo2Name = findViewById(R.id.no2_name);
        editNo2Price = findViewById(R.id.no2_price);
        editNo2Num = findViewById(R.id.no2_num);
        // no3 tabacco
        editNo3Name = findViewById(R.id.no3_name);
        editNo3Price = findViewById(R.id.no3_price);
        editNo3Num = findViewById(R.id.no3_num);
        if(helper == null){
            helper = new DBOpenHelper(getApplicationContext());
        }
        if(db == null){
            db = helper.getWritableDatabase();
        }
        setTextView(editNo1Name, editNo1Price, editNo1Num, "1");
        setTextView(editNo2Name, editNo2Price, editNo2Num, "2");
        setTextView(editNo3Name, editNo3Price, editNo3Num, "3");
        Button returnButton = findViewById(R.id.return_button);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tabaccoInfoSet(editNo1Name, editNo1Price, editNo1Num, "1");
                tabaccoInfoSet(editNo2Name, editNo2Price, editNo2Num, "2");
                tabaccoInfoSet(editNo3Name, editNo3Price, editNo3Num, "3");
                finish();
            }
        });
    }
    public void setTextView(EditText name, EditText price, EditText boxnum, String num) {
        long recodeCount = DatabaseUtils.queryNumEntries(db, "tabacco_info", "button_num = ?", new String[]{num});
        Log.d("select", "recodeCount : " + recodeCount);
        if(recodeCount != 0){
            Cursor cursor = db.query(
                    "tabacco_info",
                    new String[] { "brand", "price", "box_num" },
                    "button_num = ?",
                    new String[]{num},
                    null,
                    null,
                    null
            );
            cursor.moveToFirst();
            name.setText(cursor.getString(0));
            price.setText(cursor.getString(1));
            boxnum.setText(cursor.getString(2));
            cursor.close();
        }
    }
    public void tabaccoInfoSet(EditText name, EditText price, EditText boxnum, String num) {
        ContentValues values = new ContentValues();
        ContentValues values_old = new ContentValues();
        values_old.put("button_num", 0);
        long recodeCount = DatabaseUtils.queryNumEntries(db, "tabacco_info", "button_num = ?", new String[]{num});
        if (recodeCount != 0 && !name.getText().toString().isEmpty() && !price.getText().toString().isEmpty() && !boxnum.getText().toString().isEmpty()) {
            String brand_info = name.getText().toString();
            int price_info = Integer.valueOf(price.getText().toString());
            int boxnum_info = Integer.valueOf(boxnum.getText().toString());
            Cursor cursor = db.rawQuery("SELECT * FROM tabacco_info WHERE button_num = ?", new String[]{num});
            cursor.moveToFirst();
            String query_brand = cursor.getString(1);
            int query_boxnum = cursor.getInt(2);
            int query_price = cursor.getInt(3);
            cursor.close();
            if (boxnum_info > 0 && (!brand_info.equals(query_brand) || price_info != query_price || boxnum_info != query_boxnum)) {
                db.update("tabacco_info", values_old, "button_num = ?", new String[]{num});
                values.put("brand", brand_info);
                values.put("price", price_info);
                values.put("box_num", boxnum_info);
                values.put("one_price", price_info / boxnum_info);
                values.put("tax", price_info * 0.62 / boxnum_info);
                values.put("button_num", num);
                db.insert("tabacco_info", null,values);
            }
        }else if (recodeCount != 0 && name.getText().toString().isEmpty()  && price.getText().toString().isEmpty() && boxnum.getText().toString().isEmpty()) {
            db.update("tabacco_info",values_old, "button_num = ?", new String[]{num});
        } else if (recodeCount == 0 && !name.getText().toString().isEmpty() && !price.getText().toString().isEmpty() && !boxnum.getText().toString().isEmpty()) {
            String brand_info = name.getText().toString();
            int price_info = Integer.valueOf(price.getText().toString());
            int boxnum_info = Integer.valueOf(boxnum.getText().toString());
            if (boxnum_info > 0) {
                values.put("brand", brand_info);
                values.put("price", price_info);
                values.put("box_num", boxnum_info);
                values.put("one_price", price_info / boxnum_info);
                values.put("tax", price_info * 0.62 / boxnum_info);
                values.put("button_num", num);
                db.insert("tabacco_info", null, values);
            }
        }
    }
}