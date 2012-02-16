package com.ninedials.test;

import java.util.List;

import models.Region;
import models.Session;
import models.SessionUser;
import models.Track;
import models.User;

import org.junit.Before;
import org.junit.Test;

import play.test.UnitTest;

public class SessionTest extends UnitTest {
	
	@Before
	public void setup() {
		Session.deleteAll();
	}
	
	@Test
	public void saveSession() {
		Session session = new Session("session 1", "a description");
		Track track = session.addTrack();
		track.name = "track 1";
		Region region = track.addRegion();
		region.name = "region 1";
		region = track.addRegion();
		region.name = "region 2";
		SessionUser sessionUser = session.addSessionUser();
		sessionUser.userId = 19;
		sessionUser.role = "owner2";
		session.save();
		Session fromDb =  Session.find("name", "session 1").get();
		assertEquals("session 1", fromDb.name);
		assertEquals("a description", fromDb.description);
					
	}
	
	@Test
	public void addSessionToUser() {
		User user = User.find("byEmail", "a@a.com").first();
		Session sessino = user.createNewSession();
	}
	
	@Test
	public void shareSessionWithUser() {
		User user = User.find("byEmail", "a@a.com").first();
		Session session = new Session("session 1", "a description");
		SessionUser sessionUser = session.addSessionUser();
		sessionUser.userId = 1	;
		sessionUser.role = "owner";
		session.save();
		
		
		List<Session> sessions = user.getSessions();
		assertEquals("session 1", sessions.get(0).name);
	}
}
