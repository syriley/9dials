package controllers;

import java.util.Calendar;
import java.util.Date;

import models.BandModel;

import com.google.code.morphia.query.Query;
import com.google.code.morphia.query.UpdateOperations;
import com.google.gson.Gson;
import com.sjriley.crawler.dao.Band;


public class BandController extends CrawlerController {
	private static BandModel bandModel;
	
	static {
		bandModel = new BandModel();
	}
	public static void getNextBand() {
		Band band = bandModel.getNextBand();
		renderJSON(band);
	}
	
	public static void create () {
    	Gson gson = new Gson();
    	String data = params.get("data");
        Band band = gson.fromJson(data, Band.class);
        bandModel.save(band);
    }
}
