package com.ninedials.test;

import java.util.List;

import models.OAuthUserHelper;
import models.AUser;

import org.junit.Test;

import controllers.Instruments;

public class InstrumentsTest extends NineDialsTest{

	 @Test
	    public void testBlankSearch() {
	    	List<String> instruments = Instruments.filterInstruments(null);
	    	assertEquals("Blank search expected to match only "+Instruments.MAX_ITEMS+" items",Instruments.MAX_ITEMS, instruments.size());
	    }
}
