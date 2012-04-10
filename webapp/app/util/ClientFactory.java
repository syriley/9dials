package util;


public class ClientFactory {
	private static ClientFactory instance;
	
	private ClientFactory(){}
	
	public static ClientFactory getInstance() {
		if(instance == null) {
			instance = new ClientFactory();
		}
		return instance;
	}
	
	public HttpClient getClient() {
		return getClient(null);
	}
	
	public HttpClient getClient(String type) {
		return new StandardClient();
	}
}
