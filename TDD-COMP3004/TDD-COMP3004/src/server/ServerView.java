package server;

import java.util.Scanner;

import common.Config;

public abstract class ServerView {


	public static int promptRounds() {

		Scanner userinput = new Scanner(System.in);
		int numberOfRounds;

		System.out.print("Please enter number of rounds: ");
		numberOfRounds = userinput.nextInt();

		return numberOfRounds;
	}

	public static int promptNumberOfPlayers() {

		Scanner userinput = new Scanner(System.in);
		int numOfplayers;

		System.out.print("Please enter number of the players: ");
		numOfplayers = userinput.nextInt();

		return numOfplayers;
	}
	
	public static void displayMessage(String output) {
		
		System.out.println(output);
		
	}
	
	public static int getValidNumberOfPlayersFromUser() {
		// get number of players
		boolean numPlayersValid = false;
		int numPlayers = 0;
		while (!numPlayersValid) {
			numPlayers = ServerView.promptNumberOfPlayers();
			numPlayersValid = Config.isNumofPlayersValid(numPlayers);
		}
		return numPlayers;
	}
	
	public static int getValidNumberOfRoundsFromUser() {
		// get number of rounds
		boolean isRoundsValid = false;
		int numOfRounds = 0;
		while (!isRoundsValid) {
			numOfRounds = ServerView.promptRounds();
			isRoundsValid = Config.isNumOfRoundsValid(numOfRounds);
		}
		return numOfRounds;
	}



}
