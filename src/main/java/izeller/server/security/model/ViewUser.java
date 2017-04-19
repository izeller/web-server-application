package izeller.server.security.model;

import java.util.Set;

public class ViewUser {
	
	private String userName;
	private Set<Role> roles;
	
	public ViewUser(User user){
		this.userName = user.getName();
		this.roles = user.getRoles();
	}
	
	public String getUserName(){
		return userName;
	}
	
	public Set<Role> getRoles(){
		return roles;
	}

}
