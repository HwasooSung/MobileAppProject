package com.example.administrator.findmine;

import android.content.Context;
import android.widget.Button;

public class MineButton extends Button {

    String value;

    boolean open;
    public MineButton(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        value = "0";
        open = false;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
