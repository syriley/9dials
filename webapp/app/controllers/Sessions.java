package controllers;

import java.util.ArrayList;
import java.util.List;

import controllers.admin.UserSessions;

import models.AUser;
import models.Session;
import models.UserSession;

public class Sessions extends LoggedInController {
	
	public static void index() {
		List<Session> otherSeshions = Session.findAll();
        AUser ourUser = (AUser)renderArgs.get("_user");
        otherSeshions.removeAll(ourUser.getSessions());
        render(otherSeshions);
	}
	
	public static void show() {
		render();
	}
	
	public static void form(Long id) {
	 if(id != null) {
	        Session seshion = Session.findById(id);

	        List<UserSession> userSessions = UserSession.getSharedUserSessions(seshion);
	        List<AUser> sharedUsers = new ArrayList<AUser>();
	       
	        
	        List<AUser> allOtherUsers = AUser.findAll();
	        allOtherUsers.removeAll(sharedUsers);
	        
	        render(seshion, userSessions, allOtherUsers);
	    }
		render();
	}
	
	public static void save(Long id, String name, String description) {
		Session session;
		
		if(id == null) {
		    AUser user = AUser.find("byEmail", Security.connected()).first();
		    session = new Session(name, description).save();
		  	   
		    validate();
		    // Save
		    user.createSession(session);
		}
		else {
			 validate();
			 session = Session.findById(id);
			 session.name = name;
			 session.description = description;
			 session.save();
		}		
	    index();
	}
	
	public static void saveData(Long id, String data) {
		Session session = Session.findById(id);
		session.data = data;
		session.save();
	}
	
	public static void app(long id) {
		Session seshion = Session.findById(id);
		render(seshion);
	}
	
	public static void appJson(long id) {
		Session seshion = Session.findById(id);
		renderJSON(seshion.data);
	}
	
	public static void shareWithUser(Long sessionId, String username) {
		AUser user = AUser.find("byUsername", username).first();
		Session session = Session.findById(sessionId);
		session.shareWithUser(user);
		form(sessionId);
	}
	
	
	public static void share(Long sessionId, String access) {
		Session session = Session.findById(sessionId);
		session.access = access;
		session.save();
		form(sessionId);
	}
	
	public static void removeUser(Long sessionId, Long userId) {
		UserSession userSesssion = UserSession.findByUserAndSession(userId, sessionId);
		userSesssion.delete();
		form(sessionId);
	}
	
	private static void validate() {
		 // Validate
	    validation.valid(session);
	    if(validation.hasErrors()) {
	        render("@form", session);
	    }
	}
}

