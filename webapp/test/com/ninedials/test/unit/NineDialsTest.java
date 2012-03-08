package com.ninedials.test.unit;

import jobs.Bootstrap;

import org.junit.Before;

import play.test.Fixtures;
import play.test.UnitTest;

public abstract class NineDialsTest extends UnitTest{
	 @Before 
    public void setup() {
    	new Bootstrap().reloadData();
    }
}
