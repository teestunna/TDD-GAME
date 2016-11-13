package common;

public class Config {

	public static final int MAX_PLAYERS = 4;
	public static final int MIN_PLAYERS = 2;
	public static final int PORT_NUMBER = 3000;
	public static final String DEFAULT_IP = "127.0.0.1";
	public static final int TOTAL_SPEED = 4;
	public static final String DEFAULT_USER = "Chuboy";

	public static boolean isNumofPlayersValid(int userInput) {
		if (userInput >= Config.MIN_PLAYERS && userInput <= Config.MAX_PLAYERS) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isNumOfRoundsValid(int userInput) {
		if (userInput > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean isAttackDefenceSpeedValid(int attackSpeed, int defenceSpeed) {
		int totalSpeed = attackSpeed + defenceSpeed;
		return (totalSpeed >= TOTAL_SPEED);
	}
	
	

}
