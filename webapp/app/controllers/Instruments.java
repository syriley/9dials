package controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import play.Logger;

import models.Instrument;

public class Instruments extends LoggedInController {
	public static final int MAX_ITEMS = 10;
	
	public static void list(String term){
		List<String> instrumentNames = filterInstruments(term);
		renderJSON(instrumentNames);
	}

	public static List<String> filterInstruments(String term){
		List<Instrument> matchedInstruments = findMatchingInstruments(term);		
		List<String> instrumentNames = extractInstrumentNames(matchedInstruments);
		return instrumentNames;
	}			
	
	private static List<Instrument> findMatchingInstruments(String term) {
		List<Instrument> allInstruments = Collections.emptyList();
		if(term==null|| term.equals("")){
			allInstruments = Instrument.find("order by name").fetch(MAX_ITEMS);
		}
		else{
			allInstruments = Instrument.find("name like ? order by name","%"+term+"%").fetch(MAX_ITEMS);	
		}
		return allInstruments;
	}

	private static List<String> extractInstrumentNames(
			List<Instrument> allInstruments) {
		if(allInstruments.isEmpty()){
			return Collections.emptyList();
		}
		List<String> instrumentNames = new ArrayList<String>();
		for(Instrument i : allInstruments){
			instrumentNames.add(i.name);
		}
		return instrumentNames;
	}
}
