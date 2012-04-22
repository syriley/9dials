package controllers;

import models.User;
import play.modules.facebook.FbGraph;
import play.modules.facebook.FbGraphException;
import play.mvc.Before;
import play.mvc.Controller;
import play.mvc.Scope.Session;

public class FacebookLoggedInController extends Controller{

	@Before
    public static void checkAccess() throws Throwable {
	        try {
	            User fbUser = FacebookSecurity.getCurrentFbUser();	                     
	            renderArgs.put("fbuser", fbUser); // put the email into the session (for the Secure module)
	        } catch (FbGraphException fbge) {
	            flash.error(fbge.getMessage());
	            if (fbge.getType() != null && fbge.getType().equals("OAuthException")) {
	                Session.current().remove("fbuserid");
	            }
	        }
	}
}
