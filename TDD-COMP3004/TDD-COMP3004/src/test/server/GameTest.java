package test.server;

import static org.junit.Assert.*;

import org.junit.Test;

import common.Attack;
import common.Defence;
import common.GameState;
import server.Engine;
import server.Player;

public class GameTest {
	
	@Test
	public void fullGameTest() {
		Engine engine = new Engine();
		engine.setNumberOfPlayers(2);
		engine.setNumOfRounds(2);
		engine.startGame();
		
		
		Player p1 = new Player(1,"p1","127.23.1.1");
		Player p2 = new Player(2,"p2","127.23.1.2");
		
		engine.addPlayer(p1);
		engine.addPlayer(p2);
	
		assertTrue(engine.setSelection(p1.getName(), p2.getName(), Attack.THRUST, 2, Defence.DODGE, 2));
		assertTrue(engine.setSelection(p2.getName(), p1.getName(), Attack.SWING, 2, Defence.CHARGE, 2));
		
		assertTrue(engine.setRoll(p1.getName(), 2));
		assertTrue(engine.setRoll(p2.getName(), 1));
		
		assertEquals(engine.getPlayer(p1.getName()).getWounds(), 1);
		assertEquals(engine.getPlayer(p2.getName()).getWounds(), 1);
		
		assertEquals(engine.getGameState(), GameState.ROUND_COMPLETE);
		
		assertTrue(engine.startNextRound());
		
		assertTrue(engine.setSelection(p1.getName(), p2.getName(), Attack.THRUST, 2, Defence.DODGE, 2));
		assertTrue(engine.setSelection(p2.getName(), p1.getName(), Attack.SWING, 2, Defence.CHARGE, 2));
		
		assertTrue(engine.setRoll(p1.getName(), 2));
		assertTrue(engine.setRoll(p2.getName(), 1));
		
		assertEquals(engine.getPlayer(p1.getName()).getWounds(), 2);
		assertEquals(engine.getPlayer(p2.getName()).getWounds(), 2);
		
		assertEquals(engine.getGameState(), GameState.GAME_COMPLETE);
		assertFalse(engine.startNextRound());
		
	}
	
	

}
