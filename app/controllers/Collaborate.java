package controllers;

import java.util.List;

import models.Session;
import models.User;
import models.UserSession;
import play.Logger;

public class Collaborate extends LoggedInController {
	public static void index(Long id) {
		Session seshion = Session.findById(id);
		List<UserSession> sharedUserSessions = UserSession.getSharedUserSessions(id);
		List<User> allUsers = User.findAll();
		for(UserSession userSession : sharedUserSessions) {
			allUsers.remove(userSession.user);
		}
		Logger.debug("Logging works");
		render(seshion, sharedUserSessions, allUsers);
	}
	
	public static void shareWith(Long sessionId, Long userId) {
		Session session = Session.findById(sessionId);
		User user = User.findById(userId);
		session.shareWithUser(user);
		Logger.debug("Loading session id %s", sessionId);
		index(sessionId);
	}
	
	public static void removeUser(Long sessionId, Long userId) {
		UserSession userSession = UserSession.findByUserAndSession(userId, sessionId);
		userSession.delete();
		index(sessionId);
	}
	
	public static void makeUserOwner(Long sessionId, Long userId) {
		index(sessionId);
	}
}