package controllers;

import java.util.ArrayList;
import java.util.List;

import models.Session;
import models.User;
import models.UserSession;
import play.Play;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Sessions extends LoggedInController {
	
    private static JsonParser parser = new JsonParser();
    
	public static void index(long seshionId) {
	    User ourUser = (User)renderArgs.get("_user");
        List<Session> otherSeshions = Session.findAll();
        String studioUrl = (String)Play.configuration.get("studio.url");
        otherSeshions.removeAll(ourUser.getSessions());
        render(otherSeshions, studioUrl, seshionId);
	}
	
	public static void showJson(long id) {
	    Session seshion = Session.findById(id);
	    JsonObject dto = (JsonObject)parser.parse(seshion.data);
	    JsonElement sessionMeta = toJson(seshion, getSessionExclusionStrategy());
	    
	    dto.add("session", sessionMeta);
        renderJSON(dto.toString());
	}
	
	public static void form(Long id) {
	 if(id != null) {
	        Session seshion = Session.findById(id);

	        List<UserSession> userSessions = UserSession.getSharedUserSessions(seshion);
	        List<User> sharedUsers = new ArrayList<User>();
	        
	        List<User> allOtherUsers = User.findAll();
	        allOtherUsers.removeAll(sharedUsers);
	        
	        render(seshion, userSessions, allOtherUsers);
	    }
		render();
	}
	
	public static void create() {
	    User user = (User)renderArgs.get("_user");
	    Session seshion = new Session().save();
	    user.createSession(seshion);
	    String studioUrl = (String)Play.configuration.get("studio.url");
	    redirect(studioUrl + seshion.id);
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
    }
	
	private static ExclusionStrategy getSessionExclusionStrategy() {
	    return new ExclusionStrategy() {
            
            @Override
            public boolean shouldSkipField(FieldAttributes field) {
                if(field.getName().equals("data")) {
                    return true;
                }
                    
                return false;
            }
            
            @Override
            public boolean shouldSkipClass(Class<?> clazz) {
                return clazz == UserSession.class;
            }
        };
	}
}



