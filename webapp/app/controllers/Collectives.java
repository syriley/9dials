package controllers;

import models.Collective;
import play.mvc.Controller;

public class Collectives extends Controller{
	public static void index() {
		Collective collective = Collective.findById(new Long(1));
		render(collective);
	}
}
