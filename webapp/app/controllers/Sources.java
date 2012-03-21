package controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

import models.Source;
import models.Track;

import org.apache.commons.io.IOUtils;

import play.Logger;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;

public class Sources extends LoggedInController {
    
    public static final String AWS_ACCESS_KEY = "AKIAJXRBBFNYBXNUNBZQ";
    public static final String AWS_SECRET_KEY = "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";
    public static final String BUCKET_NAME = "9dials";
    
	public static void index() {
		render();
	}
	
	public static void upload(File  file) throws FileNotFoundException {
	    AWSCredentials awsCredentials = new BasicAWSCredentials(AWS_ACCESS_KEY, AWS_SECRET_KEY);
	    AmazonS3 s3Client = new AmazonS3Client(awsCredentials);
	    s3Client.createBucket(BUCKET_NAME);
	    String s3Key = UUID.randomUUID().toString();
	    s3Client.putObject(BUCKET_NAME, s3Key, file);
	    Source source = new Source(file.getName(),s3Key);
	    source.save();
	    
	}
	
	public static void uploadLocally(File  file) {
	    
	    FileOutputStream moveTo = null;
	    try {
	        moveTo = new FileOutputStream(new File("/tmp/test.ogg"));
	        FileInputStream fileInputStream = new FileInputStream(file);
            IOUtils.copy(fileInputStream, moveTo);
        } catch (IOException e) {
            Logger.error(e, "local upload error");
        }
	}
	 
	public static void getTrack(long id ){
	    Track track = Track.findById(id);
		response.setContentTypeIfNotSet("audio/ogg");
	    renderBinary(new File(track.audioUrl));
	}
}
