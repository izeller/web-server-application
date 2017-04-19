package izeller.server.security;

import java.util.HashMap;
import java.util.Map;

import izeller.server.security.model.Session;

public class SessionRepository {

	private Map<String, Session> sessions = new HashMap<>();
	

	public void addSession(Session session){
		sessions.put(session.getId(), session);
	}

	public void removeSession(String id){
		sessions.remove(id);
	}

	public boolean isValidSession(String id){
		return id!=null && sessions.containsKey(id) && sessions.get(id).isValid();
	}

	public Session getSession(String sessionId){
		return sessions.get(sessionId);
	}


}
