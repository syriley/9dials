package controllers;

import models.OAuthUserHelper;
import play.mvc.Controller;
import play.mvc.With;

import com.google.gson.JsonObject;

import controllers.securesocial.SecureSocialPublic;

public class Application extends PublicUserController {
	
    public static void index() {
    	render();
    }
    
    public static void loginRouter(){
    	if(flash.get("newuser")!=null){
    		NewUser.index();
    	}
    	else{
    		Sessions.index();
    	}
    }
    
    public static void fakeLogin(){
    	JsonObject data = new JsonObject();
    	String testName = "Test User";
    	String testEmail = "oauth@test.com";
    	data.addProperty("name", testName);
    	data.addProperty("email", testEmail);
    	OAuthUserHelper.oAuthCallback(data);
    	loginRouter();    	
    }
}