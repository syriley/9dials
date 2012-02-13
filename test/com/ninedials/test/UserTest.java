package com.ninedials.test;
import org.junit.*;
import java.util.*;
import play.test.*;
import models.*;

public class UserTest extends NineDialsTest {

   
    @Test
    public void createAndRetrieveUser() {
        // Create a new user and save it
        new User("bob@gmail.com", "secret", "Bob").save();
        
        // Retrieve the user with bob username
        User bob = User.find("byEmail", "bob@gmail.com").first();
        
        // Test 
        assertNotNull(bob);
        assertEquals("Bob", bob.name);
    }
    
    @Test
    public void tryConnectAsUser() {        
        // Test 
        assertNotNull(User.connect("s@s.com", "password"));
        assertNull(User.connect("bob@gmail.com", "badpassword"));
        assertNull(User.connect("tom@gmail.com", "secret"));
    }

}
