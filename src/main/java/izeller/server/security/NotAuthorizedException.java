package izeller.server.security;

import izeller.server.security.model.SecurityRouteData.Auth;
import izeller.server.web.http.HttpRequest;

public class NotAuthorizedException extends RuntimeException {

	private static final long serialVersionUID = -192747107200237235L;
	private HttpRequest request;
	private String from;
	private Auth auth;
	
	public NotAuthorizedException(Auth auth, HttpRequest request) {
		this.auth = auth;
		this.request = request;
	}
	
	public NotAuthorizedException(Auth auth, HttpRequest request, String from) {
		this.auth = auth;
		this.request = request;
		this.from = from;
	}

	public Auth getAuth(){
		return auth;
	}
	public String getFrom(){
		if(from!=null){
			return from;
		};
		return request.getPath();
	}
	

}
