package izeller.server.security.interceptors;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import izeller.server.security.NotAuthorizedException;
import izeller.server.security.SecurityService;
import izeller.server.security.model.Credentials;
import izeller.server.security.model.User;
import izeller.server.web.http.HttpRequest;

public class BasicAuthInterceptorTest {

	@Mock 
	SecurityService securityService;
	@Mock 
	HttpRequest httpRequest;
	@InjectMocks
	BasicAuthInterceptor basicAuthIntercepor;
	
	@Before
	public void setUp(){
		MockitoAnnotations.initMocks(this);
	}
	
	@Test(expected=NotAuthorizedException.class)
	public void if_no_credentials_throw_exception() {
		
		when(httpRequest.getBasicAuthCredentials()).thenReturn(Optional.empty());
		basicAuthIntercepor.intercept(httpRequest);
	}

	@Test(expected=NotAuthorizedException.class)
	public void if_invalid_credentials_throw_exception() {
		
		Credentials credentials = new Credentials("test", "test");
		when(httpRequest.getBasicAuthCredentials()).thenReturn(Optional.of(credentials));
		when(securityService.getAuthenticatedUser(credentials)).thenReturn(Optional.empty());
		
		basicAuthIntercepor.intercept(httpRequest);
	}
	
	@Test
	public void if_valid_credentials_add_principal_user() {
		
		Credentials credentials = new Credentials("test", "test");
		User user = new User("test", "test");
		when(httpRequest.getBasicAuthCredentials()).thenReturn(Optional.of(credentials));
		when(securityService.getAuthenticatedUser(credentials)).thenReturn(Optional.of(user));
		
		basicAuthIntercepor.intercept(httpRequest);
		
		verify(httpRequest, times(1)).setPrincipalUser(user);
	}
}
