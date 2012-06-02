package controllers;

import models.Track;
import models.Session;
import models.Version;
import play.mvc.Controller;
import util.JsonUtils;

import com.google.gson.JsonObject;

public class Tracks extends Controller{
	 public static void index() {
		 render();
	 }
	 
	 public static void create(String body, long sessionId) {
         JsonObject mergedSession = Track.create(body, sessionId); 
         renderJSON(mergedSession);
     }
	 
	 public static void update(String body, long sessionId) {
         Session seshion = Session.findById(sessionId);
         JsonObject mergedSession = JsonUtils.mergeJsonObjects(seshion.data, body, "tracks");
         seshion.data = mergedSession.toString();
         seshion.save();
         renderJSON(mergedSession);
     }
}
