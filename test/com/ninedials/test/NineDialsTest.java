package com.ninedials.test;

import org.junit.Before;

import play.test.Fixtures;
import play.test.UnitTest;

public abstract class NineDialsTest extends UnitTest{
	 @Before 
	    public void setup() {
	    	Fixtures.deleteDatabase();
	    	Fixtures.loadModels("data.yml");
	    }
}
