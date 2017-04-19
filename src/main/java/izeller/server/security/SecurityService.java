package izeller.server.security;

import java.util.Optional;

import izeller.server.security.model.Credentials;
import izeller.server.security.model.Session;
import izeller.server.security.model.User;

public class SecurityService {

	private UserRepository userRepository;
	private SessionRepository sessionRepository;

	public SecurityService(UserRepository userRepository, SessionRepository sesionRepository){
		this.userRepository = userRepository;
		this.sessionRepository = sesionRepository;
	}

	public Optional<User> getAuthenticatedUser(Credentials credentials) {
		User user = userRepository.findBy(credentials.getUsername());
		if(user!=null && user.isSamePassword(credentials.getPassword())){
			return Optional.of(user);
		}
		return Optional.empty();	
	}

	public void addSession(Session newSession) {
		
		sessionRepository.addSession(newSession);
		
	}

	public User findUserBy(String name) {
		return userRepository.findBy(name);
	}

	public boolean isValidSession(String sessionId) {
		return sessionRepository.isValidSession(sessionId);
	}

	public Session getSession(String sessionId) {
		return sessionRepository.getSession(sessionId);
	}
}
