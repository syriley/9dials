package controllers;

import models.User;
import play.modules.facebook.FbGraph;
import play.modules.facebook.FbGraphException;
import play.mvc.Controller;
import play.mvc.Scope.Session;

import com.google.gson.JsonObject;

public class FacebookSecurity extends Controller{

	public static void facebookLogin() {
        try {
            User fbUser = getCurrentFbUser();
            if(fbUser==null){
            	fbUser = createFbUser(FbGraph.getObject("me"));
            }            
            Session.current().put("fbuserid", fbUser.externalId); // put the email into the session (for the Secure module)
        } catch (FbGraphException fbge) {
            flash.error(fbge.getMessage());
            if (fbge.getType() != null && fbge.getType().equals("OAuthException")) {
                Session.current().remove("username");
            }
        }
        Sources.index();
    }
	
    private static User createFbUser(JsonObject profile) {
    	User ouruser  = new User();	
		ouruser.name=profile.get("name").getAsString();
		ouruser.externalProvider="facebook";
		ouruser.externalId=profile.get("id").getAsString();
		ouruser.save();
		return ouruser;
	}

    public static void facebookLogout() {
        Session.current().remove("username");
        FbGraph.destroySession();
        Wall.index();
    }

	public static User getCurrentFbUser() throws FbGraphException {
		JsonObject profile = FbGraph.getObject("me"); // fetch the logged in user
        User fbUser = User.find("byExternalIdAndExternalProvider", profile.get("id").getAsString(),"facebook").first();
        return fbUser;
	}
}
