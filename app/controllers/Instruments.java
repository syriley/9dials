package controllers;

import java.util.ArrayList;
import java.util.List;

import models.Instrument;

public class Instruments extends LoggedInController {

	public static void list(){
		List<Instrument> allInstruments = Instrument.findAll();
		List<String> instrumentNames = new ArrayList<String>();
		for(Instrument i : allInstruments){
			instrumentNames.add(i.name);
		}
		renderJSON(instrumentNames);
	}
	
	
}
