package server;

import common.Attack;
import common.Defence;

public class Round {
	
	private Player playerToAttack;
	private Attack attack;
	private int attackSpeed;
	private Defence defence;
	private int defenceSpeed;
	private int roll;
	
	public Round() {
		attack = Attack.NONE;
		attackSpeed = 0;
		defence = Defence.NONE;
		defenceSpeed = 0;
		roll = 0;
	}
	
	public boolean inflictedWound(Attack actualAttack) {
		Defence playerToAttackDefence = playerToAttack.getRound().getDefence();
		if (actualAttack == Attack.THRUST && playerToAttackDefence == Defence.CHARGE) {
			return true;
		}
		else if (actualAttack == Attack.SWING && playerToAttackDefence == Defence.DODGE) {
			return true;
		}
		else if (actualAttack == Attack.SMASH && playerToAttackDefence == Defence.DUCK) {
			return true;
		} 
		else if (attackSpeed < playerToAttack.getRound().defenceSpeed) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public Attack getActualAttack() {
		if (roll == 1 || roll == 2) {
			return attack;
		}
		else if (attack == Attack.THRUST && (roll == 3 || roll == 4)) {
			return Attack.SMASH;
		}
		else if (attack == Attack.THRUST && (roll == 5 && roll == 6)) {
			return Attack.SWING;
		}
		else if (attack == Attack.SWING && (roll == 3 || roll == 4)) {
			return Attack.THRUST;
		}
		else if (attack == Attack.SWING && (roll == 5 && roll == 6)) {
			return Attack.SMASH;
		}
		else if (attack == Attack.SMASH && (roll == 3 || roll == 4)) {
			return Attack.SWING;
		}
		else if (attack == Attack.SMASH && (roll == 5 && roll == 6)) {
			return Attack.THRUST;
		}
		return Attack.NONE;
	}
	
	public Attack getAttack() {
		return attack;
	}
	public void setAttack(Attack attack) {
		this.attack = attack;
	}
	public int getAttackSpeed() {
		return attackSpeed;
	}
	public void setAttackSpeed(int attackSpeed) {
		this.attackSpeed = attackSpeed;
	}
	public Defence getDefence() {
		return defence;
	}
	public void setDefence(Defence defence) {
		this.defence = defence;
	}
	public int getDefenceSpeed() {
		return defenceSpeed;
	}
	public void setDefenceSpeed(int defenceSpeed) {
		this.defenceSpeed = defenceSpeed;
	}
	public int getRoll() {
		return roll;
	}
	public void setRoll(int roll) {
		this.roll = roll;
	}

	public Player getPlayerToAttack() {
		return playerToAttack;
	}

	public void setPlayerToAttack(Player playerToAttack) {
		this.playerToAttack = playerToAttack;
	}
	
}
