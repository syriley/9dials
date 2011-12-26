package controllers;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import models.TabModel;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.Morphia;
import com.google.gson.Gson;
import com.sjriley.crawler.dal.MongoProvider;
import com.sjriley.crawler.dao.Tab;

public class TabController extends CrawlerController {
	
	private static TabModel tabModel;
	
	static {
		tabModel = new TabModel();
	}
    public static void index() {
        render();
    }
    
    public static void create () {
    	Gson gson = new Gson();
    	String data = params.get("data");
        Tab tab = gson.fromJson(data, Tab.class);
        tabModel.save(tab);
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
    	
    	List<Tab> tabs = tabModel.list();
    	renderJSON(tabs);
    }

}