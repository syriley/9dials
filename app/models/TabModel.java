package models;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.google.code.morphia.query.Query;
import com.google.code.morphia.query.UpdateOperations;
import com.sjriley.crawler.dao.Band;
import com.sjriley.crawler.dao.Tab;

public class TabModel extends CrawlerModel {
	public void save(Tab tab) {
		datastore.save(tab);
	}
	
	public List<Tab> list () {
		List<Tab> tabs = datastore.find(Tab.class).limit(100).asList();
		return tabs;
	}
	
	public Tab getOne(String tabType, String crawlType) {
		Tab tab = null;
		if(crawlType == null || crawlType.equals("tab")) {
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.HOUR, -1);
			Date date = calendar.getTime();
			Query query = datastore.createQuery(Tab.class);
			query.filter("type", tabType);
			query.filter("dateTabStarted = ", null);
			query.filter("dateTabCompleted = ", null);
			query.order("-numberOfVotes");
			query.limit(1);
			UpdateOperations<Tab> updateOperations = datastore.createUpdateOperations(Tab.class).set("dateTabStarted", new Date());
			tab = datastore.findAndModify(query, updateOperations);
		}
		return tab;
	}
}
