package controllers;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.Morphia;
import com.sjriley.crawler.dal.MongoProvider;

import play.mvc.Controller;

public class CrawlerController extends Controller {
	
	static Datastore datastore;
	
	static {
		datastore = new Morphia().createDatastore(MongoProvider.getMongo(), "tabcrawler");
	}
}
