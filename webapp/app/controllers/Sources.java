package controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import models.Track;

import org.apache.commons.io.IOUtils;

public class Sources extends LoggedInController {
	public static void index() {
		render();
	}
	
	public static void upload(File  file) {
	    
	    FileOutputStream moveTo = null;
	    try {
	        moveTo = new FileOutputStream(new File("/tmp/test.ogg"));
	        FileInputStream fileInputStream = new FileInputStream(file);
	      //  String content = IOUtils.toString(fileInputStream);
            IOUtils.copy(fileInputStream, moveTo);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	}
	 
	public static void getTrack(long id ){
	    Track track = Track.findById(id);
		response.setContentTypeIfNotSet("audio/ogg");
	    renderBinary(new File(track.audioUrl));
	}
}
