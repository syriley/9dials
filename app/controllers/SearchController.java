package controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.BandModel;

import com.sjriley.crawler.dao.Band;

public class SearchController extends CrawlerController {

	static BandModel bandmodel;
	
	static {
		bandmodel = new BandModel();
	}
	 public static void results(String searchTerm) {
		 Band band = new Band();
		 band.setName(searchTerm);
		 List<Band> bands = bandmodel.searchBands(band);
		 Map<String, Object> model = new HashMap<String, Object>();
		 model.put("bands", bands);
		 model.put("searchTerm", searchTerm);
        render(model);
	 }
}
