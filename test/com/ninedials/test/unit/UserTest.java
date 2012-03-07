package com.ninedials.test.unit;
import models.OAuthUserHelper;
import models.AUser;

import org.junit.Test;

import com.google.gson.JsonObject;

public class UserTest extends NineDialsTest {
   
    @Test
    public void createAndRetrieveUser() {
        // Create a new user and save it
        AUser user = new AUser("name", "test@gmail.com", "secret", "bio").save();
        
        // Retrieve the user with bob username
        AUser fromDb = AUser.find("byEmail", "test@gmail.com").first();
        
        // Test 
        assertNotNull(user);
        assertNotNull(fromDb);
        assertEquals("name", fromDb.name);
    }
    
    @Test
    public void tryConnectAsUser() {        
        // Test 
        assertNull(AUser.connect("bob@gmail.com", "badpassword"));
        assertNull(AUser.connect("tom@gmail.com", "secret"));
        assertNotNull(AUser.connect("s@s.com", "password"));
    }
}
