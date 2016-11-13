package server;

import java.util.ArrayList;

import common.Attack;
import common.Defence;
import common.GameState;


public class Engine {
	
	private int numPlayers;
	private int numRoundsRemaining;
	private ArrayList<Player> activePlayers;
	private GameState gameState;
	public String[] attackMechanism = {"THRUST","SWING","SMASH"};
	
	public Engine() {
		activePlayers = new ArrayList<Player>();
		gameState = GameState.ACCEPTING_CONNECTIONS;
	}
	
	public void setNumberOfPlayers(int numPlayers) {
		this.numPlayers = numPlayers;
	}
	
	public int getNumberOfPlayers() {
		return numPlayers;
	}
	
	public void setNumOfRounds(int numRounds) {
		this.numRoundsRemaining = numRounds;
	}
	
	public GameState getGameState() {
		return gameState;
	}

	public void startGame() {
		gameState = GameState.JOIN;
	}

	public boolean addPlayer(Player player) {
		boolean playerAdded = false;
		if (gameState == GameState.JOIN) {
			if (getPlayer(player.getName()) == null) {
				activePlayers.add(player);
				playerAdded = true;
				updateGameState();
			}
		}
		return playerAdded;
	}
	
	public boolean setSelection(String attacker, String playerToAttack,
			Attack attack, int attackSpeed, Defence defence, int defenceSpeed) {
		
		boolean selectionUpdated = false;
		if (gameState == GameState.ATTACK_SELECTION) {
			Player attackerPlayer = getPlayer(attacker);
			Player defendingPlayer = getPlayer(playerToAttack);
			if(attackerPlayer != null && defendingPlayer != null) {
				attackerPlayer.updateAttackDefence(defendingPlayer, attack, attackSpeed, defence, defenceSpeed);
				selectionUpdated = true;
				updateGameState();
			}
		}
		return selectionUpdated;
	}
	
	public boolean setRoll(String player, int roll) {
		if (gameState == GameState.ROLL) {
			Player p = getPlayer(player);
			if (p != null) {
				p.setRoll(roll);
				attack(p);
				updateGameState();
				return true;
			}
		}
		
		return false;
	}
	
	private void attack(Player attacker) {
		Attack actualAttack = attacker.getRound().getActualAttack();
		if (attacker.getRound().inflictedWound(actualAttack)) {
			Player playerToAttack = attacker.getRound().getPlayerToAttack();
			playerToAttack.inflictWound();
			updateGameState();
		}
	}
	
	public void updateGameState() {
		if (gameState == GameState.JOIN) {
			if (activePlayers.size() == numPlayers) {
				gameState = GameState.ATTACK_SELECTION;
			}
		}
		else if (gameState == GameState.ATTACK_SELECTION) {
			if (allPlayersSelectedAttack()) {
				gameState = GameState.ROLL;
			}
		}
		else if (gameState == GameState.ROLL) {
			if (allPlayersRolled()) {
				numRoundsRemaining--;
				if (numRoundsRemaining == 0) {
					gameState = GameState.GAME_COMPLETE;
				} else {
					gameState = GameState.ROUND_COMPLETE;
				}
			}
		}
	}
	
	public boolean ifAttackHitsWithSpeed(int aSpeed, int dSpeed) {
		if(aSpeed < dSpeed) {
		  return true;
		}else{
		  return false;
		}
	}
	
	public boolean startNextRound() {
		if (numRoundsRemaining != 0) {
			for (Player p : activePlayers) {
				p.resetRoundData();
			}
			gameState = GameState.ATTACK_SELECTION;
			return true;
		}
		return false;
	}
	
	public boolean allPlayersSelectedAttack() {
		for (Player player : activePlayers) {
			if (!player.selectedAttack() || !player.selectedDefence()) {
				return false;
			}
		}
		return true;
	}
	
	public boolean allPlayersRolled() {
		for (Player player : activePlayers) {
			if (!player.rolled()) {
				return false;
			}
		}
		return true;
	}
	
	public Player getPlayer(String playerName) {
		for (Player p : activePlayers) {
			if(p.getName().equalsIgnoreCase(playerName)) {
				return p;
			}
		}
		return null;
	}
	
