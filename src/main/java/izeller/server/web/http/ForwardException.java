package izeller.server.web.http;

import izeller.server.security.model.Cookie;

public class ForwardException extends RuntimeException {

	private static final long serialVersionUID = -5190416722020310354L;
	private String forwardTo;
	private Cookie cookie;

	public ForwardException(String forwardTo, Cookie cookie){
		this.forwardTo = forwardTo;
		this.cookie = cookie;
	}
	
	public Redirect getRedirect(){
		return new Redirect(forwardTo);
	}
	public String getForwardTo(){
		return forwardTo;
	}
	
	public Cookie getCookie(){
		return cookie;
	}
}
