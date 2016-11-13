package test.server;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;


import common.Config;

public class IsNumofPlayersValidTest {
	
	@Test
	public void testIsNumOfPlayersValid() {
		int amountPlayer = 2;
		
		assertTrue("NUMBER OF PLAYERS IS VALID, BUT GETTING PASSES",
				Config.isNumofPlayersValid(amountPlayer));
	}
	
	@Test
	public void testIsNumOfPlayersValid1() {
		int amountPlayer = 1;
		
		assertFalse("NUMBER OF PLAYERS IS INVALID, FAILS",
				Config.isNumofPlayersValid(amountPlayer));
	}
	
	@Test
	public void testIsNumOfPlayersValid2() {
		int amountPlayer = 7;
		
		assertFalse("NUMBER OF PLAYERS IS INVALID, BUT GETTING TRUE",
				Config.isNumofPlayersValid(amountPlayer));
	}
}
