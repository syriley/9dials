package com.sjriley.crawler.dao;

import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;

@Entity
public class StartPath {
	@Id ObjectId id;
}
