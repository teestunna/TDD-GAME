package test.server;

import static org.junit.Assert.*;

import org.junit.Test;

import client.AppClient;

public class CreateAttackDefenceSelectMessageTest {
	
	@Test
	public void testValidAttackDefenceSelectMessage() {
		AppClient a = new AppClient();
		
		String aSpeed = "3";
		String dSpeed = "1";
		String aType = "swing";
		String dType = "duck";
		String attackee = "chuboy";
		a.userName = "tope";
		
		
		assertEquals("select tope chuboy swing 3 duck 1",a.createAttackDefenceSelectMessage(attackee,aType, aSpeed,dType, dSpeed));
	}
	
	

}
