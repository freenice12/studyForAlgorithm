package day7DeleteGame.util;

import javax.jms.Session;

public class ConnectorFactory {
	private static ConnectorFactory factory = new ConnectorFactory();
	
	private ConnectorFactory() {}
	
	public static ConnectorFactory getInstance() {
		return factory;
	}
	
	public static MqConnector getActiveMQConnector(String dest, int port) {
		return new ActivemqConnector(dest, port, false, Session.AUTO_ACKNOWLEDGE);
	}
	

}
