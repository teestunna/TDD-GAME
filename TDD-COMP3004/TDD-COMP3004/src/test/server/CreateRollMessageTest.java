package test.server;

import static org.junit.Assert.*;

import org.junit.Test;

import client.AppClient;

public class CreateRollMessageTest {
	
	
	@Test
	public void testValidCreateRollMessage() {
		AppClient a = new AppClient();
		int rollChoice = 4;
		a.userName = "tope";
		
		assertEquals("roll tope 4",a.createRollMessage(rollChoice));
	}
	
	
	

}
