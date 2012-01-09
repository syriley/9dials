package controllers;

import models.Band;

import com.google.gson.Gson;


public class Bands extends CrawlerController {
	
	public static void next() {
		Band band = Band.getNext();
		renderJSON(band);
	}
	
	public static void create () {
    	Gson gson = new Gson();
    	String data = params.get("data");
        Band band = gson.fromJson(data, Band.class);
        band.save();
    }
}
