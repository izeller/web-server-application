package izeller.server.security.interceptors;

import java.util.Optional;

import izeller.server.security.NotAuthorizedException;
import izeller.server.security.SecurityService;
import izeller.server.security.model.Credentials;
import izeller.server.security.model.SecurityRouteData.Auth;
import izeller.server.security.model.User;
import izeller.server.web.ControllerHandler.RequestInterceptor;
import izeller.server.web.http.HttpRequest;

public class BasicAuthInterceptor implements RequestInterceptor{

	private SecurityService securityService;
	
	public BasicAuthInterceptor(SecurityService securityService){
		this.securityService = securityService;
	}
		
	@Override
	public void intercept(HttpRequest request) {
		
		Optional<Credentials> credentialOp = request.getBasicAuthCredentials();
		Credentials credentials = credentialOp.orElseThrow(() -> new NotAuthorizedException(Auth.SESSION, request.getPath()));
		Optional<User> userOpt = securityService.getAuthenticatedUser(credentials);
		User user = userOpt.orElseThrow(() -> new NotAuthorizedException(Auth.SESSION, request.getPath()));
		request.setPrincipalUser(user);

	}

}
