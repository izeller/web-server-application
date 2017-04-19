package izeller.server.security;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import izeller.server.security.model.User;
import izeller.server.security.model.ViewUser;

public class UserRepository {

	private Map<String, User> users = new HashMap<>();

	
	public void save(User user){
		users.put(user.getName(), user);
	}
	
	public void delete(User user){
		users.remove(user.getName());
	}
	
	public User findBy(String name){
		return users.get(name);
	}
	
	public Collection<ViewUser> getUsers(){
		return  users.values().stream().map(user-> new ViewUser(user)).collect(Collectors.toList());
	}
	
	
	public boolean contains(String userId){
		return users.containsKey(userId);
	}

	public User removeUser(String userId) {
		return users.remove(userId);
	}

	public boolean contains(User user) {
		return contains(user.getName());
	}
	
}
