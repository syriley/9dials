package com.ninedials.test;

import models.Session;
import models.User;

import org.junit.Before;
import org.junit.Test;

public class SessionTest extends NineDialsTest {
	
	User user1;
	Session session1;
	
	@Before 
	public void setup() {
		super.setup();
		user1 = User.find("byUsername", "user1").first();
		session1 = Session.find("byName", "session1").first();
	}
	
	@Test
	public void getSession() {
		Session original = Session.find("byName", "session1").first();
		Session session = user1.getSession(original.id);
		assertEquals("session1", session.name);
		assertEquals("session 1 description", session.description);
	}
	
	@Test
	public void createNewSession() {
		Session session2 = new Session("another Session", "description").save(); 
		user1.createSession(session2);
		User dbUser = User.find("byUsername", "user1").first();
		assertEquals("User 1", dbUser.name);
		assertEquals(2, dbUser.userSessions.size());
		assertEquals(1, dbUser.getSession(session2.id).currentBranch.getTracks().size());
		assertEquals("another Session", dbUser.getSession(session2.id).currentBranch.session.name);
		assertEquals("owner", dbUser.userSessions.get(0).role);
	}
	
	
//	@Test
//	public void shareSessionWithUser() {
//		Session session = Session.find("byName", "unattached Session").first();
//		User user = User.find("byEmail", "a@a.com").first();
//		session.shareWithUser(user);
//		User fromDb = User.find("byEmail", "a@a.com").first();
//		assertEquals(1, fromDb.userSessions.size());
//		assertEquals("collaborator", fromDb.userSessions.get(0).role);
//	}
	
	
//	@Test
//	public void updateSession() {
//		Session original = Session.find("byName", "session3").first();
//		Session session = Session.getSession(original.id);
//		assertEquals("session3", session.name);
//		session.name = "updatedName";
//		session.description = "updatedDescription";
//		Session inMemoryUpdated = session.updateBranch();
//		assertEquals("updatedName", inMemoryUpdated.name);
//		Session dbSession = Session.getLatestSession(1L, 1);
//		assertEquals("updatedName", dbSession.name);
//	}
}
