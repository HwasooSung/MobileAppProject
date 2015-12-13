package com.example.administrator.findmine;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.os.Vibrator;
import android.view.View;

public class OnMineButtonClickListener implements View.OnClickListener{

    MineButton button;
    Context context;

    public OnMineButtonClickListener(Context c, MineButton b) {
        // TODO Auto-generated constructor stub
        button = b;
        context = c;


    }
    public void onClick(View v) {
        // TODO Auto-generated method stub

        button.setClickable(false);

        String val = button.getValue();

        switch(val) {
            case "0" :
                MainActivity.numOfNotMine--;
                button.setTextColor(Color.WHITE);
                button.setText(val);
                break;
            case "1" :
                MainActivity.numOfNotMine--;
                button.setTextColor(Color.RED);
                button.setText(val);
                break;
            case "2" :
                MainActivity.numOfNotMine--;
                button.setTextColor(Color.YELLOW);
                button.setText(val);
                break;
            case "3" :
                MainActivity.numOfNotMine--;
                button.setTextColor(Color.GREEN);
                button.setText(val);
                break;
            case "4" :
                MainActivity.numOfNotMine--;
                button.setTextColor(Color.MAGENTA);
                button.setText(val);
                break;
            case "5" :
                MainActivity.numOfNotMine--;
                button.setTextColor(Color.BLUE);
                button.setText(val);
                break;
            case "6" :
                MainActivity.numOfNotMine--;
                button.setTextColor(0x66ccff);
                button.setText(val);
                break;
            case "7" :
                MainActivity.numOfNotMine--;
                button.setTextColor(0xff99cc);
                button.setText(val);
                break;
            case "8":
                MainActivity.numOfNotMine--;
                button.setTextColor(0x660066);
                button.setText(val);
                break;
            case "@":
                button.setBackgroundResource(R.drawable.bomb);
                Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
                vibrator.vibrate(100);
                break;
        }
    }

}
