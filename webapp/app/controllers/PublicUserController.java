package controllers;

import models.AUser;
import play.Logger;
import play.mvc.Before;
import play.mvc.Controller;
import play.mvc.With;
import securesocial.provider.SocialUser;
import controllers.securesocial.SecureSocial;
import controllers.securesocial.SecureSocialPublic;

@With( SecureSocialPublic.class )
public class PublicUserController extends UserCheckController {
	
}
