package com.example.clienttest;

import java.io.BufferedReader;
import java.io.BufferedWriter;

class Data {
	private volatile static Data data;
	int[][] mineValue; 
	private int row,col;
	private int gameLevel=0;
	public final static int EASY_ROW = 10;
	public final static int EASY_COLUMN = 6;
	public String returnMsg = null;
	public final static int MEDIUM_ROW = 9;
	public final static int MEDIUM_COLUMN = 9;

	public final static int DIFFICULT_ROW = 15;
	public final static int DIFFICULT_COLUMN = 9;
	private BufferedReader networkReader;
	private BufferedWriter networkWriter;
	private int buttonX,buttonY;
	
	private boolean startFlag = false;
	public static Data getInstance(){
		if(data==null){
			synchronized(Data.class){
				if(data==null){
					data = new Data();
				}
			}
		}
		return data;
	}
	
	
	public int getButtonX() {
		return buttonX;
	}


	public void setButtonX(int buttonX) {
		this.buttonX = buttonX;
	}


	public int getButtonY() {
		return buttonY;
	}


	public void setButtonY(int buttonY) {
		this.buttonY = buttonY;
	}


	public boolean getStartFlag(){
		return startFlag;
	}
	public void setStartFlag(boolean flag){
		this.startFlag = flag;
	}
	public void setWriter(BufferedWriter networkWriter){
		this.networkWriter = networkWriter;
	}
	public void setReader(BufferedReader networkReader){
		this.networkReader = networkReader;
	}
	public void setValue(int value){
		this.gameLevel = value;
	}
	public void setMsg(String returnMsg){
		this.returnMsg = returnMsg;
	}
	public void setRows(int row){
		this.row = row;
	}
	public void setCols(int col){
		this.col = col;
	}
	public int getValue(){
		return gameLevel;
	}
	public String getMsg(){
		return returnMsg;
	}
	public int getRows(){
		return row;
	}
	public int getCols(){
		return col;
	}
	public BufferedReader getReader(){
		return networkReader;
	}
	public BufferedWriter getWriter(){
		return networkWriter;
	}
	public void defineMineValue(int row,int col){
		mineValue = new int[row+2][col+2];
		for(int i=0;i<row+2;i++){
			for(int j=0; j<col+2;j++){
				mineValue[i][j]=-2;
			}
		}
	}
}