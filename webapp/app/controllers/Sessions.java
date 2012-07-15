package controllers;

import java.util.ArrayList;
import java.util.List;

import org.bouncycastle.jce.provider.JDKGOST3410Signer.gost3410;

import models.Session;
import models.User;
import models.UserSession;
import play.Play;
import util.JsonUtils;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Sessions extends LoggedInController {
	
    private static JsonParser parser = new JsonParser();
    
	public static void index(long seshionId) {
	    User user = getCurrentUser();
        List<Session> otherSeshions = Session.findAll();
        String studioUrl = (String)Play.configuration.get("studio.url");
        otherSeshions.removeAll(user.getSessions());
        render(otherSeshions, studioUrl, seshionId);
	}
	
	public static void indexJson(long seshionId) {
        User user = getCurrentUser();
        List<Session> seshions = user.getSessions();
        JsonArray jsonSessions = new JsonArray();
        for (Session seshion : seshions) {
            jsonSessions.add(getDto(seshion));
        }

        renderJSON(jsonSessions.toString());
    }
	
	public static void showJson(long id) {
	    Session seshion = Session.findById(id);
        renderJSON(getDto(seshion).toString());
	}
	
	private static JsonObject getDto(Session seshion){
	    JsonObject dto = (JsonObject)parser.parse(seshion.data);
        JsonElement sessionMeta = toJson(seshion, getSessionExclusionStrategy());
        
        dto.add("session", sessionMeta);
        return dto;
	}
	
	public static void create() {
	    User user = (User)renderArgs.get("_user");
	    Session seshion = new Session().save();
	    seshion.data= "{ " +
	            "id:" + seshion.id + "," +
                "version: 0.1," +
                "name: \"Untitled\", " +
                "sample_rate: 48000," +
                "playhead: {" +
                    "position: 0" +
                "}, " +
                "tracks: [" +
                    "{" +
                        "id: 4," +
                        "name: \"Untitled\"," +
                        "mute: 0," + 
                        "solo: 0," +
                        "gain: 8.0," +
                        "pan: 0.5," + 
                        "regions:[]" +
                    "}" +
                "]" +
            "}";
	    seshion.save();
	    user.createSession(seshion);
	    renderJSON(getDto(seshion).toString());
	    
	}
	
    public static void update(Long id, String body) {
        Session seshion = Session.findById(id);
        JsonObject root = JsonUtils.getJsonObject(seshion.data);
        JsonObject newSessionData = JsonUtils.getJsonObject(body);
        JsonObject mergedSession = JsonUtils.mergeJsonObjects(root, newSessionData, "");
        seshion.data = mergedSession.toString();
        seshion.save();
        renderJSON(mergedSession);
    } 
	public static void delete(Long id) {
	    Session session = Session.findById(id);
	    session.enabled = false;
	    session.save();
	    index(session.id);
	}
	
	public static void share(Long sessionId, String access) {
		Session session = Session.findById(sessionId);
		session.access = access;
		session.save();
	}
	
	public static void removeUser(Long sessionId, Long userId) {
		UserSession userSesssion = UserSession.findByUserAndSession(userId, sessionId);
		userSesssion.delete();
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



