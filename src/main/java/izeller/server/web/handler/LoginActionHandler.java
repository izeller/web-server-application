package izeller.server.web.handler;

import java.util.Optional;

import org.apache.log4j.Logger;

import izeller.server.DispatcherHandler;
import izeller.server.security.NotAuthorizedException;
import izeller.server.security.SecurityService;
import izeller.server.security.model.Cookie;
import izeller.server.security.model.Credentials;
import izeller.server.security.model.NewUser;
import izeller.server.security.model.SecurityRouteData.Auth;
import izeller.server.security.model.Session;
import izeller.server.security.model.User;
import izeller.server.web.ControllerHandler.ModelHandler;
import izeller.server.web.http.ForwardException;
import izeller.server.web.http.HttpCode;
import izeller.server.web.http.HttpException;
import izeller.server.web.http.HttpRequest;
import izeller.server.web.model.ModelResponse;

public class LoginActionHandler implements ModelHandler {

	final static Logger logger = Logger.getLogger(LoginActionHandler.class);
	
	private String homePage;
	private SecurityService securityService;

	public LoginActionHandler(SecurityService securityService, String homePage){
		this.securityService = securityService;
		this.homePage = homePage;
	}
	@Override
	public ModelResponse execute(HttpRequest request) {


		Optional<User> userOp = securityService.getAuthenticatedUser(new Credentials(request.getPostId("username"),
																request.getPostId("password")));
		
		String redirect = getRedirect(request);
		User user = userOp.orElseThrow(() -> new NotAuthorizedException(Auth.SESSION, request, redirect));

		Session newSession = new Session(user);
		securityService.addSession(newSession);

		throw new ForwardException(redirect, new Cookie(Session.SESSION_ID, newSession.getId()));
	}

	private String getRedirect(HttpRequest request){
		String redirect = request.getPostId("from");
		if(redirect == null){
			redirect = homePage;
		}
		return redirect;
	}
}
