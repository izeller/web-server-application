package izeller.server.security.interceptors;

import java.util.Base64;
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
		
		Optional<User> userOpt = securityService.getAuthenticatedUser(getCredentials(request));
		User user = userOpt.orElseThrow(() -> new NotAuthorizedException(Auth.SESSION, request));
		request.setPrincipalUser(user);

	}
	
	private Credentials getCredentials(HttpRequest request){
		
		String auth = request.getHeaders().getFirst ("Authorization");

		if (auth == null) {
			throw new NotAuthorizedException(Auth.BASIC, request);
		}
		
		int sp = auth.indexOf (' ');
		if (sp == -1 || !auth.substring(0, sp).equals ("Basic")) {
			throw new NotAuthorizedException(Auth.BASIC, request);
		}
		
		byte[] b = Base64.getDecoder().decode(auth.substring(sp+1));
		String userpass = new String (b);
		int colon = userpass.indexOf (':');
		String name = userpass.substring (0, colon);
		String pass = userpass.substring (colon+1);
		return new Credentials(name, pass);
	}


}
