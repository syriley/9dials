package models;

import java.util.List;

import com.sjriley.crawler.dao.Tab;

public class TabModel extends CrawlerModel {
	public void save(Tab tab) {
		datastore.save(tab);
	}
	
	public List<Tab> list () {
		List<Tab> tabs = datastore.find(Tab.class).limit(100).asList();
		return tabs;
	}
}
