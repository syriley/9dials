package com.ninedials.test;

import models.Session;
import models.User;

import org.junit.Test;

public class SessionTest extends NineDialsTest {
	
	@Test
	public void addSessionToUser() {
		User user = new User("new", "a@b.com", "bio").save();
		Session session = new Session("session1", "desc").save();
		user.createSession(session);
		
		User fromDb = User.find("byEmail", "a@b.com").first();
		
		assertEquals("new", fromDb.name);
		assertEquals(1, fromDb.userSessions.size());
		assertEquals("session1", fromDb.userSessions.get(0).session.name);
		assertEquals("owner", fromDb.userSessions.get(0).sessionRole.name);
	}
}
