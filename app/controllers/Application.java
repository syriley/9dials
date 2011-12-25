package controllers;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import play.mvc.Controller;

public class Application extends Controller {

    public static void index() {
        render();
    }
    
    public static void search(String searchTerm) {
        render(searchTerm);
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