package controllers;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import play.mvc.Controller;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.Morphia;
import com.sjriley.crawler.dal.MongoProvider;
import com.sjriley.crawler.dao.Tab;

public class TabController extends CrawlerController {

	
    public static void index() {
        render();
    }
    
    public static void create (String myName) {
        render(myName);
    }
    
    public static void list() {
    	URL url = null;
    	List<URL> urls = new ArrayList<URL>();
		try {
			urls.add(new URL("http://www.hotmail.com"));
			urls.add(new URL("http://www.hotmailfs.com"));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	renderJSON(urls);
    }

    public static void get() {
    	
    	List<Tab> tabs = datastore.find(Tab.class).limit(100).asList();
    	renderJSON(tabs);
    }

}