package models;

import play.Logger;
import play.mvc.Scope.Flash;

import com.google.gson.JsonObject;

public class OAuthUserHelper {

	public static void oAuthCallback(JsonObject data){
		String email = data.get("email").getAsString();		
		User user = User.find("byEmail", email).first();
		if(user == null){
			String name = data.get("name").getAsString();
			Logger.info("Creating user for: "+email);
			user = new User();
			user.email=email;
			user.name=name;
			user.save();
			setNewUserFlag();
		}
		else{
			Logger.info("Existing user found for: "+email);
		}
		play.mvc.Scope.Session.current().put("username", user.email);
	}

	private static void setNewUserFlag() {
		Flash.current().put("newuser", "true");		
	}
	
}
