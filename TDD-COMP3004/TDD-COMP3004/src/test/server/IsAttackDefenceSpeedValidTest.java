package test.server;

import static org.junit.Assert.*;

import org.junit.Test;

import common.Config;

public class IsAttackDefenceSpeedValidTest {
	
	@Test
	public void testValidAttackDefenceSpeed() {
		int attackSpeed  = 3;
		int defenceSpeed = 1;
		
		assertTrue("Attack and defence speed", Config.isAttackDefenceSpeedValid(attackSpeed, defenceSpeed));
	}
	
	@Test
	public void testInvalidAttackDefenceSpeed () {
		int attackSpeed  = 0;
		int defenceSpeed = 1;
		
		assertFalse("Attack and defence speed", Config.isAttackDefenceSpeedValid(attackSpeed, defenceSpeed));
	}
	

}
