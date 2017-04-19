package izeller.server.security.model;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class User {
	
	private String name;
	private String password;
	
	private Set<Role> roles = new HashSet<>();

	public User(NewUser newUser){
		this(newUser.getName(), newUser.getPassword());
		roles.addAll(newUser.getRoles());
	}
	
	public User(String name, String password){
		this.name = name;
		this.password = hashPassword(password);
	}
	
	public User addRole(Role role){
		roles.add(role);
		return this;
	}
	
	public String getName(){
		return name;
	}
	
	public boolean isSamePassword(String password){
		return this.password.equals(hashPassword(password));
	}
	
	public Set<Role> getRoles(){
		return roles;
	}
	public boolean containsRole(Role role) {
		return roles.contains(role);
	}
	
	private String hashPassword(String password){
		
		try {
			
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(password.getBytes(Charset.forName("UTF-8"))); 
			return new String(md.digest());
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public boolean containSomeRole(Set<Role> roles) {
		
		return roles.stream()                
                .filter(role -> this.roles.contains(role))
                .collect(Collectors.toSet()).size()>0; 
		
	}

}
