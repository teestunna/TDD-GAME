package server;

import common.Config;

public class ServerStart {

	public static void main(String[] args) {
		
		Engine engine = new Engine();
		AppServer appServer = new AppServer(Config.PORT_NUMBER, engine);

	}

}
