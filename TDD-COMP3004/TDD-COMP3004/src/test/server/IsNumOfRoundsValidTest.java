package test.server;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import common.Config;


public class IsNumOfRoundsValidTest {
	
    @Test
	public void testInvalidNumberOfRounds() {
    	int amountRounds = 0;
    	assertFalse("Number of rounds is invalid, but returned true", Config.isNumOfRoundsValid(amountRounds));
	}
    
    @Test
	public void testValidNumberOfRounds() {
    	int amountRounds = 1;
    	assertTrue("Number of rounds is valid, but returned false", Config.isNumOfRoundsValid(amountRounds));
	}
    
    
}
