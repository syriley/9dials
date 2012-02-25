package com.ninedials.test;

import models.Branch;
import models.Session;
import models.User;

import org.junit.Before;
import org.junit.Test;

public class TrackTest extends NineDialsTest{
	
	private User user1;
	private Session session1;
	private Branch branch1;
	
	@Before 
	public void setup() {
		super.setup();
		user1 = User.find("byUsername", "user1").first();
		long session1Id = ((Session)Session.find("byName", "session1").first()).id;
		session1 = user1.getSession(session1Id); 
	}
	
	@Test
	public void addTrack() {
		assertEquals(2, user1.getSession(session1.id).getTracks().size());
		session1.addTrack("guitar");
		User dbUser = User.find("byUsername", "user1").first();
		assertEquals(3, dbUser.getSession(session1.id).getTracks().size());
		assertEquals("guitar", dbUser.getSession(session1.id).getTracks().get(2).name);
	}
	
	@Test
	public void renameTrack2() {
	
		session1.addTrack("guitar");
		User dbUser = User.find("byUsername", "user1").first();
		assertEquals(1, dbUser.getSession(session1.id).getTracks().size());
		assertEquals("guitar", dbUser.getSession(session1.id).getTracks().get(0).name);
	}
	
	@Test
	public void GetTrack() {
		
	}
}
