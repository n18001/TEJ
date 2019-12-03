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


public class NotTabaccoSetting extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@NonNull Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.MyAlertDialogStyle);


        TextView tvMessage = new TextView(getContext());
        tvMessage.setText("銘柄が設定されていません");
        tvMessage.setGravity(Gravity.CENTER);
        tvMessage.setPadding(25,25,25,50);
        tvMessage.setTextSize(35);
        tvMessage.setTextColor(Color.BLACK);


        builder.setView(tvMessage)
                .setPositiveButton("設定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // FIRE ZE MISSILES!
                        Intent intent = new Intent(getActivity(), TabaccoSetting.class);
                        startActivity(intent);
                    }
                })
                .setNeutralButton("やめる", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                        System.out.println("hoge");
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}