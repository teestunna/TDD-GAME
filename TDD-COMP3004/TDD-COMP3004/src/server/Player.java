package server;

import common.Attack;
import common.Defence;

public class Player {
	
	private int id;
	private String name;
	private String ip;
	private int wounds;
	private Round roundSelection;
	
	public Player(int id, String name, String ip){
		this.id = id;
		this.name = name;
		this.setIp(ip);
		this.wounds = 0;
		this.roundSelection = new Round();
	}
	
	public Round getRound() {
		return roundSelection;
	}
	
	public String getName(){
		return name;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public void setRoll(int roll) {
		roundSelection.setRoll(roll);
	}

	public boolean rolled() {
		return roundSelection.getRoll() != 0;
	}
	
	public void updateAttackDefence(Player playerToAttack, Attack attack, int attackSpeed, Defence defence, int defenceSpeed) {
		roundSelection.setPlayerToAttack(playerToAttack);
		roundSelection.setAttack(attack);
		roundSelection.setAttackSpeed(attackSpeed);
		roundSelection.setDefence(defence);
		roundSelection.setDefenceSpeed(defenceSpeed);
	}

	public boolean selectedAttack() {
		return roundSelection.getAttack() != Attack.NONE;
	}

	public boolean selectedDefence() {
		return roundSelection.getDefence() != Defence.NONE;
	}
	
	public void resetRound() {
		roundSelection = new Round();
	}
	
	public int getWounds() {
		return wounds;
	}

	public void inflictWound() {
		wounds++;
	}
	
	public void resetRoundData() {
		roundSelection = new Round();
	}

	@Override
	public String toString() {
		return "name: " + name + " wounds: " + wounds;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	
	

}
