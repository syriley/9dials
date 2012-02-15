package com.ninedials.test;

import java.util.List;

import models.Session;
import models.UserSession;

import org.junit.Test;

public class UserSessionTest extends NineDialsTest {
	@Test
	public void getSharedUsers() {
		Session session = Session.find("byName", "Session 1").first();
		List<UserSession> userSessions = UserSession.getSharedUserSessions(session.id);
		assertEquals(2, userSessions.size());
		
	}
}
