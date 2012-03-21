package controllers;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import controllers.admin.UserSessions;

import models.AUser;
import models.Session;
import models.UserSession;

public class Sessions extends LoggedInController {
	
    private static JsonParser parser = new JsonParser();
    
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
	
	private static void validate() {
        // Validate
       validation.valid(session);
       if(validation.hasErrors()) {
           render("@form", session);
       }
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
	
	public static void app2(long id) {
        Session seshion = Session.findById(id);
        render(seshion);
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
	
	
	
	public static void addTrack(long sessionId, String track) {
	    Session session = Session.findById(sessionId);
        JsonObject sessionJson = (JsonObject) parser.parse(session.data);
        
        JsonObject trackJson = (JsonObject) parser.parse(track);
        
        JsonArray tracks = sessionJson.getAsJsonArray("tracks");
        tracks.add(trackJson);
        sessionJson.add("tracks", tracks);
        session.data = sessionJson.toString();
        session.save();
        app2(sessionId);
    }
	
	public static void addSource(long sessionId, String source) {
	    Session session = Session.findById(sessionId);
        JsonObject sessionJson = (JsonObject) parser.parse(session.data);
	    
        JsonObject sourceJson = (JsonObject) parser.parse(source);
        JsonArray trackJsonArray = sessionJson.getAsJsonArray("sources");
        trackJsonArray.add(sourceJson);
        sessionJson.add("sources", trackJsonArray);

        session.data = sessionJson.toString();
        session.save();
        app2(sessionId);
    }
	
	public static void addRegion(long sessionId, int trackId, String region) {
	    Session session = Session.findById(sessionId);
        JsonObject sessionJson = (JsonObject) parser.parse(session.data);
        
        JsonObject regionJson = (JsonObject) parser.parse(region);
        JsonArray tracksJson = sessionJson.getAsJsonArray("tracks");
        JsonArray newTracksJson = new JsonArray();
        
        for(int i = 0; i < tracksJson.size(); i++) {
            JsonObject track = tracksJson.get(i).getAsJsonObject();
            
            if(track.get("id").getAsInt() == trackId) {
                JsonArray regionsJson = (JsonArray)track.get("regions");
                if (regionsJson == null) {
                    regionsJson = new JsonArray();
                }
                regionsJson.add(regionJson);
                track.add("regions", regionJson);
            }
            
            newTracksJson.add(track);
        }
        
        sessionJson.add("tracks", newTracksJson);
        
        session.data = sessionJson.toString();
        session.save();
        app2(sessionId);
    }
}

