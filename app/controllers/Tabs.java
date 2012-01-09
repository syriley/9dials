package controllers;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import models.Tab;
import play.mvc.Controller;

import com.google.gson.Gson;

public class Tabs extends Controller {
	
    public static void index(String type) {
    	Tab tab = Tab.getOne("Tabs", type);
        render(tab);
    }
    
    public static void next(String type) {
    	Tab tab = Tab.getOne("Tabs", type);
    	renderJSON(tab);
    }
    
    public static void create () {
    	Gson gson = new Gson();
    	String data = params.get("data");
        Tab tab = gson.fromJson(data, Tab.class);
        tab.save();
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
    	
    	List<Tab> tabs = Tab.list();
    	renderJSON(tabs);
    }

}