	public ArrayList<String> getOutcome() {
		ArrayList<String> outcomes = new ArrayList<String>();
		for (Player p : activePlayers) {
			outcomes.add(p.toString());
		}
		return outcomes;
	}

    public boolean ifActualAttackHitsDefence(String attackType,String defenceType) {
		
		if (attackType.equals("THRUST") && defenceType.equals("CHARGE")) {
			return true;
		}
		else if (attackType.equals("SWING") && defenceType.equals("DODGE")) {
			return true;
		}
		else if (attackType.equals("SMASH") && defenceType.equals("DUCK")) {
			return true;
		}
		else {
			return false;
		}
	}
    
    public boolean ifConcludedFinalEndAttackDoesntHit(String attackType,String defenceType) {
		
	    if(attackType.equals(isEndAttackValid(attackMechanism[0], 1)) && defenceType.equals("DODGE")) {
		   return true;
		}else if (attackType.equals(isEndAttackValid(attackMechanism[0], 2)) && defenceType.equals("DUCK")) {
		   return true;
	    }else if (attackType.equals(isEndAttackValid(attackMechanism[1], 1)) && defenceType.equals("CHARGE")) {
	       return true;
	    }
	    else if (attackType.equals(isEndAttackValid(attackMechanism[1], 2)) && defenceType.equals("DUCK")) {
	       return true;
	    }
	    else if (attackType.equals(isEndAttackValid(attackMechanism[2], 1)) && defenceType.equals("CHARGE")) {
	    	return true;
	    }
	    else if (attackType.equals(isEndAttackValid(attackMechanism[2], 2)) && defenceType.equals("DODGE")) {
	    	return true;
	    }
	    else{
	    	return false;
	    }		
	    
}
      public String isEndAttackValid(String a, int userInput) {
		
        String theOutput = " ";
		
		if (a.equals(attackMechanism[0])) {
			if (userInput ==1 || userInput == 2) {
			  theOutput = "THRUST";
		    }
			else if (userInput ==3 ||userInput == 4){
			  theOutput = "SMASH";
			}
			else if(userInput ==5 || userInput ==6){
			  theOutput = "SWING";
			}else{
				theOutput = "INVALID";
			}
		}else if(a.equals(attackMechanism[1])) {
			if(userInput == 1 || userInput == 2 ) {
				theOutput = "SWING";
			}
			if(userInput == 3 || userInput == 4 ) {
				theOutput = "THRUST";
			}
			if(userInput == 5 || userInput == 6 ) {
				theOutput = "SMASH";
			}
		}else if(a.equals(attackMechanism[2]) ) {
			if(userInput == 1 || userInput == 2) {
				theOutput = "SMASH";
			}
			if(userInput == 3 || userInput == 4) {
				theOutput = "SWING";
			}
			if(userInput == 5 || userInput == 6) {
				theOutput = "THRUST";
			}
			
		}else{
			theOutput = "INVALID";
		}
		
		 return theOutput;
		
		
	}
      
      public boolean ifConcludedFinalEndAttackHits(String attackType,String defenceType) {
  		if(attackType.equals(isEndAttackValid(attackMechanism[0], 1)) && defenceType.equals("CHARGE")) {
  		   return true;
  		}else if (attackType.equals(isEndAttackValid(attackMechanism[0], 2)) && defenceType.equals("CHARGE")) {
  		   return true;
  	    }else if (attackType.equals(isEndAttackValid(attackMechanism[1], 1)) && defenceType.equals("DODGE")) {
  	       return true;
  	    }
  	    else if (attackType.equals(isEndAttackValid(attackMechanism[1], 2)) && defenceType.equals("DODGE")) {
  	       return true;
  	    }
  	    else if (attackType.equals(isEndAttackValid(attackMechanism[2], 1)) && defenceType.equals("DUCK")) {
  	    	return true;
  	    }
  	    else if (attackType.equals(isEndAttackValid(attackMechanism[2], 2)) && defenceType.equals("DUCK")) {
  	    	return true;
  	    }
  	    else{
  	    	return false;
  	    }
  	 }
	
	
	
}
