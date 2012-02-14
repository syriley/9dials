package controllers;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import models.Session;
import models.User;

import play.mvc.Before;
import play.mvc.Controller;

public class Application extends LoggedInController {
	
    public static void index() {
    	render();
    }
}