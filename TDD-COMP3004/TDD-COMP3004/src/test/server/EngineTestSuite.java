package test.server;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

	@RunWith(Suite.class)
	@Suite.SuiteClasses({
	   ActualAttackHitsADefence.class,
	   GameTest.class,
	   IfAttackHitsWithSpeedTest.class,
	   IsAttackDefenceSpeedValidTest.class,
	   IsAttackSelectionMessageValidTest.class,
	   IsDefenceSelectionMessageValidTest.class,
	   IsNumofPlayersValidTest.class,
	   IsNumOfRoundsValidTest.class,
	   TestConcludesEndAttackDoesntHit.class,
	   TestConcludesEndAttackHits.class,
	   CreateAttackDefenceSelectMessageTest.class,
	   CreateJoinMessageTest.class,
	   CreateRollMessageTest.class
	 
	})

	public class EngineTestSuite {   
	} 

