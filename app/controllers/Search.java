package controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.Band;


public class Search extends CrawlerController {

	 public static void results(String searchTerm) {
		 List<Band> bands = Band.search(searchTerm);
		 Map<String, Object> model = new HashMap<String, Object>();
		 model.put("bands", bands);
		 model.put("searchTerm", searchTerm);
         render(model);
	 }
}
