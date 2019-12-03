package com.example.ejtabacco;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;


public class HintDialog extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@NonNull Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.MyAlertDialogStyle);
        TextView tvtitle = new TextView(getContext());
        tvtitle.setText("助言");
        tvtitle.setGravity(Gravity.CENTER);
        tvtitle.setPadding(25, 25,0,0);
        tvtitle.setTextSize(35);
        tvtitle.setTextColor(Color.RED);


        String messageText = "○四体から一体だけ選べる\n○名前を入力し,登録する\n○二度と変えられない";
        TextView tvMessage = new TextView(getContext());
        tvMessage.setText(messageText);
        tvMessage.setGravity(Gravity.TOP);
        tvMessage.setPadding(25, 25, 0, 50);
        tvMessage.setTextSize(35);
        tvMessage.setTextColor(Color.BLACK);


        builder.setCustomTitle(tvtitle);
        builder.setView(tvMessage)
                .setPositiveButton("閉じる", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}