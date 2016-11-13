package test.server;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import server.Engine;

public class IfAttackHitsWithSpeedTest {
	Engine eng;

	@BeforeClass
	public static void setUpBeforeClass() {
		
	}

	@AfterClass
	public static void tearDownAfterClass()  {
	}

	@Before
	public void setUp() throws Exception {
		eng = new Engine();
	}

	@After
	public void tearDown() throws Exception {
		eng = null;
	}

	@Test
	public void testIfAttackHitsWithSpeed1() {
		
		int aSpeed = 1;
		int dSpeed = 1;
		
		assertFalse("Attack is equal than defence",eng.ifAttackHitsWithSpeed(aSpeed, dSpeed));
		
	}
	
	@Test
	public void testIfAttackHitsWithSpeed2() {
		
		int aSpeed = 1;
		int dSpeed = 2;
		
		assertTrue("Attack is smaller than defence",eng.ifAttackHitsWithSpeed(aSpeed, dSpeed));
		
		
	}
	
	@Test
	public void testIfAttackHitsWithSpeed3() {
		
		int aSpeed = 1;
		int dSpeed = 3;
		
		assertTrue("Attack is smaller than defence",eng.ifAttackHitsWithSpeed(aSpeed, dSpeed));
		
		
	}
	
	
	
	@Test
	public void testIfAttackHitsWithSpeed4() {
		
		int aSpeed = 2;
		int dSpeed = 1;
		
		assertFalse("Attack is greater than defence",eng.ifAttackHitsWithSpeed(aSpeed, dSpeed));
		
		
	}
	
	@Test
	public void testIfAttackHitsWithSpeed5() {
		
		int aSpeed = 2;
		int dSpeed = 2;
		
		assertFalse("Attack is equal to defence",eng.ifAttackHitsWithSpeed(aSpeed, dSpeed));
		
		
	}
	
	@Test
	public void testIfAttackHitsWithSpeed6() {
		
		int aSpeed = 2;
		int dSpeed = 3;
		
		assertTrue("Attack is smaller than defence",eng.ifAttackHitsWithSpeed(aSpeed, dSpeed));
		
		
	}
	
	@Test
	public void testIfAttackHitsWithSpeed7() {
		
		int aSpeed = 3;
		int dSpeed = 1;
		
		assertFalse("Attack is greater than defence",eng.ifAttackHitsWithSpeed(aSpeed, dSpeed));
	
	}
	
	@Test
	public void testIfAttackHitsWithSpeed8() {
		
		int aSpeed = 3;
		int dSpeed = 2;
		
		assertFalse("Attack is greater than defence",eng.ifAttackHitsWithSpeed(aSpeed, dSpeed));
		
		
	}
	
	@Test
	public void testIfAttackHitsWithSpeed9() {
		
		int aSpeed = 3;
		int dSpeed = 3;
		
		assertFalse("Attack is equal to defence",eng.ifAttackHitsWithSpeed(aSpeed, dSpeed));
		
		
	}
	
	

}
