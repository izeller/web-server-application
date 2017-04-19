package izeller.server.security.model;

import java.util.HashSet;
import java.util.Set;

public class SecurityRouteData {

	public enum Auth{
		BASIC,
		SESSION;
	}
	
	private Set<Role> roles = new HashSet<>();
	private Auth auth;

	public SecurityRouteData(Auth auth, Role role){
		this.auth = auth;
		this.roles.add(role);
	}

	public SecurityRouteData addRole(Role role){
		this.roles.add(role);
		return this;
	}
	
	public Set<Role> getRoles() {
		return roles;
	}

	public Auth getAuth() {
		return auth;
	}

	public boolean containsRoles() {
		return roles.size()>0;
	}

}
