package controllers;

import java.util.List;

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
        render(bands);
	 }
}
