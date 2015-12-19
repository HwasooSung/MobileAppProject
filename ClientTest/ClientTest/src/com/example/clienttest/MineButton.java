package com.example.clienttest;

import android.content.Context;
import android.widget.Button;

public class MineButton extends Button {

	boolean open;
	int mineValue;
	private int x, y;

	public int getButtonX() {
		return x;
	}

	public void setButtonX(int x) {
		this.x = x;
	}

	public int getButtonY() {
		return y;
	}

	public void setButtonY(int y) {
		this.y = y;
	}

	public MineButton(Context context) {
		super(context);
		// TODO Auto-generated constructor stub

		open = false;
		mineValue = 0;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public int getMineValue() {
		return mineValue;
	}

	public void setMineValue(int mineValue) {
		this.mineValue = mineValue;
	}

}
