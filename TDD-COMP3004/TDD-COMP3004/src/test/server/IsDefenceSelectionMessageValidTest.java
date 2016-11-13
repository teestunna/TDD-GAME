package test.server;

import static org.junit.Assert.*;

import org.junit.Test;

import common.Defence;

public class IsDefenceSelectionMessageValidTest {
	
	@Test
	public void testIsDefenceSelectionValid () {
		String defenceSelection = "DODGE";
		
		assertTrue("Attack selection is valid",Defence.isValid(defenceSelection));
	}
	
	@Test
	public void testIsDefenceSelectionInvalid () {
		String defenceSelection = "HIDE";
		
		assertFalse("Attack selection is invalid, fails",Defence.isValid(defenceSelection));
	}
	
	
	

}
