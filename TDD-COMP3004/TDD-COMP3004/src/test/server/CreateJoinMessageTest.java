package test.server;

import static org.junit.Assert.*;

import java.net.UnknownHostException;

import org.junit.Test;

import client.AppClient;

public class CreateJoinMessageTest {
	
	@Test
	public void testValidCreateJoinMessage() throws UnknownHostException {
		AppClient a = new AppClient();

		String nameOfPlayer = "tope";
		System.out.println(a.createJoinMessage(nameOfPlayer));
		
		assertEquals("join tope 127.0.0.1",a.createJoinMessage(nameOfPlayer));
	}
	
	@Test
	public void testInvalidCreateJoinMessage() throws UnknownHostException {
		AppClient a = new AppClient();

		String nameOfPlayer = "tope";
		System.out.println(a.createJoinMessage(nameOfPlayer));
		
		assertEquals("join tope 127.0.0.1",a.createJoinMessage(nameOfPlayer));
	}

}
