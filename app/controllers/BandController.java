package controllers;

import com.sjriley.crawler.dao.Band;


public class BandController extends CrawlerController {

	public static void getNextBand() {
		Band band = datastore.find(Band.class, "dateCompleted", null).order("-numberOfTabs").get();
		renderJSON(band);
	}
}
