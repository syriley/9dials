package com.ninedials.test.unit;
import java.util.List;

import models.User;
import models.Session;

import org.junit.Test;

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
        assertNull(User.connect("bob@gmail.com", "badpassword"));
        assertNull(User.connect("tom@gmail.com", "secret"));
        assertNotNull(User.connect("s@s.com", "password"));
    }
    
    @Test
    public void getSessions() {        
        // Test 
        User user = User.findByUsername("user1");
        List<Session> sessions = user.getSessions();
        assertEquals("session1", sessions.get(0).name);
    }
    
}
