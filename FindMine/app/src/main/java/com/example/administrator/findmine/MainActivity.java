package com.example.administrator.findmine;

import java.util.Random;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class MainActivity extends Activity {

    public final static String TAG = "FINDMINEGAME";

    public final static int EASY_ROW = 10;
    public final static int EASY_COLUMN = 6;

    public final static int MEDIUM_ROW = 9;
    public final static int MEDIUM_COLUMN = 9;

    public final static int DIFFICULT_ROW = 15;
    public final static int DIFFICULT_COLUMN = 9;

    ScrollView scrollView;
    Button buttonReady;
    TextView textViewNowPlaying;

    RadioGroup radioGroup;
    RadioButton radioButton1;
    RadioButton radioButton2;
    RadioButton radioButton3;

    TableLayout tableLayout;
    TableRow[] tableRow;

    TextView textViewRestOfMine;
    Button buttonBack;

    MineButton[][] buttons;
    int[] minePosition;

    static int numOfNotMine=-1;

    int gameLevel; // 1초급 2중급 3고급

    int rowNum;
    int colNum;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    public void makeMineFarm(int level) {
        rowNum = EASY_ROW;
        colNum = EASY_COLUMN;

        switch (level) {
            case 1:
                break;
            case 2:
                rowNum = MEDIUM_ROW;
                colNum = MEDIUM_COLUMN;
                break;
            case 3:
                rowNum = DIFFICULT_ROW;
                colNum = DIFFICULT_COLUMN;
                break;
        }

        tableRow = new TableRow[rowNum];
        buttons = new MineButton[rowNum][colNum];

        int[] valArr = getMineValue(rowNum, colNum);

        for (int i = 0; i < rowNum; i++) {
            tableRow[i] = new TableRow(this);

            // Table Row Layout 설정
            TableRow.LayoutParams layoutParams
                    = new TableRow.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            tableRow[i].setLayoutParams(layoutParams);

            for (int j = 0; j < colNum; j++) {

                buttons[i][j] = new MineButton(this);

                // Button Layout 설정
                TableRow.LayoutParams lp = new TableRow.LayoutParams();
                lp.width = 60;
                lp.height = 60;
                lp.setMargins(-5, -5, -5, -5);
                buttons[i][j].setLayoutParams(lp);
                buttons[i][j].setTextSize(13);

                // Mine Value를 Button에 뿌려주기
                if (valArr[colNum * i + j] == -1) {
                    buttons[i][j].setValue("@");
                } else {
                    buttons[i][j].setValue(Integer.toString(valArr[colNum * i + j]));
                }

                // Button에 리스너 달기
                buttons[i][j].setOnClickListener(new OnMineButtonClickListener(buttons[i][j]));

                // Table Row에 버튼 달기
                tableRow[i].addView(buttons[i][j]);
            }

            tableLayout.addView(tableRow[i]);
        }

    }

    public int[] getMineValue(int row, int col) {

        int[] arr = new int[row * col];

        // 지뢰 개수는 버튼 개수의 1/6
       minePosition = new int[row * col / 6];

        numOfNotMine = (row*col) - (row*col/6);
        //
        textViewRestOfMine.setText("\n남은 숫자 버튼 개수 : " + numOfNotMine);
        textViewRestOfMine.setVisibility(View.VISIBLE);


        // 0 initialize
        for (int i = 0; i < minePosition.length; i++) {
            minePosition[i] = -1;
        }

        Random random = new Random();
        int num = 0;

        do {
            // 1 Produce Random Number

            int randomPos = random.nextInt(row * col);
            boolean same = false;

            // 2 Compare
            for (int j = 0; j < num; j++) {
                if (minePosition[j] == randomPos) {
                    same = true;
                    break;
                }
            }

            if (same) {
            }
            else {
                // 3
                minePosition[num] = randomPos;
                num++;
            }
        } while (num < minePosition.length);

        Log.d(TAG, "After while loop        in getMineValue()");
        for (int i = 0; i < arr.length; i++) {
            arr[i] = 0;
        }
        for (int i = 0; i < minePosition.length; i++) {
            arr[minePosition[i]] = -1;
        }

        boolean c1, c2, c3, c4;
        for (int i = 0; i < arr.length; i++) {

            if (arr[i] == -1) {
                continue;
            }
            c1 = (i / col == 0);
            c2 = (i % col == 0);
            c3 = (i % col == col - 1);
            c4 = (i / col == row - 1);

            int sum = 0;

            if (c1 && c2) {
                if (arr[i + 1] == -1) {
                    sum++;
                }
                if (arr[i + col + 1] == -1) {
                    sum++;
                }
                if (arr[i + col] == -1) {
                    sum++;
                }
            } else if (c1 && c3) {
                if (arr[i - 1] == -1) {
                    sum++;
                }
                if (arr[i + col - 1] == -1) {
                    sum++;
                }
                if (arr[i + col] == -1) {
                    sum++;
                }
            } else if (c2 && c4) {
                if (arr[i - col] == -1) {
                    sum++;
                }
                if (arr[i - (col - 1)] == -1) {
                    sum++;
                }
                if (arr[i + 1] == -1) {
                    sum++;
                }
            } else if (c3 && c4) {
                if (arr[i - (col + 1)] == -1) {
                    sum++;
                }
                if (arr[i - col] == -1) {
                    sum++;
                }
                if (arr[i - 1] == -1) {
                    sum++;
                }
            } else if (c1) {
                if (arr[i - 1] == -1) {
                    sum++;
                }
                if (arr[i + 1] == -1) {
                    sum++;
                }
                if (arr[i + col + 1] == -1) {
                    sum++;
                }
                if (arr[i + col] == -1) {
                    sum++;
                }
                if (arr[i + col - 1] == -1) {
                    sum++;
                }
            } else if (c2) {
                if (arr[i - col] == -1) {
                    sum++;
                }
                if (arr[i - (col - 1)] == -1) {
                    sum++;
                }
                if (arr[i + 1] == -1) {
                    sum++;
                }
                if (arr[i + col + 1] == -1) {
                    sum++;
                }
                if (arr[i + col] == -1) {
                    sum++;
                }
            } else if (c3) {
                if (arr[i - col] == -1) {
                    sum++;
                }
                if (arr[i - (col + 1)] == -1) {
                    sum++;
                }
                if (arr[i - 1] == -1) {
                    sum++;
                }
                if (arr[i + col - 1] == -1) {
                    sum++;
                }
                if (arr[i + col] == -1) {
                    sum++;
                }

            } else if (c4) {
                if (arr[i - 1] == -1) {
                    sum++;
                }
                if (arr[i - (col + 1)] == -1) {
                    sum++;
                }
                if (arr[i - col] == -1) {
                    sum++;
                }
                if (arr[i - (col - 1)] == -1) {
                    sum++;
                }
                if (arr[i + 1] == -1) {
                    sum++;
                }
            } else {
                if (arr[i - (col + 1)] == -1) {
                    sum++;
                }
                if (arr[i - col] == -1) {
                    sum++;
                }
                if (arr[i - (col - 1)] == -1) {
                    sum++;
                }
                if (arr[i - 1] == -1) {
                    sum++;
                }
                if (arr[i + 1] == -1) {
                    sum++;
                }
                if (arr[i + col - 1] == -1) {
                    sum++;
                }
                if (arr[i + col] == -1) {
                    sum++;
                }
                if (arr[i + col + 1] == -1) {
                    sum++;
                }
            }
            arr[i] = sum;
        }

        return arr;

    }

    public void drawWinDialog(boolean win) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);

        if(win) {
            alertDialogBuilder.setTitle("게임 승리");
        }
        else{
            alertDialogBuilder.setTitle("게임 패배");
        }
        // 제목셋팅
        alertDialogBuilder
                .setMessage("게임 한 판 더?")
                .setCancelable(false)
                .setPositiveButton("종료",
                        new DialogInterface.OnClickListener() {
                            public void onClick(
                                    DialogInterface dialog, int id) {
                                // 프로그램을 종료한다
                                MainActivity.this.finish();
                            }
                        })
                .setNegativeButton("새 게임하기",
                        new DialogInterface.OnClickListener() {
                            public void onClick(
                                    DialogInterface dialog, int id) {
                                dialog.cancel();
                                MainActivity.this.finish();
                                startActivity(new Intent(MainActivity.this, MainActivity.class));
                            }
                        });
        // 다이얼로그 생성
        AlertDialog alertDialog = alertDialogBuilder.create();

        // 다이얼로그 보여주기
        alertDialog.show();
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
                radioGroup.setVisibility(View.INVISIBLE);
                buttonReady.setVisibility(View.INVISIBLE);
                buttonBack.setVisibility(View.VISIBLE);
                textViewNowPlaying.setVisibility(View.VISIBLE);
                // 지뢰밭 생성
                makeMineFarm(gameLevel);
                break;

            case R.id.button_back:
                MainActivity.this.finish();
                startActivity(new Intent(MainActivity.this, MainActivity.class));
                break;
        }
    }

    public void initView() {

        scrollView = (ScrollView) findViewById(R.id.scroll_view);
        buttonReady = (Button) findViewById(R.id.button_ready);

        radioGroup = (RadioGroup) findViewById(R.id.radio_group);
        radioButton1 = (RadioButton) findViewById(R.id.radio_button_1);
        radioButton2 = (RadioButton) findViewById(R.id.radio_button_2);
        radioButton3 = (RadioButton) findViewById(R.id.radio_button_3);
        radioButton1.setChecked(true);

        tableLayout = (TableLayout) findViewById(R.id.table_layout);

        textViewRestOfMine = (TextView) findViewById(R.id.text_view_num_of_rest_mine);
        buttonBack = (Button) findViewById(R.id.button_back);
        textViewNowPlaying = (TextView) findViewById(R.id.text_view_now_playing);
    }

    public class OnMineButtonClickListener implements View.OnClickListener{

        MineButton button;

        public OnMineButtonClickListener(MineButton b) {
            // TODO Auto-generated constructor stub
            button = b;
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
                    Vibrator vibrator = (Vibrator) MainActivity.this.getSystemService(Context.VIBRATOR_SERVICE);
                    vibrator.vibrate(100);
                    drawWinDialog(false);
                    break;
            }
            textViewRestOfMine.setText("\n남은 숫자 버튼 개수 : " + numOfNotMine);

            if (numOfNotMine == 0) {

                for(int i=0 ; i<minePosition.length ; i++) {
                    buttons[minePosition[i]/colNum][minePosition[i]%colNum].setBackgroundResource(R.drawable.bomb);
                }

                drawWinDialog(true);
            }
        }
    }

}

