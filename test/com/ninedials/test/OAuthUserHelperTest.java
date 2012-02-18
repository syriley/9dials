package com.ninedials.test;
import models.OAuthUserHelper;
import models.User;

import org.junit.Test;

import com.google.gson.JsonObject;

public class OAuthUserHelperTest extends NineDialsTest {

    @Test
    public void testOAuthUserCreation() {
    	JsonObject data = new JsonObject();
    	String testName = "OAuth Test";
    	String testEmail = "oauth@test.com";
    	data.addProperty("name", testName);
    	data.addProperty("email", testEmail);
    	OAuthUserHelper.oAuthCallback(data);
        
        // Retrieve the user with bob username
        User fromDb = User.find("byEmail", "oauth@test.com").first();
        
        // Test 
        assertNotNull(fromDb);
        assertEquals(testName, fromDb.name);
        assertEquals(testEmail, fromDb.email);
    }

}
