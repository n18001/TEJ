package com.example.ejtabacco;

import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.os.Handler;
import android.text.Layout;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

public class OriginalToast {

    private static Context _context;
    private static String  _message;
    private static int     _dispTime;

    public static OriginalToast makeText(Context context, String message, int dispTime){

        _context  = context;
        _message  = message;
        _dispTime = dispTime;

        return new OriginalToast();
    }

    public void show(int hoge){

        // ルートビューを取得する
        final View rootView = ((Activity)_context).findViewById(android.R.id.content).getRootView();

        // 自作Toast用(手順2で作成)のViewを取得する
        final RelativeLayout layout = (RelativeLayout) (((Activity)_context).getLayoutInflater().inflate(hoge, null));

        // 自作Toast用のViewからメッセージを表示するためのTextViewを取得する
        TextView textView =  (TextView)layout.findViewById(R.id.textMessage);

        // 表示するテキストを設定する
        textView.setText(_message);

        // ルートビューに自作Toast用のViewをaddする
        ((ViewGroup)rootView).addView(layout, new RelativeLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT));

        // 自作Toastの削除を予約する
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                ((ViewGroup)rootView).removeView(layout);

            }
        }, _dispTime);
    }
}