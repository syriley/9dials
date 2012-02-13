package controllers;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import models.Session;
import models.User;

import play.mvc.Before;
import play.mvc.Controller;

public class Application extends Controller {

	@Before
    static void setConnectedUser() {
        if(Security.isConnected()) {
            User user = User.find("byEmail", Security.connected()).first();
            renderArgs.put("user", user);
        }
    }
	
    public static void index() {
    	List<Session> seshion = Session.findAll();
        render(seshion);
    }
    
    public static void getNextTab() {
    	URL url = null;
    	List<URL> urls = new ArrayList<URL>();
		try {
			urls.add(new URL("http://www.hotmail.com"));
			urls.add(new URL("http://www.hotmailfs.com"));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
    	renderJSON(urls);
    }

}