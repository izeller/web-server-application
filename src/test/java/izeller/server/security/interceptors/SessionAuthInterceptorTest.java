package izeller.server.security.interceptors;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import izeller.server.security.NotAuthorizedException;
import izeller.server.security.SecurityService;
import izeller.server.security.model.Session;
import izeller.server.security.model.User;
import izeller.server.web.http.HttpRequest;

public class SessionAuthInterceptorTest {

	@Mock 
	SecurityService securityService;
	@Mock 
	HttpRequest httpRequest;
	@Mock 
	Session session;
	@Mock 
	User user;
	@InjectMocks
	SessionAuthInterceptor sesionAuthIntercepor;
	String sessionId = "test_session_id";
	
	@Before
	public void setUp(){
		MockitoAnnotations.initMocks(this);
		when(httpRequest.getSessionId()).thenReturn(sessionId);
	}
	
	@Test(expected=NotAuthorizedException.class)
	public void if_invalid_session_throw_exception() {
		
		
		when(securityService.isValidSession(sessionId)).thenReturn(false);
		sesionAuthIntercepor.intercept(httpRequest);
	}

	@Test
	public void if_valid_session_update_lastAccess_and_add_user_to_request() {
		
		when(securityService.isValidSession(sessionId)).thenReturn(true);
		when(securityService.getSession(sessionId)).thenReturn(session);
		when(session.getUser()).thenReturn(user);
		
		sesionAuthIntercepor.intercept(httpRequest);
		
		verify(session, times(1)).updateLastAccess();
		verify(httpRequest, times(1)).setPrincipalUser(user);
		
	}
	

}
