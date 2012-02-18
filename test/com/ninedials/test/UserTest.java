package com.ninedials.test;
import models.OAuthUserHelper;
import models.User;

import org.junit.Test;

import com.google.gson.JsonObject;

public class UserTest extends NineDialsTest {
   
    @Test
    public void createAndRetrieveUser() {
        // Create a new user and save it
        User user = new User("name", "test@gmail.com", "secret", "bio").save();
        
        // Retrieve the user with bob username
        User fromDb = User.find("byEmail", "test@gmail.com").first();
        
        // Test 
        assertNotNull(user);
        assertNotNull(fromDb);
        assertEquals("name", fromDb.name);
    }
    
    @Test
    public void tryConnectAsUser() {        
        // Test 
        assertNotNull(User.connect("s@s.com", "password"));
        assertNull(User.connect("bob@gmail.com", "badpassword"));
        assertNull(User.connect("tom@gmail.com", "secret"));
    }
}
