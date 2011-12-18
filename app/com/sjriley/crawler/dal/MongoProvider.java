package com.sjriley.crawler.dal;

import java.net.UnknownHostException;

import org.apache.log4j.Logger;

import com.mongodb.Mongo;
import com.mongodb.MongoException;


public abstract class MongoProvider {
	private static final Logger logger = Logger.getLogger(MongoProvider.class);
	private static Mongo mongo;
	
	public static Mongo getMongo() {
		if (mongo == null) {
			try {
				mongo = new Mongo();
			} catch (UnknownHostException e) {
				logger.error("Could not connect to database", e);
			} catch (MongoException e) {
				logger.error("Could not connect to database", e);
			}
		}
		return mongo;
	}
}
