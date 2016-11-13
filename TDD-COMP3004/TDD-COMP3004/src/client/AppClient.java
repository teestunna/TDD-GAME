package client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Random;

import common.Attack;
import common.Config;
import common.Defence;
import common.GameState;


public class AppClient implements Runnable {
	private int ID = 0;
	private Socket socket = null;
	private Thread thread = null;
	private ClientThread client = null;
	private BufferedReader console = null;
	private BufferedReader streamIn = null;
	private BufferedWriter streamOut = null;

	private GameState gameState;
	public String userName;
	int roundCount;
	
	public AppClient() {
		
	}

	public AppClient(String serverName, int serverPort) {
		System.out.println(ID + ": Establishing connection. Please wait ...");
		gameState = GameState.WAITING;
		try {
			this.socket = new Socket(serverName, serverPort);
			this.ID = socket.getLocalPort();
			System.out.println(ID + ": Connected to server: "
					+ socket.getInetAddress());
			System.out.println(ID + ": Connected to portid: "
					+ socket.getLocalPort());
			this.start();
		} catch (UnknownHostException uhe) {
			System.err.println(ID + ": Unknown Host");
			
		} catch (IOException ioe) {
			System.out.println(ID + ": Unexpected exception");
			
		}
	}

	public int getID() {
		return this.ID;
	}

	public void start() throws IOException {
		try {
			console = new BufferedReader(new InputStreamReader(System.in));
			streamIn = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			streamOut = new BufferedWriter(new OutputStreamWriter(
					socket.getOutputStream()));

			if (thread == null) {
				client = new ClientThread(this, socket);
				thread = new Thread(this);
				thread.start();
			}
		} catch (IOException ioe) {
			// Trace.getInstance().exception(this,ioe);
			throw ioe;
		}
	}

	public void run() {
		System.out.println(ID + ": Client Started...");
		gameState = GameState.WAITING;
		System.out.println("waiting for server to start game..");
		while (thread != null) {
			try {
				if (streamOut != null) {
					streamOut.flush();
					synchronized (gameState) {
						if (gameState == GameState.ACCEPTING_CONNECTIONS) {
							streamOut.write(createJoinMessage(getUserName())
									+ "\n");
							gameState = GameState.WAITING;
							System.out.println("waiting for server..");
						} else if (gameState == GameState.ATTACK_SELECTION) {
							System.out.println("Starting Round " + ++roundCount);
							String selectMsg = createAttackDefenceSelectMessage(
									getPlayerToAttack(), getAttack(),
									getAttackSpeed(), getDefence(),
									getDefenceSpeed());
							streamOut.write(selectMsg + "\n");
							gameState = GameState.WAITING;
							System.out.println("waiting for server..");
						} else if (gameState == GameState.ROLL) {
							streamOut
									.write(createRollMessage(getRoll()) + "\n");
							gameState = GameState.WAITING;
							System.out.println("waiting for server..");
						}
					}
				} else {
					System.out.println(ID + ": Stream Closed");
				}
			} catch (IOException e) {
				// Trace.getInstance().exception(this,e);
				stop();
			}
		}
		System.out.println(ID + ": Client Stopped...");
	}

	private String getUserName() throws IOException {
		System.out.print("Enter your name: ");
		userName = console.readLine();
		return userName;
	}
	
	private String getPlayerToAttack() throws IOException {
		System.out.print("Enter name of player to attack: ");
		return console.readLine();
	}
	
	private String getAttack() throws IOException {
		Attack attack = Attack.NONE;
		while (attack == Attack.NONE ){
			System.out.print("Enter valid attack: ");
			attack = Attack.getAttack(console.readLine());
		}
		return attack.name().toLowerCase();
	}
	
	private String getAttackSpeed() throws IOException {
		String speed;
		while (true){
			System.out.print("Enter attack speed: ");
			try {
				speed = console.readLine();
				Integer.parseInt(speed);
				break;
			} catch(Exception e) {};
		}
		return speed;
	}
	
	private String getDefence() throws IOException {
		Defence defence = Defence.NONE;
		while (defence == Defence.NONE){
			System.out.print("Enter valid defence: ");
			defence = Defence.getDefence(console.readLine());
		}
		return defence.name().toLowerCase();
	}
	
	private String getDefenceSpeed() throws IOException {
		String speed;
		while (true){
			System.out.print("Enter valid defence speed: ");
			try {
				speed = console.readLine();
				Integer.parseInt(speed);
				break;
			} catch(Exception e) {};
		}
		return speed;
	}
	
	private int getRoll() throws IOException {
		System.out.print("Enter a character to start roll: ");
		console.readLine();
		Random rand = new Random();
		int  n = rand.nextInt(6) + 1;
		return n;
	}

	public String createRollMessage(int roll) {
		return "roll " + userName + " " + roll;
	}
	
	public String createJoinMessage(String userName)
			throws UnknownHostException {
		return "join " + userName +" "
				+"127.0.0.1";
	}
	
	public String createAttackDefenceSelectMessage(String playerToAttack, String a, String aSpeed, String d, String dSpeed) {
		return "select " 
				+ userName + " "
				+ playerToAttack + " "
				+ a + " "
				+ aSpeed + " "
				+ d + " "
				+ dSpeed;
	}

	public void handle(String msg) {
		if (msg.equalsIgnoreCase("GAME OVER")) {
			System.out.println(ID + ": Game Over...");
			stop();
		} else if (msg.equalsIgnoreCase("ACCEPTING PLAYERS")) {
			synchronized (gameState) {
				gameState = GameState.ACCEPTING_CONNECTIONS;
			}			
		}
		else if (msg.equalsIgnoreCase("GAME READY")){
			synchronized (gameState) {
				gameState = GameState.ATTACK_SELECTION;
			}
		} else if (msg.equalsIgnoreCase("ROLL")) {
			synchronized (gameState) {
				gameState = GameState.ROLL;
			}
		} else {
			System.out.println(msg);
		}
	}

	public void stop() {
		try {
			if (thread != null)
				thread = null;
			if (console != null)
				console.close();
			if (streamIn != null)
				streamIn.close();
			if (streamOut != null)
				streamOut.close();

			if (socket != null)
				socket.close();

			this.socket = null;
			this.console = null;
			this.streamIn = null;
			this.streamOut = null;
		} catch (IOException ioe) {
			// Trace.getInstance().exception(this,ioe);
		}
		client.close();
	}

	@SuppressWarnings("unused")
	public static void main(String args[]) {
		AppClient client = new AppClient(Config.DEFAULT_IP, Config.PORT_NUMBER);
	}
}
