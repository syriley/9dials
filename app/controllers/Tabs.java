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
    
    public static void show(String id) {
    	Tab tab = Tab.findById(id);
    	renderJSON(tab);
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
    

    public static void list(String artist) {
    	List<Tab> tabs = Tab.list(artist);
    	
    	for (Tab tab : tabs) {
			tab.idString = tab.getId().toString();
		}
    	renderJSON(tabs);
    }

}