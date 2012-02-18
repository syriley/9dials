package controllers;

import models.Picture;

public class PictureServer extends LoggedInController{

	 public static void getPicture(long id) {
	        Picture picture = Picture.findById(id);
	        response.setContentTypeIfNotSet(picture.image.type());
	        renderBinary(picture.image.get());
	    }
}
