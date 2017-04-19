package izeller.server.web;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import izeller.server.web.ControllerHandler.RequestInterceptor;
import izeller.server.web.http.HttpCode;
import izeller.server.web.http.HttpRequest;
import izeller.server.web.http.HttpResponse;
import izeller.server.web.model.ModelResponse;
import izeller.server.web.route.Route.RequestMethod;
import izeller.server.web.route.RouteMatcher;
import izeller.server.web.route.RouteMatcherSet;
import izeller.server.web.route.Router;
import izeller.server.web.view.View;

public class InvokerHandlerTest {

	@Mock 
	HttpRequest httpRequest;
	@Mock 
	RequestInterceptor requestInterceptor;
	@Mock 
	HttpResponse httpResponse;
	@Mock 
	Router router;
	@Mock 
	RouteMatcherSet routerMatcherSet;
	@Mock 
	RouteMatcher routerMatcher;
	@Mock 
	ControllerHandler controllerHandler;
	@Mock 
	View view;
	@Mock 
	ModelResponse modelResponse;
	@InjectMocks
	InvokerHandler invokerHandler;
	String path = "/p1";
	RequestMethod requestMethod = RequestMethod.GET;
	
	@Before
	public void setUp(){
		MockitoAnnotations.initMocks(this);
		when(router.get(path)).thenReturn(routerMatcherSet);
		when(httpRequest.getPath()).thenReturn(path);
		when(httpRequest.getRequestMethod()).thenReturn(requestMethod);
		when(routerMatcherSet.get(requestMethod)).thenReturn(routerMatcher);
		when(routerMatcher.getControllerHandler()).thenReturn(controllerHandler);
		when(controllerHandler.getInterceptors()).thenReturn(Arrays.asList(requestInterceptor));
		when(controllerHandler.getView()).thenReturn(view);
		when(controllerHandler.execute(httpRequest)).thenReturn(modelResponse);
	}
	
	@Test
	public void if_path_not_found_return_404() {
		
		when(routerMatcherSet.matches()).thenReturn(false);
		invokerHandler.invoke(httpRequest, httpResponse);
		verify(httpResponse, times(1)).writeStatusCodeResponse(HttpCode.NOT_FOUND);
	}

	@Test
	public void if_methd_not_allowed_return_405() {
		
		when(routerMatcherSet.matches()).thenReturn(true);
		when(routerMatcherSet.isMethodAllowed(requestMethod)).thenReturn(false);
		invokerHandler.invoke(httpRequest, httpResponse);
		verify(httpResponse, times(1)).writeStatusCodeResponse(HttpCode.METHOD_NOT_ALLOWED);
	}
	
	@Test
	public void if_valid_path_execute_interceptor_and_controller() {
		
		when(routerMatcherSet.matches()).thenReturn(true);
		when(routerMatcherSet.isMethodAllowed(requestMethod)).thenReturn(true);
		
		invokerHandler.invoke(httpRequest, httpResponse);
		
		verify(requestInterceptor, times(1)).intercept(httpRequest);
		verify(controllerHandler, times(1)).execute(httpRequest);
		verify(httpResponse, times(1)).sendReponse(modelResponse, view);
	}
	
}
