package izeller.server.web.handler;

import izeller.server.security.NotAuthorizedException;
import izeller.server.security.SessionRepository;
import izeller.server.security.model.SecurityRouteData.Auth;
import izeller.server.web.ControllerHandler.ModelHandler;
import izeller.server.web.http.HttpRequest;
import izeller.server.web.model.ModelResponse;

public class RemoveSessionHandler implements ModelHandler {

	private SessionRepository sessionRepository;
	
	public RemoveSessionHandler(SessionRepository sessionRepository){
		this.sessionRepository = sessionRepository;
	}
	@Override
	public ModelResponse execute(HttpRequest request) {
		sessionRepository.removeSession(request.getSessionId());
		throw new NotAuthorizedException(Auth.SESSION, request, request.getQueryId("from"));
	}

}
