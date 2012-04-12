/*
 * silvertunnel.org Demo - Java example applications accessing anonymity networks
 * Copyright (C) 2009-2011 silvertunnel.org
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation; either version 2 of the License, or (at your option)
 * any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for
 * more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, see <http://www.gnu.org/licenses/>.
 */
package util.connection;

import java.io.IOException;
import java.net.URL;
import java.util.logging.Logger;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.silvertunnel.netlib.adapter.httpclient.NetlibSocketFactory;
import org.silvertunnel.netlib.api.NetLayer;


/**
 * Client implementation that uses Apache Http Client 4.
 * 
 * @author hapke
 */
public class AnonymousClient implements util.connection.HttpClient {
    private static final Logger log = Logger.getLogger(AnonymousClient.class.getName());
    private NetLayer lowerNetLayer;
    private static final String USER_AGENT = "Mozilla/5.0 (Windows; U; Windows NT 5.1; de; rv:1.9.0.1) Gecko/2008070208 Firefox/3.0.1";
    
    /** use the same HTTPClient for multiple requests to one site to allow cookie handling */
    private HttpClient httpClient;

    /**
     * Initialize HttpComponents Client
     * 
     * @param lowerNetLayer    TCP/IP compatible layer;
     *                         layer for SSL/TLS/https connections
     *                         will be created inside this class
     *                         and may not be passed as argument here 
     */
    public AnonymousClient(NetLayer lowerNetLayer) {
    	httpClient = null;
        // register the "http" protocol scheme, it is required
        // by the default operator to look up socket factories.
        SchemeRegistry schemeRegistry = new SchemeRegistry();
        final int DEFAULT_PORT_HTTP = 80;
        this.lowerNetLayer = lowerNetLayer;
        schemeRegistry.register(new Scheme("http", new NetlibSocketFactory(lowerNetLayer), DEFAULT_PORT_HTTP));

        /* TODO - DOES CURRENTLY NOT WORK: register the "https" protocol scheme
        final int DEFAULT_PORT_HTTPS = 443;
        NetLayer httpsLowerNetLayer = new TLSNetLayer(lowerNetLayer);  
        schemeRegistry.register(new Scheme("https", new NetlibSocketFactory(httpsLowerNetLayer), DEFAULT_PORT_HTTPS));
        */

        // set http(s) client parameters
        HttpParams params = new BasicHttpParams();
        HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
        HttpProtocolParams.setContentCharset(params, "UTF-8");
        if (USER_AGENT!=null) {
            HttpProtocolParams.setUserAgent(params, USER_AGENT);
        }

        // create http(s) client
        ClientConnectionManager ccm = new ThreadSafeClientConnManager(schemeRegistry);
        httpClient = new DefaultHttpClient(ccm, params);
        
        log.info("HttpComponents Client/httpClient initialized="+httpClient.toString());
    }
    
    public String download(URL source) throws IOException {
        // set the request URL
        HttpUriRequest request = new HttpGet(source.toExternalForm());

        // send request and receive response
        log.info("download (start) from source="+source);
        HttpResponse response = httpClient.execute(request);
        log.info("download (end)   from source="+source+"    with statusLine=\""+response.getStatusLine()+"\"");
 
        // read the response
        String responseString = EntityUtils.toString(response.getEntity());
        EntityUtils.consume(response.getEntity());
        return responseString;
    }
    
    public Document downloadAsDocument(URL source) throws IOException {
		return Jsoup.parse(download(source));
	}
}
