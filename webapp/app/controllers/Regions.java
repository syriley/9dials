package controllers;

import models.Session;
import models.Version;
import play.mvc.Controller;
import util.JsonUtils;

import com.google.gson.JsonObject;

public class Regions extends Controller{
	 public static void index() {
		 render();
	 }
	 
	 public static void create(String body, long sessionId) {
	     Session seshion = Session.findById(sessionId);
	     JsonObject jsonTrack = JsonUtils.getJsonObject(body);
	     JsonObject root = JsonUtils.getJsonObject(seshion.data);
	     Version version = new Version().save();
	     jsonTrack.addProperty("id", version.id);
	     JsonObject mergedSession = JsonUtils.mergeJsonObjects(root, jsonTrack, "tracks");
	     seshion.data = mergedSession.toString();
	     seshion.save();
         renderJSON(mergedSession);
     }
	 
	 public static void update(String body, long sessionId) {
         Session seshion = Session.findById(sessionId);
         JsonObject region = JsonUtils.getJsonObject(body);
         String path = "tracks/" + seshion.getTrackIdFromRegionId(region.get("id").getAsInt()) + "/regions";
         JsonObject mergedSession = JsonUtils.mergeJsonObjects(seshion.data, body, path);
         seshion.data = mergedSession.toString();
         seshion.save();
         renderJSON(mergedSession);
     }
}
