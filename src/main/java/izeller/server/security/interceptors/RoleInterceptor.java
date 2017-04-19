package izeller.server.security.interceptors;

import java.util.Set;

import izeller.server.security.model.Role;
import izeller.server.web.ControllerHandler.RequestInterceptor;
import izeller.server.web.http.HttpCode;
import izeller.server.web.http.HttpException;
import izeller.server.web.http.HttpRequest;

public class RoleInterceptor implements RequestInterceptor {

	private Set<Role> roles;

	public RoleInterceptor(Set<Role> roles) {
		this.roles = roles;
	}

	@Override
	public void intercept(HttpRequest request) {
		
		if(!request.getUser().containSomeRole(roles)){
			throw new HttpException(HttpCode.FORBIDDEN);
		}

	}

}
