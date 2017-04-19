package izeller.server.security.interceptors;

import izeller.server.security.NotAuthorizedException;
import izeller.server.security.SecurityService;
import izeller.server.security.model.SecurityRouteData.Auth;
import izeller.server.web.ControllerHandler.RequestInterceptor;
import izeller.server.web.http.HttpRequest;
import izeller.server.security.model.Session;

public class AuthInterceptor implements RequestInterceptor{

	private SecurityService securityService;
	
	public AuthInterceptor(SecurityService securityService){
		this.securityService = securityService;
	}
	
	@Override
	public void intercept(HttpRequest request) {
		
		String sessionId = request.getSessionId();
		
		if(securityService.isValidSession(sessionId)){
			Session session = securityService.getSession(sessionId);
			session.updateLastAccess();
			request.setPrincipalUser(session.getUser());
			
		}else{
			
			throw new NotAuthorizedException(Auth.SESSION, request);
		}
		
	}

}
