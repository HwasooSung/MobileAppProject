import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class Data {
	private volatile static Data data;
	public Socket client1,client2,client3;
	
	public final int[] easySize = { 6, 10 };
	public final int[] normalSize = { 9, 9 };
	public final int[] hardSize = { 9, 15 };
	
	public final int numEasyMine = 10;
	public final int numNormalMine = 13;
	public final int numHardMine = 22;
	
	public int difficulty = 0;
	private int[][] mineValue;
	
	public int row, col;
	
	private boolean startFlag = false;
	private boolean playFlag = false;
	private int numPlayer = 0;
	private int x,y;

	public static Data getInstance() {
		if (data == null) {
			synchronized (Data.class) {
				if (data == null) {
					data = new Data();
				}
			}
		}
		return data;
	}

	Data() {
		client1 = new Socket();
		client2 = new Socket();
		client3 = new Socket();
	}
	public void getX(int x) {
		this.x = x;
	}

	public void getY(int y) {
		this.y = y;
	}
	
	public int setX(){
		return x;
	}
	public int setY(){
		return y;
	}
	public int getMineValue(int x, int y) {
		return mineValue[x][y];
	}

	public void setMineValue(int[][] a) {
		this.mineValue = a;
	}

	public int getNumPlayer() {
		return numPlayer;
	}

	public void setNumPlayer() {
		numPlayer++;
	}

	public boolean getStartFlag() {
		return startFlag;
	}

	public void setStartFlag(boolean flag) {
		this.startFlag = flag;
	}
	public boolean getPlayFlag() {
		return playFlag;
	}

	public void setPlayFlag(boolean flag) {
		this.playFlag = flag;
	}

	public int getRow(int difficulty) {
		switch (difficulty) {
		case 1:
			row = 10;
			return 10;
		case 2:
			row = 9;
			return 9;
		case 3:
			row = 15;
			return 15;
		default:
			return 0;
		}
	}

	public int getCol(int difficulty) {
		switch (difficulty) {
		case 1:
			col = 6;
			return 6;
		case 2:
			col = 9;
			return 9;
		case 3:
			col = 9;
			return 9;
		default:
			return 0;
		}
	}

	public void defineMineValue(int row, int col) {
		mineValue = new int[row + 2][col + 2];
		for (int i = 0; i < row + 2; i++) {
			for (int j = 0; j < col + 2; j++) {
				mineValue[i][j] = -2;
			}
		}
	}
	public void buttonChange(){
		PrintWriter out1;
		try {
			out1 = new PrintWriter(
					new BufferedWriter(new OutputStreamWriter(client1.getOutputStream())), true);
			out1.println("Button" + "," + x + "," + y);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PrintWriter out2;
		try {
			out2 = new PrintWriter(
					new BufferedWriter(new OutputStreamWriter(client2.getOutputStream())), true);
			out2.println("Button" + "," + x + "," + y);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*
		PrintWriter out3;
		try {
			out3 = new PrintWriter(
					new BufferedWriter(new OutputStreamWriter(client3.getOutputStream())), true);
			out3.println("Button" + "," + x + "," + y);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
	}
}