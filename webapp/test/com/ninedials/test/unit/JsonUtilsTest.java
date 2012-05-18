package com.ninedials.test.unit;

import models.Session;

import org.junit.Before;
import org.junit.Test;

import util.JsonUtils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


public class JsonUtilsTest extends NineDialsTest{
    
    private JsonParser parser = new JsonParser();
    private Session session;
    private JsonObject root;
    
    @Before
    public void setUp() {
        session = Session.findByName("session1");
        root = (JsonObject)parser.parse(session.data);
    }
    
    @Test
    public void testAddSource() {
        JsonObject source = (JsonObject)parser.parse(" {" +
                "url: \"thisisunique\", " +
                "id: 90," +
                "sample_rate: 48000" +
            "}");
        JsonObject newJson = JsonUtils.mergeJsonObjects(root, source, "sources");
        assertTrue(newJson.toString().contains("thisisunique"));
        assertTrue(newJson.toString().contains("90"));
    }
    
    @Test
    public void testEditSource() {
        JsonObject source = (JsonObject)parser.parse(" {" +
                "url: \"thisisunique\", " +
                "id: 3," +
                "sample_rate: 48000" +
            "}");
        JsonObject newJson = JsonUtils.mergeJsonObjects(root, source, "sources");
        assertTrue(newJson.toString().contains("thisisunique"));
    }
    
    @Test
    public void testAddRegion() {
        JsonObject source = (JsonObject)parser.parse(" {" +
                "url: \"thisisunique\", " +
                "id: 90," +
                "sample_rate: 48000" +
            "}");
        JsonObject newJson = JsonUtils.mergeJsonObjects(root, source, "tracks/3/regions");
        assertTrue(newJson.toString().contains("thisisunique"));
        assertTrue(newJson.toString().contains("90"));
    }
}
