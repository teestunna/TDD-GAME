package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

//import edu.carleton.scs.chat.utils.Trace;

public class ClientThread extends Thread {
	private Socket         socket   = null;
	private AppClient      client   = null;
	private BufferedReader streamIn = null;
	private boolean done = false;
	
	public ClientThread(AppClient client, Socket socket) {  
		this.client = client;
		this.socket = socket;
		this.open();  
		this.start();
	}
	
	public void open () {
		try {  
			streamIn  = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	    } catch(IOException ioe) {  
	    	System.out.println("Error getting input stream");
//	    	Trace.getInstance().exception(this,ioe);
			client.stop();
	    }
	}
	
	public void close () {
		done = true;
		try {  
			if (streamIn != null) streamIn.close();
			if (socket != null) socket.close();
			this.socket = null;
			this.streamIn = null;
		} catch(IOException ioe) { 
//			Trace.getInstance().exception(this,ioe);
	   }	
	}
	
	public void run() {
		System.out.println("Client Thread " + socket.getLocalPort() + " running.");
		while (!done) { 
			try {  
				client.handle(streamIn.readLine());
			} catch(IOException ioe) {  
//				Trace.getInstance().exception(this,ioe);
	    }}
	}	

}
