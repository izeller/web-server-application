package izeller.server.web.route;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import izeller.server.security.SecurityInterceptorManager;
import izeller.server.security.model.SecurityRouteData;
import izeller.server.web.ControllerHandler;

public class Router {
	
	private List<ControllerHandler> controllerHandler = new ArrayList<>();
	
	private SecurityInterceptorManager securityManager;
	
	public Router(SecurityInterceptorManager securityManager){
		this.securityManager = securityManager;
	}

	public void attach(ControllerHandler handler){
		controllerHandler.add(handler);
	}
	
	public void attach(ControllerHandler routeHandler, SecurityRouteData security){
		routeHandler.addInterceptors(securityManager.getInterceptors(security));
		controllerHandler.add(routeHandler);
	}
	
	public RouteMatcherCollection get(String path){
		
		List<RouteMatcher> routesMatcher = controllerHandler.stream().map(routeHandler -> new RouteMatcher(routeHandler, path))
		.filter(routeMatcher -> routeMatcher.matches()).collect(Collectors.toList());

		return new RouteMatcherCollection(routesMatcher);
	}

}
