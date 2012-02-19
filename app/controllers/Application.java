package controllers;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonObject;

import models.OAuthUserHelper;
import models.Session;
import models.User;

import play.mvc.Before;
import play.mvc.Controller;

public class Application extends Controller {
	
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