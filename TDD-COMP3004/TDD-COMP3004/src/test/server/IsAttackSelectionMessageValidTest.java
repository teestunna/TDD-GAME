package test.server;

import static org.junit.Assert.*;

import org.junit.Test;

import common.Attack;

public class IsAttackSelectionMessageValidTest {
	
	@Test
	public void testIsAttackSelectionValid () {
		String attackSelection = "THRUST";
		
		assertTrue("Attack selection is valid",Attack.isValid(attackSelection));
	}
	
	@Test
	public void testIsAttackSelectionInvalid () {
		String attackSelection = "CATAPULT";
		
		assertFalse("Attack selection is invalid, fails",Attack.isValid(attackSelection));
	}
	
	

}
