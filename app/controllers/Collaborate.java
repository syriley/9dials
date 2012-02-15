package controllers;

import java.util.List;

import controllers.admin.UserSessions;

import play.db.jpa.JPA;

import models.Session;
import models.User;
import models.UserSession;

public class Collaborate extends LoggedInController {
	public static void index(Long id) {
		Session seshion = Session.findById(id);
		List<UserSession> sharedUserSessions = UserSession.getSharedUserSessions(id);
		List<User> allUsers = User.findAll();
//		for(UserSession userSession : sharedUserSessions) {
//			allUsers.remove(userSession.user);
//		}
		render(seshion, sharedUserSessions, allUsers);
	}
}