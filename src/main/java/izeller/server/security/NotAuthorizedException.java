package izeller.server.security;

import izeller.server.security.model.SecurityRouteData.Auth;

public class NotAuthorizedException extends RuntimeException {

	private static final long serialVersionUID = -192747107200237235L;
	private String from;
	private Auth auth;
	private String path;
	
	public NotAuthorizedException(Auth auth, String path) {
		this.auth = auth;
		this.path = path;
	}
	
	public NotAuthorizedException(Auth auth, String path, String from) {
		this.auth = auth;
		this.path = path;
		this.from = from;
	}

	public Auth getAuth(){
		return auth;
	}
	public String getFrom(){
		if(from!=null){
			return from;
		};
		return path;
	}
	

}
