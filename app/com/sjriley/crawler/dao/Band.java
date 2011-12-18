package com.sjriley.crawler.dao;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.Id;

public class Band {
	@Id ObjectId id;
	private String name;
	private Date dateCompleted;
	private String url;
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