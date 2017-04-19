package izeller.server.security.model;

import java.util.Set;

public class NewUser {
	
	private String name;
	private String password;
	private Set<Role> roles;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Set<Role> getRoles() {
		return roles;
	}
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	
	public boolean isValid() {
		return notNull() &&	minimumLength();
	}
	
	private boolean minimumLength() {
		return name.length()>3 && password.length()>3;
	}
	
	private boolean notNull() {
		return name!=null && password!=null;
	}
	

}
