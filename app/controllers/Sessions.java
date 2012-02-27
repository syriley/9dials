package controllers;

import java.util.List;

import javax.swing.text.html.HTML.Tag;

import models.Session;
import models.User;
import net.sf.oval.guard.Post;
import play.Logger;
import play.mvc.Controller;
import play.mvc.WebSocketController;
import play.mvc.Http.WebSocketClose;
import play.mvc.Http.WebSocketEvent;
import play.mvc.Http.WebSocketFrame;

public class Sessions extends LoggedInController {
	
	public static void index() {
		List<Session> seshion = Session.findAll();
        render(seshion);
	}
	
	public static void show() {
		render();
	}
	
	public static void form(Long id) {
	 if(id != null) {
	        Session seshion = Session.findById(id);
	        render(seshion);
	    }
		render();
	}
	
	public static void save(Long id, String name, String description) {
		Session session;
		
		if(id == null) {
		    User user = User.find("byEmail", Security.connected()).first();
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
	
	public static void app(long id) {
		Session seshion = Session.findById(id);
		render(seshion);
	}
	
	public static void share(Long sessionId, String access) {
		Session session = Session.findById(sessionId);
		session.access = access;
		session.save();
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

