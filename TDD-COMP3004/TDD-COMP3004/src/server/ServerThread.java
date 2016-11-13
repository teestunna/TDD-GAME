package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class ServerThread extends Thread {
	private int ID = -1;
	private Socket socket = null;
	private AppServer server = null;
	private BufferedReader streamIn = null;
	private BufferedWriter streamOut = null;
	
	private String clientAddress = null;;

	private boolean done = false;

	public ServerThread(AppServer server, Socket socket) {
		super();
		this.server = server;
		this.socket = socket;
		this.ID = socket.getPort();
		this.clientAddress = socket.getInetAddress().getHostAddress();
	}

	public int getID() {
		return this.ID;
	}

	public String getSocketAddress () {
		return clientAddress;
	}
	
	/** The server processes the messages and passes it to the client to send it */
	public void send(String msg) {
		try {
			streamOut.write(msg + "\n");
			streamOut.flush();
		} catch (IOException ioe) {
			
			server.remove(ID);
		}
	}

	/** server thread that listens for incoming message from the client
	 * on the assigned port 
	 * */
	public void run() {
		
		while (!done) {
			try {
				/** Received a message and pass to the server to handle */
				server.handle(ID, streamIn.readLine());
			} catch (IOException ioe) {
				
				server.remove(ID);
				break;
			}}
	}

	public void open() throws IOException {
		streamIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		streamOut = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
	}

	public void close() {
		try {
			if (socket != null) socket.close();
			if (streamIn != null) streamIn.close();
			
			this.done = true;
			this.socket = null;
			this.streamIn = null;
		} catch (IOException e) { }
	}

}
