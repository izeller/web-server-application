package izeller.server.web.route;

import java.util.regex.Pattern;

import izeller.server.security.model.Role;
import izeller.server.security.model.SecurityRouteData.Auth;

public class Route {

	public enum RequestMethod{
		POST,
		GET,
		DELETE;
	}
	
	private String path;
	
	private Pattern pattern; 
	private RequestMethod requestMethod;

	private Auth auth;
	
	public Route(String path, RequestMethod requestMethod){
		this(path, requestMethod, null);
	}
	
	public Route(String path, RequestMethod requestMethod, Auth auth) {
		pattern = Pattern.compile(path);
		this.path = path;
		this.requestMethod = requestMethod;
		this.auth = auth;
	}

	public Pattern getPattern(){
		return pattern;
	}


	public String getPath(){
		return path;
	}
	
	public RequestMethod getRequestMethod(){
		return requestMethod;
	}

	public boolean isAuth() {
		return auth!=null;
	}

	public boolean isMethodAllowed(RequestMethod requestMethod) {
		return this.requestMethod.equals(requestMethod);
	}

	
}
