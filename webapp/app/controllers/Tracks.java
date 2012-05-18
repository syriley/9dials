package controllers;

import models.Session;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import play.mvc.Controller;
import util.JsonUtils;

public class Tracks extends Controller{
	 public static void index() {
		 render();
	 }
	 
	 public static void create(String body, long sessionId) {
	     Session seshion = Session.findById(sessionId);
	     JsonObject mergedSession = JsonUtils.mergeJsonObjects(seshion.data, body, "tracks");
         renderJSON(mergedSession);
     }
}
