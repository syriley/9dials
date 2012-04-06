package controllers;

import models.User;
import play.Logger;
import play.mvc.Before;
import play.mvc.Controller;
import play.mvc.With;
import securesocial.provider.SocialUser;
import controllers.securesocial.SecureSocial;

@With( SecureSocial.class )
public class LoggedInController extends UserCheckController {
}
