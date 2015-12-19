import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	
	public static void main(String[] args) {
		final int ServerPort = 9992;
		final String ServerIP = "165.194.17.32";
	
		try {
			System.out.println("S: Connecting...");
			ServerSocket serverSocket;
			serverSocket = new ServerSocket(ServerPort);
			 
			Data.getInstance().client1= serverSocket.accept();
			System.out.println("S: Receiving 1 ...");
			Thread client1Thread = new Thread(new TCPServer(Data.getInstance().client1,true));
			client1Thread.start();
			
			Data.getInstance().client2= serverSocket.accept();
			System.out.println("S: Receiving 2 ...");
			Thread client2Thread = new Thread(new TCPServer(Data.getInstance().client2,false));
			client2Thread.start();
			
			Data.getInstance().client3= serverSocket.accept();
			System.out.println("S: Receiving 3 ...");
			Thread client3Thread = new Thread(new TCPServer(Data.getInstance().client3,false));
			client3Thread.start();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

