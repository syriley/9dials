package com.ninedials.test;

import java.util.List;

import models.Track;

import org.junit.Test;

public class TrackTest extends NineDialsTest{
	@Test
	public void addSessionToUser() {
		List<Track> tracks = Track.getTracks(1L);
		assertEquals(2, tracks.size());
		assertEquals(1, tracks.get(1).regions.size());
		assertEquals(5L, tracks.get(1).regions.get(0).startPosition.longValue());
//		assertEquals("owner", fromDb.userSessions.get(0).role);
	}
}
