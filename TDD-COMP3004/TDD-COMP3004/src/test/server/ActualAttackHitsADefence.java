package test.server;


import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import server.Engine;


public class ActualAttackHitsADefence {
     Engine eng;
    
	@BeforeClass
	public static void setUpBeforeClass()  {
	}

	@AfterClass
	public static void tearDownAfterClass()  {
	}

	@Before
	public void setUp()  {
		eng = new Engine();
	}

	@After
	public void tearDown() {
		eng = null;
	}

	@Test
	public void testActualAttackHitsDefence1() {
		String attackType  = "THRUST";
		String defenceType = "CHARGE";
		
		assertTrue("Should hit",eng.ifActualAttackHitsDefence(attackType,defenceType));

	}
	
	@Test
	public void testActualAttackHitsDefence2() {
		String attackType  = "THRUST";
		String defenceType = "DODGE";
		
		assertFalse("Should not hit",eng.ifActualAttackHitsDefence(attackType,defenceType));

	}
	
	@Test
	public void testActualAttackHitsDefence3() {
		String attackType  = "THRUST";
		String defenceType = "DUCK";
		
		assertFalse("Should hit",eng.ifActualAttackHitsDefence(attackType,defenceType));

	}
	
	@Test
	public void testActualAttackHitsDefence4() {
		String attackType  = "SWING";
		String defenceType = "CHARGE";
		
		assertFalse("Should not hit",eng.ifActualAttackHitsDefence(attackType,defenceType));

	}

	@Test
	public void testActualAttackHitsDefence5() {
		String attackType  = "SWING";
		String defenceType = "DODGE";
		
		assertTrue("Should hit",eng.ifActualAttackHitsDefence(attackType,defenceType));

	}
	

	@Test
	public void testActualAttackHitsDefence6() {
		String attackType  = "SWING";
		String defenceType = "DUCK";
		
		assertFalse("Should not hit",eng.ifActualAttackHitsDefence(attackType,defenceType));

	}
	

	@Test
	public void testActualAttackHitsDefence7() {
		String attackType  = "SMASH";
		String defenceType = "CHARGE";
		
		assertFalse("Should not hit",eng.ifActualAttackHitsDefence(attackType,defenceType));

	}
	

	@Test
	public void testActualAttackHitsDefence8() {
		String attackType  = "SMASH";
		String defenceType = "DODGE";
		
		assertFalse("Should not hit",eng.ifActualAttackHitsDefence(attackType,defenceType));

	}
	

	@Test
	public void testActualAttackHitsDefence9() {
		String attackType  = "SMASH";
		String defenceType = "DUCK";
		
		assertTrue("Should hit",eng.ifActualAttackHitsDefence(attackType,defenceType));

	}
}
