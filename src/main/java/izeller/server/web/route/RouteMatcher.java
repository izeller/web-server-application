package izeller.server.web.route;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import izeller.server.web.ControllerHandler;
import izeller.server.web.ControllerHandler.RequestInterceptor;

public class RouteMatcher{

	private ControllerHandler routeHandler;
	private List<String> pathParams = new ArrayList<>();
	private String path;
	private boolean matches = false;
	
	public RouteMatcher(ControllerHandler routeHandler, String path){
		this.routeHandler = routeHandler;
		this.path = path;
		Matcher matcher = routeHandler.getMatcher(path);
		matches = matcher.matches();
		if(matches){
			for(int i = 0; i<matcher.groupCount(); i++){
				pathParams.add(matcher.group(i+1));
			}
		}
	}

	public boolean matches(){
		return matches;
	}

	public ControllerHandler getRouteHandler(){
		return routeHandler;
	}

	public List<String> getPathParams(){
		return pathParams;
	}

	public String getPath(){
		return path;
	}

}
