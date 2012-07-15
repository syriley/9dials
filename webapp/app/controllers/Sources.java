package controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import jobs.AudioConverterJob;

import models.Source;
import models.User;

import org.apache.commons.io.IOUtils;

import play.Logger;
import play.Play;

import play.libs.F.Promise;
import play.modules.facebook.FbGraphException;
import play.mvc.Router;
import play.mvc.Router.ActionDefinition;
import util.FileUtils;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;

public class Sources extends LoggedInController{
    
	public static void index() {
		render();
	}

	public static void audio() {
		render();
	}
	
	public static void upload(File file, String name) throws FileNotFoundException, FbGraphException {
	    User user = getCurrentUser();
	    String extension = FileUtils.extension(file);
        String s3key = UUID.randomUUID().toString();
        Source source = null;
	    if(extension.equals("ogg")){
            String mp3Name = s3key+".mp3";
            Promise<File> mp3 = new AudioConverterJob(file, s3key, "mp3").now();
            File mp3File = await(mp3);
            source = Source.uploadToS3(user, mp3File,mp3Name,name);
        }
        else{
            source = Source.uploadToS3(user, file,s3key,name);
        }
       
        renderJSON(source.getDto());
	}

	public static void list() throws FbGraphException{
		User user = getCurrentUser();		
		renderJSON(Source.getDtosByUser(user));
	}
}
