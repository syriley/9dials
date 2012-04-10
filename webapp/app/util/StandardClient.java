package util;

import java.io.IOException;
import java.net.URL;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StandardClient implements HttpClient{
	private static final Logger logger = LoggerFactory.getLogger(StandardClient.class);
	DefaultHttpClient client = new DefaultHttpClient();
	public String download(URL source) throws IOException, ClientProtocolException {
		
    	HttpGet request = new HttpGet(source.toExternalForm());
    	request.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; .NET CLR 1.0.3705; .NET CLR 1.1.4322; .NET CLR 1.2.30703)"); 
    	try {
			HttpResponse response = client.execute(request);
			String responseSting = EntityUtils.toString(response.getEntity());
			EntityUtils.consume(response.getEntity());
	    	return responseSting;
		}
		catch (IOException e) {
			logger.error("Could not get tab", e);
		}
    	return null;
	}
	public Document downloadAsDocument(URL source) throws IOException, ClientProtocolException {
		return Jsoup.parse(download(source));
	}
}
