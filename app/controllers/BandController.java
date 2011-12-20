package controllers;

import java.util.Calendar;
import java.util.Date;

import com.google.code.morphia.query.Query;
import com.google.code.morphia.query.UpdateOperations;
import com.google.gson.Gson;
import com.sjriley.crawler.dao.Band;


public class BandController extends CrawlerController {

	public static void getNextBand() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.HOUR, -1);
		Date oneHourAgo = calendar.getTime();
		Query query = datastore.createQuery(Band.class)
				.filter("dateCompleted = ", null)
				.filter("dateStarted = ", null)
				.order("-numberOfTabs")
				.limit(1);
		UpdateOperations<Band> updateOperations = datastore.createUpdateOperations(Band.class).set("dateStarted", new Date());
		Band band = datastore.findAndModify(query,updateOperations);
		renderJSON(band);
	}
	
	public static void create () {
    	Gson gson = new Gson();
    	String data = params.get("data");
        Band band = gson.fromJson(data, Band.class);
        datastore.save(band);
    }
}
