package controllers;

import models.User;
import play.modules.facebook.FbGraph;
import play.modules.facebook.FbGraphException;
import play.modules.facebook.Parameter;
import play.mvc.Controller;
import play.mvc.Scope.Session;

import com.google.gson.JsonObject;

public class Wall extends Controller {

    public static void index() {
        render();
    }       
  
	public static void postMessage(String message){
    	try {
			FbGraph.publish("me/feed", Parameter.with("message", "Hello World!").parameters());
		} catch (FbGraphException e) {
			// 
			e.printStackTrace();
		}
    }

}