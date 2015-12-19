package com.example.clienttest;

import java.io.PrintWriter;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;
import com.example.clienttest.Data;


public class MainActivity extends Activity {

	public final static String TAG = "FINDMINEGAME";


	Thread thread;
	ScrollView scrollView;
	Button buttonReady;
	TextView textViewNowPlaying;

	RadioGroup radioGroup;
	RadioButton radioButton1;
	RadioButton radioButton2;
	RadioButton radioButton3;

	TableLayout tableLayout;
	TableRow[] tableRow;
	OnClickListener onMineClickListener;

	TextView textViewRestOfMine;
	private MyHandler handler;
	public static MineButton[][] buttons;
	//int[][] mineValue;
	int[] minePosition;

	int gameLevel; // 1초급 2중급 3고급

	int rowNum = 0;
	int colNum = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		handler = new MyHandler();
		TCPNetwork network = new TCPNetwork();
		network.setHandler(handler);
		thread = new Thread(network);
		thread.start();

		initView();
	}


	public void function(int x, int y) {
		
		MainActivity.buttons[x][y].setClickable(false);
		MainActivity.setValueOnMineFarm(MainActivity.buttons[x][y]);
	}
	
	
	
	
	public void onButtonClick(View view) {
		switch (view.getId()) {
		case R.id.button_ready:
			if (radioButton1.isChecked()) {
				gameLevel = 1;
				textViewNowPlaying.setText("PLAYING GAME\n         6 X 10");
			} else if (radioButton2.isChecked()) {
				gameLevel = 2;
				textViewNowPlaying.setText("PLAYING GAME\n          9 X 9");
			} else if (radioButton3.isChecked()) {
				gameLevel = 3;
				textViewNowPlaying.setText("PLAYING GAME\n         9 X 15");
			}
			defineRowColNum();
			Data.getInstance().setMsg("ready");
			Data.getInstance().setValue(gameLevel);
			
			PrintWriter out = new PrintWriter(Data.getInstance().getWriter(), true);
			Log.w("Network",Data.getInstance().getMsg());          
			out.println(Data.getInstance().getMsg() + ","
					+ Data.getInstance().getValue());
			
			while(!Data.getInstance().getStartFlag()){}
			radioGroup.setVisibility(View.INVISIBLE);
			buttonReady.setVisibility(View.INVISIBLE);
			textViewNowPlaying.setVisibility(View.VISIBLE);
			
			int count=0;
			// 지뢰밭 생성
			while(true){
				for(int i=0;i<rowNum+2;i++){
					for(int j=0;j<colNum+2;j++){
						if(Data.getInstance().mineValue[i][j]!=-2){
							//Log.w("Network","Here!!");
							count++;
							//Log.w("Network",Data.getInstance().mineValue[i+1][j+1]+"");
						}
					}
				}
			
				//Log.w("Network","!!!!");
				if(count >= rowNum*colNum) break;
				count=0;
			}
			makeMineFarmView();
			
		}
	}

	public void initView() {
		scrollView = (ScrollView) findViewById(R.id.scroll_view);
		buttonReady = (Button) findViewById(R.id.button_ready);

		textViewNowPlaying = (TextView) findViewById(R.id.text_view_now_playing);

		radioGroup = (RadioGroup) findViewById(R.id.radio_group);
		radioButton1 = (RadioButton) findViewById(R.id.radio_button_1);
		radioButton2 = (RadioButton) findViewById(R.id.radio_button_2);
		radioButton3 = (RadioButton) findViewById(R.id.radio_button_3);
		radioButton1.setChecked(true);

		tableLayout = (TableLayout) findViewById(R.id.table_layout);
		onMineClickListener = new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				MineButton b = (MineButton) v;
				int x,y;
				x=b.getButtonX();
				y=b.getButtonY();
				b.setClickable(false);
				PrintWriter out = new PrintWriter(Data.getInstance().getWriter(), true);

				out.println("Button" + "," + x+","+y);
				setValueOnMineFarm(b);
				
				
			}
		};

		textViewRestOfMine = (TextView) findViewById(R.id.text_view_num_of_rest_mine);
	}

	public static void setValueOnMineFarm(MineButton b) {
		int val = b.getMineValue();
		
		switch (val) {
		case -1:
			b.setBackgroundResource(R.drawable.bomb);
			break;
		case 0:
			b.setTextColor(Color.WHITE);
			b.setText(val + "");
			break;
		case 1:
			b.setTextColor(Color.RED);
			b.setText(val + "");
			break;
		case 2:
			b.setTextColor(Color.YELLOW);
			b.setText(val + "");
			break;
		case 3:
			b.setTextColor(Color.GREEN);
			b.setText(val + "");
			break;
		case 4:
			b.setTextColor(Color.MAGENTA);
			b.setText(val + "");
			break;
		case 5:
			b.setTextColor(Color.BLUE);
			b.setText(val + "");
			break;
		case 6:
			b.setTextColor(0x66ccff);
			b.setText(val + "");
			break;
		case 7:
			b.setTextColor(0xff99cc);
			b.setText(val + "");
			break;
		case 8:
			b.setTextColor(0x660066);
			b.setText(val + "");
			break;
		}
	}

	public void makeMineFarmView() {

/*
		mineValue = new int[rowNum + 2][colNum + 2];
		for (int i = 0; i < rowNum + 2; i++) {
			for (int j = 0; j < colNum + 2; j++) {
				mineValue[i][j] = 0;
			}
		}
*/		
		tableRow = new TableRow[rowNum];
		buttons = new MineButton[rowNum][colNum];

		// Table Row Layout 설정
		TableRow.LayoutParams tableRowLayoutParams = new TableRow.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

		// Button Layout 설정
		TableRow.LayoutParams buttonLayoutParams = new TableRow.LayoutParams();
		buttonLayoutParams.width = 50;
		buttonLayoutParams.height = 50;
		buttonLayoutParams.setMargins(-5, -5, -5, -5);

		for (int i = 0; i < rowNum; i++) {
			tableRow[i] = new TableRow(MainActivity.this);
			tableRow[i].setLayoutParams(tableRowLayoutParams);

			for (int j = 0; j < colNum; j++) {
				buttons[i][j] = new MineButton(MainActivity.this);
				buttons[i][j].setButtonX(i);
				buttons[i][j].setButtonY(j);
				
				buttons[i][j].setLayoutParams(buttonLayoutParams);
				buttons[i][j].setTextSize(10);

				buttons[i][j].setMineValue(Data.getInstance().mineValue[i + 1][j + 1]);

				buttons[i][j].setOnClickListener(onMineClickListener);
				// Table Row에 버튼 달기
				tableRow[i].addView(buttons[i][j]);
			}
			tableLayout.addView(tableRow[i]);
		}

	}

	public void defineRowColNum() {
		rowNum = Data.EASY_ROW;
		colNum = Data.EASY_COLUMN;

		switch (gameLevel) {
		case 1:
			break;
		case 2:
			rowNum = Data.MEDIUM_ROW;
			colNum = Data.MEDIUM_COLUMN;
			break;
		case 3:
			rowNum = Data.DIFFICULT_ROW;
			colNum = Data.DIFFICULT_COLUMN;
			break;
		}
		
		Data.getInstance().setCols(colNum);
		Data.getInstance().setRows(rowNum);
		Data.getInstance().defineMineValue(rowNum,colNum);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	class MyHandler extends Handler { 
		
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			
			int x = msg.arg1;
			int y = msg.arg2;

			buttons[x][y].setClickable(false);
			setValueOnMineFarm(buttons[x][y]);
		}
		
	}
}


