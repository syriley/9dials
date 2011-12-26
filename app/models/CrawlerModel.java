package models;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.Morphia;
import com.sjriley.crawler.dal.MongoProvider;

public class CrawlerModel {
static Datastore datastore;
	
	static {
		datastore = new Morphia().createDatastore(MongoProvider.getMongo(), "tabcrawler");
	}
}
