package models;

import com.google.code.morphia.annotations.Embedded;

@Embedded
public class SessionUser {
	public long userId;
	public String role;
}
