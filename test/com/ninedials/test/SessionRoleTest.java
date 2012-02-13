package com.ninedials.test;

import models.SessionRole;

import org.junit.Test;

public class SessionRoleTest extends NineDialsTest{
	@Test 
	public void SessionRoleCount() {
		assertEquals(3,SessionRole.count());
	}
}
