package izeller.server.security.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.Test;

public class UserTest {

	@Test
	public void user_does_not_contain_any_role(){
		User user = new User("test", "test");
		assertFalse(user.containSomeRole(toSet(Role.PAGE_1)));
	}
	
	@Test
	public void user_contains_role(){
		User user = new User("test", "test").addRole(Role.PAGE_1);
		assertTrue(user.containSomeRole(toSet(Role.PAGE_1)));
	}
	
	@Test
	public void validate_password(){
		User user = new User("test", "test");
		assertTrue(user.isSamePassword("test"));
	}
	
	@Test
	public void invalidate_password(){
		User user = new User("test", "test");
		assertFalse(user.isSamePassword("no_valid"));
	}
	
	private Set<Role> toSet(Role ...a){
		return Arrays.asList(a).stream().collect(Collectors.toSet());
	}
	
	
	
}
