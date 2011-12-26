package com.sjriley.crawler.dao;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

import org.bson.types.ObjectId;

import play.db.jpa.Model;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;

@Entity public class Band{
	@Id ObjectId id;
	private String name;
	private Date dateCompleted;
	private Date dateStarted;
	private String url;
	
	public Date getDateStarted() {
		return dateStarted;
	}
	public void setDateStarted(Date dateStarted) {
		this.dateStarted = dateStarted;
	}
	
	private int numberOfTabs;
	
	public int getNumberOfTabs() {
		return numberOfTabs;
	}
	public void setNumberOfTabs(int numberOfTabs) {
		this.numberOfTabs = numberOfTabs;
	}
	public ObjectId getId() {
		return id;
	}
	public void setId(ObjectId id) {
		this.id = id;
	}
	public URL getUrl() {
		try {
			return new URL (url);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public void setUrl(URL url) {
		this.url = url.toString();
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getDateCompleted() {
		return dateCompleted;
	}
	public void setDateCompleted(Date dateComplete) {
		this.dateCompleted = dateComplete;
	}
}
