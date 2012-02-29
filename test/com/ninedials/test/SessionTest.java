package com.ninedials.test;

import models.Session;
import models.AUser;

import org.junit.Test;

public class SessionTest extends NineDialsTest {
	
	@Test
	public void addSessionToUser() {
		AUser user = new AUser("new", "a@b.com", "password", "bio").save();
		Session session = new Session("session1", "desc").save();
		user.createSession(session);
		
		AUser fromDb = AUser.find("byEmail", "a@b.com").first();
		
		assertEquals("new", fromDb.name);
		assertEquals(1, fromDb.userSessions.size());
		assertEquals("session1", fromDb.userSessions.get(0).session.name);
		assertEquals("owner", fromDb.userSessions.get(0).role);
	}
	
	@Test
	public void shareSessionWithUser() {
		Session session = Session.find("byName", "unattached Session").first();
		AUser user = AUser.find("byEmail", "a@a.com").first();
		session.shareWithUser(user);
		AUser fromDb = AUser.find("byEmail", "a@a.com").first();
		assertEquals(1, fromDb.userSessions.size());
		assertEquals("collaborator", fromDb.userSessions.get(0).role);
	}
	
	@Test
	public void getSession() {
		Session session = Session.getSession(1L);
		assertEquals("session3", session.name);
		assertEquals("description", session.description);
	}
	
	@Test
	public void updateSession() {
		Session session = Session.getSession(1L);
		assertEquals("session3", session.name);
		Session update = new Session("updatedName", "updatedDescription");
		Session inMemoryUpdated = Session.updateSession(session.id, update);
		assertEquals("updatedName", inMemoryUpdated.name);
		Session updateTest = Session.getSession(1L);
		assertEquals("updatedName", updateTest.name);
	}
}
