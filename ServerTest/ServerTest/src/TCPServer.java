import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.StringTokenizer;

public class TCPServer implements Runnable {
	public Socket client;
	public boolean flag = false;
	boolean isHost = false;

	TCPServer(Socket client, boolean isHost) {
		this.client = client;
		this.isHost = isHost;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

		try {
			while (true) {
				BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
				String str = in.readLine();

				if (!Data.getInstance().getPlayFlag()) {
					StringTokenizer st = new StringTokenizer(str, ",");

					String isReady = st.nextToken();
					int difficulty = (int) Integer.parseInt(st.nextToken());

					if (isHost && isReady.equals("ready")) {
						// Data.getInstance().setNumPlayer();

						Init init = new Init(difficulty);
						Data.getInstance().setStartFlag(true);
						// if(Data.getInstance().getNumPlayer()==2){
						// }
					}
				} else {
					StringTokenizer st = new StringTokenizer(str, ",");
					String a = st.nextToken();
					if (a.equals("Button")) {
						System.out.println("Button!");
						Data.getInstance().getX((int)Integer.parseInt(st.nextToken()));
						Data.getInstance().getY((int)Integer.parseInt(st.nextToken()));
						Data.getInstance().buttonChange();


					}
				}

				if (Data.getInstance().getStartFlag()) {
					PrintWriter out = new PrintWriter(
							new BufferedWriter(new OutputStreamWriter(client.getOutputStream())), true);
					String a = "MineValue,";
					for (int i = 0; i < Data.getInstance().row; i++) {
						for (int j = 0; j < Data.getInstance().col; j++) {
							a += Data.getInstance().getMineValue(i + 1, j + 1) + ",";
						}
					}
					out.println(a);
					a = "start";
					out.println(a);
					Data.getInstance().setPlayFlag(true);
					System.out.println(Data.getInstance().getPlayFlag());
				}
			}

		} catch (

		Exception e)

		{
			System.out.println("S: Error");

			// e.printStackTrace();

		}

	}
}
