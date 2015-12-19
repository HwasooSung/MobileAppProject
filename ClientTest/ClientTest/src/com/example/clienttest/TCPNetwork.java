package com.example.clienttest;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.StringTokenizer;

import com.example.clienttest.MainActivity.MyHandler;

import android.content.Context;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

class TCPNetwork implements Runnable {
	private MyHandler handler;
	
	public void setHandler(MyHandler handler) {
		this.handler = handler;
	}

	private Socket socket;

	private BufferedReader networkReader;
	private BufferedWriter networkWriter;

	private String ip = "165.194.17.32";
	private int port = 9992; // PORT¹øÈ£
	
	public void run() {
		// TODO Auto-generated method stub

		try {
			setSocket(ip, port);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while (true) {
			try {
				String str = networkReader.readLine();
				Log.w("Network", "Msg received");
				distinctString(str);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
			}
		}

	}


	public void setSocket(String ip, int port) throws IOException {
		try {
			socket = new Socket(ip, port);

			Data.getInstance().setWriter(
					new BufferedWriter(new OutputStreamWriter(socket
							.getOutputStream())));

			Data.getInstance().setReader(
					new BufferedReader(new InputStreamReader(socket
							.getInputStream())));
			networkReader = Data.getInstance().getReader();
			networkWriter = Data.getInstance().getWriter();
		} catch (IOException e) {
			Log.w("Network", "Connection Fail");
			System.out.println(e);
			e.printStackTrace();
		}

	}

	private void distinctString(String a) {
		StringTokenizer st = new StringTokenizer(a, ",");
		String[] b = new String[a.length()]; //
		int count = 0;
		int row = Data.getInstance().getRows();
		int col = Data.getInstance().getCols();
		int[][] value = new int[row + 2][col + 2];
		
		b[count++] = st.nextToken();

		if (b[0].equals("MineValue")) {
			for (int i = 1; i < row + 1; i++) {
				for (int j = 1; j < col + 1; j++) {
					value[i][j] = (int) Integer.parseInt(st.nextToken());
				}
			}

			Data.getInstance().mineValue = value;

		} else if (b[0].equals("start")) {
			Data.getInstance().setStartFlag(true);
		} else if (b[0].equals("Button")) {
			int x = (int) Integer.parseInt(st.nextToken());
			int y = (int) Integer.parseInt(st.nextToken());
			Message msg = handler.obtainMessage();
			msg.arg1 = x;
			msg.arg2 = y;
			handler.sendMessage(msg);
			
		} else {
		}

	}
	
	

}

