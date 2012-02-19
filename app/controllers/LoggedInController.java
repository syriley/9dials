package controllers;

import models.User;
import play.Logger;
import play.mvc.Before;
import play.mvc.Controller;
import play.mvc.With;
import securesocial.provider.SocialUser;
import controllers.securesocial.SecureSocial;

@With( SecureSocial.class )
public class LoggedInController extends Controller {
	private static final String GET = "GET";
	private static final String ROOT = "/";
	private static final String ORIGINAL_URL = "originalUrl";

	private static void rememberOriginalUrl(){
		final String originalUrl = request.method.equals(GET) ? request.url : ROOT;
        flash.put(ORIGINAL_URL, originalUrl);
	}
	
	@Before
    static void setConnectedUser() {    	
		rememberOriginalUrl();
		SocialUser user =  SecureSocial.getCurrentUser();
		if(user!=null && !session.contains("_user")){
			User ouruser = User.find("byEmail", user.email).first();
			if(ouruser!=null){
			   renderArgs.put("_user", ouruser);
			}
		}
    }
}
