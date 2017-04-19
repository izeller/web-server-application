package izeller.server.security;

import java.util.ArrayList;
import java.util.List;

import izeller.server.security.interceptors.AuthInterceptor;
import izeller.server.security.interceptors.BasicAuthInterceptor;
import izeller.server.security.interceptors.RoleInterceptor;
import izeller.server.security.model.SecurityRouteData;
import izeller.server.security.model.SecurityRouteData.Auth;
import izeller.server.web.ControllerHandler.RequestInterceptor;

public class SecurityInterceptorManager {
	
	private SecurityService securityService;

	public SecurityInterceptorManager(SecurityService securityService){
		this.securityService = securityService;
		
	}
	
	public List<RequestInterceptor> getInterceptors(SecurityRouteData security){
		
		List<RequestInterceptor> interceptors = new ArrayList<>();
		
		if(Auth.BASIC.equals(security.getAuth())){
			
			interceptors.add(new BasicAuthInterceptor(securityService));
			
		}else if(Auth.SESSION.equals(security.getAuth())){
			
			interceptors.add(new AuthInterceptor(securityService));
		}
		
		if(security.containsRoles()){
			
			interceptors.add(new RoleInterceptor(security.getRoles()));
		}
		
		return interceptors;
	}

}
