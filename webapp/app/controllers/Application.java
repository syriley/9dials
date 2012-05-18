package controllers;

import java.util.Collection;

import models.MailingListRecipient;
import models.OAuthUserHelper;
import play.Logger;
import play.mvc.Controller;
import play.mvc.With;
import securesocial.provider.ProviderRegistry;
import securesocial.provider.ProviderType;

import com.google.gson.JsonObject;

import controllers.securesocial.SecureSocialPublic;

public class Application extends PublicUserController {
    private static final String ORIGINAL_URL = "originalUrl";

    public static void index() {
        final Collection providers = ProviderRegistry.all();
        flash.keep(ORIGINAL_URL);
        boolean userPassEnabled = ProviderRegistry.get(ProviderType.userpass) != null;
    	render(providers, userPassEnabled);
    }
    
    public static void loginRouter(){
    	if(flash.get("newuser")!=null){
    		NewUser.index();
    	}
    	else{
    		Sessions.index(-1L);
    	}
    }
    
    public static void mailingList(String emailAddress, String requestUrl) {
        new MailingListRecipient(emailAddress).save();
        redirect(requestUrl);
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