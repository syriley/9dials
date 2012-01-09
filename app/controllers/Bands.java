package controllers;

import models.Band;
import play.mvc.Controller;

import com.google.gson.Gson;


public class Bands extends Controller {
	
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
