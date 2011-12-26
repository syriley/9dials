package models;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.google.code.morphia.query.Query;
import com.google.code.morphia.query.UpdateOperations;
import com.google.gson.Gson;
import com.sjriley.crawler.dao.Band;

public class BandModel extends CrawlerModel {
	
	public Band getNextBand() {
		Query query = datastore.createQuery(Band.class)
				.filter("dateCompleted = ", null)	
				.filter("dateStarted != ", null)
				.order("-numberOfTabs")
				.limit(1);
		UpdateOperations<Band> updateOperations = datastore.createUpdateOperations(Band.class).set("dateStarted", new Date());
		Band band = datastore.findAndModify(query,updateOperations);
		return band;
	}
	
	public List<Band> searchBands(Band band) {
		
		Query query = datastore.createQuery(Band.class)
			.filter("name ", band.getName() + ".*/");
		return query.asList();
	}
	
	public void save(Band band) {
        datastore.save(band);
	}
}
