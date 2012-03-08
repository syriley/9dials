package com.ninedials.test.unit;
import models.OAuthUserHelper;
import models.AUser;

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
        AUser fromDb = AUser.find("byEmail", "oauth@test.com").first();
        
        // Test 
        assertNotNull(fromDb);
        assertEquals(testName, fromDb.name);
        assertEquals(testEmail, fromDb.email);
    }

}
