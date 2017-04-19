package izeller.server.web.route;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import izeller.server.web.ControllerHandler;

public class RouteMatcher{

	private ControllerHandler controllerHandler;
	private List<String> pathParams = new ArrayList<>();
	private String path;
	private boolean matches = false;
	
	public RouteMatcher(ControllerHandler controllerHandler, String path){
		this.controllerHandler = controllerHandler;
		this.path = path;
		Matcher matcher = controllerHandler.getMatcher(path);
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

	public ControllerHandler getControllerHandler(){
		return controllerHandler;
	}

	public List<String> getPathParams(){
		return pathParams;
	}

	public String getPath(){
		return path;
	}

}
