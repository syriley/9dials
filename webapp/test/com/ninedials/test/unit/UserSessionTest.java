package com.ninedials.test.unit;

import java.util.List;

import models.Session;
import models.AUser;
import models.UserSession;

import org.junit.Test;

public class UserSessionTest extends NineDialsTest {
	@Test
	public void getSharedUsers() {
		Session session = Session.find("byName", "session1").first();
		List<UserSession> userSessions = UserSession.getSharedUserSessions(session);
		assertEquals(2, userSessions.size());
	}
	
//	@Test
//	public void removeUserSession() {
//		Session session = Session.find("byName", "Session 1").first();
//		User user = User.find("byEmail", "a@a.com").first();
//		session.removeUser(user.id);
//	}
}