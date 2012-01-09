package models;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import play.modules.morphia.Model;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.query.Criteria;
import com.google.code.morphia.query.Query;
import com.google.code.morphia.query.UpdateOperations;

@Entity
public class Tab extends Model {
	public String url;
	public String artist;
	public String title;
	public int rating;
	public String type;
	public int numberOfVotes;
	public Date updated;
	public Date pageProcessed;
	public Date dateTabStarted;
	public Date dateTabCompleted;
	public String originalTabText;
	public boolean expire;
	
	public URL getUrl() {
		try {
			return new URL(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void setUrl(URL url) {
		this.url = url.toExternalForm();
	} 
	
	public static List<Tab> list () {
		List<Tab> tabs = Tab.q().limit(100).asList();
		return tabs;
	}
	
	public static Tab getOne(String tabType, String crawlType) {
		Tab tab = null;
		if(crawlType == null || crawlType.equals("tab")) {
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.HOUR, -1);
			Date date = calendar.getTime();
			Datastore datastore = Tab.ds();
			MorphiaQuery morphiaQuery = Tab.q();
			morphiaQuery.filter("type", tabType);
			morphiaQuery.filter("dateTabStarted = ", null);
			morphiaQuery.filter("dateTabCompleted = ", null);
			//query.or(query.criteria("expire").equal("false"), query.criteria("expire").notEqual(true));
			morphiaQuery.filter("expire != ", false);
			morphiaQuery.order("-numberOfVotes");
			Query query = morphiaQuery.limit(1).getMorphiaQuery();
			UpdateOperations<Tab> updateOperations = datastore.createUpdateOperations(Tab.class).set("dateTabStarted", new Date());
			tab = datastore.findAndModify(query, updateOperations);
		}
		return tab;
	}
}
