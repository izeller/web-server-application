package izeller.server.web.route;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import izeller.server.web.route.Route.RequestMethod;

public class RouteMatcherSet {

	private Map<RequestMethod,RouteMatcher> routesMatcher = new HashMap<>();
	
	public RouteMatcherSet(List<RouteMatcher> routesMatcher){
		routesMatcher.stream().forEach(routeMatcher -> add(routeMatcher));
	}

	public void add(RouteMatcher routeMatcher){
		routesMatcher.put(routeMatcher.getControllerHandler().getRoute().getRequestMethod(), routeMatcher);
	}
	
	public boolean matches(){
		return routesMatcher.size()>0;
	}
	
	public boolean isMethodAllowed(RequestMethod requestMethod){
		return routesMatcher.containsKey(requestMethod);
	}
	
	public RouteMatcher get(RequestMethod requestMethod){
		return routesMatcher.get(requestMethod);
	}
}
