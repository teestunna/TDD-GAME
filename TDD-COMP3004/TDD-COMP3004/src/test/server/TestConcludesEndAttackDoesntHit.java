package test.server;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import server.Engine;
import static org.junit.Assert.*;


public class TestConcludesEndAttackDoesntHit {

		
		Engine eng;

		@BeforeClass
		public static void setUpBeforeClass() throws Exception {
		}

		@AfterClass
		public static void tearDownAfterClass() throws Exception {
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
		public void testConcludeEndAttackDoesntHit() {

			int rollNumber    = 1;
			String attackType = eng.isEndAttackValid(eng.attackMechanism[0], rollNumber);
			String defenceType = "DODGE";
			System.out.println(attackType);
				
				
			assertTrue("Should not hit",eng.ifConcludedFinalEndAttackDoesntHit(attackType,defenceType));
		}
	
}
