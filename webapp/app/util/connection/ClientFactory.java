package util.connection;

import org.silvertunnel.netlib.api.NetFactory;
import org.silvertunnel.netlib.api.NetLayer;
import org.silvertunnel.netlib.api.NetLayerIDs;

import util.connection.AnonymousClient;


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
	    if(type != null) {
            if(type.equals("anonymous")) {
                NetLayer lowerNetLayer = NetFactory.getInstance().getNetLayerById(NetLayerIDs.TOR_OVER_TLS_OVER_TCPIP);
                return new  AnonymousClient(lowerNetLayer);
            }
	    }
		return new StandardClient();
	}
}
