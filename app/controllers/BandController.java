package controllers;

import com.google.gson.Gson;
import com.sjriley.crawler.dao.Band;


public class BandController extends CrawlerController {

	public static void getNextBand() {
		Band band = datastore.find(Band.class, "dateCompleted", null).order("-numberOfTabs").get();
		renderJSON(band);
	}
	
	public static void create () {
    	Gson gson = new Gson();
    	String data = params.get("data");
        Band band = gson.fromJson(data, Band.class);
        datastore.save(band);
    }
}
