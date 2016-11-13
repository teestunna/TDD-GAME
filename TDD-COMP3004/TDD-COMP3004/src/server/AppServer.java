package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Set;
import java.util.logging.Filter;

import org.apache.log4j.Logger;

import common.Attack;
import common.Command;
import common.Config;
import common.Defence;
import common.GameState;

public class AppServer implements Runnable {
	int clientCount = 0;

	private Thread thread = null;
	private ServerSocket server = null;
	private HashMap<Integer, ServerThread> clients;
	Engine engine;
	int maxConnections;
	int numConnections;
    static final Logger l = Logger.getLogger("ServerLogger");
	
	
	public AppServer(int port, Engine engine) {
		try {
			this.engine = engine;
			
			int numPlayers = ServerView.getValidNumberOfPlayersFromUser();
			int numRounds = ServerView.getValidNumberOfRoundsFromUser();
			engine.setNumberOfPlayers(numPlayers);
			engine.setNumOfRounds(numRounds);
			
			l.info("Binding to port " + port + ", please wait  ...");
			System.out.println("Binding to port " + port + ", please wait  ...");
			clients = new HashMap<Integer, ServerThread>();
			server = new ServerSocket(port);
			server.setReuseAddress(true);
			start();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	public synchronized boolean isMaxConnectionReached() {
		return maxConnections == numConnections;
	}
	
	/** Now we start the servers main thread */
	public void start() {
		if (thread == null) {
			thread = new Thread(this);
			thread.start();
			
		}
	}

	/** The main server thread starts and is listening for clients to connect */
	public void run() {
		while (thread != null) {
			try {
				addThread(server.accept());
				numConnections++;
			} catch (IOException e) {				
				
			}}
	}

	/** 
	 * Client connection is accepted and now we need to handle it and register it 
	 * and with the server | HashTable 
	 **/
	private void addThread(Socket socket) {
		
		if (clientCount < engine.getNumberOfPlayers()) {
			try {
				/** Create a separate server thread for each client */
				ServerThread serverThread = new ServerThread(this, socket);
				/** Open and start the thread */
				serverThread.open();
				serverThread.start();
				clients.put(serverThread.getID(), serverThread);
				this.clientCount++;
				if (clientCount == engine.getNumberOfPlayers()) {
					engine.startGame();
					if (engine.getGameState() == GameState.JOIN) {
						broadcastMessage("ACCEPTING PLAYERS");
					}
				}
			} catch (IOException e) {
				
			}
		}
	}

	private void broadcastMessage(String msg) {
		for (Integer key : clients.keySet()) {
			clients.get(key).send(msg);
		}
	}
	
	private void broadcastGameOutcome() {
		for (String outcome : engine.getOutcome()) {
			broadcastMessage(outcome);
		}
	}
	
	/*private void broadcastJoinOutcome() {
		for (String joinOutcome :engine.getJoinOutcome()) {
			broadcastMessage(joinOutcome);
		}
	}*/
	
	public void sendMessage(int id, String msg) {
		for (Integer key : clients.keySet()) {
			if (key == id) {
				clients.get(key).send(msg);
			}
		}
	}
	
	public synchronized void handle(int id, String msg) {
		if (msg != null && !msg.isEmpty()) {
			String[] msgArr = msg.split(" ");
			String command = msgArr[0];
			switch (command) {
				case "join":
					if (isValidJoinMsg(msg)) {
						Player newPlayer = new Player(id, msgArr[1], msgArr[2]);
						if (engine.addPlayer(newPlayer)) {
							l.info(newPlayer.getName() + " has joined the game.");
							System.out.println(newPlayer.getName() + " has joined the game.");
							if (engine.getGameState() == GameState.ATTACK_SELECTION) {
								broadcastMessage("GAME READY");
							}
						} else {
							l.info("join message was parsed but rejected: " + msg);
							System.out.println("join message was parsed but rejected: " + msg);
						}
					}
					else {
						l.info("unable to parse join message: " + msg);
						System.out.println("unable to parse join message: " + msg);
					}
					break;
				case "select":
					if (isAttackSelectionMessageValid(msg)) {
						String attacker = msgArr[1];
						String playerToAttack = msgArr[2];
						Attack attack = Attack.getAttack(msgArr[3]);
						int attackSpeed = Integer.parseInt(msgArr[4]);
						Defence defence = Defence.getDefence(msgArr[5]);
						int defenceSpeed = Integer.parseInt(msgArr[6]);
						if (engine.setSelection(attacker, playerToAttack, attack, attackSpeed, defence, defenceSpeed)) {
							System.out.println(attacker + " selected attack and defence");
							if (engine.getGameState() == GameState.ROLL) {
								broadcastMessage("ROLL");
							}
						} else {
							System.out.println("select message was parsed but rejected: " + msg);
						}
					}
					else {
						System.out.println("unable to parse select message: " + msg);
					}
					break;
				case "roll":
					if (isRollMessageValid(msg)) {
						String player = msgArr[1];
						int roll = Integer.parseInt(msgArr[2]);
						if (engine.setRoll(player, roll)) {
							System.out.println(player + " rolled");
							if (engine.getGameState() == GameState.ROUND_COMPLETE) {
								broadcastGameOutcome();
								engine.startNextRound();
								broadcastMessage("GAME READY");
							}
							else if(engine.getGameState() == GameState.GAME_COMPLETE) {
								broadcastGameOutcome();
								broadcastMessage("Game Over");
								shutdown();
							}
						} else {
							System.out.println("roll message was parsed but rejected: " + msg);
						}
					} else {
						System.out.println("unable to parse roll message: " + msg);
					}
					break;
				default:
					System.out.println("unable to recognize message: " + msg);
			}
		}
		
	}
	
	private boolean isRollMessageValid(String msg) {
		boolean isValidMsgLength = false;
		boolean isValidRoll = false;
		
		String[] msgArr = msg.split(" ");
		isValidMsgLength = (msgArr.length == 3);
		if (isValidMsgLength) {
			try {
				Integer.parseInt(msgArr[2]);
				isValidRoll = true;
			} catch (Exception e) {
				isValidRoll = false;
			}
		}
		return isValidMsgLength && isValidRoll;
	}

	public boolean isValidJoinMsg(String msg) {
		boolean isValidMsgLength = false;
		boolean isValidCommand = false;
		
		String[] msgArr = msg.split(" ");
		isValidMsgLength = (msgArr.length == 3);
		if (isValidMsgLength) {
			isValidCommand = (msgArr[0].equalsIgnoreCase(Command.JOIN.name()));
		}
		return isValidMsgLength && isValidCommand;
	}
	
	private boolean isAttackSelectionMessageValid(String message) {
		
		boolean isValidMsgLength = false;
		boolean isValidAttack = false;
		boolean isValidSpeed = false;
		boolean isValidDefence = false;
		
		String[] messageArr = message.split(" ");
		isValidMsgLength = messageArr.length == 7;
		
		if(isValidMsgLength) {
			isValidAttack = Attack.isValid(messageArr[3]);
			int attackSpeed = Integer.parseInt(messageArr[4]);
			int defenceSpeed = Integer.parseInt(messageArr[6]);
			isValidSpeed = Config.isAttackDefenceSpeedValid(attackSpeed, defenceSpeed);
			isValidDefence = Defence.isValid(messageArr[5]);
		}

		return isValidMsgLength && isValidAttack && isValidDefence
				&& isValidSpeed;
		
	}

	/** Try and shutdown the client cleanly */
	public synchronized void remove(int ID) {
		if (clients.containsKey(ID)) {
			ServerThread toTerminate = clients.get(ID);
			clients.remove(ID);
			clientCount--;

			toTerminate.close();
			toTerminate = null;
		}
	}

	/** Shutdown the server cleanly */
	public void shutdown() {
		Set<Integer> keys = clients.keySet();

		if (thread != null) {
			thread = null;
		}
		try {
			for (Integer key : keys) {
				clients.get(key).close();
				clients.put(key, null);
			}
			clients.clear();
			server.close();
		} catch (IOException e) {
			
		}
		
	}
	

	
}