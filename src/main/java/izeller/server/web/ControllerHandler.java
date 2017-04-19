package izeller.server.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;

import izeller.server.web.http.HttpRequest;
import izeller.server.web.model.ModelResponse;
import izeller.server.web.route.Route;
import izeller.server.web.route.Route.RequestMethod;
import izeller.server.web.view.View;

public class ControllerHandler {

	private Route route;
	private ModelHandler handler;
	private View view;
	private List<RequestInterceptor> interceptors = new ArrayList<>();
	
	public interface RequestInterceptor{
		public void intercept(HttpRequest request);
	}
	
	public interface ModelHandler{
		public ModelResponse execute(HttpRequest request);
	}

	private ControllerHandler(Route route, ModelHandler handler, 
			List<RequestInterceptor> interceptors, View view){
		this.route = route;
		this.handler = handler;
		this.interceptors = interceptors;
		this.view = view;
	}
	
	public void addInterceptors(List<RequestInterceptor> interceptors){
		this.interceptors.addAll(interceptors);
	}
	
	public View getView(){
		return view;
	}
	public Route getRoute() {
		return route;
	}
	
	public ModelResponse execute(HttpRequest httpRequest) {
		return handler.execute(httpRequest);
	}
	
	public List<RequestInterceptor> getInterceptors() {
		return interceptors;
	}
	
	public boolean isMethodAllowed(RequestMethod requestMethod){
		return route.isMethodAllowed(requestMethod);
	}
	
	public static class Builder{
		
		private Route route;
		private ModelHandler handler;
		private List<RequestInterceptor> interceptors = new ArrayList<>();
		private View view;
		
		public Builder(Route route){
			this.route = route;
		}
		
		public Builder addView(View view){
			this.view = view;
			return this;
		}
			
		public Builder addInterceptors(Collection<RequestInterceptor> interceptor){	
			interceptors.addAll(interceptor);
			return this;
		}
		
		public Builder addHandler(ModelHandler handler){
			this.handler = handler;
			return this;
		}
		
		public ControllerHandler create(){
			return new ControllerHandler(route, handler, interceptors, view);
		}
	}

	public boolean containsView() {
		return view!=null;
	}

	public Matcher getMatcher(String path) {
		return getRoute().getPattern().matcher(path);
	}
	
}
