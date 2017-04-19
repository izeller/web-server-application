package izeller.server.security.model;

import java.util.Date;
import java.util.UUID;

public class Session {

	public static String SESSION_ID = "JSESSIONID";
	private static int FIVE_MINUTES_IN_MILLISECONS = 300000;
	private Date lastAccess;
	private String id;
	private User user;
	
	public Session(User user){
		this.id = UUID.randomUUID().toString();
		lastAccess = new Date();
		this.user = user;
	}
	
	public String getId(){
		return id;
	}
	
	public void updateLastAccess(){
		lastAccess = new Date();
	}
	
	public User getUser(){
		return user;
	}
	
	public boolean isValid(){
		return (new Date().getTime()-lastAccess.getTime())<FIVE_MINUTES_IN_MILLISECONS;
	}
	
	
	
}
