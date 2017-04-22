package izeller.server.web;

import org.apache.log4j.Logger;

import izeller.server.security.NotAuthorizedException;
import izeller.server.security.model.SecurityRouteData.Auth;
import izeller.server.web.http.ForwardException;
import izeller.server.web.http.HttpCode;
import izeller.server.web.http.HttpException;
import izeller.server.web.http.HttpRequest;
import izeller.server.web.http.HttpResponse;
import izeller.server.web.model.ModelResponse;
import izeller.server.web.route.RouteMatcher;
import izeller.server.web.route.RouteMatcherSet;
import izeller.server.web.route.Router;

public class InvokerHandler {

	final static Logger logger = Logger.getLogger(InvokerHandler.class);
	
	private Router router;
	
	public InvokerHandler(Router router){
		this.router = router;
	}

	public void invoke(final HttpRequest request, final HttpResponse response){

		try{
			logger.info("Routing path: "+request.getRequestMethod()+request.getPath());
			RouteMatcherSet routeMatcherCollection = router.get(request.getPath());

			validate(routeMatcherCollection, request);
			RouteMatcher routeMatcher = routeMatcherCollection.get(request.getRequestMethod());
			request.addPathParams(routeMatcher.getPathParams());

			ControllerHandler controllerHandler = routeMatcher.getControllerHandler();
			controllerHandler.getInterceptors().forEach(interceptor -> interceptor.intercept(request));

			ModelResponse modelResponse = controllerHandler.execute(request);
			response.sendReponse(modelResponse, controllerHandler.getView());

		}catch(ForwardException forwardException){
			logger.info(forwardException.getMessage(), forwardException);
			response.addCookie(forwardException.getCookie());
			response.sendRedirect(forwardException.getRedirect());

		}catch(NotAuthorizedException notAuthException){
			logger.info("Not Auth route: "+request.getPath());
			retryAuth(response, notAuthException);
			
		}catch(HttpException httpException){
			logger.info(httpException.getMessage(), httpException);
			response.writeStatusCodeResponse(httpException.getHttpCode());

		}
	}

	private void validate(RouteMatcherSet routeMatcherCollection, HttpRequest request) {
		
		if(!routeMatcherCollection.matches()){
			logger.info("Not found path "+request.getPath());
			throw new HttpException(HttpCode.NOT_FOUND);
		}

		if(!routeMatcherCollection.isMethodAllowed(request.getRequestMethod())){
			logger.info("Method not allowed "+request.getRequestMethod());
			throw new HttpException(HttpCode.METHOD_NOT_ALLOWED);
		}
	}

	private void retryAuth(final HttpResponse response, NotAuthorizedException notAuthException) {
		if(Auth.BASIC.equals(notAuthException.getAuth())){
			response.retryBasicAuth();	
		}else{
			response.retrySessionAuth(notAuthException.getFrom());
		}
	}
}
