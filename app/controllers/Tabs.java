package controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    	render(tab);
    }
    
    public static void next(String type) {
    	Tab tab = Tab.getOne("Chords", type);
    	renderJSON(tab);
    }
    
    public static void create () {
    	Gson gson = new Gson();
    	String data = params.get("data");
        Tab tab = gson.fromJson(data, Tab.class);
        tab.save();
    }
    

    public static void list() {
    	String search = params.get("search");
    	List<Tab> tabs = Tab.list(search);
    	List<Map<String,String>> result = new ArrayList<Map<String,String>>();
    	for (Tab tab : tabs) {
    		Map<String,String> resultItem = new HashMap<String, String>();
			resultItem.put("idString", tab.getId().toString());
			resultItem.put("artist", tab.artist);
			resultItem.put("title", tab.title);
			result.add(resultItem);
		}
    	renderJSON(result);
    }

}