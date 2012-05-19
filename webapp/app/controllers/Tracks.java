package controllers;

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
	     Session seshion = Session.findById(sessionId);
	     JsonObject jsonTrack = JsonUtils.getJsonEObject(body);
	     JsonObject root = JsonUtils.getJsonEObject(seshion.data);
	     Version version = new Version().save();
	     jsonTrack.addProperty("id", version.id);
	     JsonObject mergedSession = JsonUtils.mergeJsonObjects(root, jsonTrack, "tracks");
	     seshion.data = mergedSession.toString();
	     seshion.save();
         renderJSON(mergedSession);
     }
}
